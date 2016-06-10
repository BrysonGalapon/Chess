package data_types;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Tests for the Square class 
 * @author Bryson
 */
public class SquareTest {
    // Testing Strategy:
    //  
    // addPiece:
    //  - piece is a pawn, piece is not a pawn  
    //  - piece is white, piece is not white
    //  - piece is black, piece is not black
    // 
    // removePiece:
    //  - square was occupied, square was not occupied
    // 
    // getPiece:
    //  - piece is a pawn, piece is not a pawn
    // 
    // coordinate:
    //  - coordinate is origin, coordinate is not origin
    //  - coordinate is on edge, coordinate is not on edge 
    //  - coordinate is in center, coordinate is not in center
    //
    // isOccupied:
    //  - square is occupied, square is not occupied
    //   
    // toString:
    //  - square is on edge, square is not on edge
    // 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testAddPieceBlackPawn() {
        Coordinate coord = new Coordinate(1,4);
        PieceColor color = PieceColor.BLACK;
        Piece pawn = Piece.pawn(color, true);
        
        Square square = new Square(coord);
        square.addPiece(pawn);
        
        assertTrue("Expected square to be occupied", square.isOccupied());
        assertEquals("Expected correct piece on the square", pawn, square.getPiece());
    }
    
    @Test
    public void testAddPieceWhiteKnight() {
        Coordinate coord = new Coordinate(1,4);
        PieceColor color = PieceColor.WHITE;
        Piece knight = Piece.knight(color, true);
        
        Square square = new Square(coord);
        square.addPiece(knight);
        
        assertTrue("Expected square to be occupied", square.isOccupied());
        assertEquals("Expected correct piece on the square", knight, square.getPiece());
    }
    
    @Test
    public void testRemovePieceOccupied() {
        Coordinate coord = new Coordinate(6,7);
        PieceColor color = PieceColor.WHITE;
        Piece queen = Piece.queen(color, false);
        
        Square square = new Square(coord);
        square.addPiece(queen);
        
        square.removePiece();
        
        assertFalse("Expected square to be unoccupied", square.isOccupied());
    }
    
    @Test
    public void testRemovePieceUnOccupied() {
        Coordinate coord = new Coordinate(6,7);
        Square square = new Square(coord);
        Square squareCopy = new Square(coord);
        
        square.removePiece();
        
        assertEquals("Expected nothing to happen when removing from empty square", squareCopy.coordinate(), square.coordinate());
        assertEquals("Expected nothing to happen when removing from empty square", squareCopy.isOccupied(), square.isOccupied());
        assertFalse("Expected empty square", square.isOccupied());
    }
    
    @Test
    public void testGetPiecePawn() {
        Coordinate coord = new Coordinate(1,4);
        PieceColor color = PieceColor.WHITE;
        Piece pawn = Piece.pawn(color, true);
        
        Square square = new Square(coord);
        square.addPiece(pawn);
        
        assertEquals("Expected correct piece on the square", pawn, square.getPiece());
    }
    
    @Test
    public void testGetPieceNotPawn() {
        Coordinate coord = new Coordinate(1,4);
        PieceColor color = PieceColor.BLACK;
        Piece knight = Piece.knight(color, true);
        
        Square square = new Square(coord);
        square.addPiece(knight);
        
        assertEquals("Expected correct piece on the square", knight, square.getPiece());
    }
    
    @Test
    public void testcoordinateOrigin() {
        Coordinate coord = new Coordinate(0, 0);
        
        Square square = new Square(coord);
        assertEquals("Expected equivalent coordinate", coord, square.coordinate());
    }
    
    @Test
    public void testcoordinateEdge() {
        Coordinate coord = new Coordinate(0, 6);
        
        Square square = new Square(coord);
        assertEquals("Expected equivalent coordinate", coord, square.coordinate());
    }
    
    @Test
    public void testcoordinateCenter() {
        Coordinate coord = new Coordinate(3, 4);
        
        Square square = new Square(coord);
        assertEquals("Expected equivalent coordinate", coord, square.coordinate());
    }
    
    @Test
    public void testIsOccupiedOccupied() {
        Coordinate coord = new Coordinate(1,4);
        PieceColor color = PieceColor.BLACK;
        Piece pawn = Piece.pawn(color, true);
        
        Square square = new Square(coord);
        square.addPiece(pawn);
        
        assertTrue("Expected square to be occupied after adding piece", square.isOccupied());
    }
    
    @Test
    public void testIsOccpuiedUnOccupied() {
        Coordinate coord = new Coordinate(3,4);
        Square square = new Square(coord);
        
        assertFalse("Expected empty initial square", square.isOccupied());
    }
    
    @Test
    public void testToStringEdge() {
        Coordinate coord = new Coordinate(0, 7);
        Square square = new Square(coord);
        
        assertEquals("Expected correct square", "a8", square.toString());
    }
    
    @Test
    public void testToStringCenter() {
        Coordinate coord = new Coordinate(3, 3);
        Square square = new Square(coord);
        
        assertEquals("Expected correct square", "d4", square.toString());
    }
    
    @Test
    public void testReplacement() {
        Coordinate coord = new Coordinate("a1");
        Square square = new Square(coord);
        
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        Piece whiteKing = Piece.king(white, true);
        Piece blackQueen = Piece.queen(black, true);
        Piece whiteKnight = Piece.knight(white, false);
        
        square.addPiece(blackQueen);
        square.addPiece(whiteKing);
        
        assertEquals("Expected white king to replace black queen", whiteKing, square.getPiece());
        
        square.addPiece(whiteKnight);
        
        assertEquals("Expected white knight to NOT replace white king", whiteKing, square.getPiece());
    }
}
