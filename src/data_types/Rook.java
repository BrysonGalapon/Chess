package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Rook at a Coordinate on a chess board
 * @author bgalapon
 */
public class Rook implements Piece {
    private final PieceColor color;
    private final boolean moved;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);

    
    // Abstraction Function: 
    //  - represents a Rook with color color located at coordinate coordinate
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
     * Create a new Rook
     * @param color of this Rook
     */
    public Rook(PieceColor color, boolean moved) {
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
    public Set<Coordinate> moveSet(Coordinate coordinate) {
        final Set<Coordinate> moves = new HashSet<Coordinate>();
        
        final int x = coordinate.getX();
        final int y = coordinate.getY();
        
        // Fix y, vary x
        for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
            if (i != x) 
                moves.add(new Coordinate(i, y));
        }
        
        // Fix x, vary y
        for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
            if (i != y) 
                moves.add(new Coordinate(x, i));
        }
        
        checkRep();
        return moves;
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
        if (! (other instanceof Rook)) {return false;}
        
        Rook otherRook = (Rook) other; 
        
        boolean moveSetSame = this.moveSet(D4_COORDINATE).equals(otherRook.moveSet(D4_COORDINATE));
        boolean colorSame = this.color().equals(otherRook.color());
        boolean movedSame = this.moved() == otherRook.moved();
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return  moveSetSame && colorSame && movedSame;
    }
        
    @Override
    public String toString() {
        return "R";
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
    public boolean moved() {
        return moved;
    }
}
