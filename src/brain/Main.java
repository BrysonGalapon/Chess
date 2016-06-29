package brain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;

import data_types.Board;
import data_types.Coordinate;
import data_types.Move;
import data_types.Piece;
import data_types.PieceColor;
import engine.Engine;
import gui.GUI;

public class Main {
    private final static int ENGINE_WAIT_TIME = 5000; // milliseconds 
    
    private final static Board board = new Board();
    private static PieceColor playerSide;
    private static GUI gui;
    private static BlockingQueue<Move> moveQueue;
    
    public static void main(String[] args) {
        
        moveQueue = new LinkedBlockingDeque<Move>();
        
        runGameComputer();
        
        if (board.turn().equals(PieceColor.WHITE)) {
            JOptionPane.showMessageDialog(null, PieceColor.BLACK + " wins!");
        } else {
            JOptionPane.showMessageDialog(null, PieceColor.WHITE + " wins!");
        }
    }
    
    /**
     * Play a two-player game
     */
    private static void runGameTwoPlayer() {
        gui = configureGUI(board, moveQueue, PieceColor.WHITE);
        
        while (!board.checkMate()) {
            Move move;
            try {
                move = moveQueue.take();
                board.move(move);
                gui.refreshBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Invalid move, Please try again.");
                continue;
            }
        }
    }
    
    /**
     * Play a game against a computer
     */
    private static void runGameComputer() {
        playerSide = pickSide();
        gui = configureGUI(board, moveQueue, playerSide);
        
        // initial startup
        if (playerSide.equals(PieceColor.WHITE)) {
            JOptionPane.showMessageDialog(null, "You are white. Good luck!");
        } else if (playerSide.equals(PieceColor.BLACK)) {
            JOptionPane.showMessageDialog(null, "You are black. Good luck!");
            
            Move computerMove = getComputerMove(board);
            board.move(computerMove);
            gui.refreshBoard();
        } else {
            throw new RuntimeException("Side to play is not either white or black");
        }
        
        while (!board.checkMate()) {
            Move move;
            try {
                move = moveQueue.take();
                board.move(move);
                gui.refreshBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Invalid move, Please try again.");
                continue;
            }
            
            if (board.checkMate()) {
                break;
            }
            
            // computer turn
            Move computerMove = getComputerMove(board);
            board.move(computerMove);
            gui.refreshBoard();
        }
    }
    
    /**
     * Pick the side (white or black) that the player will play as
     * @return an arbitrary side (which or black) that the player will play as
     */
    public static PieceColor pickSide() {
        Random generator = new Random();
        int picker = generator.nextInt(2);
        
        if (picker == 0) {
            return PieceColor.WHITE;
        }
        
        if (picker == 1) {
            return PieceColor.BLACK;
        }
        
        throw new RuntimeException("May not pick a color other than white or black with nonzero probability");
    }
    
    public static Move getComputerMove(Board board) {
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        int originalHeuristic = heuristic(boardCopy);
        
        if (boardCopy.turn().equals(PieceColor.WHITE)) {
            Map<Move, Integer> bestCaptureMap = maxMoveCaptures(boardCopy, 5);
            Move bestCapture = getArbitrary(bestCaptureMap.keySet());
            int bestCaptureValue = bestCaptureMap.get(bestCapture);
            
            if (bestCaptureValue-originalHeuristic > 0) {
                return bestCapture;
            }
            
            Move bestNormalMove = Move.undefined();
            int bestNormalValue = -1000;
            
            for (Move move : boardCopy.legalMoves()) {
                if (!move.isCapture()) {
                    boardCopy.move(move);
                    Map<Move, Integer> bestResponseMap = minMoveCaptures(boardCopy, 3);
                    Move bestResponseCapture = getArbitrary(bestResponseMap.keySet());
                    int bestResponseValue = bestResponseMap.get(bestResponseCapture);
                    
                    if (bestResponseValue >= bestNormalValue) {
                        bestNormalMove = move;
                        bestNormalValue = bestResponseValue;
                    }
                    
                    boardCopy.takeBackLastMove();
                }
            }
            
            if (bestCapture.isUndefined() || bestNormalValue > bestCaptureValue) {return bestNormalMove;}
            
            return bestCapture;
        } else if (boardCopy.turn().equals(PieceColor.BLACK)) {
            Map<Move, Integer> bestCaptureMap = minMoveCaptures(boardCopy, 5);
            Move bestCapture = getArbitrary(bestCaptureMap.keySet());
            int bestCaptureValue = bestCaptureMap.get(bestCapture);
            
            if (bestCaptureValue-originalHeuristic < 0) {
                return bestCapture;
            }
            
            Move bestNormalMove = Move.undefined();
            int bestNormalValue = 1000;
            
            for (Move move : boardCopy.legalMoves()) {
                if (!move.isCapture()) {
                    boardCopy.move(move);
                    Map<Move, Integer> bestResponseMap = maxMoveCaptures(boardCopy, 3);
                    Move bestResponseCapture = getArbitrary(bestResponseMap.keySet());
                    int bestResponseValue = bestResponseMap.get(bestResponseCapture);
                    
                    if (bestResponseValue <= bestNormalValue) {
                        bestNormalMove = move;
                        bestNormalValue = bestResponseValue;
                    }
                    
                    boardCopy.takeBackLastMove();
                }
            }
            
            if (bestCapture.isUndefined() || bestNormalValue < bestCaptureValue) {return bestNormalMove;}
            
            return bestCapture;
        } else {
            throw new RuntimeException("Board turn is not one of white or black");
        }
    }
    
    public static Map<Move, Integer> maxMoveCaptures(Board board, int levelAnalysis) {
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        Map<Move, Integer> bestCaptureMap = new HashMap<>();
        
        Set<Move> captures = boardCopy.getCaptures();
        
        Move bestCapture = Move.undefined();
        int bestValue = -1000;
        
        if (captures.size() == 0) {
            bestCaptureMap.put(bestCapture, heuristic(boardCopy));
            return bestCaptureMap;
        }
        
        if (levelAnalysis <= 0) {
            for (Move capture : captures) {
                boardCopy.move(capture);
                
                int heuristic = heuristic(boardCopy);
                if (heuristic >= bestValue) {
                    bestCapture = capture;
                    bestValue = heuristic;
                }
                
                boardCopy.takeBackLastMove();
            }
            
            bestCaptureMap.put(bestCapture, bestValue);
            return bestCaptureMap;
        }
        
        for (Move capture : captures) {
            boardCopy.move(capture);
            
            Map<Move, Integer> responseCaptureMap = minMoveCaptures(boardCopy, levelAnalysis-1);
            Move bestResponseCapture = getArbitrary(responseCaptureMap.keySet());
            int bestResponseValue = responseCaptureMap.get(bestResponseCapture);
            
            if (bestResponseValue >= bestValue) {
                bestCapture = capture;
                bestValue = bestResponseValue;
            }
            
            boardCopy.takeBackLastMove();
        }
        
        bestCaptureMap.put(bestCapture, bestValue);
        return bestCaptureMap;
    }
    
    public static Map<Move, Integer> minMoveCaptures(Board board, int levelAnalysis) {
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        Map<Move, Integer> bestCaptureMap = new HashMap<>();
        
        Set<Move> captures = boardCopy.getCaptures();
        
        Move bestCapture = Move.undefined();
        int bestValue = 1000;
        
        if (captures.size() == 0) {
            bestCaptureMap.put(bestCapture, heuristic(boardCopy));
            return bestCaptureMap;
        }
        
        if (levelAnalysis <= 0) {
            for (Move capture : captures) {
                boardCopy.move(capture);
                
                int heuristic = heuristic(boardCopy);
                if (heuristic <= bestValue) {
                    bestCapture = capture;
                    bestValue = heuristic;
                }
                
                boardCopy.takeBackLastMove();
            }
            
            bestCaptureMap.put(bestCapture, bestValue);
            return bestCaptureMap;
        }
        
        for (Move capture : captures) {
            boardCopy.move(capture);
            
            Map<Move, Integer> responseCaptureMap = maxMoveCaptures(boardCopy, levelAnalysis-1);
            Move bestResponseCapture = getArbitrary(responseCaptureMap.keySet());
            int bestResponseValue = responseCaptureMap.get(bestResponseCapture);
            
            if (bestResponseValue <= bestValue) {
                bestCapture = capture;
                bestValue = bestResponseValue;
            }
            
            boardCopy.takeBackLastMove();
        }
        
        bestCaptureMap.put(bestCapture, bestValue);
        return bestCaptureMap;
    }
    
    /**
     * Obtain a heuristic for a board
     * @param board Board to obtain heuristic for
     * @return a heuristic evaluating the board
     */
    public static int heuristic(Board board) {
        int heuristic = 0;
        
        if (board.checkMate()) {
            if (board.turn().equals(PieceColor.WHITE)) {
                return -1000;
            } else if (board.turn().equals(PieceColor.BLACK)) {
                return 1000;
            } else {
                throw new RuntimeException("Board turn is not one of white or black");
            }
        }
        
        Map<Piece, Set<Coordinate>> blackPieces = board.blackPieces();
        Map<Piece, Set<Coordinate>> whitePieces = board.whitePieces();

        for (Piece blackPiece : blackPieces.keySet()) {
            heuristic -= blackPiece.value()*blackPieces.get(blackPiece).size();
        }
        
        for (Piece whitePiece : whitePieces.keySet()) {
            heuristic += whitePiece.value()*whitePieces.get(whitePiece).size();
        }
        
        return heuristic;
    }
    
    /**
     * Configure the GUI
     * @param board board to show on GUI
     * @param moveQueue message passing queue for moves between Controller and Main
     * @param playerSide side of the board that will be oriented to
     * @return a GUI that represents a window configured as described above
     */
    private static GUI configureGUI(Board board, BlockingQueue<Move> moveQueue, PieceColor playerSide) {
        GUI gui = new GUI(board, moveQueue, playerSide);
        return gui;
    }
    
    public static void runEngine(Board board) {
        Thread engineThread = new Thread(new Runnable() {
            public void run() {
                Engine engine = new Engine(board);
                engine.start();
            }
        });
        
        engineThread.start();
    }
    
    /**
     * Retrieve an arbitrary element out of a set
     * @param set Set of elements to remove from
     * @return any element contained in set
     */
    private static <T> T getArbitrary(Set<T> set) {
        Set<T> setCopy = new HashSet<>(set);
        if (set.size() == 0) {
            throw new RuntimeException("Can not remove an arbitrary element out of empty set");
        }
        
        Random generator = new Random();
        int pick = generator.nextInt(set.size());
        
        int i = 0;
        for (T item : setCopy) {
            if (i == pick) {
                return item;
            }
            i++;
        }
        
        throw new RuntimeException("Item must have been picked");
    }
}
