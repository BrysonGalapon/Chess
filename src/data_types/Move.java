package data_types;

import java.util.Set;

/**
 * Represents a Chess Move (handles castling). This class is immutable.
 * @author Bryson
 */
public interface Move {
    
    // Data-type Definition:
    // Move = Normal(piece:Piece, coordFrom:Coordinate, coordTo:Coordinate) + Castle(turnSide:PieceColor, castleSide:CastleSide)
    
    /**
     * Create a new move.
     *  - to castle, set squareFrom to be the square that the king that is castling is currently on
     *           and set squareTo to be the square that the king will land on after castling
     * @param squareFrom square that the current player clicked first
     * @param sqaureTo square that the current player clicked last
     * @return a Move object that represents the move that the current player made 
     */
    public static Move createMove(Square squareFrom, Square squareTo) {
        Piece movedPiece = squareFrom.getPiece();
        
        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
        
        Coordinate coordFrom = squareFrom.coordinate();
        Coordinate coordTo = squareTo.coordinate();
        boolean pieceMovedTwoSquares = Math.abs(coordFrom.getX() - coordTo.getX()) == 2;
        
        if ((movedPiece.equals(unmovedWhiteKing) || movedPiece.equals(unmovedBlackKing)) && pieceMovedTwoSquares) {
            if (movedPiece.equals(unmovedWhiteKing)) {
                // if the king lands on g1, then we castle kingside. Otherwise, we castle queenside.
                CastleSide castleSide = (coordTo.equals(new Coordinate("g1"))) ? CastleSide.KINGSIDE : CastleSide.QUEENSIDE;
                return new Castle(movedPiece.color(), castleSide);
            } else if (movedPiece.equals(unmovedBlackKing)) {
                // if the king lands on g1, then we castle kingside. Otherwise, we castle queenside.
                CastleSide castleSide = (coordTo.equals(new Coordinate("g8"))) ? CastleSide.KINGSIDE : CastleSide.QUEENSIDE;
                return new Castle(movedPiece.color(), castleSide);
            } else {
                throw new RuntimeException("Impossible to be here. You broke computer science.");
            }
        } else {
            return new Normal(movedPiece, coordFrom, coordTo);
        }
    }
    
    /**
     * Retrieve the pieces that moved
     * @return the set of pieces that move on this move
     */
    public Set<Piece> movedPieces();
    
    /**
     * Retrieve the coordinate of the square that the current player
     * has to click on first to make this move 
     * @return the coordinate of the square that the current player
     *          has to click on first to make this move
     */
    public Coordinate coordFrom();
    
    /**
     * Retrieve the coordinate of the square that the current player
     * has to click on last to make this move
     * @return the coordinate of the square that the current player
     *          has to click on last to make this move
     */
    public Coordinate coordTo();
    
    /**
     * Retrieve the set of coordinates on the chess board for which
     * the pieces at these coordinates change
     * @return the set of coordinates on the chess board for which
     *          the pieces at these coordinates change
     */
    public Set<Coordinate> coordinatesChanged();
    
    /**
     * Check if this move is a castling move
     * @return true if and only if this move is a castling move
     */
    public boolean isCastle();
    
    /**
     * Retrieve the string representation of this move
     * @return a string in the form "P: c1 -> c2", 
     * 
     *         where P is the piece in chess notation being moved,
     *               c1 is the coordinate that this piece is being moved from
     *               c2 is the coordinate that this piece is being moved to
     */
    @Override
    public String toString();
    
    @Override
    public boolean equals(Object other);
    
    @Override
    public int hashCode();
}
