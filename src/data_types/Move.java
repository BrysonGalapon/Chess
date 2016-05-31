package data_types;

import java.util.Set;

/**
 * Represents a Chess Move. This class is immutable.
 * @author Bryson
 */
public class Move {
    private final Piece piece;
    private final Square squareFrom;
    private final Square squareTo;
    
    // Abstraction Function:
    //  - represents the move of piece piece form square squareFrom to 
    //        square squareTo on a chess board
    // 
    // Rep Invariant:
    //  - moving the piece from squareFrom to squareTo is a valid chess move
    // 
    // Safety From Rep Exposure:
    //  - all fields are private and final
    //  - all mutable inputs are defensively copied
    //  - all mutable outputs are defensively copied
    // 
    
    /**
     * Create a new Move object
     * @param piece Piece to move
     * @param squareFrom square to move this piece from
     * @param squareTo square to move this piece to
     */
    public Move(Piece piece, Square squareFrom, Square squareTo) {
        this.piece = piece;
        this.squareFrom = new Square(squareFrom.squareCoordinate());
        this.squareTo = new Square(squareTo.squareCoordinate());
        
        if (squareFrom.isOccupied()) {
            this.squareFrom.addPiece(squareFrom.getPiece());
        } 
        
        if (squareTo.isOccupied()) {
            this.squareTo.addPiece(squareTo.getPiece());
        } 
        
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        Coordinate source = squareFrom.squareCoordinate();
        Coordinate target = squareTo.squareCoordinate();
        
        Set<Coordinate> validTargets = piece.moveSet(source);
        
        assert validTargets.contains(target);
    }
    
    /**
     * Retrieve the piece that is being moved
     * @return the piece that is being moved
     */
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * Retrieve the square that this piece is being moved from
     * @return the square that this piece is being moved from
     */
    public Square squareFrom() {
        Square squareFromCopy = new Square(squareFrom.squareCoordinate()); 
        if (squareFrom.isOccupied()) {
            squareFromCopy.addPiece(squareFrom.getPiece());
        }
        
        checkRep();
        return squareFromCopy;
    }
    
    /**
     * Retrieve the square that this piece is being moved to
     * @return the square that this piece is being moved to
     */
    public Square squareTo() {
        Square squareToCopy = new Square(squareTo.squareCoordinate()); 
        if (squareTo.isOccupied()) {
            squareToCopy.addPiece(squareTo.getPiece());
        }
        
        checkRep();
        return squareToCopy;
    }
    
    /**
     * Retrieve the string representation of this move
     * @return a string in the form "P: s1 -> s2", 
     * 
     *         where P is the piece in chess notation being moved,
     *               s1 is the square that this piece is being moved from
     *               s2 is the square that this piece is being moved to
     */
    @Override
    public String toString() {
        return piece + ": " + squareFrom + " -> " + squareTo;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Move)) {return false;}
        
        Move otherMove = (Move) other;
        
        boolean pieceEquivalent = this.getPiece().equals(otherMove.getPiece());
        boolean squareToEquivalent = this.squareTo().equals(otherMove.squareTo());
        boolean squareFromEquivalent = this.squareTo().equals(otherMove.squareTo());
        
        return pieceEquivalent && squareToEquivalent && squareFromEquivalent;
    }
    
    @Override
    public int hashCode() {
        return piece.hashCode();
    }
}
