package brain;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import data_types.Bishop;
import data_types.Board;
import data_types.Coordinate;
import data_types.Knight;
import data_types.Move;
import data_types.Piece;
import data_types.PieceColor;
import data_types.Queen;
import data_types.Rook;
import data_types.Square;
import engine.Engine;
import gui.GUI;

public class Main {
    private final static int ENGINE_WAIT_TIME = 5000; // milliseconds 
    
    public static void main(String[] args) {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        Random generator = new Random();
        
        int sidePicker = generator.nextInt(2);
        
        while (!board.checkMate()) {
            if (sidePicker == 0) {
                System.out.println("You are white. Good luck!\n");
                sidePicker = 2;
            } else if (sidePicker == 1) {
                System.out.println("You are black. Good luck!\n");
                Move computerMove = getComputerMove(board);
                board.move(computerMove);
                System.out.println("Computer made move= " + computerMove);
                sidePicker = 2;
            }
            
            System.out.println();
            System.out.println(board);
            
            // player turn
            System.out.print("Square to move piece from: ");
            String squareFrom = reader.nextLine();
            
            System.out.print("Square to move piece to: ");
            String squareTo = reader.nextLine();
            System.out.println();
            
            try {
                Move move = Move.createMove(board.getSquare(squareFrom), board.getSquare(squareTo));
                
                board.move(move);
                System.out.println(board);
                System.out.println("You made move= " + move);

            } catch(IllegalArgumentException e) {
                System.out.println("Move invalid. Try again");
            }
            
            
            if (board.checkMate()) {
                break;
            }
            
            // computer turn
            Move computerMove = getComputerMove(board);
            board.move(computerMove);
            System.out.println("Computer made move= " + computerMove);
        }
        
        
        if (board.turn().equals(PieceColor.WHITE)) {
            System.out.println(PieceColor.BLACK + " wins!\n");
        } else {
            System.out.println(PieceColor.WHITE + " wins!\n");
        }

        System.out.println("FINAL BOARD");
        System.out.println(board);

        
//        Board board = new Board();
//        
//        GUI gui = configureGUI(board);
//        runEngine(board);
    }
    
    public static Move getComputerMove(Board board) {
        // obtain a random legal move
//        Random generator = new Random();
//        int randomIndex = generator.nextInt(legalMoves.size());
//        
//        int i = 0;
//        Move computerMOVE = null;
//        for (Move computerMove : legalMoves) {
//            if (i == randomIndex) {
//                System.out.println("Computer played: " + computerMove);
//                computerMOVE = computerMove;
//            }
//            i++;
//        } 
        
        if (board.turn().equals(PieceColor.WHITE)) {
            return maxHeuristicMove(board, 1);
        } else {
            return minHeuristicMove(board, 1);
        }
    }
    
    public static Move maxHeuristicMove(Board board, int levelsUp) {
        System.out.println("Entering level: " + levelsUp);
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn());
        Set<Move> legalMoves = boardCopy.legalMoves();
        Move bestMove = null;
        int bestValue = 0;
        Set<Square> stateOriginal = new HashSet<>();
        Set<Square> stateIntermediate = new HashSet<>();

        for (Move move : legalMoves) {
            for (Coordinate coord : move.coordinatesChanged()) {
                stateOriginal.add(boardCopy.getSquare(coord));
            }
            
            boardCopy.move(move);
            
            if (boardCopy.checkMate()) {
                return move;
            }
            
            if (levelsUp == 0) {
                // chill
            } else {
                Move bestResponse = minHeuristicMove(boardCopy, levelsUp-1);
                for (Coordinate coord : bestResponse.coordinatesChanged()) {
                    stateIntermediate.add(boardCopy.getSquare(coord));
                }
                boardCopy.move(bestResponse);
            }
            
            int boardCopyValue = heuristic(boardCopy);
            if (bestMove == null || boardCopyValue > bestValue) {
                bestMove = move;
                bestValue = boardCopyValue;
            }
            
            if (levelsUp == 0) {
                // chill
            } else {
                boardCopy.setSquareSet(stateIntermediate);
            }
            boardCopy.setSquareSet(stateOriginal);
            
            if (!boardCopy.turn().equals(board.turn())) {
                boardCopy.flipTurn();
            }
            
            if (!boardCopy.getLastMove().equals(board.getLastMove())) {
                boardCopy.setLastMove(board.getLastMove());
            }
            
//            assert board.whitePieces().equals(boardCopy.whitePieces());
//            assert board.blackPieces().equals(boardCopy.blackPieces());
//            assert board.turn().equals(boardCopy.turn());
//            assert board.toString().equals(boardCopy.toString());
//            assert board.legalMoves().equals(boardCopy.legalMoves());
        }
        
        return bestMove;
    }
    
    public static Move minHeuristicMove(Board board, int levelsUp) {
        System.out.println("Entering level: " + levelsUp);
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn());
        Set<Move> legalMoves = boardCopy.legalMoves();
        Move bestMove = null;
        int bestValue = 0;
        Set<Square> stateOriginal = new HashSet<>();
        Set<Square> stateIntermediate = new HashSet<>();

        for (Move move : legalMoves) {
            for (Coordinate coord : move.coordinatesChanged()) {
                stateOriginal.add(boardCopy.getSquare(coord));
            }
            
            boardCopy.move(move);
            
            if (boardCopy.checkMate()) {
                return move;
            }
            
            if (levelsUp == 0) {
                // chill
            } else {
                Move bestResponse = maxHeuristicMove(boardCopy, levelsUp-1);
                for (Coordinate coord : bestResponse.coordinatesChanged()) {
                    stateIntermediate.add(boardCopy.getSquare(coord));
                }
                boardCopy.move(bestResponse);
            }
            
            int boardCopyValue = heuristic(boardCopy);
            if (bestMove == null || boardCopyValue < bestValue) {
                bestMove = move;
                bestValue = boardCopyValue;
            }
            
            if (levelsUp == 0) {
                // chill
            } else {
                boardCopy.setSquareSet(stateIntermediate);
            }
            boardCopy.setSquareSet(stateOriginal);
            
            if (!boardCopy.turn().equals(board.turn())) {
                boardCopy.flipTurn();
            }
            
            if (!boardCopy.getLastMove().equals(board.getLastMove())) {
                boardCopy.setLastMove(board.getLastMove());
            }
            
//            assert board.whitePieces().equals(boardCopy.whitePieces());
//            assert board.blackPieces().equals(boardCopy.blackPieces());
//            assert board.turn().equals(boardCopy.turn());
//            assert board.toString().equals(boardCopy.toString());
//            assert board.legalMoves().equals(boardCopy.legalMoves());
        }
        
        return bestMove;
    }
    
    public static int heuristic(Board board) {
        int total = 0;
        
        if (board.checkMate()) {
            if (board.turn().equals(PieceColor.WHITE)) {
                total -= 1000;
            } else {
                total += 1000;
            }
        }
        
        Map<Piece, Set<Coordinate>> whitePieces = board.whitePieces();
        Map<Piece, Set<Coordinate>> blackPieces = board.blackPieces();
        
        for (Piece whitePiece : whitePieces.keySet()) {
            if (whitePiece.isPawn()) {
                total += whitePieces.get(whitePiece).size()*1;
            } else if (whitePiece instanceof Rook) {
                total += whitePieces.get(whitePiece).size()*5;
            } else if (whitePiece instanceof Bishop) {
                total += whitePieces.get(whitePiece).size()*3;
            } else if (whitePiece instanceof Knight) {
                total += whitePieces.get(whitePiece).size()*3;
            } else if (whitePiece instanceof Queen) {
                total += whitePieces.get(whitePiece).size()*9;
            }
        }
        
        for (Piece blackPiece : blackPieces.keySet()) {
            if (blackPiece.isPawn()) {
                total -= blackPieces.get(blackPiece).size()*1;
            } else if (blackPiece instanceof Rook) {
                total -= blackPieces.get(blackPiece).size()*5;
            } else if (blackPiece instanceof Bishop) {
                total -= blackPieces.get(blackPiece).size()*3;
            } else if (blackPiece instanceof Knight) {
                total -= blackPieces.get(blackPiece).size()*3;
            } else if (blackPiece instanceof Queen) {
                total -= blackPieces.get(blackPiece).size()*9;
            }
        }

        return total;
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
}
