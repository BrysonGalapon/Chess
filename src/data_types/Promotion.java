package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a move that promotes a pawn to a piece in chess
 * @author Bryson
 */
public class Promotion implements Move {
    
    private final Square squareFrom;
    private final Square squareTo;
    private final Piece promotedPiece;
    
    // Abstraction Function:
    //  TODO finish this
    // 
    // Rep Invariant:
    //  - piece being moved is a pawn
    //  - promotedPiece is one of queen, king, bishop, or knight
    //  - promotedPiece is same color as piece being moved
    //  - piece on squareTo is opposite color to piece on squareFrom
    // 
    // Safety From Rep Exposure:
    //  TODO finish this
    // 
    
    /**
     * Create a new Promotion move
     * @param squareFrom square to move pawn
     * @param squareTo
     * @param promotedPiece
     */
    public Promotion(Square squareFrom, Square squareTo, Piece promotedPiece) {
        this.squareFrom = squareFrom.squareCopy();
        this.squareTo = squareTo.squareCopy();
        this.promotedPiece = promotedPiece;
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        assert piece().color().equals(promotedPiece.color());
        assert piece().isPawn();
        assert promotedPiece.toString().matches("Q|N|R|B");
        assert !piece().color().equals(squareTo.getPiece().color());
    }

    /**
     * Retrieve the piece moved on this move
     * @return the piece moved on this move
     */
    public Piece piece() {
        return squareFrom.getPiece();
    }
    
    @Override
    public Piece promotedPiece() {
        return promotedPiece;
    }
    
    @Override
    public Set<Piece> movedPieces() {
        Set<Piece> movedPieces = new HashSet<>();
        movedPieces.add(piece());
        
        checkRep();
        return movedPieces;
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
    public Set<Coordinate> coordinatesChanged() {
        Set<Coordinate> coordsChanged = new HashSet<>();
        coordsChanged.add(squareFrom.coordinate());
        coordsChanged.add(squareTo.coordinate());
        
        checkRep();
        return coordsChanged;
    }

    @Override
    public boolean isCastle() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return piece().hashCode() + coordTo().hashCode() + coordFrom().hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Promotion)) {return false;}
        
        Promotion otherPromotion = (Promotion) other;
        
        boolean piecesEquivalent = this.movedPieces().equals(otherPromotion.movedPieces());
        boolean coordToEquivalent = this.coordTo().equals(otherPromotion.coordTo());
        boolean coordFromEquivalent = this.coordFrom().equals(otherPromotion.coordFrom());
        boolean promotedPieceEquivalent = this.promotedPiece().equals(otherPromotion.promotedPiece());
        
        return piecesEquivalent && coordToEquivalent && coordFromEquivalent && promotedPieceEquivalent;
    }
    
    @Override
    public String toString() {
        if (isCapture()) {
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
            
            return file + "x" + squareTo.coordinate() + "=" + promotedPiece;
        } else {
            return squareTo.coordinate() + "=" + promotedPiece;
        }
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
        return true;
    }

    @Override
    public boolean isEnPassent() {
        return false;
    }

    @Override
    public Piece capturedPiece() throws RuntimeException {
        if (isCapture()) {
            return squareTo.getPiece();
        } else {
            throw new RuntimeException("This move is not a capture move");
        }
    }
}
