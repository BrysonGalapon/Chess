package data_types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
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
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testLegalMovesCastleKingMoved() {
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
        assertEquals("Expected 24 legal moves for white, including castling", 24, board.legalMoves().size());
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
        assertEquals("Expected 24 legal moves for white, including castling", 24, board.legalMoves().size());
        
        Move castleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        Move castleQueenside = Move.createMove(board.getSquare("e1"), board.getSquare("c1"));

        assertFalse("Expected castling kingside is illegal", board.legalMoves().contains(castleKingside));
        assertFalse("Expected castling queenside is illegal", board.legalMoves().contains(castleQueenside));
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        assertTrue("Expected null initial first move", lastMove == null);
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        assertFalse("Expected unmoved pawn to be unmoved", unmovedPawn.moved());
        
        Move firstMove = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        
        board.move(firstMove);
        
        Piece movedPiece = board.getSquare("e4").getPiece();
        
        assertTrue("Expected a pawn on e4", movedPiece.isPawn());
        
        Pawn movedPawn = (Pawn) movedPiece;
        
        assertTrue("Expected pawn moved", movedPawn.moved());
    }
    
    @Test 
    public void testMoveKnight() {
        PieceColor white = PieceColor.WHITE;
        Piece knight = Piece.knight(white, false);
        
        Board board = new Board();
        
        Square squareFrom = board.getSquare("b1");
        Square squareTo = board.getSquare("c3");
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        board.move(move);
        
        Set<Coordinate> whiteKnightPlacement = new HashSet<Coordinate>();
        whiteKnightPlacement.add(new Coordinate("c3"));
        whiteKnightPlacement.add(new Coordinate("g1"));
        
        assertEquals("Expected Knight to move to c3", whiteKnightPlacement, board.whitePieces().get(knight));
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
        
        Board board = new Board(whitePieces, blackPieces, turn);
        
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
