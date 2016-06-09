package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an immutable Normal chess move that moves a single piece from one square to another
 * @author Bryson
 */
public class Normal implements Move {
    private final Piece piece;
    private final Coordinate coordFrom;
    private final Coordinate coordTo;
    
    // Abstraction Function:
    //  - represents the move of piece piece from the square at coordinate coordFrom to 
    //        the square at coordinate coordTo on a chess board
    // 
    // Rep Invariant:
    //  - moving the piece from coordFrom to coordTo is a valid chess move
    // 
    // Safety From Rep Exposure:
    //  - all fields are private and final
    //  - all mutable inputs are defensively copied
    //  - all mutable outputs are defensively copied
    // 
    
    /**
     * Create a new normal move
     * @param piece piece that is being moved
     * @param coordFrom coordinate that this piece is being moved from
     * @param coordTo coordinate that this piece is being moved to
     */
    public Normal(Piece piece, Coordinate coordFrom, Coordinate coordTo) {
        this.piece = piece;
        this.coordFrom = coordFrom;
        this.coordTo = coordTo;
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        Set<Coordinate> validTargets = piece.moveSet(coordFrom);
        
        assert validTargets.contains(coordTo);
    }

    @Override
    public Set<Piece> movedPieces() {
        Set<Piece> pieces = new HashSet<Piece>();
        pieces.add(piece);
        
        checkRep();
        return pieces;
    }

    @Override
    public Coordinate coordFrom() {
        return coordFrom;
    }

    @Override
    public Coordinate coordTo() {
        return coordTo;
    }

    @Override
    public boolean isCastle() {
        return false;
    }
    
    @Override
    public Set<Coordinate> coordinatesChanged() {
        Set<Coordinate> coordsChanged = new HashSet<>();
        coordsChanged.add(coordFrom);
        coordsChanged.add(coordTo);
        checkRep();
        return coordsChanged;
    }
    
    @Override
    public String toString() {
        return piece + ": " + coordFrom + " -> " + coordTo;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Normal)) {return false;}
        
        Normal otherNormal = (Normal) other;
        
        boolean piecesEquivalent = this.movedPieces().equals(otherNormal.movedPieces());
        boolean coordToEquivalent = this.coordTo().equals(otherNormal.coordTo());
        boolean coordFromEquivalent = this.coordFrom().equals(otherNormal.coordFrom());
        
        return piecesEquivalent && coordToEquivalent && coordFromEquivalent;
    }
    
    @Override
    public int hashCode() {
        return piece.hashCode() + coordTo.hashCode() + coordFrom.hashCode();
    }
}
