package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an immutable Normal chess move that moves a single piece from one square to another
 * @author Bryson
 */
public class Normal implements Move {
    private final Square squareFrom;
    private final Square squareTo;
    
    // Abstraction Function:
    //  - represents the move of piece piece from the square at coordinate coordFrom to 
    //        the square at coordinate coordTo on a chess board, possibly taking capturedPiece 
    //        on the arrival square
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
     * @param squareFrom coordinate that this piece is being moved from
     * @param squareTo coordinate that this piece is being moved to
     * @throws IllegalArgumentException if move is an invalid chess move
     */
    public Normal(Square squareFrom, Square squareTo) throws IllegalArgumentException{
        this.squareFrom = squareFrom.squareCopy();
        this.squareTo = squareTo.squareCopy();
        
        if (!piece().moveSet(squareFrom.coordinate()).contains(squareTo.coordinate())) {
            throw new IllegalArgumentException("Illegal move attempted");
        }
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        Set<Coordinate> validTargets = piece().moveSet(squareFrom.coordinate());
        
        assert validTargets.contains(squareTo.coordinate());
    }

    /**
     * Retrieve the piece moved on this move
     * @return the piece moved on this move
     */
    public Piece piece() {
        return squareFrom.getPiece();
    }
    
    @Override
    public Set<Piece> movedPieces() {
        Set<Piece> pieces = new HashSet<Piece>();
        pieces.add(piece());
        
        checkRep();
        return pieces;
    }

    @Override
    public Coordinate coordFrom() {
        return squareFrom.coordinate();
    }

    @Override
    public Coordinate coordTo() {
        return squareTo.coordinate();
    }

    @Override
    public boolean isCastle() {
        return false;
    }
    
    @Override
    public Set<Coordinate> coordinatesChanged() {
        Set<Coordinate> coordsChanged = new HashSet<>();
        coordsChanged.add(squareFrom.coordinate());
        coordsChanged.add(squareTo.coordinate());
        
        checkRep();
        return coordsChanged;
    }
    
    @Override
    public String toString() {
        if (isCapture()) {
            if (piece().isPawn()) {
                String file = "";
                switch (squareFrom.coordinate().getX()) {
                case 0: 
                    file += "a";
                    break;
                case 1:
                    file += "b";
                    break;
                case 2:
                    file += "c";
                    break;
                case 3:
                    file += "d";
                    break;
                case 4:
                    file += "e";
                    break;
                case 5:
                    file += "f";
                    break;
                case 6:
                    file += "g";
                    break;
                case 7:
                    file += "h";
                    break;
                 default:
                     throw new RuntimeException("Square From is outside of board!");
                }    
                
                return file + "x" + squareTo.coordinate();
            } else {
                return piece().toString() + "x" + squareTo.coordinate();
            }
        } else {
            return piece().toString() + squareTo.toString();
        }
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
        return piece().hashCode() + coordTo().hashCode() + coordFrom().hashCode();
    }

    @Override
    public boolean isCapture() {
        Piece capturedPiece = squareTo.getPiece();
        
        if (!capturedPiece.exists()) {return false;}
        
        // return true if capturing opposite color
        return !(capturedPiece.color().equals(piece().color()));
    }

    @Override
    public Coordinate captureCoordinate() throws RuntimeException {
        if (isCapture()) {
            return squareTo.coordinate();
        } else {
            throw new RuntimeException("This move is not a capture move");
        }
    }
    
    @Override
    public boolean isPromotion() {
        return false;
    }

    @Override
    public Piece promotedPiece() throws RuntimeException {
        throw new RuntimeException("This move does not promote a piece");
    }
}
