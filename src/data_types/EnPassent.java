package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an en passent move
 * @author Bryson
 */
public class EnPassent implements Move{
    
    private final Square squareFrom;
    private final Square squareTo;
    
    // Abstraction Function:
    //  TODO finish this
    // 
    // Rep Invariant:
    //  - piece being moved is a pawn
    //  - given piece's color, squareTo is one of the capture squares for piece being moved
    // 
    // Safety From Rep Exposure:
    //  TODO finish this
    // 
    
    /**
     * Create a new EnPassent object
     * @param squareFrom square to move pawn from
     * @param squareTo square to move pawn to
     */
    public EnPassent(Square squareFrom, Square squareTo) {
        this.squareFrom = squareFrom.squareCopy();
        this.squareTo = squareTo.squareCopy();
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        assert piece().isPawn();
        
        Coordinate sourceCoordinate = squareFrom.coordinate();
        Coordinate captureCoordinate = squareTo.coordinate();
        
        if (piece().color().equals(PieceColor.WHITE)) {
            boolean validCapture = captureCoordinate.getX() != sourceCoordinate.getX() &&
                                   captureCoordinate.getY() == sourceCoordinate.getY() + 1;
            assert validCapture;
        } else if (piece().color().equals(PieceColor.BLACK)) {
            boolean validCapture = captureCoordinate.getX() != sourceCoordinate.getX() &&
                                   captureCoordinate.getY() == sourceCoordinate.getY() - 1;
            assert validCapture;
        } else {
            throw new IllegalArgumentException("Piece color is not either one of white or black");
        }
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
        
        int scalar = piece().color().equals(PieceColor.WHITE) ? -1 : 1;
        
        // en passent capture square is one below coordTo on white and one above coordTo on black
        coordsChanged.add(new Coordinate(squareTo.coordinate().getX(), squareTo.coordinate().getY()+scalar));
        
        checkRep();
        return coordsChanged;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof EnPassent)) {return false;}
        
        EnPassent otherEnPassent = (EnPassent) other;
        
        boolean piecesEquivalent = this.movedPieces().equals(otherEnPassent.movedPieces());
        boolean coordToEquivalent = this.coordTo().equals(otherEnPassent.coordTo());
        boolean coordFromEquivalent = this.coordFrom().equals(otherEnPassent.coordFrom());
        
        return piecesEquivalent && coordToEquivalent && coordFromEquivalent;
    }
    
    @Override
    public int hashCode() {
        return piece().hashCode() + coordTo().hashCode() + coordFrom().hashCode();
    }
    
    @Override
    public String toString() {
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
    }

    @Override
    public boolean isCastle() {
        return false;
    }

    @Override
    public boolean isCapture() {
        return true;
    }

    @Override
    public Coordinate captureCoordinate() throws RuntimeException {
        int scalar = piece().color().equals(PieceColor.WHITE) ? -1 : 1;
        
        return new Coordinate(squareTo.coordinate().getX(), squareTo.coordinate().getY()+scalar);
    }
    
    @Override
    public boolean isPromotion() {
        return false;
    }

    @Override
    public Piece promotedPiece() throws RuntimeException {
        throw new RuntimeException("This move does not promote a piece");
    }

    @Override
    public boolean isEnPassent() {
        return true;
    }
    
    @Override
    public Piece capturedPiece() throws RuntimeException {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        if (piece().color().equals(white)) {
            return Piece.pawn(black, true);
        } else if (piece().color().equals(black)) {
            return Piece.pawn(white, true);
        } else {
            throw new RuntimeException("En passent capture pawn is not one of white or black");
        }
    }

    @Override
    public boolean isUndefined() {
        return false;
    }
}
