package data_types;

import java.util.Set;

/**
 * Represents a move that can not been defined
 * @author Bryson
 */
public class UndefinedMove implements Move {
    
    // Abstraction Function:
    //  TODO finish this
    // 
    // Rep Invariant:
    //  - true
    // 
    // Safety From Rep Exposure:
    //  TODO finish this
    // 
    
    /**
     * Create a new undefined move
     */
    public UndefinedMove() {
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        assert true;
    }

    @Override
    public Coordinate captureCoordinate() throws RuntimeException {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public Set<Piece> movedPieces() {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public Coordinate coordFrom() {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public Coordinate coordTo() {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public Set<Coordinate> coordinatesChanged() {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public boolean isCastle() {
        return false;
    }

    @Override
    public boolean isPromotion() {
        return false;
    }

    @Override
    public boolean isEnPassent() {
        return false;
    }

    @Override
    public Piece promotedPiece() throws RuntimeException {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public Piece capturedPiece() throws RuntimeException {
        throw new RuntimeException("Called operation on undefined move");
    }

    @Override
    public boolean isCapture() {
        return false;
    }

    @Override
    public boolean isUndefined() {
        return true;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof UndefinedMove)) {return false;}
        
        return true;
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Called operation on undefined move");
    }
}
