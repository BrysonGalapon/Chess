package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a King at a Coordinate on a chess board with color color
 * @author bgalapon
 */
public class King implements Piece {
    private final PieceColor color;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);
    
    // Abstraction Function: 
    //  - represents a King with color color located at coordinate coordinate
    // 
    // Rep Invariant: 
    //  -  true
    // 
    // Safety from Rep Exposure:
    //  - all fields are private and final
    //  - all inputs are immutable
    //  - all outputs that are mutable are defensively copied
    //
    
    /**
     * Create a new King
     * @param color of this King
     */
    public King(PieceColor color) {
        this.color = color;
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        assert true;
    }
    
    @Override
    public Set<Coordinate> moveSet(Coordinate coord) {
        final Set<Coordinate> moves = new HashSet<Coordinate>();
        
        final int x = coord.getX();
        final int y = coord.getY();
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1; j++) {
                if ((!(i==0 && j==0)) && inRange(x+i, y+j))
                    moves.add(new Coordinate(x+i, y+j));
            }
        }
        
        checkRep();
        return moves;
    }

    /** Check to see if this coordinate pair is in range of a 8x8 board 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    private boolean inRange(int x, int y) {
        boolean xInRange = 0 <= x && x <= 7;
        boolean yInRange = 0 <= y && y <= 7;
        
        return xInRange && yInRange;
    }
    @Override
    public PieceColor color() {
        return color;
    }

    @Override
    public boolean exists() {
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof King)) {return false;}
        
        King otherKing = (King) other; 
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return this.moveSet(D4_COORDINATE).equals(otherKing.moveSet(D4_COORDINATE)) && this.color().equals(otherKing.color());
    }
        
    @Override
    public String toString() {
        return "K";
    }
    
    @Override
    public int hashCode() {
        return moveSet(D4_COORDINATE).size() + (color().equals(PieceColor.BLACK) ? 0 : 1);
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}

