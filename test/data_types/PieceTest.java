package data_types;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for the Piece interface
 * @author Bryson
 */
public class PieceTest {
    // Testing Strategy:
    // 
    // moveSet:
    //  - pawn:
    //    - pawn has moved, pawn has not moved
    //    - pawn is on the edge, pawn is not on the edge
    //
    //  - king:
    //    - king is in the corner, king is not in the corner
    //    - king is on the edge, king is not on the edge
    //    - king is in the center, king is not in the center
    //    - king is unmoved and can castle
    //
    //  - knight:
    //    - knight is in the corner, knight is not in the corner
    //    - knight is on the edge, knight is not on the edge
    //    - knight is in the center, knight is not in the center
    //
    //  - rook:
    //    - rook is in the corner, rook is not in the corner
    //    - rook is on the edge, rook is not on the edge
    //    - rook is in the center, rook is not in the center
    //
    //  - bishop:
    //    - bishop is in the corner, bishop is not in the corner
    //    - bishop is on the edge, bishop is not on the edge
    //    - bishop is in the center, bishop is not in the center
    // 
    //  - queen:
    //    - bishop is in the corner, bishop is not in the corner
    //    - bishop is on the edge, bishop is not on the edge
    //    - bishop is in the center, bishop is not in the center
    // 
    // exists:
    //  - piece is a pawn, piece is not a pawn
    //  - piece is a king, piece is not a king
    //  - piece is a knight, piece is not a knight
    //  - piece is a rook, piece is not a rook
    //  - piece is a bishop, piece is not a bishop
    //  - piece is a queen, piece is not a queen
    //  
    // isPawn:
    //  - piece is a pawn, piece is not a pawn
    //  - piece is a king, piece is not a king
    //  - piece is a knight, piece is not a knight
    //  - piece is a rook, piece is not a rook
    //  - piece is a bishop, piece is not a bishop
    //  - piece is a queen, piece is not a queen
    //
    // color: 
    //  - piece is a pawn, piece is not a pawn
    // 
    // toString:
    //  - piece is a pawn, piece is not a pawn
    //  - piece is a king, piece is not a king
    //  - piece is a knight, piece is not a knight
    //  - piece is a rook, piece is not a rook
    //  - piece is a bishop, piece is not a bishop
    //  - piece is a queen, piece is not a queen
    //  - piece is empty, piece is not empty
    // 
    // moved: 
    //  - piece is a pawn, piece is not a pawn
    //  - piece has moved, piece has not moved
    // 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testMovedMovedPawn() {
        Piece pawn = Piece.pawn(PieceColor.WHITE, true);
        assertTrue("Expected moved pawn to have moved", pawn.moved());
    }
    
    @Test
    public void testMovedUnmovedKnight() {
        Piece knight = Piece.knight(PieceColor.BLACK, false);
        assertFalse("Expected unmoved knight to have not moved", knight.moved());
    }
    
    @Test
    public void testCastlingKingWhite() {
        PieceColor color = PieceColor.WHITE;
        Piece unmovedKing = Piece.king(color, false);
        Piece movedKing = Piece.king(color, true);
        
        Set<Coordinate> expectedUnmovedWhiteKingCoords = new HashSet<>();
        expectedUnmovedWhiteKingCoords.add(new Coordinate("d1"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("d2"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("e2"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("f2"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("f1"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("g1"));
        expectedUnmovedWhiteKingCoords.add(new Coordinate("c1"));
        
        Set<Coordinate> expectedMovedWhiteKingCoords = new HashSet<>();
        expectedMovedWhiteKingCoords.add(new Coordinate("d1"));
        expectedMovedWhiteKingCoords.add(new Coordinate("d2"));
        expectedMovedWhiteKingCoords.add(new Coordinate("e2"));
        expectedMovedWhiteKingCoords.add(new Coordinate("f2"));
        expectedMovedWhiteKingCoords.add(new Coordinate("f1"));
        
        assertEquals("Expected correct move set for unmoved king on e1", expectedUnmovedWhiteKingCoords, unmovedKing.moveSet(new Coordinate("e1")));
        assertEquals("Expected correct move set for moved king on e1", expectedMovedWhiteKingCoords, movedKing.moveSet(new Coordinate("e1")));
    }
    
    @Test
    public void testCastlingKingBlack() {
        PieceColor color = PieceColor.BLACK;
        Piece unmovedKing = Piece.king(color, false);
        Piece movedKing = Piece.king(color, true);
        
        Set<Coordinate> expectedUnmovedBlackKingCoords = new HashSet<>();
        expectedUnmovedBlackKingCoords.add(new Coordinate("d8"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("d7"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("e7"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("f7"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("f8"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("g8"));
        expectedUnmovedBlackKingCoords.add(new Coordinate("c8"));
        
        Set<Coordinate> expectedMovedBlackKingCoords = new HashSet<>();
        expectedMovedBlackKingCoords.add(new Coordinate("d8"));
        expectedMovedBlackKingCoords.add(new Coordinate("d7"));
        expectedMovedBlackKingCoords.add(new Coordinate("e7"));
        expectedMovedBlackKingCoords.add(new Coordinate("f7"));
        expectedMovedBlackKingCoords.add(new Coordinate("f8"));
        
        assertEquals("Expected correct move set for unmoved king on e8", expectedUnmovedBlackKingCoords, unmovedKing.moveSet(new Coordinate("e8")));
        assertEquals("Expected correct move set for moved king on e8", expectedMovedBlackKingCoords, movedKing.moveSet(new Coordinate("e8")));
    }
    
    @Test
    public void testBlackPawnMoveSet7thRank() {
        PieceColor black = PieceColor.BLACK;
        Piece pawn = Piece.pawn(black, true);
        
        Coordinate coord = new Coordinate("b2");
        
        Set<Coordinate> moveSet = pawn.moveSet(coord);
        
        assertEquals("Expected 3 legal moves for black pawn on b2", 3, moveSet.size());
        
        Set<Coordinate> expectedMoveSet = new HashSet<>();
        
        expectedMoveSet.add(new Coordinate("a1"));
        expectedMoveSet.add(new Coordinate("b1"));
        expectedMoveSet.add(new Coordinate("c1"));
        
        assertEquals("Expected correct move set for black pawn on b2", expectedMoveSet, moveSet);
    }
    
    @Test
    public void testBlackPawnMoveSetUnMoved() {
        PieceColor black = PieceColor.BLACK;
        Piece pawn = Piece.pawn(black, false);
        
        Coordinate coord = new Coordinate("b7");
        
        Set<Coordinate> moveSet = pawn.moveSet(coord);
        
        assertEquals("Expected 4 legal moves for black pawn on b7", 4, moveSet.size());
        
        Set<Coordinate> expectedMoveSet = new HashSet<>();
        
        expectedMoveSet.add(new Coordinate("b6"));
        expectedMoveSet.add(new Coordinate("b5"));
        expectedMoveSet.add(new Coordinate("c6"));
        expectedMoveSet.add(new Coordinate("a6"));
        
       // assertEquals("Expected correct move set for black pawn on b7", expectedMoveSet, moveSet);
    }
    
    @Test
    public void testPawnMove() {
        PieceColor color = PieceColor.WHITE;
        
        Piece pawn1 = Piece.pawn(color, true);
        Piece pawn2 = Piece.pawn(color, false);
        
        assertTrue("Expected pawns to be pawns", pawn1.isPawn());
        assertTrue("Expected pawns to be pawns", pawn2.isPawn());

        Pawn movedPawn = (Pawn) pawn1;
        Pawn unmovedPawn = (Pawn) pawn2;
        
        assertTrue("Expected moved pawn to have moved", movedPawn.moved());
        assertFalse("Expected unmoved pawn to not have moved", unmovedPawn.moved());
    }
    
    @Test
    public void testIsPawn() {
        PieceColor color = PieceColor.BLACK;
        
        Piece king = Piece.king(color, true);
        Piece queen = Piece.queen(color, true);
        Piece pawn1 = Piece.pawn(color, false);
        Piece pawn2 = Piece.pawn(color, true);
        Piece rook = Piece.rook(color, true);
        Piece bishop = Piece.bishop(color, true);
        Piece knight = Piece.knight(color, true);
        
        assertFalse("Expected king to be not a pawn", king.isPawn());
        assertFalse("Expected queen to be not a pawn", queen.isPawn());
        assertFalse("Expected rook to be not a pawn", rook.isPawn());
        assertFalse("Expected bishop to be not a pawn", bishop.isPawn());
        assertFalse("Expected knight to be not a pawn", knight.isPawn());
        
        assertTrue("Expected moved pawn to be a pawn", pawn1.isPawn());
        assertTrue("Expected unmoved pawn to be a pawn", pawn2.isPawn());
    }
    
    @Test
    public void testMoveSetPawnNotMovedEdge() {
        Coordinate coord = new Coordinate(0, 1);
        PieceColor color = PieceColor.WHITE;
        Piece pawn = Piece.pawn(color, false);
        
        Set<Coordinate> moves = pawn.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(0, 2));
        expectedMoves.add(new Coordinate(0, 3));
        expectedMoves.add(new Coordinate(1, 2));

        assertEquals("Expected 3 specific moves for white pawn on a2", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetPawnMovedCenter() {
        Coordinate coord = new Coordinate(3, 3);
        PieceColor color = PieceColor.WHITE;
        Piece pawn = Piece.pawn(color, true);
        
        Set<Coordinate> moves = pawn.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(3, 4));
        expectedMoves.add(new Coordinate(4, 4));
        expectedMoves.add(new Coordinate(2, 4));

        assertEquals("Expected 3 specific moves for white pawn on d4", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSEtPawnNotMovedCenter() {
        Coordinate coord = new Coordinate(2, 1);
        PieceColor color = PieceColor.WHITE;
        Piece pawn = Piece.pawn(color, false);
        
        Set<Coordinate> moves = pawn.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(2, 3));
        expectedMoves.add(new Coordinate(1, 2));
        expectedMoves.add(new Coordinate(3, 2));

        assertEquals("Expected 4 specific moves for white pawn on c2", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKingCorner() {
        Coordinate coord = new Coordinate(0, 0);
        PieceColor color = PieceColor.WHITE;
        Piece king = Piece.king(color, true);
        
        Set<Coordinate> moves = king.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 0));
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(1, 1));
        
        assertEquals("Expected 3 specific moves for king on a1", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKingEdge() {
        Coordinate coord = new Coordinate(1, 0);
        PieceColor color = PieceColor.WHITE;
        Piece king = Piece.king(color, true);
        
        Set<Coordinate> moves = king.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(2, 0));
        expectedMoves.add(new Coordinate(2, 1));
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(1, 1));

        assertEquals("Expected 5 specific moves for king on b1", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKingCenter() {
        Coordinate coord = new Coordinate(1, 1);
        PieceColor color = PieceColor.WHITE;
        Piece king = Piece.king(color, true);
        
        Set<Coordinate> moves = king.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(0, 2));
        expectedMoves.add(new Coordinate(1, 2));
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(2, 1));
        expectedMoves.add(new Coordinate(2, 0));
        expectedMoves.add(new Coordinate(1, 0));

        assertEquals("Expected 8 specific moves for king on b2", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKnightCorner() {
        Coordinate coord = new Coordinate(7, 7);
        PieceColor color = PieceColor.WHITE;
        Piece knight = Piece.knight(color, true);
        
        Set<Coordinate> moves = knight.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(5, 6));
        expectedMoves.add(new Coordinate(6, 5));
        
        assertEquals("Expected 2 specific moves for knight on h8", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKnightEdge() {
        Coordinate coord = new Coordinate(2, 7);
        PieceColor color = PieceColor.BLACK;
        Piece knight = Piece.knight(color, true);
        
        Set<Coordinate> moves = knight.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(0, 6));
        expectedMoves.add(new Coordinate(1, 5));
        expectedMoves.add(new Coordinate(3, 5));
        expectedMoves.add(new Coordinate(4, 6));
        
        assertEquals("Expected 4 specific moves for knight on c8", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetKnightCenter() {
        Coordinate coord = new Coordinate(3, 3);
        PieceColor color = PieceColor.BLACK;
        Piece knight = Piece.knight(color, true);
        
        Set<Coordinate> moves = knight.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 4));
        expectedMoves.add(new Coordinate(1, 2));
        expectedMoves.add(new Coordinate(2, 5));
        expectedMoves.add(new Coordinate(2, 1));
        expectedMoves.add(new Coordinate(4, 5));
        expectedMoves.add(new Coordinate(4, 1));
        expectedMoves.add(new Coordinate(5, 4));
        expectedMoves.add(new Coordinate(5, 2));
     
        assertEquals("Expected 8 specific moves for knight on d4", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetBishopCorner() {
        Coordinate coord = new Coordinate(0, 7);
        PieceColor color = PieceColor.BLACK;
        Piece bishop = Piece.bishop(color, true);
        
        Set<Coordinate> moves = bishop.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 6));
        expectedMoves.add(new Coordinate(2, 5));
        expectedMoves.add(new Coordinate(3, 4));
        expectedMoves.add(new Coordinate(4, 3));
        expectedMoves.add(new Coordinate(5, 2));
        expectedMoves.add(new Coordinate(6, 1));
        expectedMoves.add(new Coordinate(7, 0));
     
        assertEquals("Expected 7 specific moves for bishop on a8", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetBishopEdge() {
        Coordinate coord = new Coordinate(0, 6);
        PieceColor color = PieceColor.BLACK;
        Piece bishop = Piece.bishop(color, true);
        
        Set<Coordinate> moves = bishop.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 7));
        expectedMoves.add(new Coordinate(1, 5));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(3, 3));
        expectedMoves.add(new Coordinate(4, 2));
        expectedMoves.add(new Coordinate(5, 1));
        expectedMoves.add(new Coordinate(6, 0));
     
        assertEquals("Expected 7 specific moves for bishop on a7", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetBishopCenter() {
        Coordinate coord = new Coordinate(3, 3);
        PieceColor color = PieceColor.BLACK;
        Piece bishop = Piece.bishop(color, true);
        
        Set<Coordinate> moves = bishop.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(4, 4));
        expectedMoves.add(new Coordinate(5, 5));
        expectedMoves.add(new Coordinate(6, 6));
        expectedMoves.add(new Coordinate(7, 7));
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(1, 1));
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(1, 5));
        expectedMoves.add(new Coordinate(0, 6));
        expectedMoves.add(new Coordinate(4, 2));
        expectedMoves.add(new Coordinate(5, 1));
        expectedMoves.add(new Coordinate(6, 0));
     
        assertEquals("Expected 13 specific moves for bishop on d4", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetRookCorner() {
        Coordinate coord = new Coordinate(0, 0);
        PieceColor color = PieceColor.BLACK;
        Piece rook = Piece.rook(color, true);
        
        Set<Coordinate> moves = rook.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(0, 2));
        expectedMoves.add(new Coordinate(0, 3));
        expectedMoves.add(new Coordinate(0, 4));
        expectedMoves.add(new Coordinate(0, 5));
        expectedMoves.add(new Coordinate(0, 6));
        expectedMoves.add(new Coordinate(0, 7));
        expectedMoves.add(new Coordinate(1, 0));
        expectedMoves.add(new Coordinate(2, 0));
        expectedMoves.add(new Coordinate(3, 0));
        expectedMoves.add(new Coordinate(4, 0));
        expectedMoves.add(new Coordinate(5, 0));
        expectedMoves.add(new Coordinate(6, 0));
        expectedMoves.add(new Coordinate(7, 0));

        assertEquals("Expected 14 specific moves for rook on a1", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetRookEdge() {
        Coordinate coord = new Coordinate(2, 0);
        PieceColor color = PieceColor.BLACK;
        Piece rook = Piece.rook(color, true);
        
        Set<Coordinate> moves = rook.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(2, 1));
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(2, 3));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(2, 5));
        expectedMoves.add(new Coordinate(2, 6));
        expectedMoves.add(new Coordinate(2, 7));
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(1, 0));
        expectedMoves.add(new Coordinate(3, 0));
        expectedMoves.add(new Coordinate(4, 0));
        expectedMoves.add(new Coordinate(5, 0));
        expectedMoves.add(new Coordinate(6, 0));
        expectedMoves.add(new Coordinate(7, 0));

        assertEquals("Expected 14 specific moves for rook on c1", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetRookCenter() {
        Coordinate coord = new Coordinate(2, 3);
        PieceColor color = PieceColor.BLACK;
        Piece rook = Piece.rook(color, true);
        
        Set<Coordinate> moves = rook.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(2, 1));
        expectedMoves.add(new Coordinate(2, 0));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(2, 5));
        expectedMoves.add(new Coordinate(2, 6));
        expectedMoves.add(new Coordinate(2, 7));
        expectedMoves.add(new Coordinate(1, 3));
        expectedMoves.add(new Coordinate(0, 3));
        expectedMoves.add(new Coordinate(3, 3));
        expectedMoves.add(new Coordinate(4, 3));
        expectedMoves.add(new Coordinate(5, 3));
        expectedMoves.add(new Coordinate(6, 3));
        expectedMoves.add(new Coordinate(7, 3));

        assertEquals("Expected 14 specific moves for rook on c4", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetQueenCorner() {
        Coordinate coord = new Coordinate(0, 0);
        PieceColor color = PieceColor.BLACK;
        Piece queen = Piece.queen(color, true);
        
        Set<Coordinate> moves = queen.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 0));
        expectedMoves.add(new Coordinate(2, 0));
        expectedMoves.add(new Coordinate(3, 0));
        expectedMoves.add(new Coordinate(4, 0));
        expectedMoves.add(new Coordinate(5, 0));
        expectedMoves.add(new Coordinate(6, 0));
        expectedMoves.add(new Coordinate(7, 0));
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(0, 2));
        expectedMoves.add(new Coordinate(0, 3));
        expectedMoves.add(new Coordinate(0, 4));
        expectedMoves.add(new Coordinate(0, 5));
        expectedMoves.add(new Coordinate(0, 6));
        expectedMoves.add(new Coordinate(0, 7));
        expectedMoves.add(new Coordinate(1, 1));
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(3, 3));
        expectedMoves.add(new Coordinate(4, 4));
        expectedMoves.add(new Coordinate(5, 5));
        expectedMoves.add(new Coordinate(6, 6));
        expectedMoves.add(new Coordinate(7, 7));

        assertEquals("Expected 21 specific moves for queen on a1", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetQueenEdge() {
        Coordinate coord = new Coordinate(0, 6);
        PieceColor color = PieceColor.BLACK;
        Piece queen = Piece.queen(color, true);
        
        Set<Coordinate> moves = queen.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(1, 7));
        expectedMoves.add(new Coordinate(1, 5));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(3, 3));
        expectedMoves.add(new Coordinate(4, 2));
        expectedMoves.add(new Coordinate(5, 1));
        expectedMoves.add(new Coordinate(6, 0));
        expectedMoves.add(new Coordinate(0, 7));
        expectedMoves.add(new Coordinate(0, 5));
        expectedMoves.add(new Coordinate(0, 4));
        expectedMoves.add(new Coordinate(0, 3));
        expectedMoves.add(new Coordinate(0, 2));
        expectedMoves.add(new Coordinate(0, 1));
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(1, 6));
        expectedMoves.add(new Coordinate(2, 6));
        expectedMoves.add(new Coordinate(3, 6));
        expectedMoves.add(new Coordinate(4, 6));
        expectedMoves.add(new Coordinate(5, 6));
        expectedMoves.add(new Coordinate(6, 6));
        expectedMoves.add(new Coordinate(7, 6));

        assertEquals("Expected 21 specific moves for queen on a7", expectedMoves, moves);
    }
    
    @Test
    public void testMoveSetQueenCenter() {
        Coordinate coord = new Coordinate(3, 3);
        PieceColor color = PieceColor.BLACK;
        Piece queen = Piece.queen(color, true);
        
        Set<Coordinate> moves = queen.moveSet(coord);
        
        Set<Coordinate> expectedMoves = new HashSet<Coordinate>();
        expectedMoves.add(new Coordinate(4, 4));
        expectedMoves.add(new Coordinate(5, 5));
        expectedMoves.add(new Coordinate(6, 6));
        expectedMoves.add(new Coordinate(7, 7));
        expectedMoves.add(new Coordinate(2, 2));
        expectedMoves.add(new Coordinate(1, 1));
        expectedMoves.add(new Coordinate(0, 0));
        expectedMoves.add(new Coordinate(2, 4));
        expectedMoves.add(new Coordinate(1, 5));
        expectedMoves.add(new Coordinate(0, 6));
        expectedMoves.add(new Coordinate(4, 2));
        expectedMoves.add(new Coordinate(5, 1));
        expectedMoves.add(new Coordinate(6, 0));
        expectedMoves.add(new Coordinate(3, 4));
        expectedMoves.add(new Coordinate(3, 5));
        expectedMoves.add(new Coordinate(3, 6));
        expectedMoves.add(new Coordinate(3, 7));
        expectedMoves.add(new Coordinate(3, 2));
        expectedMoves.add(new Coordinate(3, 1));
        expectedMoves.add(new Coordinate(3, 0));
        expectedMoves.add(new Coordinate(4, 3));
        expectedMoves.add(new Coordinate(5, 3));
        expectedMoves.add(new Coordinate(6, 3));
        expectedMoves.add(new Coordinate(7, 3));
        expectedMoves.add(new Coordinate(2, 3));
        expectedMoves.add(new Coordinate(1, 3));
        expectedMoves.add(new Coordinate(0, 3));
     
        assertEquals("Expected 27 specific moves for queen on d4", expectedMoves, moves);
    }
    
    @Test
    public void testToStringKing() {
        PieceColor color = PieceColor.WHITE;
        Piece king = Piece.king(color, true);
        
        assertEquals("Expected K for king", "K", king.toString());
    }
    
    @Test
    public void testToStringQueen() {
        PieceColor color = PieceColor.WHITE;
        Piece queen = Piece.queen(color, true);
        
        assertEquals("Expected Q for queen", "Q", queen.toString());
    }
    
    @Test
    public void testToStringRook() {
        PieceColor color = PieceColor.WHITE;
        Piece rook = Piece.rook(color, true);
        
        assertEquals("Expected R for rook", "R", rook.toString());
    }
    
    @Test
    public void testToStringPawn() {
        PieceColor color = PieceColor.WHITE;
        Piece pawn = Piece.pawn(color, true);
        
        assertEquals("Expected '' for pawn", "", pawn.toString());
    }
    
    @Test
    public void testToStringKnight() {
        PieceColor color = PieceColor.WHITE;
        Piece knight = Piece.knight(color, true);
        
        assertEquals("Expected N for knight", "N", knight.toString());
    }
    
    @Test
    public void testToStringBishop() {
        PieceColor color = PieceColor.WHITE;
        Piece bishop = Piece.bishop(color, true);
        
        assertEquals("Expected B for bishop", "B", bishop.toString());
    }
    
    @Test
    public void testExists() {
        PieceColor color = PieceColor.WHITE;
        
        Piece pawn = Piece.pawn(color, true);
        Piece king = Piece.king(color, true);
        Piece queen = Piece.queen(color, true);
        Piece rook = Piece.rook(color, true);
        Piece knight = Piece.knight(color, true);
        Piece bishop = Piece.bishop(color, true);
        Piece empty = new EmptyPiece();
        
        assertTrue("Pawns exist.", pawn.exists());
        assertTrue("Kings exist.", king.exists());
        assertTrue("Queens exist.", queen.exists());
        assertTrue("Rooks exist.", rook.exists());
        assertTrue("Knights exist.", knight.exists());
        assertTrue("Bishops exist.", bishop.exists());
        assertFalse("'Nothing' does not exist.", empty.exists());
    }
    
    @Test
    public void testColor() {
        PieceColor black = PieceColor.BLACK;
        PieceColor white = PieceColor.WHITE;
        
        Piece pawn = Piece.pawn(black, true);
        Piece king = Piece.king(white, true);
        
        assertEquals("Expected a black pawn to be black", PieceColor.BLACK, pawn.color());
        assertEquals("Expected a white king to be white", PieceColor.WHITE, king.color());
    }
    
    @Test
    public void testEquals() {
        PieceColor black = PieceColor.BLACK;
        PieceColor white = PieceColor.WHITE;
        
        Piece blackPawn1 = Piece.pawn(black, true);
        Piece whitePawn1 = Piece.pawn(white, true);
        Piece blackRook1 = Piece.rook(black, true);
        Piece whiteRook1 = Piece.rook(white, true);
        Piece blackBishop1 = Piece.bishop(black, true);
        Piece whiteBishop1 = Piece.bishop(white, true);
        Piece blackKnight1 = Piece.knight(black, true);
        Piece whiteKnight1 = Piece.knight(white, true);
        Piece blackKing1 = Piece.king(black, true);
        Piece whiteKing1 = Piece.king(white, true);
        Piece blackQueen1 = Piece.queen(black, true);
        Piece whiteQueen1 = Piece.queen(white, true);
        
        Piece blackPawn2 = Piece.pawn(black, true);
        Piece whitePawn2 = Piece.pawn(white, true);
        Piece blackRook2 = Piece.rook(black, true);
        Piece whiteRook2 = Piece.rook(white, true);
        Piece blackBishop2 = Piece.bishop(black, true);
        Piece whiteBishop2 = Piece.bishop(white, true);
        Piece blackKnight2 = Piece.knight(black, true);
        Piece whiteKnight2 = Piece.knight(white, true);
        Piece blackKing2 = Piece.king(black, true);
        Piece whiteKing2 = Piece.king(white, true);
        Piece blackQueen2 = Piece.queen(black, true);
        Piece whiteQueen2 = Piece.queen(white, true);
        
        assertEquals("Expected equivalent pieces to be equivalent", blackPawn1, blackPawn2);
        assertEquals("Expected equivalent pieces to be equivalent", whitePawn1, whitePawn2);
        assertEquals("Expected equivalent pieces to be equivalent", blackRook1, blackRook2);
        assertEquals("Expected equivalent pieces to be equivalent", whiteRook1, whiteRook2);
        assertEquals("Expected equivalent pieces to be equivalent", blackKnight1, blackKnight2);
        assertEquals("Expected equivalent pieces to be equivalent", whiteKnight1, whiteKnight2);
        assertEquals("Expected equivalent pieces to be equivalent", blackBishop1, blackBishop2);
        assertEquals("Expected equivalent pieces to be equivalent", whiteBishop1, whiteBishop2);
        assertEquals("Expected equivalent pieces to be equivalent", blackKing1, blackKing2);
        assertEquals("Expected equivalent pieces to be equivalent", whiteKing1, whiteKing2);
        assertEquals("Expected equivalent pieces to be equivalent", blackQueen1, blackQueen2);
        assertEquals("Expected equivalent pieces to be equivalent", whiteQueen1, whiteQueen2);
        
        assertFalse("Expected non-equivalent pieces to be not equivalent", whiteKnight1.equals(whiteKing1));
    }
    
}
