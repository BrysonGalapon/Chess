package data_types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for the Board class
 * @author Bryson
 */
public class BoardTest {
    @Test
    public void testBoardInit() {
        Board board = new Board();
        Map<Piece, Set<Coordinate>> whitePieces = board.whitePieces();
        Map<Piece, Set<Coordinate>> blackPieces = board.blackPieces();
        
        assertEquals("Expected 6 different white pieces", 6, whitePieces.size());
        assertEquals("Expected 6 different black pieces", 6, blackPieces.size());
        
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;

        Set<Coordinate> whitePawns = whitePieces.get(Piece.pawn(white, false));
        Set<Coordinate> blackPawns = blackPieces.get(Piece.pawn(black, false));
        Set<Coordinate> whiteRooks = whitePieces.get(Piece.rook(white, false));
        Set<Coordinate> blackRooks = blackPieces.get(Piece.rook(black, false));
        Set<Coordinate> whiteKnights = whitePieces.get(Piece.knight(white, false));
        Set<Coordinate> blackKnights = blackPieces.get(Piece.knight(black, false));
        Set<Coordinate> whiteBishops = whitePieces.get(Piece.bishop(white, false));
        Set<Coordinate> blackBishops = blackPieces.get(Piece.bishop(black, false));
        Set<Coordinate> whiteKing = whitePieces.get(Piece.king(white, false));
        Set<Coordinate> blackKing = blackPieces.get(Piece.king(black, false));
        Set<Coordinate> whiteQueen = whitePieces.get(Piece.queen(white, false));
        Set<Coordinate> blackQueen = blackPieces.get(Piece.queen(black, false));

        assertEquals("Expected 8 white pawns", 8, whitePawns.size());
        assertEquals("Expected 8 black pawns", 8, blackPawns.size());
        assertEquals("Expected 2 white rooks", 2, whiteRooks.size());
        assertEquals("Expected 2 black rooks", 2, blackRooks.size());
        assertEquals("Expected 2 white knights", 2, whiteKnights.size());
        assertEquals("Expected 2 black knights", 2, blackKnights.size());
        assertEquals("Expected 2 white bishops", 2, whiteBishops.size());
        assertEquals("Expected 2 black bishops", 2, blackBishops.size());
        assertEquals("Expected 1 white king", 1, whiteKing.size());
        assertEquals("Expected 1 black king", 1, blackKing.size());
        assertEquals("Expected 1 white queen", 1, whiteQueen.size());
        assertEquals("Expected 1 black queen", 1, blackQueen.size());
        
        // check if all pawns are on their correct rank
        for (Coordinate coord : whitePawns) {
            assertEquals("Expected all white pawns to be on 2nd rank", 1, coord.getY());
        }
        
        for (Coordinate coord : blackPawns) {
            assertEquals("Expected all black pawns to be on 7th rank", 6, coord.getY());
        }
        
        // check if all pieces are in their correct places
        
        for (Coordinate coord : whiteRooks) {
            assertEquals("Expected all white rooks to be on 1st rank", 0, coord.getY());
            assertTrue("Expected all rooks to be on either 1st column or 8th column", coord.getX() == 0 || coord.getX() == 7);
        }
        
        for (Coordinate coord : blackRooks) {
            assertEquals("Expected all black rooks to be on 8th rank", 7, coord.getY());
            assertTrue("Expected all rooks to be on either 1st column or 8th column", coord.getX() == 0 || coord.getX() == 7);
        }
        
        for (Coordinate coord : whiteKnights) {
            assertEquals("Expected all white Knights to be on 1st rank", 0, coord.getY());
            assertTrue("Expected all Knights to be on either 2nd column or 7th column", coord.getX() == 1 || coord.getX() == 6);
        }
        
        for (Coordinate coord : blackKnights) {
            assertEquals("Expected all black Knights to be on 8th rank", 7, coord.getY());
            assertTrue("Expected all Knights to be on either 2nd column or 7th column", coord.getX() == 1 || coord.getX() == 6);
        }
        
        for (Coordinate coord : whiteBishops) {
            assertEquals("Expected all white Bishops to be on 1st rank", 0, coord.getY());
            assertTrue("Expected all Bishops to be on either 3rd column or 6th column", coord.getX() == 2 || coord.getX() == 5);
        }
        
        for (Coordinate coord : blackBishops) {
            assertEquals("Expected all black Bishops to be on 8th rank", 7, coord.getY());
            assertTrue("Expected all Bishops to be on either 3rd column or 6th column", coord.getX() == 2 || coord.getX() == 5);
        }
        
        for (Coordinate coord : whiteKing) {
            assertEquals("Expected white King to be on 1st rank", 0, coord.getY());
            assertTrue("Expected King to be on 5th column", coord.getX() == 4);
        }
        
        for (Coordinate coord : blackKing) {
            assertEquals("Expected black King to be on 8th rank", 7, coord.getY());
            assertTrue("Expected King to be on 5th column", coord.getX() == 4);
        }
        
        for (Coordinate coord : whiteQueen) {
            assertEquals("Expected white Queen to be on 1st rank", 0, coord.getY());
            assertTrue("Expected Queen to be on 4rth column", coord.getX() == 3);
        }
        
        for (Coordinate coord : blackQueen) {
            assertEquals("Expected black Queen to be on 8th rank", 7, coord.getY());
            assertTrue("Expected Queen to be on 4rth column", coord.getX() == 3);
        }
    }
    
    @Test
    public void testBoardConstructor() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("e7"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("g1"), board.getSquare("f3")));
        board.move(Move.createMove(board.getSquare("b8"), board.getSquare("c6")));
        board.move(Move.createMove(board.getSquare("f1"), board.getSquare("c4")));        
        board.move(Move.createMove(board.getSquare("f8"), board.getSquare("c5")));
        board.move(Move.createMove(board.getSquare("c2"), board.getSquare("c3")));
        board.move(Move.createMove(board.getSquare("g8"), board.getSquare("f6")));
        board.move(Move.createMove(board.getSquare("d2"), board.getSquare("d4")));

        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        
        assertEquals("Expected board copy to have same white pieces", board.whitePieces(), boardCopy.whitePieces());
        assertEquals("Expected board copy to have same black pieces", board.blackPieces(), boardCopy.blackPieces());
        assertEquals("Expected board copy to have same turn", board.turn(), boardCopy.turn());
        assertEquals("Expected board copy to have same set of legal moves", board.legalMoves(), boardCopy.legalMoves());
        assertEquals("Expected board copy to look the same as board", board.toString(), boardCopy.toString());
    }
    
    @Test
    public void testPerformance() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        
        Set<Coordinate> unmovedWhiteBishopPlacement = new HashSet<>();
        Set<Coordinate> unmovedWhitePawnPlacement = new HashSet<>();

        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        Set<Coordinate> unmovedBlackQueenPlacement = new HashSet<>();
        Set<Coordinate> unmovedBlackRookPlacement = new HashSet<>();
        Set<Coordinate> unmovedBlackBishopPlacement = new HashSet<>();
        Set<Coordinate> unmovedBlackPawnPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("g1"));
        whiteQueenPlacement.add(new Coordinate("b3"));
        whiteRookPlacement.add(new Coordinate("f1"));
        whiteKnightPlacement.add(new Coordinate("f3"));
        whiteBishopPlacement.add(new Coordinate("f7"));
        whitePawnPlacement.add(new Coordinate("d4"));
        
        unmovedWhiteBishopPlacement.add(new Coordinate("c1"));
        unmovedWhitePawnPlacement.add(new Coordinate("a2"));
        unmovedWhitePawnPlacement.add(new Coordinate("f2"));
        unmovedWhitePawnPlacement.add(new Coordinate("g2"));
        unmovedWhitePawnPlacement.add(new Coordinate("h2"));

        blackKingPlacement.add(new Coordinate("f8"));
        blackKnightPlacement.add(new Coordinate("c6"));
        blackBishopPlacement.add(new Coordinate("a1"));
        
        unmovedBlackQueenPlacement.add(new Coordinate("d8"));
        unmovedBlackRookPlacement.add(new Coordinate("a8"));
        unmovedBlackRookPlacement.add(new Coordinate("h8"));
        unmovedBlackBishopPlacement.add(new Coordinate("c8"));
        unmovedBlackPawnPlacement.add(new Coordinate("a7"));
        unmovedBlackPawnPlacement.add(new Coordinate("b7"));
        unmovedBlackPawnPlacement.add(new Coordinate("c7"));
        unmovedBlackPawnPlacement.add(new Coordinate("d7"));
        unmovedBlackPawnPlacement.add(new Coordinate("g7"));
        unmovedBlackPawnPlacement.add(new Coordinate("h7"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        whitePieces.put(Piece.knight(white, true), whiteKnightPlacement);
        whitePieces.put(Piece.bishop(white, true), whiteBishopPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        
        whitePieces.put(Piece.bishop(white, false), unmovedWhiteBishopPlacement);
        whitePieces.put(Piece.pawn(white, false), unmovedWhitePawnPlacement);
        
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        blackPieces.put(Piece.queen(black, false), unmovedBlackQueenPlacement);
        blackPieces.put(Piece.rook(black, false), unmovedBlackRookPlacement);
        blackPieces.put(Piece.bishop(black, false), unmovedBlackBishopPlacement);
        blackPieces.put(Piece.pawn(black, false), unmovedBlackPawnPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        double constructorStartTime = System.currentTimeMillis();
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        double constructorLength = System.currentTimeMillis() - constructorStartTime;
        
        double blackPiecesStartTime = System.currentTimeMillis();
        board.blackPieces();
        double blackPiecesLength = System.currentTimeMillis() - blackPiecesStartTime;
        
        double whitePiecesStartTime = System.currentTimeMillis();
        board.whitePieces();
        double whitePiecesLength = System.currentTimeMillis() - whitePiecesStartTime;
        
        double createMoveStartTime = System.currentTimeMillis();
        Move move = Move.createMove(board.getSquare("c1"), board.getSquare("g5"));
        double createMoveLength = System.currentTimeMillis() - createMoveStartTime;
        
        double moveStartTime = System.currentTimeMillis();
        board.move(move);
        double moveLength = System.currentTimeMillis() - moveStartTime;
        
        double legalMovesStartTime = System.currentTimeMillis();
        board.legalMoves();
        double legalMovesLength = System.currentTimeMillis() - legalMovesStartTime;
        
        double checkMateStartTime = System.currentTimeMillis();
        board.checkMate();
        double checkMateLength = System.currentTimeMillis() - checkMateStartTime;
        
        Square givenSquare = new Square(new Coordinate("b3"));
        givenSquare.addPiece(Piece.queen(white, true));
        double setSquareStartTime = System.currentTimeMillis();
        board.setSquare(givenSquare);
        double setSquareLength = System.currentTimeMillis() - setSquareStartTime;
        
        System.out.println("Performance Report: (ms)\n");
        System.out.println("Constructor: " + constructorLength);
        System.out.println("Black Pieces: " + blackPiecesLength);
        System.out.println("White Pieces: " + whitePiecesLength);
        System.out.println("Create Move: " + createMoveLength);
        System.out.println("Move: " + moveLength);
        System.out.println("Legal Moves: " + legalMovesLength);
        System.out.println("CheckMate: " + checkMateLength);
        System.out.println("SetSquare: " + setSquareLength);
    }
    
    // Testing Strategy:
    // 
    // move:
    //  - piece is a knight, piece is not a knight
    //  - piece moves 1 square, piece moves farther than 1 square
    //  - move is a capture, move is not a capture
    //  - move results in checkmate, move doesn't result in checkmate
    //  - move blocks a check, move does not block a check
    //  - move is a promotion, move is not a promotion
    //  - move is a castle move, move is not a castle move
    //  - move is an en passent, move is not an en passent
    // 
    // blackPieces:
    //  - number of black pieces is 1, number of black pieces is > 1
    //
    // whitePieces:
    //  - number of white pieces is 1, number of white pieces is > 1
    //
    // turn:
    //  - position has black to move, position has white to move
    //
    // legalMoves:
    //  - #legal moves is 1, #legal moves is > 1
    //  - position is in check, position is not in check
    //  - position has a piece that's pinned, position doesn't have a piece that's pinned
    //  - current player can castle kingside, current player can not castle kingside
    //  - current player can castle queenside, current player can not castle queenside
    //  - current player can capture en passent, current player can not capture en passent
    //  - current player can promote a pawn, current player can not promote a pawn
    // 
    // checkMate:
    //  - position is checkmate, position is not checkmate
    // 
    // getSquare:
    //  - square is on the edge of the board, square is in the center of the board
    // 
    // setSquare:
    //  - square is on the edge of the board, square is in the center of the board
    // 
    // lastMove:
    //  - last move was the first move in the game, last move was not the first move in the game
    //  - last move was white's last move, last move was black's last move
    // 
    // flipTurn:
    //  - turn is white's move, turn is black's move
    // 
    // getChecksAndCaptures:
    //  - no captures exist, multiple captures exist
    //  - no checks exist, multiple checks exist
    //  
    // takeBackLastMove: 
    //  - last move was a castling move, last move was not a castling move
    //  - last move promoted a piece, last move did not promote a piece
    //  - last move was an en passent, last move was not an en passent
    //  - last move captured a piece, last move did not capture a piece
    //  - last move moved a piece normally, last move did not move a piece normally
    // 
    // movesPlayed:
    //  - no moves were played, multiple moves were played
    //  - one was move was played, more than one move was played
    //  - moves played includes castling, moves played does not include castling
    //  - moves played includes en passent, moves played does not include en passent
    //  - moves played includes a promotion, moves played does not include a promotion
    //  
    
    // TODO write tests for isEnPassent
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testMovesPlayedNone() {
        Board board1 = new Board();
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board2 = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected empty move history", 0, board1.movesPlayed().size());
        assertEquals("Expected empty move history", 0, board2.movesPlayed().size());
    }
    
    @Test
    public void testTakeBackLastMoveCaptureAndNormal() {
        Board board = new Board();
        
        Move firstMove = Move.createMove(board.getSquare("b2"), board.getSquare("b4"));
        Board firstExpectedBoard = new Board();
        board.move(firstMove);
        board.takeBackLastMove();
        
        assertEquals("Expected board to return to initial state", firstExpectedBoard, board);
        
        board.move(firstMove);
        
        Move secondMove = Move.createMove(board.getSquare("g8"), board.getSquare("h6"));
        Board secondExpectedBoard = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        board.move(secondMove);
        board.takeBackLastMove();
        assertEquals("Expected board to return to original state", secondExpectedBoard, board);
        
        board.move(secondMove);
        board.move(Move.createMove(board.getSquare("b4"), board.getSquare("b5")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a5")));
        
        Board thirdExpectedBoard = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        
        board.move(Move.enPassent(board.getSquare("b5"), board.getSquare("a6")));
        board.takeBackLastMove();

        assertEquals("Expected board to return to original state", thirdExpectedBoard, board);
    }
    
    @Test
    public void testMovesPlayedOneMove() {
        Board board = new Board();
        
        Move move = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        List<Move> movesPlayed = Arrays.asList(move);
        
        board.move(move);
        
        assertEquals("Expected movesPlayed to handle a single move", movesPlayed, board.movesPlayed());
    }
    
    @Test
    public void testMovesPlayedEnPassent() {
        Board board = new Board();
        
        Move move1 = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move1);
        Move move2 = Move.createMove(board.getSquare("a7"), board.getSquare("a6"));
        board.move(move2);
        Move move3 = Move.createMove(board.getSquare("e4"), board.getSquare("e5"));
        board.move(move3);
        Move move4 = Move.createMove(board.getSquare("d7"), board.getSquare("d5"));
        board.move(move4);
        Move move5 = Move.enPassent(board.getSquare("e5"), board.getSquare("d6"));
        board.move(move5);
        
        List<Move> movesPlayed = Arrays.asList(move1, move2, move3, move4, move5);
        
        assertEquals("Expected sequence of moves played to handle en passents", movesPlayed, board.movesPlayed());
    }
    
    @Test
    public void testMovesPlayedReverse() {
        Board board = new Board();

        Move move1 = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move1);
        Move move2 = Move.createMove(board.getSquare("e7"), board.getSquare("e5"));
        board.move(move2);
        Move move3 = Move.createMove(board.getSquare("g1"), board.getSquare("f3"));
        board.move(move3);
        Move move4 = Move.createMove(board.getSquare("b8"), board.getSquare("c6"));
        board.move(move4);
        Move move5 = Move.createMove(board.getSquare("f1"), board.getSquare("c4"));
        board.move(move5);
        Move move6 = Move.createMove(board.getSquare("f8"), board.getSquare("c5"));
        board.move(move6);
        Move move7 = Move.createMove(board.getSquare("c2"), board.getSquare("c3"));
        board.move(move7);
        Move move8 = Move.createMove(board.getSquare("g8"), board.getSquare("f6"));
        board.move(move8);
        Move move9 = Move.createMove(board.getSquare("d2"), board.getSquare("d4"));
        board.move(move9);
        Move move10 = Move.createMove(board.getSquare("e5"), board.getSquare("d4"));
        board.move(move10);
        Move move11 = Move.createMove(board.getSquare("c3"), board.getSquare("d4"));
        board.move(move11);
        Move move12 = Move.createMove(board.getSquare("c5"), board.getSquare("b4"));
        board.move(move12);
        Move move13 = Move.createMove(board.getSquare("b1"), board.getSquare("c3"));
        board.move(move13);
        Move move14 = Move.createMove(board.getSquare("f6"), board.getSquare("e4"));
        board.move(move14);
        Move move15 = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        board.move(move15);
        
        // take back every move that has been played
        int numMovesPlayed = board.movesPlayed().size();
        for (int i = 0; i < numMovesPlayed; i++) {
            board.takeBackLastMove();
        }
        
        assertEquals("Expected to revert back to original board", new Board(), board);
    }
    
    @Test
    public void testTakeBackLastMovePromotion() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.promote(board.getSquare("f7"), board.getSquare("g8"), Piece.knight(PieceColor.WHITE, true));
        board.move(move);
        
        List<Move> movesPlayed = Arrays.asList(move);
        
        assertEquals("Expected promotion to be included in movesPlayed", movesPlayed, board.movesPlayed());
    }
    
    @Test
    public void testMovesPlayedCastling() {
        Board board = new Board();

        Move move1 = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move1);
        Move move2 = Move.createMove(board.getSquare("e7"), board.getSquare("e5"));
        board.move(move2);
        Move move3 = Move.createMove(board.getSquare("g1"), board.getSquare("f3"));
        board.move(move3);
        Move move4 = Move.createMove(board.getSquare("b8"), board.getSquare("c6"));
        board.move(move4);
        Move move5 = Move.createMove(board.getSquare("f1"), board.getSquare("c4"));
        board.move(move5);
        Move move6 = Move.createMove(board.getSquare("f8"), board.getSquare("c5"));
        board.move(move6);
        Move move7 = Move.createMove(board.getSquare("c2"), board.getSquare("c3"));
        board.move(move7);
        Move move8 = Move.createMove(board.getSquare("g8"), board.getSquare("f6"));
        board.move(move8);
        Move move9 = Move.createMove(board.getSquare("d2"), board.getSquare("d4"));
        board.move(move9);
        Move move10 = Move.createMove(board.getSquare("e5"), board.getSquare("d4"));
        board.move(move10);
        Move move11 = Move.createMove(board.getSquare("c3"), board.getSquare("d4"));
        board.move(move11);
        Move move12 = Move.createMove(board.getSquare("c5"), board.getSquare("b4"));
        board.move(move12);
        Move move13 = Move.createMove(board.getSquare("b1"), board.getSquare("c3"));
        board.move(move13);
        Move move14 = Move.createMove(board.getSquare("f6"), board.getSquare("e4"));
        board.move(move14);
        Move move15 = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        board.move(move15);
        
        List<Move> movesPlayed = Arrays.asList(move1, move2, move3, move4, move5,
                                               move6, move7, move8, move9, move10,
                                               move11, move12, move13, move14, move15);
        
        assertEquals("Expected moves played to include castling", movesPlayed, board.movesPlayed());
    }
    
    @Test
    public void testTakeBackLastMoveCastling() {
        Board board = new Board();

        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("e7"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("g1"), board.getSquare("f3")));
        board.move(Move.createMove(board.getSquare("b8"), board.getSquare("c6")));
        board.move(Move.createMove(board.getSquare("f1"), board.getSquare("c4")));
        board.move(Move.createMove(board.getSquare("f8"), board.getSquare("c5")));
        board.move(Move.createMove(board.getSquare("c2"), board.getSquare("c3")));
        board.move(Move.createMove(board.getSquare("g8"), board.getSquare("f6")));
        board.move(Move.createMove(board.getSquare("d2"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("e5"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("c3"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("c5"), board.getSquare("b4")));
        board.move(Move.createMove(board.getSquare("b1"), board.getSquare("c3")));
        board.move(Move.createMove(board.getSquare("f6"), board.getSquare("e4")));
       
        Board expectedBoard = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());
        
        board.move(Move.createMove(board.getSquare("e1"), board.getSquare("g1")));
        board.takeBackLastMove();

        assertEquals("Expected board to return to original state", expectedBoard, board);
    }
    
    @Test
    public void testGetChecksAndCapturesNoChecksOrCaptures() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackQueenPlacement = new HashSet<>();
        Set<Coordinate> blackRookPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e7"));
        blackKingPlacement.add(new Coordinate("b2"));
        blackBishopPlacement.add(new Coordinate("h3"));
        blackKnightPlacement.add(new Coordinate("a3"));
        blackRookPlacement.add(new Coordinate("a2"));
        blackQueenPlacement.add(new Coordinate("a1"));
        blackPawnPlacement.add(new Coordinate("e3"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.queen(black, true), blackQueenPlacement);
        blackPieces.put(Piece.rook(black, true), blackRookPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected no checks or captures for black", 0, board.getChecksAndCaptures().size());
    }
    
    @Test
    public void testGetChecksAndCapturesOnlyChecks() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("e4"));
        whiteRookPlacement.add(new Coordinate("b2"));
        whiteQueenPlacement.add(new Coordinate("c3"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Set<Move> checksAndCaptures = board.getChecksAndCaptures();
        
        assertEquals("Expected 12 checks and 0 captures for black", 12, checksAndCaptures.size());
    }
    
    @Test
    public void testLegalMovesEnPassentCapture() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackQueenPlacement = new HashSet<>();
        Set<Coordinate> blackRookPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("g7"));
        whitePawnPlacement.add(new Coordinate("b2"));
        blackKingPlacement.add(new Coordinate("h2"));
        blackBishopPlacement.add(new Coordinate("a4"));
        blackKnightPlacement.add(new Coordinate("d4"));
        blackRookPlacement.add(new Coordinate("b8"));
        blackQueenPlacement.add(new Coordinate("a2"));
        blackPawnPlacement.add(new Coordinate("c4"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.pawn(white, false), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.queen(black, true), blackQueenPlacement);
        blackPieces.put(Piece.rook(black, true), blackRookPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);

        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        board.move(Move.createMove(board.getSquare("b2"), board.getSquare("b4")));
        
        assertEquals("Expected 43 legal moves for black, including en passent", 43, board.legalMoves().size());
    }
    
    @Test
    public void testGetChecksAndCapturesOnlyCaptures() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e7"));
        blackKingPlacement.add(new Coordinate("h6"));
        blackBishopPlacement.add(new Coordinate("h3"));
        blackKnightPlacement.add(new Coordinate("b5"));
        whiteRookPlacement.add(new Coordinate("g2"));
        whiteQueenPlacement.add(new Coordinate("c3"));
        whitePawnPlacement.add(new Coordinate("h5"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Set<Move> checksAndCaptures = board.getChecksAndCaptures();
        
        assertEquals("Expected 0 checks and 3 captures for black", 3, checksAndCaptures.size());
        
        Move move1 = Move.createMove(board.getSquare("b5"), board.getSquare("c3"));
        Move move2 = Move.createMove(board.getSquare("h6"), board.getSquare("h5"));
        Move move3 = Move.createMove(board.getSquare("h3"), board.getSquare("g2"));

        assertTrue("Expected knight capture of queen to be a capture", checksAndCaptures.contains(move1));
        assertTrue("Expected king capture of pawn to be a capture", checksAndCaptures.contains(move2));
        assertTrue("Expected bishop capture of rook to be a capture", checksAndCaptures.contains(move3));
    }
    
    @Test
    public void testGetSquareOverloadEdge() {
        Board board = new Board();
        Square squareCoordinate = board.getSquare(new Coordinate(0, 1));
        Square squareNotation = board.getSquare("a2");
        Square squareXY = board.getSquare(0, 1);
        
        assertEquals("Expected equivalent squares to be equivalent", squareCoordinate, squareNotation);
        assertEquals("Expected equivalent squares to be equivalent", squareNotation, squareXY);
        assertEquals("Expected equivalent squares to be equivalent", squareXY, squareCoordinate);
    }
    
    @Test
    public void testGetSquareOverloadCenter() {
        Board board = new Board();
        Square squareCoordinate = board.getSquare(new Coordinate(3, 1));
        Square squareNotation = board.getSquare("d2");
        Square squareXY = board.getSquare(3, 1);
        
        assertEquals("Expected equivalent squares to be equivalent", squareCoordinate, squareNotation);
        assertEquals("Expected equivalent squares to be equivalent", squareNotation, squareXY);
        assertEquals("Expected equivalent squares to be equivalent", squareXY, squareCoordinate);
    }
    
    @Test
    public void testFlipTurn() {
        Board board = new Board();
        board.flipTurn();
        assertEquals("Expected turn to flip to black", PieceColor.BLACK, board.turn());
        board.flipTurn();
        assertEquals("Expected turn to flip to white", PieceColor.WHITE, board.turn());
    }
    
    @Test
    public void testEnPassentWhite() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a6")));
        board.move(Move.createMove(board.getSquare("e4"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("d7"), board.getSquare("d5")));
        Move enPassent = Move.enPassent(board.getSquare("e5"), board.getSquare("d6")); 
        
        assertTrue("Expected en passent to be a legal move in position: \n" + board, board.legalMoves().contains(enPassent));
    
        board.move(Move.createMove(board.getSquare("d2"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("h7"), board.getSquare("h6")));
        
        assertFalse("Expected en passent to not be a legal move in position: \n" + board, board.legalMoves().contains(enPassent));
    }
    
    @Test
    public void testEnPassentWhiteCapture() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a6")));
        board.move(Move.createMove(board.getSquare("e4"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("d7"), board.getSquare("d5")));
        board.move(Move.enPassent(board.getSquare("e5"), board.getSquare("d6")));
        
        assertFalse("Expected pawn on d5 to be taken", board.getSquare("d5").isOccupied());
    }
    
    @Test
    public void testEnPassentBlackapture() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("a2"), board.getSquare("a3")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a5")));
        board.move(Move.createMove(board.getSquare("c2"), board.getSquare("c4")));
        board.move(Move.createMove(board.getSquare("a5"), board.getSquare("a4")));
        board.move(Move.createMove(board.getSquare("b2"), board.getSquare("b4")));
        board.move(Move.enPassent(board.getSquare("a4"), board.getSquare("b3")));
        
        assertFalse("Expected pawn on b4 to be taken", board.getSquare("b4").isOccupied());
    }
    
    @Test
    public void testEnPassentBlack() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("a2"), board.getSquare("a3")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a5")));
        board.move(Move.createMove(board.getSquare("c2"), board.getSquare("c4")));
        board.move(Move.createMove(board.getSquare("a5"), board.getSquare("a4")));
        board.move(Move.createMove(board.getSquare("b2"), board.getSquare("b4")));
        Move enPassent = Move.enPassent(board.getSquare("a4"), board.getSquare("b3")); 
        
        assertTrue("Expected en passent to be a legal move in position: \n" + board, board.legalMoves().contains(enPassent));
    
        board.move(Move.createMove(board.getSquare("b7"), board.getSquare("b6")));
        board.move(Move.createMove(board.getSquare("g1"), board.getSquare("f3")));
        
        assertFalse("Expected en passent to not be a legal move in position: \n" + board, board.legalMoves().contains(enPassent));
    }
    
    @Test
    public void testLegalMovesBlockedPawn() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteBishopPlacement.add(new Coordinate("a6"));
        blackPawnPlacement.add(new Coordinate("a7"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.bishop(white, true), whiteBishopPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        blackPieces.put(Piece.pawn(black, false), blackPawnPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move chessMove = Move.createMove(board.getSquare("a7"), board.getSquare("a5"));
        
        assertFalse("Expected unable for black pawns to jump over pieces", board.legalMoves().contains(chessMove));
    }
    
    @Test
    public void testLegalMovesCastleWhiteKingMoved() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteRookPlacement.add(new Coordinate("h1"));
        whiteRookPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("b2"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, false), whiteRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 24 legal moves for white, including castling", 24, board.legalMoves().size());
    }
    
    @Test
    public void testLegalMovesCastleBlackKingMoved() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackRookPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        blackRookPlacement.add(new Coordinate("a8"));
        blackRookPlacement.add(new Coordinate("h8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.rook(black, false), blackRookPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        for (Move move : board.legalMoves()) {
            assertFalse("Expected no castle moves for moved king", move.isCastle());
        }
    }
    
    @Test
    public void testLegalMovesCastleRookMoved() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteRookPlacement.add(new Coordinate("h1"));
        whiteRookPlacement.add(new Coordinate("a1"));
        
        whitePieces.put(Piece.king(white, false), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 24 legal moves for white, including castling", 24, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        Move castleQueenside = Move.createMove(board.getSquare("e1"), board.getSquare("c1"));

        assertFalse("Expected castling kingside is illegal", board.legalMoves().contains(castleKingside));
        assertFalse("Expected castling queenside is illegal", board.legalMoves().contains(castleQueenside));
    }
    
    @Test
    public void testLegalMovesEnPassent() {
        Board board = new Board();
        
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a5")));
        board.move(Move.createMove(board.getSquare("e4"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("f7"), board.getSquare("f5")));
        
        assertEquals("Expected 31 legal moves for position: " + board, 31, board.legalMoves().size());
        
        board.move(Move.enPassent(board.getSquare("e5"), board.getSquare("f6")));
        board.move(Move.createMove(board.getSquare("a5"), board.getSquare("a4")));
        board.move(Move.createMove(board.getSquare("b2"), board.getSquare("b4")));

        assertEquals("Expected 24 legal moves for this position: " + board, 24, board.legalMoves().size());
    }
    
    @Test
    public void testMovePromotionQueen() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.promote(board.getSquare("f7"), board.getSquare("e8"), Piece.queen(PieceColor.WHITE, true));
        
        board.move(move);
        
        assertTrue("Expected promotion square to be occupied", board.getSquare("e8").isOccupied());
        assertEquals("Expected white queen on e8", "Q", board.getSquare("e8").getPiece().toString());
    }
    
    @Test
    public void testMovePromotionKnight() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.promote(board.getSquare("f7"), board.getSquare("g8"), Piece.knight(PieceColor.WHITE, true));
        
        board.move(move);
        
        assertTrue("Expected promotion square to be occupied", board.getSquare("g8").isOccupied());
        assertEquals("Expected white knight on g8", "N", board.getSquare("g8").getPiece().toString());
    }
    
    @Test
    public void testMovePromotionRook() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.promote(board.getSquare("f7"), board.getSquare("f8"), Piece.rook(PieceColor.WHITE, true));
        
        board.move(move);
        
        assertTrue("Expected promotion square to be occupied", board.getSquare("f8").isOccupied());
        assertEquals("Expected white rook on f8", "R", board.getSquare("f8").getPiece().toString());
    }
    
    @Test
    public void testMovePromotionBishop() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move firstMove = Move.promote(board.getSquare("f7"), board.getSquare("e8"), Piece.queen(PieceColor.WHITE, true));
        board.move(firstMove);
        
        Move secondMove = Move.promote(board.getSquare("a2"), board.getSquare("b1"), Piece.bishop(PieceColor.BLACK, true));
        board.move(secondMove);
        
        assertTrue("Expected promotion square to be occupied", board.getSquare("b1").isOccupied());
        assertEquals("Expected black bishop on b1", "B", board.getSquare("b1").getPiece().toString());
    }
    
    @Test
    public void testLegalMovesPromotion() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whitePawnPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h2"));
        blackKingPlacement.add(new Coordinate("b7"));
        whitePawnPlacement.add(new Coordinate("f7"));
        blackPawnPlacement.add(new Coordinate("a2"));
        whiteKnightPlacement.add(new Coordinate("b1"));
        blackKnightPlacement.add(new Coordinate("g8"));
        blackKnightPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("e8"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, false), whiteKnightPlacement);
        whitePieces.put(Piece.pawn(white, true), whitePawnPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, true), blackPawnPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 20 legal moves for white, including promotions", 20, board.legalMoves().size());
        
        board.flipTurn();
        
        assertEquals("Expected 22 legal moves for black, including promotions", 22, board.legalMoves().size());
    }
    
    @Test
    public void testLegalMovesCastleIntoCheck() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteRookPlacement.add(new Coordinate("h1"));
        whiteRookPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("b2"));
        
        whitePieces.put(Piece.king(white, false), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, false), whiteRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 25 legal moves for white, including castling", 25, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        Move castleQueenside = Move.createMove(board.getSquare("e1"), board.getSquare("c1"));

        assertTrue("Expected castling kingside is legal", board.legalMoves().contains(castleKingside));
        assertFalse("Expected castling queenside is illegal (into check)", board.legalMoves().contains(castleQueenside));
    }
    
    @Test
    public void testLegalMovesCastleThroughCheck() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteRookPlacement.add(new Coordinate("h1"));
        whiteRookPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("c2"));
        
        whitePieces.put(Piece.king(white, false), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, false), whiteRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 24 legal moves for white, including castling", 24, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        Move castleQueenside = Move.createMove(board.getSquare("e1"), board.getSquare("c1"));

        assertTrue("Expected castling kingside is legal", board.legalMoves().contains(castleKingside));
        assertFalse("Expected castling queenside is illegal (through check)", board.legalMoves().contains(castleQueenside));
    }
    
    @Test
    public void testLegalMovesCastleOutOfCheck() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("e1"));
        blackKingPlacement.add(new Coordinate("e8"));
        whiteRookPlacement.add(new Coordinate("h1"));
        whiteRookPlacement.add(new Coordinate("a1"));
        blackBishopPlacement.add(new Coordinate("d2"));
        
        whitePieces.put(Piece.king(white, false), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, false), whiteRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        blackPieces.put(Piece.bishop(black, true), blackBishopPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 5 legal moves for white, including castling", 5, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        Move castleQueenside = Move.createMove(board.getSquare("e1"), board.getSquare("c1"));

        assertFalse("Expected castling kingside is illegal", board.legalMoves().contains(castleKingside));
        assertFalse("Expected castling queenside is illegal", board.legalMoves().contains(castleQueenside));
    }
    
    @Test
    public void testLegalMovesCastleQueenside() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("c1"));
        blackKingPlacement.add(new Coordinate("e8"));
        blackRookPlacement.add(new Coordinate("a8"));
        blackRookPlacement.add(new Coordinate("h8"));
        whiteBishopPlacement.add(new Coordinate("d6"));
        
        whitePieces.put(Piece.king(white, false), whiteKingPlacement);
        blackPieces.put(Piece.rook(black, false), blackRookPlacement);
        blackPieces.put(Piece.king(black, false), blackKingPlacement);
        whitePieces.put(Piece.bishop(white, true), whiteBishopPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected 23 legal moves for black, including castling", 23, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e8"), board.getSquare("g8"));
        Move castleQueenside = Move.createMove(board.getSquare("e8"), board.getSquare("c8"));
        
        assertFalse("Expected castling kingside is illegal", board.legalMoves().contains(castleKingside));
        assertTrue("Expected castling queenside is legal", board.legalMoves().contains(castleQueenside));
    }
    
    @Test
    public void testMoveCastle() {
        Board board = new Board();

        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("e7"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("g1"), board.getSquare("f3")));
        board.move(Move.createMove(board.getSquare("b8"), board.getSquare("c6")));
        board.move(Move.createMove(board.getSquare("f1"), board.getSquare("c4")));
        board.move(Move.createMove(board.getSquare("f8"), board.getSquare("c5")));
        board.move(Move.createMove(board.getSquare("c2"), board.getSquare("c3")));
        board.move(Move.createMove(board.getSquare("g8"), board.getSquare("f6")));
        board.move(Move.createMove(board.getSquare("d2"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("e5"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("c3"), board.getSquare("d4")));
        board.move(Move.createMove(board.getSquare("c5"), board.getSquare("b4")));
        board.move(Move.createMove(board.getSquare("b1"), board.getSquare("c3")));
        board.move(Move.createMove(board.getSquare("f6"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("e1"), board.getSquare("g1")));
        
        String expectedBoard = "r - b q k - - r \n" 
                             + "p p p p - p p p \n"
                             + "- - n - - - - - \n" 
                             + "- - - - - - - - \n" 
                             + "- b B P n - - - \n"
                             + "- - N - - N - - \n"
                             + "P P - - - P P P \n"
                             + "R - B Q - R K - \n";

        assertEquals("Expected castling to be a valid move", expectedBoard, board.toString());
    }
    
    @Test
    public void testLastMoveInit() {
        Board board = new Board();
        Move lastMove = board.getLastMove();
        
        assertTrue("Expected null initial first move", lastMove.isUndefined());
    }
    
    @Test
    public void testLastMoveWhite() {
        Board board = new Board();
        Set<Move> legalMovesWhite = board.legalMoves();
        
        Move firstMove = getArbitrary(legalMovesWhite);
        
        board.move(firstMove);
        
        Move lastMove = board.getLastMove();
        
        assertEquals("Expected last move to be the first move", firstMove, lastMove);
    }
    
    @Test
    public void testLastMoveBlack() {
        Board board = new Board();
        Move firstMove = getArbitrary(board.legalMoves());
        
        board.move(firstMove);
        
        Move secondMove = getArbitrary(board.legalMoves());
        
        board.move(secondMove);
        
        Move lastMove = board.getLastMove();
        
        assertEquals("Expected last move to be the second move", secondMove, lastMove);
    }
    
    @Test
    public void testMoveCapture() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        blackKingPlacement.add(new Coordinate("b1"));
        whiteRookPlacement.add(new Coordinate("c1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.createMove(board.getSquare("b1"), board.getSquare("c1"));
        
        board.move(move);
        
        assertEquals("Expected king to take on c1", Piece.king(black, true), board.getSquare("c1").getPiece());
        assertFalse("Expected king to leave c1", board.getSquare("b1").isOccupied());
    }
    
    @Test
    public void testMoveCheckMate() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        blackKingPlacement.add(new Coordinate("b1"));
        whiteRookPlacement.add(new Coordinate("h2"));
        whiteRookPlacement.add(new Coordinate("g3"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.rook(white, true), whiteRookPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.createMove(board.getSquare("g3"), board.getSquare("g1"));
        
        board.move(move);
        
        assertTrue("Expected position to be checkmate", board.checkMate());
    }
    
    @Test
    public void testMoveBlockCheck() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteKnightPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        whiteKnightPlacement.add(new Coordinate("e8"));
        blackBishopPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.knight(white, true), whiteKnightPlacement);
        blackPieces.put(Piece.bishop(black, false), blackBishopPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.createMove(board.getSquare("e8"), board.getSquare("g7"));
        
        board.move(move);
        
        assertEquals("Expected Knight to move to g7 to block check", Piece.knight(white, true), board.getSquare("g7").getPiece());
    }
    
    @Test
    public void testMoveOneSquare() {
        Board board = new Board();
        PieceColor white = PieceColor.WHITE;
        
        Move move = Move.createMove(board.getSquare("d2"), board.getSquare("d3"));
        
        board.move(move);
        
        assertEquals("Expected pawn move legal", Piece.pawn(white, true), board.getSquare("d3").getPiece());
    }
    
    @Test
    public void testMoveMultipleSquares() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackRookPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        blackRookPlacement.add(new Coordinate("g7"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.rook(black, true), blackRookPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Move move = Move.createMove(board.getSquare("g7"), board.getSquare("a7"));
        
        board.move(move);
        
        assertFalse("Expected rook to leave square", board.getSquare("g7").isOccupied());
        assertEquals("Expected rook to visit a7", Piece.rook(black, true), board.getSquare("a7").getPiece());
    }
    
    @Test
    public void testPawns() {
        Board board = new Board();
        
        Piece piece = board.getSquare("e2").getPiece();
        
        assertTrue("Expected a pawn on e2", piece.isPawn());
        
        Pawn unmovedPawn = (Pawn) piece;
        
        assertFalse("Expected unmoved pawn to be unmoved", unmovedPawn.hasMoved());
        
        Move firstMove = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        
        board.move(firstMove);
        
        Piece movedPiece = board.getSquare("e4").getPiece();
        
        assertTrue("Expected a pawn on e4", movedPiece.isPawn());
        
        Pawn movedPawn = (Pawn) movedPiece;
        
        assertTrue("Expected pawn moved", movedPawn.hasMoved());
    }
    
    @Test 
    public void testMoveKnight() {
        PieceColor white = PieceColor.WHITE;
        Piece unmovedKnight = Piece.knight(white, false);
        Piece movedKnight = Piece.knight(white, true);
        
        Board board = new Board();
        
        Square squareFrom = board.getSquare("b1");
        Square squareTo = board.getSquare("c3");
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        board.move(move);
        
        Set<Coordinate> whiteUnmovedKnightPlacement = new HashSet<Coordinate>();
        whiteUnmovedKnightPlacement.add(new Coordinate("g1"));
        
        Set<Coordinate> whiteMovedKnightPlacement = new HashSet<Coordinate>();
        whiteMovedKnightPlacement.add(new Coordinate("c3"));
        
        assertEquals("Expected Knight to move from g1 to c3", whiteUnmovedKnightPlacement, board.whitePieces().get(unmovedKnight));
        assertEquals("Expected Knight to move from g1 to c3", whiteMovedKnightPlacement, board.whitePieces().get(movedKnight));
        assertTrue("Expected knight on c3 to have moved", board.getSquare("c3").getPiece().hasMoved());
    }
    
    @Test
    public void testGetSquareEdge() {
        Board board = new Board();
        PieceColor color = PieceColor.WHITE;
        Square square = board.getSquare("a1");
        
        assertEquals("Expected rook on a1", Piece.rook(color, false), square.getPiece());
    }
    
    @Test
    public void testGetSquareCenter() {
        Board board = new Board();
        PieceColor color = PieceColor.BLACK;
        Square square1 = board.getSquare("f7");
        Square square2 = board.getSquare("d4");
        
        assertEquals("Expected pawn on f7", Piece.pawn(color, false), square1.getPiece());
        assertFalse("Expected that d4 square is empty on init", square2.isOccupied());
    }
    
    @Test
    public void testSetSquareEdge() {
        Board board = new Board();
        PieceColor color = PieceColor.WHITE;
        Square square = board.getSquare("a1");
        
        Piece queen = Piece.queen(color, true);
        
        square.removePiece();
        square.addPiece(queen);
        
        board.setSquare(square);
        
        assertEquals("Expected queen on a1", queen, square.getPiece());
    }
    
    @Test
    public void testSetSquareCenter() {
        Board board = new Board();
        PieceColor color = PieceColor.WHITE;
        Square square = board.getSquare("d4");
        
        Piece queen = Piece.queen(color, true);
        
        square.addPiece(queen);
        
        board.setSquare(square);
        
        assertEquals("Expected queen on d4", queen, square.getPiece());
    }
    
    @Test
    public void testPieces() {
        Board board = new Board();
        
        Map<Piece, Set<Coordinate>> whitePieces = board.whitePieces();
        Map<Piece, Set<Coordinate>> blackPieces = board.blackPieces();

        for (Piece whitePiece : whitePieces.keySet()) {
            assertEquals("Expected all white pieces to be white", PieceColor.WHITE, whitePiece.color());
        }
        
        for (Piece blackPiece : blackPieces.keySet()) {
            assertEquals("Expected all black pieces to be black", PieceColor.BLACK, blackPiece.color());
        }
    }
    
    @Test
    public void testWhitePiecesOnePiece() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackQueenPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        blackQueenPlacement.add(new Coordinate("g7"));
        blackBishopPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.bishop(black, false), blackBishopPlacement);
        blackPieces.put(Piece.queen(black, true), blackQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected correct retrieval of 1 white piece", whitePieces, board.whitePieces());
        assertEquals("Expected correct retrieval of multiple black pieces", blackPieces, board.blackPieces());
    }
    
    @Test
    public void testBlackPiecesOnePiece() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        whiteQueenPlacement.add(new Coordinate("g7"));
        whiteBishopPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.bishop(white, false), whiteBishopPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected correct retrieval of 1 black piece", blackPieces, board.blackPieces());
        assertEquals("Expected correct retrieval of multiple white pieces", whitePieces, board.whitePieces());
    }
    
    @Test
    public void testTurn() {
        Board board = new Board();
        
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        assertEquals("Expected initial turn to be white", white, board.turn());
        
        Square squareFrom1 = board.getSquare("b1");
        Square squareTo1 = board.getSquare("c3");
        Move move1 = Move.createMove(squareFrom1, squareTo1);
        
        board.move(move1);
        
        assertEquals("Expected 2nd turn to be black", black, board.turn());
        
        Square squareFrom2 = board.getSquare("f7");
        Square squareTo2 = board.getSquare("f5");
        Move move2 = Move.createMove(squareFrom2, squareTo2);
        
        board.move(move2);
        
        assertEquals("Expected 3rd turn to be white", white, board.turn());
    }
    
    @Test
    public void testCheckMateTrue() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> blackQueenPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        blackQueenPlacement.add(new Coordinate("g7"));
        blackBishopPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.bishop(black, false), blackBishopPlacement);
        blackPieces.put(Piece.queen(black, true), blackQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.WHITE;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertTrue("Expected this position to be checkmate for white", board.checkMate());
    }
    
    @Test
    public void testCheckMateFalse() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        whiteQueenPlacement.add(new Coordinate("g7"));
        whiteBishopPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("b1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        blackPieces.put(Piece.bishop(white, false), whiteBishopPlacement);
        blackPieces.put(Piece.queen(white, true), whiteQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertFalse("Expected no checkmate for black", board.checkMate());
    }
    
    @Test
    public void testLegalMovesOne() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h8"));
        whiteQueenPlacement.add(new Coordinate("b1"));
        blackKingPlacement.add(new Coordinate("a1"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected only 1 legal move", 1, board.legalMoves().size());
        
        Move move = Move.createMove(board.getSquare("a1"), board.getSquare("b1"));
    
        assertTrue("Expected only move to be to capture queen", board.legalMoves().contains(move));
    }
    
    @Test
    public void testLegalMovesBiggerThanOne() {
        Board board = new Board();
        Set<Move> movesForWhite = board.legalMoves();
        
        assertEquals("Expected 20 initial moves for white", 20, movesForWhite.size());
        
        for (Move move : movesForWhite) { // only one move to be played
            board.move(move);
            break;
        }
        
        Set<Move> movesForBlack = board.legalMoves();
        
        assertEquals("Expected 20 initial moves for black", 20, movesForBlack.size());
    }
    
    @Test
    public void testLegalMovesIntoPin() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteBishopPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackQueenPlacement = new HashSet<>();
        Set<Coordinate> blackBishopPlacement = new HashSet<>();
        Set<Coordinate> blackPawnPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h1"));
        whiteBishopPlacement.add(new Coordinate("b2"));
        blackKingPlacement.add(new Coordinate("h8"));
        blackQueenPlacement.add(new Coordinate("c7"));
        blackBishopPlacement.add(new Coordinate("g8"));
        blackPawnPlacement.add(new Coordinate("h7"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.bishop(white, false), whiteBishopPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.queen(black, true), blackQueenPlacement);
        blackPieces.put(Piece.bishop(black, false), blackBishopPlacement);
        blackPieces.put(Piece.pawn(black, false), blackPawnPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        Set<Move> legalMoves = board.legalMoves();

        assertEquals("Expected 3 ways to block with queen", 3, legalMoves.size());
        
        Move move1 = Move.createMove(board.getSquare("c7"), board.getSquare("g7"));
        Move move2 = Move.createMove(board.getSquare("c7"), board.getSquare("e5"));
        Move move3 = Move.createMove(board.getSquare("c7"), board.getSquare("c3"));
        
        Set<Move> expectedLegalMoves = new HashSet<>();
        expectedLegalMoves.add(move1);
        expectedLegalMoves.add(move2);
        expectedLegalMoves.add(move3);
        
        assertEquals("Expected 3 ways to block with queen", expectedLegalMoves, legalMoves);
    }
    
    @Test
    public void testPawnCapture() {
        Board board = new Board();
        
        Move move1 = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move1);
        Move move2 = Move.createMove(board.getSquare("d7"), board.getSquare("d5"));
        board.move(move2);
        Move move3 = Move.createMove(board.getSquare("e4"), board.getSquare("d5"));
        board.move(move3);
        
        String expectedString = "r n b q k b n r \n" +
                                "p p p - p p p p \n" +
                                "- - - - - - - - \n" +
                                "- - - P - - - - \n" +
                                "- - - - - - - - \n" + 
                                "- - - - - - - - \n" + 
                                "P P P P - P P P \n" + 
                                "R N B Q K B N R \n";
        
        assertEquals("Expected pawn captures to be allowable", expectedString, board.toString());
    }
    
    @Test
    public void testLegalMovesPinned() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        Set<Coordinate> whiteKingPlacement = new HashSet<>();
        Set<Coordinate> whiteQueenPlacement = new HashSet<>();
        Set<Coordinate> blackKingPlacement = new HashSet<>();
        Set<Coordinate> blackKnightPlacement = new HashSet<>();
        
        whiteKingPlacement.add(new Coordinate("h1"));
        whiteQueenPlacement.add(new Coordinate("a1"));
        blackKingPlacement.add(new Coordinate("h8"));
        blackKnightPlacement.add(new Coordinate("g7"));
        
        whitePieces.put(Piece.king(white, true), whiteKingPlacement);
        whitePieces.put(Piece.queen(white, true), whiteQueenPlacement);
        blackPieces.put(Piece.king(black, true), blackKingPlacement);
        blackPieces.put(Piece.knight(black, true), blackKnightPlacement);
        
        PieceColor turn = PieceColor.BLACK;
        
        Board board = new Board(whitePieces, blackPieces, turn, Move.undefined());
        
        assertEquals("Expected only 2 legal moves for black", 2, board.legalMoves().size());
        
        Move move1 = Move.createMove(board.getSquare("h8"), board.getSquare("h7"));
        Move move2 = Move.createMove(board.getSquare("h8"), board.getSquare("g8"));

        assertTrue("Expected Kh7 legal move", board.legalMoves().contains(move1));
        assertTrue("Expected Kg8 legal move", board.legalMoves().contains(move2));
    }
    
    @Test
    public void testToStringInit() {
        Board board = new Board();
        String expectedString = "r n b q k b n r \n" +
                                "p p p p p p p p \n" +
                                "- - - - - - - - \n" +
                                "- - - - - - - - \n" +
                                "- - - - - - - - \n" + 
                                "- - - - - - - - \n" + 
                                "P P P P P P P P \n" + 
                                "R N B Q K B N R \n";
        
        assertEquals(expectedString, board.toString());
    }
    
    @Test
    public void testToStringMove() {
        Board board = new Board();
        Move move = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move);
        
        String expectedString = "r n b q k b n r \n" +
                                "p p p p p p p p \n" +
                                "- - - - - - - - \n" +
                                "- - - - - - - - \n" +
                                "- - - - P - - - \n" + 
                                "- - - - - - - - \n" + 
                                "P P P P - P P P \n" + 
                                "R N B Q K B N R \n";
        
        assertEquals(expectedString, board.toString());

    }
    
    /**
     * Retrieve an arbitrary element out of a set
     * @param set Set of elements to remove from
     * @return any element contained in set
     */
    private <T> T getArbitrary(Set<T> set) {
        if (set.size() == 0) {
            throw new RuntimeException("Can not remove an arbitrary element out of empty set");
        }
        
        T arbitraryItem = null;
        
        for (T item : set) {
            arbitraryItem = item;
        }
        
        return arbitraryItem;
    }
}
