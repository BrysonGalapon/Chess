package data_types;

import java.util.HashMap;
import java.util.Map;

/** Represents an Immutable (x,y) coordinate on the chess board, where axes extend to the right and upwards so that
 *  - (0,0) is the a1 square
 *  - (1,0) is the b1 square
 *  - (0,1) is the a2 square 
 * @author bgalapon
 *
 */
public class Coordinate {
    private final int x;
    private final int y;
    
    private final Map<Character, Integer> letterToNumber = new HashMap<Character, Integer>();
    
    // Abstraction Function:
    //  - represents a coordinate (x, y) on the plane
    //  
    // Rep Invariant:
    //  - 0 <= x <= Board.DEFAULT_SIZE-1
    //  - 0 <= y <= Board.DEFAULT_SIZE-1
    // 
    // Safety from Rep Exposure:
    //  - all fields are private and final
    //  - all inputs are immutable
    //  - all outputs are immutable
    
    /** Create a new chess coordinate using standard (x,y) coordinates
     *  - Requires that 0 <= x,y < Board.DEFAULT_SIZE
     */
    public Coordinate(int x, int y) {
        initializeMap();
        
        this.x = x;
        this.y = y;
        
        checkRep();
    }
    
    /**
     * Create a new chess coordinate using chess notation
     *  - Requires that coordinate matches the regex [a-h][1-8]
     * @param coordinate
     */
    public Coordinate(String coordinate) {
        initializeMap();
        
        String coordinateRegex = "[a-h][1-8]";
        
        if (! coordinate.matches(coordinateRegex)) {
            throw new IllegalArgumentException("Could not recognize chess coordinate");
        }
        
        this.x = letterToNumber.get(coordinate.charAt(0));  // take the letter and convert to 0-indexed number
        this.y = Integer.valueOf(coordinate.substring(1,2)) - 1;
        
        checkRep();
    }
    
    /**
     * Configure the letterToNumber map
     */
    private void initializeMap() {
        letterToNumber.put('a', 0);
        letterToNumber.put('b', 1);
        letterToNumber.put('c', 2);
        letterToNumber.put('d', 3);
        letterToNumber.put('e', 4);
        letterToNumber.put('f', 5);
        letterToNumber.put('g', 6);
        letterToNumber.put('h', 7);
    }
    
    /** Retrieve the X-coordinate of this Coordinate
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }
    
    /** Retrieve the Y-coordinate of this Coordinate
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Assert the Rep Invariant.
     */
    private void checkRep() {
        assert 0 <= x && x < Board.DEFAULT_SIZE;
        assert 0 <= y && y < Board.DEFAULT_SIZE;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Coordinate)) {return false;}
        
        Coordinate otherCoordinate = (Coordinate) other; 
        
        return this.getX() == otherCoordinate.getX() && this.getY() == otherCoordinate.getY();
    }
    
    /**
     * Retrieve the string representation of this coordinate
     * 
     * @return a string in the form "(x, y)", where (x, y) is the coordinate associated
     *         to this Coordinate
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }
    
    @Override
    public int hashCode() {
        return getX()*getY();
    }

    //TODO: Add tests for toString, equals

}
