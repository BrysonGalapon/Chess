package data_types;

import java.util.Set;

/**
 * Represents the absence of a chess piece
 * @author Bryson
 */
public class EmptyPiece implements Piece {
    
    // Abstraction Function: 
    //  - represents the absence of a chess piece
    // 
    // Rep Invariant: 
    //  -  true
    // 
    // Safety from Rep Exposure:
    //  - there is no representation, and so therefore it can not be exposed
    //
    
    /**
     * Assert the Rep Invariant.
     */
    private void checkRep() {
        assert true;
    }

    @Override
    public Set<Coordinate> moveSet(Coordinate coord) {
        checkRep();
        throw new RuntimeException("Called operation on non-existent piece");
    }

    @Override
    public PieceColor color() {
        throw new RuntimeException("Called operation on non-existent piece");
    }

    @Override
    public boolean exists() {
        return false;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof EmptyPiece)) {return false;}
        
        return true;
    }
        
    @Override
    public String toString() {
        return "non-existent";
    }
    
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean moved() {
        throw new RuntimeException("Called operation on non-existent piece");
    }
}
