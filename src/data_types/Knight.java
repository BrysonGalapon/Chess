package data_types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Knight at a Coordinate on a chess board with color color
 * @author bgalapon
 */
public class Knight implements Piece {
    private final PieceColor color;
    private final boolean moved;
    
    private static final Coordinate D4_COORDINATE = new Coordinate(3, 3);
    
    // Abstraction Function: 
    //  - represents a Knight with color color located at coordinate coordinate
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
     * Create a new Knight
     * @param color of this Knight
     */
    public Knight(PieceColor color, boolean moved) {
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
        final Set<Coordinate> moves = new HashSet<Coordinate>();
        
        final int x = coord.getX();
        final int y = coord.getY();
        
        List<List<Integer>> vectors = Arrays.asList(Arrays.asList(2,1), 
                                                    Arrays.asList(2,-1),
                                                    Arrays.asList(1,2),
                                                    Arrays.asList(1,-2),
                                                    Arrays.asList(-2,1),
                                                    Arrays.asList(-2,-1),
                                                    Arrays.asList(-1,2),
                                                    Arrays.asList(-1,-2));
        
        for (List<Integer> vectorPair : vectors) {
            final int xCoord = x + vectorPair.get(0);
            final int yCoord = y + vectorPair.get(1);
            
            if (inRange(xCoord,yCoord)) {
                moves.add(new Coordinate(xCoord,yCoord));
            }
        }
        
        checkRep();
        return moves;
    }
    
    /** Check to see if this coordinate pair is in range of a 8x8 board 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    private boolean inRange(int x, int y) {
        boolean xInRange = 0 <= x && x < Board.DEFAULT_SIZE;
        boolean yInRange = 0 <= y && y < Board.DEFAULT_SIZE;
        
        return xInRange && yInRange;
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
        if (! (other instanceof Knight)) {return false;}
        
        Knight otherKnight = (Knight) other; 
        
        boolean moveSetSame = this.moveSet(D4_COORDINATE).equals(otherKnight.moveSet(D4_COORDINATE));
        boolean colorSame = this.color().equals(otherKnight.color());
        boolean movedSame = this.hasMoved() == otherKnight.hasMoved();
        
        // considered equivalent if they have an equivalent moveSet when placed on d4
        return  moveSetSame && colorSame && movedSame;
    }
        
    @Override
    public String toString() {
        return "N";
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
        return new Knight(color, true);
    }

    @Override
    public int value() {
        return 3;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
