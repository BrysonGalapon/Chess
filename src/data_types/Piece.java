package data_types;

import java.util.Set;

/**
 * Represents an Immutable Chess Piece
 * @author bgalapon
 */
public interface Piece {
    
    // Data-type Definition:
    // Piece = Pawn(moved:boolean, color:PieceColor) + Knight(moved:boolean, color:PieceColor) + 
    //         Bishop(moved:boolean, color:PieceColor) + King(moved:boolean, color:PieceColor) + 
    //         Queen(moved:boolean, color:PieceColor) + Rook(moved:boolean, color:PieceColor) +
    //         EmptyPiece()
    
    /**
     * Create a new pawn with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a pawn with color color that has (or has not) moved
     */
    public static Piece pawn(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new Pawn(color, moved);
    }
    
    /**
     * Create a new knight with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a knight with color color that has (or has not) moved
     */
    public static Piece knight(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new Knight(color, moved);
    }
    
    /**
     * Create a new bishop with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a bishop with color color that has (or has not) moved
     */
    public static Piece bishop(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new Bishop(color, moved);
    }
    
    /**
     * Create a new rook with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a rook with color color that has (or has not) moved
     */
    public static Piece rook(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new Rook(color, moved);
    }
    
    /**
     * Create a new queen with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a queen with color color that has (or has not) moved
     */
    public static Piece queen(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new Queen(color, moved);
    }
    
    /**
     * Create a new king with color color
     * @param color color of piece
     *          - requires !color.equals(PieceColor.TRANSPARENT)
     * @param moved whether of not this piece has moved
     * @return a king with color color that has (or has not) moved
     */
    public static Piece king(PieceColor color, boolean moved) {
        if (color.equals(PieceColor.TRANSPARENT)) {
            throw new IllegalArgumentException("Can't make a transparent piece!");
        }
        return new King(color, moved);
    }
    
    /**
     * Check if this piece has moved or not
     * @return true if this pawn has moved
     */
    public boolean hasMoved();

    /**
     * Retrieve the moved version of this piece. 
     * @return the moved version of this piece
     *          - if this piece has already moved, then 
     *            return itself
     */
    public Piece getMovedVersion();
    
    /** Retrieve the set of coordinates a particular chess piece can land on 
     *      on an empty board, including capture and castling moves, 
     *      when placed on coord.
     * @param coord coordinate to have 'placed' the piece
     * @return a set of coordinate indicating the set of coordinates this piece
     *          can move to on an empty board
     */
    public Set<Coordinate> moveSet(Coordinate coord);
    
    /**
     * Retrieve the color of this piece
     * @return the color of this piece
     *            - if this piece does not exist, return PieceColor.TRANSPARENT
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
    
    /**
     * Check if this piece is a king
     * @return true if this piece is a king
     */
    public boolean isKing();
    
    /**
     * Retrieve the value of this piece 
     * @return the material value of this piece, where the value mappings are given by:
     * 
     *      King   -> 0 points (king has an undefined material value)
     *      Queen  -> 9 points
     *      Rook   -> 5 points
     *      Bishop -> 3 points
     *      Knight -> 3 points
     *      Pawn   -> 1 point
     */
    public int value();
    
    // TODO Finish re-implementing equals/write tests for it
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
