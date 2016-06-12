package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Bishop at a Coordinate on a chess board with color color
 * @author bgalapon
 */
public class Bishop implements Piece {
    private final PieceColor color;
    private final boolean moved;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);

    
    // Abstraction Function: 
    //  - represents a Bishop with color color located at coordinate coordinate
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
     * Create a new Bishop
     * @param color of this Bishop
     */
    public Bishop(PieceColor color, boolean moved) {
        this.color = color;
        this.moved = moved;
        checkRep();
    }
    
    /** 
     * Assert the Rep Invariant.
     */
    private void checkRep() {
        assert true;
    }
    
    @Override
    public Set<Coordinate> moveSet(Coordinate coord) {
        final Set<Coordinate> moves = new HashSet<Coordinate>();
        
        final int x = coord.getX();
        final int y = coord.getY();
        
        for (int i = -7; i <= 7; i++) {
            if (inRange(x+i, y+i) && i != 0) 
                moves.add(new Coordinate(x+i, y+i));
            if (inRange(x+i, y-i) && i != 0)
                moves.add(new Coordinate(x+i, y-i));
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
        if (! (other instanceof Bishop)) {return false;}
        
        Bishop otherBishop = (Bishop) other; 
        
        boolean moveSetSame = this.moveSet(D4_COORDINATE).equals(otherBishop.moveSet(D4_COORDINATE));
        boolean colorSame = this.color().equals(otherBishop.color());
        boolean movedSame = this.hasMoved() == otherBishop.hasMoved();
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return  moveSetSame && colorSame && movedSame;
    }
        
    @Override
    public String toString() {
        return "B";
    }
    
    @Override
    public int hashCode() {
        return moveSet(D4_COORDINATE).size() + (color().equals(PieceColor.BLACK) ? 0 : 1);
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean hasMoved() {
        return moved;
    }

    @Override
    public Piece getMovedVersion() {
        return new Bishop(color, true);
    }

    @Override
    public int value() {
        return 3;
    }
}
