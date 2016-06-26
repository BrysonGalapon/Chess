package brain;

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

            } catch (IllegalArgumentException e) {
                System.out.println("Move invalid. Try again");
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
        return getArbitrary(boardCopy.legalMoves());
    }
    
    /**
     * Obtain a heuristic for a board
     * @param board Board to obtain heuristic for
     * @return a heuristic evaluating the board
     */
    public static int heuristic(Board board) {
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());

        int heuristic = 0;
        
        Map<Piece, Set<Coordinate>> blackPieces = boardCopy.blackPieces();
        Map<Piece, Set<Coordinate>> whitePieces = boardCopy.whitePieces();

        for (Piece blackPiece : blackPieces.keySet()) {
            heuristic -= blackPiece.value()*blackPieces.get(blackPiece).size();
        }
        
        for (Piece whitePiece : whitePieces.keySet()) {
            heuristic += whitePiece.value()*blackPieces.get(whitePiece).size();
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
