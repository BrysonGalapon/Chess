package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Pawn at a Coordinate on a chess board
 * @author bgalapon
 */
public class Pawn implements Piece {
    private final PieceColor color;
    private final boolean moved;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);
    
    // Abstraction Function: 
    //  - represents a Pawn with color color located at coordinate coordinate that 
    //     has already moved if and only if moved is true
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
     * Create a new Pawn
     * @param color of this Pawn
     */
    public Pawn(PieceColor color, boolean moved) {
        this.color = color;
        this.moved = moved;
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
        
        final int scaleFactor = (color.equals(PieceColor.WHITE)) ? 1 : -1;
        
        moves.add(new Coordinate(x, y+scaleFactor)); // pawn can always move 1 square up
        
        if (!moved) {
            moves.add(new Coordinate(x, y+2*scaleFactor)); // pawn can move two squares on first move
        } 
        
        // captures
        if (x == 0 || x == Board.DEFAULT_SIZE - 1){ // pawn is on edge of board
            moves.add((x == 0) ? new Coordinate(x+1, y+scaleFactor) : new Coordinate(x-1,y+scaleFactor));
        } else {
            moves.add(new Coordinate(x+1, y+scaleFactor)); // capture left
            moves.add(new Coordinate(x-1, y+scaleFactor)); // capture right
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
        if (! (other instanceof Pawn)) {return false;}
        
        Pawn otherPawn = (Pawn) other; 
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        boolean moveSetSame = this.moveSet(D4_COORDINATE).equals(otherPawn.moveSet(D4_COORDINATE));
        boolean colorSame = this.color().equals(otherPawn.color());
        boolean movedSame = this.hasMoved() == otherPawn.hasMoved();
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return  moveSetSame && colorSame && movedSame;
    }
        
    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public int hashCode() {
        return moveSet(D4_COORDINATE).size() + (color().equals(PieceColor.BLACK) ? 0 : 1);
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean hasMoved() {
       return moved;
    }

    @Override
    public Piece getMovedVersion() {
        return new Pawn(color, true);
    }
}
