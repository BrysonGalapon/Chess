package brain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import data_types.Board;
import data_types.Coordinate;
import data_types.Move;
import data_types.Piece;
import data_types.PieceColor;
import engine.Engine;
import gui.GUI;

public class Main {
    private final static int ENGINE_WAIT_TIME = 5000; // milliseconds 
    
    public static void main(String[] args) {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        PieceColor playerSide = pickSide();
        
        // initial startup
        if (playerSide.equals(PieceColor.WHITE)) {
            System.out.println("You are white. Good luck!\n");
        } else if (playerSide.equals(PieceColor.BLACK)) {
            System.out.println("You are black. Good luck!\n");
            Move computerMove = getComputerMove(board);
            board.move(computerMove);
            System.out.println("Computer made move: " + computerMove);
        } else {
            reader.close();
            throw new RuntimeException("Side to play is not either white or black");
        }
        
        while (!board.checkMate()) {
            System.out.println();
            System.out.println(board);
            System.out.println("Evaluation: " + heuristic(board));
            
            // player turn
            System.out.print("Square to move piece from: ");
            String squareFrom = reader.nextLine();
            
            System.out.print("Square to move piece to: ");
            String squareTo = reader.nextLine();
            System.out.println();
            
            try {
                Move move;
                
                if (board.isEnPassent(new Coordinate(squareFrom), new Coordinate(squareTo))) {
                    move = Move.enPassent(board.getSquare(squareFrom), board.getSquare(squareTo));
                } else {
                    move = Move.createMove(board.getSquare(squareFrom), board.getSquare(squareTo));
                }
                
                board.move(move);
                System.out.println("You made move: " + move);
                System.out.println(board);
                System.out.println("Evaluation: " + heuristic(board));

            } catch (IllegalArgumentException e) {
                System.out.println("Move invalid. Try again");
                continue;
            }
            
            if (board.checkMate()) {
                break;
            }
            
            // computer turn
            Move computerMove = getComputerMove(board);
            board.move(computerMove);
            System.out.println("Computer made move: " + computerMove);
        }
        
        if (board.turn().equals(PieceColor.WHITE)) {
            System.out.println(PieceColor.BLACK + " wins!\n");
        } else {
            System.out.println(PieceColor.WHITE + " wins!\n");
        }

        reader.close();
        System.out.println("FINAL BOARD");
        System.out.println(board);
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
        
        if (boardCopy.turn().equals(PieceColor.WHITE)) {
            Map<Move, Integer> bestCaptureMap = maxMoveCaptures(boardCopy, 5);
            Move bestCapture = getArbitrary(bestCaptureMap.keySet());
            int bestCaptureValue = bestCaptureMap.get(bestCapture);
            
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
    
    public static GUI configureGUI(Board board) {
        GUI gui = new GUI(board);
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
