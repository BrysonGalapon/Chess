package data_types;

import java.util.Set;

/**
 * Represents a Queen at a Coordinate on a chess board
 * @author bgalapon
 */
public class Queen implements Piece {
    private final PieceColor color;
    private final boolean moved;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);
    
    // Abstraction Function: 
    //  - represents a Queen with color color located at coordinate coordinate
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
     * Create a new Queen
     * @param color of this Queen
     */
    public Queen(PieceColor color, boolean moved) {
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
        // imagine a rook and a bishop on the same square
        Piece rook = Piece.rook(color, moved);
        Piece bishop = Piece.bishop(color, moved);
        
        // concactenate their moveSets
        final Set<Coordinate> moves = rook.moveSet(coord);
        moves.addAll(bishop.moveSet(coord));
        
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
        if (! (other instanceof Queen)) {return false;}
        
        Queen otherQueen = (Queen) other; 
        
        boolean moveSetSame = this.moveSet(D4_COORDINATE).equals(otherQueen.moveSet(D4_COORDINATE));
        boolean colorSame = this.color().equals(otherQueen.color());
        boolean movedSame = this.hasMoved() == otherQueen.hasMoved();
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return  moveSetSame && colorSame && movedSame;        
    }
        
    @Override
    public String toString() {
        return "Q";
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
        return new Queen(color, true);
    }

}
