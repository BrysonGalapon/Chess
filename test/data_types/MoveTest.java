package data_types;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MoveTest {
    
    // Testing Strategy:
    // 
    // movedPieces:
    //  - piece is pawn, piece is not a pawn
    //  - move is a castle move, move is not a castle move
    //  - white castled, black castled
    // 
    // coordTo:
    //  - coordinate is on the edge, coordinate is not on the edge
    // 
    // coordFrom:
    //  - coordinate is on the edge, coordinate is not on the edge
    // 
    // isCastle:
    //  - move is a castle move, move is not a castle move
    //
    // coordinatesChanged:
    //  - move is a castle move, move is not a castle move
    //  - move is a pawn move, move is not a pawn move
    // 
    // isCapture:
    //  - move is a capture, move is not a capture
    //  - capture is with a pawn, capture is not with a pawn
    //  - move is a castle move, move is not a castle move
    // 
    
    // TODO: write tests for captureCoordinate, isPromotion, and promotedPiece
    //       isEnPassent, capturedPiece, isUndefined
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testToString() {
        Board board = new Board();
        
        Move move = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        board.move(move);
        assertEquals("e4", move.toString());
        
        move = Move.createMove(board.getSquare("d7"), board.getSquare("d5"));
        board.move(move);
        assertEquals("d5", move.toString());
        
        move = Move.createMove(board.getSquare("e4"), board.getSquare("d5"));
        board.move(move);
        assertEquals("exd5", move.toString());
        
        move = Move.createMove(board.getSquare("c7"), board.getSquare("c5"));
        board.move(move);
        assertEquals("c5", move.toString());
        
        move = Move.enPassent(board.getSquare("d5"), board.getSquare("c6"));
        board.move(move);
        assertEquals("dxc6", move.toString());
        
        move = Move.createMove(board.getSquare("b8"), board.getSquare("c6"));
        board.move(move);
        assertEquals("Nxc6", move.toString());
        
        move = Move.createMove(board.getSquare("f1"), board.getSquare("b5"));
        board.move(move);
        assertEquals("Bb5", move.toString());
        
        move = Move.createMove(board.getSquare("a7"), board.getSquare("a5"));
        board.move(move);
        assertEquals("a5", move.toString());
        
        move = Move.createMove(board.getSquare("g1"), board.getSquare("h3"));
        board.move(move);
        assertEquals("Nh3", move.toString());
        
        move = Move.createMove(board.getSquare("a5"), board.getSquare("a4"));
        board.move(move);
        assertEquals("a4", move.toString());
        
        move = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        board.move(move);
        assertEquals("O-O", move.toString());
        
        move = Move.createMove(board.getSquare("a4"), board.getSquare("a3"));
        board.move(move);
        assertEquals("a3", move.toString());
        
        move = Move.createMove(board.getSquare("f1"), board.getSquare("e1"));
        board.move(move);
        assertEquals("Re1", move.toString());
        
        move = Move.createMove(board.getSquare("a3"), board.getSquare("b2"));
        board.move(move);
        assertEquals("axb2", move.toString());
        
        move = Move.createMove(board.getSquare("e1"), board.getSquare("e7"));
        board.move(move);
        assertEquals("Rxe7", move.toString());
        
        move = Move.createMove(board.getSquare("d8"), board.getSquare("e7"));
        board.move(move);
        assertEquals("Qxe7", move.toString());
        
        move = Move.createMove(board.getSquare("d1"), board.getSquare("h5"));
        board.move(move);
        assertEquals("Qh5", move.toString());
        
        move = Move.promote(board.getSquare("b2"), board.getSquare("a1"), Piece.rook(PieceColor.BLACK, true));
        board.move(move);
        assertEquals("bxa1=R", move.toString());
    }
    
    @Test
    public void testPromotion() {
        PieceColor white = PieceColor.WHITE;
        
        Square squareFrom = new Square(new Coordinate("g7"));
        Square squareTo = new Square(new Coordinate("h8"));
        
        Piece promotingPawn = Piece.pawn(white, true);
        Piece promotingPiece = Piece.knight(white, true);
        
        squareFrom.addPiece(promotingPawn);
        
        Move move = Move.promote(squareFrom, squareTo, promotingPiece);
        
        // if test makes it to this point, then move is a valid promotion, which is
        // the crux of what this test is testing
        assertFalse("Expected promotion to not be a castling move", move.isCastle());
    }
    
    @Test
    public void testEnPassentCapture() {
        Board board = new Board();
        
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a5")));
        board.move(Move.createMove(board.getSquare("e4"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("f7"), board.getSquare("f5")));
        
        Move move = Move.enPassent(board.getSquare("e5"), board.getSquare("f6"));
        
        // if test makes it to this point, then move is a valid en passent, which is
        // the crux of what this test is testing

        assertTrue("Expected en Passent to be a capture", move.isCapture());
        assertFalse("Expected en Passent to not be castle move", move.isCastle());
    }
    
    @Test
    public void testCoordinatesChangedEnPassent() {
        Board board = new Board();
        
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("a7"), board.getSquare("a6")));
        board.move(Move.createMove(board.getSquare("e4"), board.getSquare("e5")));
        board.move(Move.createMove(board.getSquare("f7"), board.getSquare("f5")));
        
        Move enPassent = Move.enPassent(board.getSquare("e5"), board.getSquare("f6"));
        
        Set<Coordinate> expectedCoordinatesChanged = new HashSet<>();
        expectedCoordinatesChanged.add(new Coordinate("e5"));
        expectedCoordinatesChanged.add(new Coordinate("f5"));
        expectedCoordinatesChanged.add(new Coordinate("f6"));

        assertEquals("Expected en passent to change f6 square", expectedCoordinatesChanged, enPassent.coordinatesChanged());
    }
    
    @Test
    public void testIsCaptureCastle() {
        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
        
        Square squareFromWhite = new Square(new Coordinate("e1"));
        Square squareFromBlack = new Square(new Coordinate("e8"));
        
        Square squareToWhite = new Square(new Coordinate("g1"));
        Square squareToBlack = new Square(new Coordinate("c8"));
        
        squareFromWhite.addPiece(unmovedWhiteKing);
        squareFromBlack.addPiece(unmovedBlackKing);
        
        Move whiteCastleKingside = Move.createMove(squareFromWhite, squareToWhite);
        Move blackCastleQueenside = Move.createMove(squareFromBlack, squareToBlack);
        
        assertFalse("Expected castling to never capture a piece", whiteCastleKingside.isCapture());
        assertFalse("Expected castling to never capture a piece", blackCastleQueenside.isCapture());
    }
    
    @Test
    public void testIsCaptureNotCapture() {
        Board board = new Board();
        Move move = Move.createMove(board.getSquare("e2"), board.getSquare("e4"));
        
        assertFalse("Expected opening pawn move to not be a capture", move.isCapture());
    }
    
    @Test
    public void testIsCaptureCapture() {
        Board board = new Board();
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("d7"), board.getSquare("d5")));
        
        Move pawnCapture = Move.createMove(board.getSquare("e4"), board.getSquare("d5"));

        assertTrue("Expected pawn capture to be a capture move", pawnCapture.isCapture());
        
        board.move(pawnCapture);
        
        Move queenCapture = Move.createMove(board.getSquare("d8"), board.getSquare("d5"));
        
        assertTrue("Expected queen recapture to be a capture move", queenCapture.isCapture());
    }
    
    @Test
    public void testCoordinatesChangedCastle() {
        
        Board board = new Board();
        
        board.move(Move.createMove(board.getSquare("e2"), board.getSquare("e4")));
        board.move(Move.createMove(board.getSquare("d7"), board.getSquare("d5")));
        board.move(Move.createMove(board.getSquare("g1"), board.getSquare("f3")));
        board.move(Move.createMove(board.getSquare("b8"), board.getSquare("c6")));
        board.move(Move.createMove(board.getSquare("f1"), board.getSquare("e2")));
        board.move(Move.createMove(board.getSquare("c8"), board.getSquare("e6")));
        board.move(Move.createMove(board.getSquare("a2"), board.getSquare("a3")));
        board.move(Move.createMove(board.getSquare("d8"), board.getSquare("d7")));

        Move whiteCastleKingside = Move.createMove(board.getSquare("e1"), board.getSquare("g1"));
        board.flipTurn();
        Move blackCastleQueenside = Move.createMove(board.getSquare("e8"), board.getSquare("c8"));
        board.flipTurn();
        
        Set<Coordinate> expectedCoordinatesChangedWhite = new HashSet<>();
        Set<Coordinate> expectedCoordinatesChangedBlack = new HashSet<>();
        
        expectedCoordinatesChangedWhite.add(new Coordinate("e1"));
        expectedCoordinatesChangedWhite.add(new Coordinate("g1"));
        expectedCoordinatesChangedWhite.add(new Coordinate("h1"));
        expectedCoordinatesChangedWhite.add(new Coordinate("f1"));

        expectedCoordinatesChangedBlack.add(new Coordinate("e8"));
        expectedCoordinatesChangedBlack.add(new Coordinate("c8"));
        expectedCoordinatesChangedBlack.add(new Coordinate("a8"));
        expectedCoordinatesChangedBlack.add(new Coordinate("d8"));
        
        assertEquals("Expected correct changed coordinates for white castling kingside", expectedCoordinatesChangedWhite, whiteCastleKingside.coordinatesChanged());
        assertEquals("Expected correct changed coordinates for black castling queenside", expectedCoordinatesChangedBlack, blackCastleQueenside.coordinatesChanged());
    }
    
    @Test
    public void testIsCastleIsCastle() {
        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
        
        Square squareFromWhite = new Square(new Coordinate("e1"));
        Square squareFromBlack = new Square(new Coordinate("e8"));
        
        Square squareToWhite = new Square(new Coordinate("g1"));
        Square squareToBlack = new Square(new Coordinate("c8"));
        
        squareFromWhite.addPiece(unmovedWhiteKing);
        squareFromBlack.addPiece(unmovedBlackKing);
        
        Move whiteCastleKingside = Move.createMove(squareFromWhite, squareToWhite);
        Move blackCastleQueenside = Move.createMove(squareFromBlack, squareToBlack);
        
        assertTrue("Expected white castling move to be a castle move", whiteCastleKingside.isCastle());
        assertTrue("Expected black castling move to be a castle move", blackCastleQueenside.isCastle());
    }
    
    @Test
    public void testIsCastleNotCastle() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(5, 2);
        Coordinate coordTo = new Coordinate(5, 3);
        
        Piece pawn = Piece.pawn(color, true);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        squareFrom.addPiece(pawn);
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        assertFalse("Expected pawn move to not be a castle move", move.isCastle());
    }
    
    @Test
    public void testMovePiecesCastle() {
        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
        Piece unmovedBlackRook = Piece.rook(PieceColor.BLACK, false);
        Piece unmovedWhiteRook = Piece.rook(PieceColor.WHITE, false);
        
        Square squareFromWhite = new Square(new Coordinate("e1"));
        Square squareFromBlack = new Square(new Coordinate("e8"));
        
        Square squareToWhite = new Square(new Coordinate("g1"));
        Square squareToBlack = new Square(new Coordinate("c8"));
        
        squareFromWhite.addPiece(unmovedWhiteKing);
        squareFromBlack.addPiece(unmovedBlackKing);
        
        Move whiteCastleKingside = Move.createMove(squareFromWhite, squareToWhite);
        Move blackCastleQueenside = Move.createMove(squareFromBlack, squareToBlack);
        
        Set<Piece> movedPiecesWhite = whiteCastleKingside.movedPieces();
        Set<Piece> movedPiecesBlack = blackCastleQueenside.movedPieces();
        
        Set<Piece> expectedMovedPiecesWhite = new HashSet<>();
        Set<Piece> expectedMovedPiecesBlack = new HashSet<>();
        
        expectedMovedPiecesWhite.add(unmovedWhiteRook);
        expectedMovedPiecesWhite.add(unmovedWhiteKing);
        expectedMovedPiecesBlack.add(unmovedBlackRook);
        expectedMovedPiecesBlack.add(unmovedBlackKing);
        
        assertEquals("Expected correct set of moved pieces", expectedMovedPiecesWhite, movedPiecesWhite);
        assertEquals("Expected correct set of moved pieces", expectedMovedPiecesBlack, movedPiecesBlack);
    }
    
    @Test
    public void testMovedPiecesNotPawn() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(0, 0);
        Coordinate coordTo = new Coordinate(3, 3);
        
        Piece bishop = Piece.bishop(color, false);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        squareFrom.addPiece(bishop);
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        Set<Piece> expectedPieces = new HashSet<>();
        expectedPieces.add(bishop);
        
        assertEquals("Expected correct set of pieces", expectedPieces, move.movedPieces());
    }
    
    @Test
    public void testMovedPiecesPawn() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(5, 2);
        Coordinate coordTo = new Coordinate(5, 3);
        
        Piece pawn = Piece.pawn(color, true);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        squareFrom.addPiece(pawn);
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        Set<Piece> expectedPieces = new HashSet<>();
        expectedPieces.add(pawn);
        
        assertEquals("Expected correct set of pieces", expectedPieces, move.movedPieces());
    }
    
    @Test
    public void testCoordFromEdgeToCenter() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(0, 0);
        Coordinate coordTo = new Coordinate(5, 5);
        
        Piece bishop = Piece.bishop(color, false);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        squareFrom.addPiece(bishop);
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        assertEquals("Expected correct coordFrom coordinate", squareFrom.coordinate(), move.coordFrom());
        
        assertEquals("Expected correct coordTo coordinate", squareTo.coordinate(), move.coordTo());
    }
    
    @Test
    public void testCoordFromCenterToEdge() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(6, 5);
        Coordinate coordTo = new Coordinate(7, 6);
        
        Piece bishop = Piece.bishop(color, true);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        squareFrom.addPiece(bishop);
        
        Move move = Move.createMove(squareFrom, squareTo);
        
        assertEquals("Expected correct coordFrom coordinate", squareFrom.coordinate(), move.coordFrom());
        
        assertEquals("Expected correct coordTo coordinate", squareTo.coordinate(), move.coordTo());
    }
}
