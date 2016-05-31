package data_types;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Coordinate class
 * @author Bryson
 */
public class CoordinateTest {
    
    // Testing Strategy:
    //
    // getX:
    //  - x == 0, x > 0
    //  - y == 0, y > 0
    //
    // getY:
    //  - x == 0, x > 0
    //  - y == 0, y > 0
    // 
    // chessNotation:
    //  - rank == 0, rank != 0
    //  - rank == Board.DEFAULT_LENGTH-1, rank != Board.DEFAULT_LENGTH-1
    //  - column == 0, column != 0
    //  - column == Board.DEFAULT_LENGTH-1, column != Board.DEFAULT_LENGTH-1
    // 
    // toString:
    //  - x == 0, x > 0
    //  - y == 0, y > 0
    // 
    // 
    // equals:
    // - coordinate1 != coordinate2, coordinate1 == coordinate2 
    // 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetXOrigin() {
        final int x = 0;
        final int y = 0;
        
        Coordinate coordinate = new Coordinate(x, y);
        
        assertEquals("Expected x == 0 for origin", 0, coordinate.getX());
    }
    
    @Test
    public void testGetXPoint() {
        final int x = 5;
        final int y = 6;
        
        Coordinate coordinate = new Coordinate(x, y);
        
        assertEquals("Expected to succeed when x > 0", 5, coordinate.getX());
    }
    
    @Test
    public void testGetYOrigin() {
        final int x = 0;
        final int y = 0;
        
        Coordinate coordinate = new Coordinate(x, y);
        
        assertEquals("Expected y == 0 for origin", 0, coordinate.getY());
    }
    
    @Test
    public void testGetYPoint() {
        final int x = 5;
        final int y = 6;
        
        Coordinate coordinate = new Coordinate(x, y);
        
        assertEquals("Expected to succeed when y > 0", 6, coordinate.getY());
    }
    
    @Test
    public void testChessNotationCorners() {
        String point1 = "a1";
        String point2 = "a8";
        String point3 = "h1";
        String point4 = "h8";
        
        Coordinate coordinate1 = new Coordinate(point1);
        Coordinate coordinate2 = new Coordinate(point2);
        Coordinate coordinate3 = new Coordinate(point3);
        Coordinate coordinate4 = new Coordinate(point4);

        assertEquals("Expected correct x-coordinate", 0, coordinate1.getX());
        assertEquals("Expected correct y-coordinate", 0, coordinate1.getY());
        
        assertEquals("Expected correct x-coordinate", 0, coordinate2.getX());
        assertEquals("Expected correct y-coordinate", 7, coordinate2.getY());
        
        assertEquals("Expected correct x-coordinate", 7, coordinate3.getX());
        assertEquals("Expected correct y-coordinate", 0, coordinate3.getY());
        
        assertEquals("Expected correct x-coordinate", 7, coordinate4.getX());
        assertEquals("Expected correct y-coordinate", 7, coordinate4.getY());
    }
    
    @Test
    public void testChessNotationEdges() {
        String point1 = "a2";
        String point2 = "b8";
        String point3 = "g1";
        String point4 = "h5";
        
        Coordinate coordinate1 = new Coordinate(point1);
        Coordinate coordinate2 = new Coordinate(point2);
        Coordinate coordinate3 = new Coordinate(point3);
        Coordinate coordinate4 = new Coordinate(point4);

        assertEquals("Expected correct x-coordinate", 0, coordinate1.getX());
        assertEquals("Expected correct y-coordinate", 1, coordinate1.getY());
        
        assertEquals("Expected correct x-coordinate", 1, coordinate2.getX());
        assertEquals("Expected correct y-coordinate", 7, coordinate2.getY());
        
        assertEquals("Expected correct x-coordinate", 6, coordinate3.getX());
        assertEquals("Expected correct y-coordinate", 0, coordinate3.getY());
        
        assertEquals("Expected correct x-coordinate", 7, coordinate4.getX());
        assertEquals("Expected correct y-coordinate", 4, coordinate4.getY());
    }
    
    @Test
    public void testChessNotationCenter() {
        String point = "d4";
        
        Coordinate coordinate = new Coordinate(point);
        
        assertEquals("Expected correct x-coordinate", 3, coordinate.getX());
        assertEquals("Expected correct y-coordinate", 3, coordinate.getY());
    }
    
    @Test
    public void testToStringOrigin() {
        String point = "a1";
        
        Coordinate coordinate = new Coordinate(point);
        
        assertEquals("Expected (0, 0) for origin", "(0, 0)" , coordinate.toString());
    }
    
    @Test
    public void testToStringCenter() {
        String point = "f7";
        
        Coordinate coordinate = new Coordinate(point);
        
        assertEquals("Expected correct toString for center square", "(5, 6)" , coordinate.toString());
    }
    
    @Test
    public void testEqualsSame() {
        Coordinate coordinate1 = new Coordinate("h8");
        Coordinate coordinate2 = new Coordinate(7, 7);
        
        assertTrue("Expected equivalent coordinates to be coordinate", coordinate1.equals(coordinate2));
    }
    
    @Test
    public void testEqualsDifferent() {
        Coordinate coordinate1 = new Coordinate(3, 4);
        Coordinate coordinate2 = new Coordinate(7, 2);
        
        assertFalse("Expected equals to fail on different coordinates", coordinate1.equals(coordinate2));
    }
    
}
