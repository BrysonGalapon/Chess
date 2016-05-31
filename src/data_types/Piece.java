package data_types;

import java.util.Set;

/**
 * Represents an Immutable Chess Piece
 * @author bgalapon
 */
public interface Piece {
    
    // Data-type Definition:
    // Piece = Pawn(coord:Coordinate, color:PieceColor) + Knight(coord:Coordinate, color:PieceColor) + 
    //         Bishop(coord:Coordinate, color:PieceColor) + King(coord:Coordinate, color:PieceColor) + 
    //         Queen(coord:Coordinate, color:PieceColor) + Rook(coord:Coordinate, color:PieceColor) +
    //         EmptyPiece()
    
    /**Create a new pawn with color color
     * @param moved whether or not this pawn has moved
     */
    public static Piece pawn(PieceColor color, boolean moved) {
        return new Pawn(color, moved);
    }
    
    /**Create a new knight with color color
     */
    public static Piece knight(PieceColor color) {
        return new Knight(color);
    }
    
    /**Create a new bishop with color color
     */
    public static Piece bishop(PieceColor color) {
        return new Bishop(color);
    }
    
    /**Create a new rook with color color
     */
    public static Piece rook(PieceColor color) {
        return new Rook(color);
    }
    
    /**Create a new queen with color color
     */
    public static Piece queen(PieceColor color) {
        return new Queen(color);
    }
    
    /**Create a new king with color color
     */
    public static Piece king(PieceColor color) {
        return new King(color);
    }
    
    /** Retrieve the move set of a particular chess piece on an empty board, including 
     *      capture moves, when placed on coord
     * @param coord coordinate to have 'placed' the piece
     * @return a set of coordinate indicating the set of coordinates this piece
     *          can move to on an empty board
     */
    public Set<Coordinate> moveSet(Coordinate coord);
    
    /**
     * Retrieve the color of this piece
     * @return the color of this piece
     */
    public PieceColor color();
    
    /**
     * Check if this piece exists
     * @return true only if this piece exists
     */
    public boolean exists();

    /**
     * Check if this piece is a pawn
     * @return true if this piece is a pawn
     */
    public boolean isPawn();
    
    @Override
    public boolean equals(Object other);
        
    
    /**
     * Return the Chess Notation version of this piece
     * @return if piece.exists():
     *         - "K" if this piece is a king,
     *         - "Q" if this piece is a queen,
     *         - "R" if this piece is a rook,
     *         - "B" if this piece is a bishop,
     *         - "N" if this piece is a knight,
     *         - ""  if this piece is a pawn,
     *         
     *         else:
     *         - "non-existent"
     */
    @Override
    public String toString();
    
    @Override
    public int hashCode();
        
}
