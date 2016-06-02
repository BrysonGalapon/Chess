package data_types;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTest {
    
    // Testing Strategy:
    // 
    // getPiece:
    //  - piece is pawn, piece is not a pawn
    // 
    // squareTo:
    //  - square is on the edge, square is not on the edge
    // 
    // squareFrom:
    //  - square is on the edge, square is not on the edge
    // 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetPieceNotPawn() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(0, 0);
        Coordinate coordTo = new Coordinate(3, 3);
        
        Piece bishop = Piece.bishop(color, false);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        Move move = new Move(bishop, squareFrom, squareTo);
        
        assertEquals("Expected correct piece", bishop, move.getPiece());
    }
    
    @Test
    public void testGetPiecePawn() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(5, 2);
        Coordinate coordTo = new Coordinate(5, 3);
        
        Piece pawn = Piece.pawn(color, true);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        Move move = new Move(pawn, squareFrom, squareTo);
        
        assertEquals("Expected correct piece", pawn, move.getPiece());
    }
    
    @Test
    public void testSquareFromEdgeToCenter() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(0, 0);
        Coordinate coordTo = new Coordinate(5, 5);
        
        Piece bishop = Piece.bishop(color, false);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        Move move = new Move(bishop, squareFrom, squareTo);
        
        assertEquals("Expected correct squareFrom coordinate", squareFrom.squareCoordinate(), move.squareFrom().squareCoordinate());
        assertEquals("Expected correct squareFrom occupied", squareFrom.isOccupied(), move.squareFrom().isOccupied());
        assertEquals("Expected correct squareFrom piece", squareFrom.getPiece(), move.squareFrom().getPiece());
        
        assertEquals("Expected correct squareTo coordinate", squareTo.squareCoordinate(), move.squareTo().squareCoordinate());
        assertEquals("Expected correct squareTo occupied", squareTo.isOccupied(), move.squareTo().isOccupied());
        assertEquals("Expected correct squareTo piece", squareTo.getPiece(), move.squareTo().getPiece());
    }
    
    @Test
    public void testSquareFromCenterToEdge() {
        PieceColor color = PieceColor.WHITE;
        Coordinate coordFrom = new Coordinate(6, 5);
        Coordinate coordTo = new Coordinate(7, 6);
        
        Piece bishop = Piece.bishop(color, true);
        Square squareFrom = new Square(coordFrom);
        Square squareTo = new Square(coordTo);
        
        Move move = new Move(bishop, squareFrom, squareTo);
        
        assertEquals("Expected correct squareFrom coordinate", squareFrom.squareCoordinate(), move.squareFrom().squareCoordinate());
        assertEquals("Expected correct squareFrom occupied", squareFrom.isOccupied(), move.squareFrom().isOccupied());
        assertEquals("Expected correct squareFrom piece", squareFrom.getPiece(), move.squareFrom().getPiece());
        
        assertEquals("Expected correct squareTo coordinate", squareTo.squareCoordinate(), move.squareTo().squareCoordinate());
        assertEquals("Expected correct squareTo occupied", squareTo.isOccupied(), move.squareTo().isOccupied());
        assertEquals("Expected correct squareTo piece", squareTo.getPiece(), move.squareTo().getPiece());
    }
}
