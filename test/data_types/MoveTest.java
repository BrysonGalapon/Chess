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
    
    // TODO: Add tests for coordinatesChanged
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
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
        
        assertEquals("Expected correct coordFrom coordinate", squareFrom.squareCoordinate(), move.coordFrom());
        
        assertEquals("Expected correct coordTo coordinate", squareTo.squareCoordinate(), move.coordTo());
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
        
        assertEquals("Expected correct coordFrom coordinate", squareFrom.squareCoordinate(), move.coordFrom());
        
        assertEquals("Expected correct coordTo coordinate", squareTo.squareCoordinate(), move.coordTo());
    }
}
