package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the immutable chess move castling
 * @author Bryson
 */
public class Castle implements Move{
    /** The side (white or black) that is castling */
    private final PieceColor turnSide;
    /** The side of the board (queenside or kingside) to castle to */
    private final CastleSide castleSide;
    
    // Abstraction Function:
    //  - represents the chess move of player turnSide that is castling castleSide
    // 
    // Rep Invariant:
    //  - true
    // 
    // Safety From Rep Exposure:
    //  - all fields are private and final
    //  - all mutable inputs are defensively copied
    //  - all mutable outputs are defensively copied
    // 

    /**
     * Create a new Castle object
     * @param turnSide side (white or black) that is castling
     * @param castleSide side (queenside or kingside) to castle to
     */
    public Castle(PieceColor turnSide, CastleSide castleSide) {
        this.turnSide = turnSide;
        this.castleSide = castleSide;
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        assert true;
    }

    @Override
    public Set<Piece> movedPieces() {
        Piece unmovedKing = Piece.king(turnSide, false);
        Piece unmovedRook = Piece.rook(turnSide, false);
        
        Set<Piece> pieces = new HashSet<Piece>();
        pieces.add(unmovedRook);
        pieces.add(unmovedKing);
        
        checkRep();
        return pieces;
    }

    @Override
    public Coordinate coordFrom() {
        return (turnSide.equals(PieceColor.WHITE)) ? new Coordinate("e1") : new Coordinate("e8");
    }

    @Override
    public Coordinate coordTo() {
        int y = (turnSide.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE-1; // 0-indexed y-placement of king
                                                                                // either the first rank if white, or eigth rank if black
        
        int x = (castleSide.equals(CastleSide.KINGSIDE)) ? 6 : 2; // 0-indexed x-placement of king  
                                                                  // either the g file if kingside, or the c file if queenside
        
        checkRep();
        return new Coordinate(x, y);
    }

    @Override
    public boolean isCastle() {
        return true;
    }
    
    @Override
    public Set<Coordinate> coordinatesChanged() {
        Set<Coordinate> coordsChanged = new HashSet<>();
        int rookY = (turnSide.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE-1; 
        int rookFromX = (castleSide.equals(CastleSide.KINGSIDE)) ? Board.DEFAULT_SIZE-1 : 0; // 0-indexed y-placement of king
                                                                                             // either the first rank if white, or eigth rank if black
        int rookToX = (castleSide.equals(CastleSide.KINGSIDE)) ? 5 : 3; // 0-indexed x-placement of rook  
                                                                        // either the f file if kingside, or the d file if queenside
        
        coordsChanged.add(coordFrom());
        coordsChanged.add(coordTo());
        coordsChanged.add(new Coordinate(rookFromX, rookY));
        coordsChanged.add(new Coordinate(rookToX, rookY));
        checkRep();
        return coordsChanged;
    }
    
    @Override
    public String toString() {
        Piece king = Piece.king(turnSide, false);
        
        return king + ": " + coordFrom() + " -> " + coordTo();
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Castle)) {return false;}
        
        Castle otherCastle = (Castle) other;
        
        boolean piecesEquivalent = this.movedPieces().equals(otherCastle.movedPieces());
        boolean coordToEquivalent = this.coordTo().equals(otherCastle.coordTo());
        boolean coordFromEquivalent = this.coordFrom().equals(otherCastle.coordFrom());
        
        return piecesEquivalent && coordToEquivalent && coordFromEquivalent;
    }
    
    @Override
    public int hashCode() {
        Piece king = Piece.king(turnSide, false);
        
        return king.hashCode() + coordTo().hashCode() + coordFrom().hashCode();
    }
}
