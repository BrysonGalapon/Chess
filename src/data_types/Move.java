package data_types;

import java.util.Scanner;
import java.util.Set;

/**
 * Represents a Chess Move (handles castling). This class is immutable.
 * @author Bryson
 */
public interface Move {
    
    // Data-type Definition:
    // Move = Normal(piece:Piece, squareFrom:Square, squareTo:Square) + Castle(turnSide:PieceColor, castleSide:CastleSide)
    //      + Promotion(piece:Piece, squareFrom:Square, squareTo:Square, Piece:promotedPiece)
    //      + EnPassent(squareFrom:Square, squareTo:Square) + UndefinedMove()
    
    /**
     * Create a new move.
     *  - to castle, set squareFrom to be the square that the king that is castling is currently on
     *           and set squareTo to be the square that the king will land on after castling
     *  - this method does not handle en passent captures, @see enPassent method
     * @param squareFrom square that the current player clicked first
     * @param sqaureTo square that the current player clicked last
     * @return a Move object that represents the move that the current player made 
     * @throws IllegalArgumentException if move is an invalid chess move
     */
    public static Move createMove(Square squareFrom, Square squareTo) throws IllegalArgumentException{
        Square squareFromCopy = squareFrom.squareCopy();
        Square squareToCopy = squareTo.squareCopy();
        
        Piece movedPiece = squareFrom.getPiece();
        Coordinate coordFrom = squareFrom.coordinate();
        Coordinate coordTo = squareTo.coordinate();
        
        // detect promotion
        if (movedPiece.isPawn() && ((coordTo.getY() == 7) || (coordTo.getY() == 0))) {
            
            // white can't promote on 1st rank
            if (movedPiece.color().equals(PieceColor.WHITE) && coordTo.getY() == 0) {
                throw new IllegalArgumentException("Invalid promotion");
            }
            
            // black can't promote on 8th rank
            if (movedPiece.color().equals(PieceColor.BLACK) && coordTo.getY() == 7) {
                throw new IllegalArgumentException("Invalid promotion");
            }
            
            // can only promote to a square you can reach
            if (!movedPiece.moveSet(coordFrom).contains(coordTo)) {
                throw new IllegalArgumentException("Unreachable destination");
            }
            
            // TODO figure out how to use only *one* reader 
            @SuppressWarnings("resource")
            Scanner reader = new Scanner(System.in);
            System.out.println("What piece would you like to promote to?");
            System.out.println("You have the option of: Q, R, B, N");
            try {
                String piece = reader.nextLine();
                String regex = "Q|R|B|N";
                
                if (!piece.matches(regex)) {
                    throw new IllegalArgumentException("Unrecognized choice for promoted piece");
                }
                
                switch (piece) {
                case "Q":
                    return promote(squareFromCopy, squareToCopy, Piece.queen(movedPiece.color(), true));
                case "R":
                    return promote(squareFromCopy, squareToCopy, Piece.rook(movedPiece.color(), true));
                case "B":
                    return promote(squareFromCopy, squareToCopy, Piece.bishop(movedPiece.color(), true));
                case "N":
                    return promote(squareFromCopy, squareToCopy, Piece.knight(movedPiece.color(), true));
                default:
                    throw new RuntimeException("Regex failed to capture only queen, rook, bishop, or knight promotions");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
        
        boolean pieceMovedTwoSquares = Math.abs(coordFrom.getX() - coordTo.getX()) == 2;
        
        if ((movedPiece.equals(unmovedWhiteKing) || movedPiece.equals(unmovedBlackKing)) && pieceMovedTwoSquares) {
            if (movedPiece.equals(unmovedWhiteKing)) {
                // if the king lands on g1, then we castle kingside. Otherwise, we castle queenside.
                CastleSide castleSide = (coordTo.equals(new Coordinate("g1"))) ? CastleSide.KINGSIDE : CastleSide.QUEENSIDE;
                return new Castle(movedPiece.color(), castleSide);
            } else if (movedPiece.equals(unmovedBlackKing)) {
                // if the king lands on g8, then we castle kingside. Otherwise, we castle queenside.
                CastleSide castleSide = (coordTo.equals(new Coordinate("g8"))) ? CastleSide.KINGSIDE : CastleSide.QUEENSIDE;
                return new Castle(movedPiece.color(), castleSide);
            } else {
                throw new RuntimeException("Impossible to be here. You broke computer science.");
            }
        } else {
            return new Normal(squareFrom, squareTo);
        }
    }
    
    /**
     * Create a new promotion.
     * @param squareFrom square that the current player clicked first
     * @param squareTo square that the current player clicked second
     *          - requires that squareTo lives on either the 1st or 8th rank
     * @param piece piece to promote to
     * @return a Move object representing the promotion of a pawn to piece
     * @throws IllegalArgumentException if squareFrom -> squareTo is not a legal promotion move
     */
    public static Move promote(Square squareFrom, Square squareTo, Piece piece) throws IllegalArgumentException {
        Piece movedPiece = squareFrom.getPiece();
        
        // can't promote if:
        //                   - the piece isn't a pawn,
        //                   - promoted piece is not of the same color as promoting pawn
        //                   - pawn is not landing on the 8th/1st rank
        if (!movedPiece.isPawn() || !piece.color().equals(movedPiece.color()) 
                || !((squareTo.coordinate().getY() == 7) || (squareTo.coordinate().getY() == 0))) {
            throw new IllegalArgumentException("Invalid promotion");
        }
        
        return new Promotion(squareFrom, squareTo, piece);
    }
    
    /**
     * Create a new en passent capture
     * @param squareFrom square to move pawn from
     * @param squareTo square that is being captured on
     * @return a Move object representing an en passent move from squareFrom to squareTo
     * @throws IllegalArgumentException if squareFrom -> squareTo is not a legal en passent move
     */
    public static Move enPassent(Square squareFrom, Square squareTo) throws IllegalArgumentException {
        
        Piece movedPiece = squareFrom.getPiece();
        
        Coordinate sourceCoordinate = squareFrom.coordinate();
        Coordinate captureCoordinate = squareTo.coordinate();
        
        boolean validCapture;
        
        if (movedPiece.color().equals(PieceColor.WHITE)) {
            validCapture = captureCoordinate.getX() != sourceCoordinate.getX() &&
                                   captureCoordinate.getY() == sourceCoordinate.getY() + 1;
        } else if (movedPiece.color().equals(PieceColor.BLACK)) {
            validCapture = captureCoordinate.getX() != sourceCoordinate.getX() &&
                                   captureCoordinate.getY() == sourceCoordinate.getY() - 1;
        } else {
            throw new IllegalArgumentException("Piece color is not either one of white or black");
        }
        
        if (!movedPiece.isPawn() || !validCapture) {
            throw new IllegalArgumentException("Invalid en passent");
        }
        
        return new EnPassent(squareFrom, squareTo);
    }
    
    /**
     * Retrieve an undefined move
     * @return a move that can not be defined
     */
    public static Move undefined() {
        return new UndefinedMove();
    }
    
    /**
     * Retrieve the capture coordinate for this move
     * @return the capture coordinate for this move
     * @throws RuntimeException if this move is not a capture move
     */
    public Coordinate captureCoordinate() throws RuntimeException;
    
    /**
     * Retrieve the pieces that moved
     * @return the set of pieces that move on this move, not including the pieces that get captured
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
     * the pieces at these coordinates change given that this move is played
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
     * Check if this move promotes a piece
     * @return true if and only if this move promotes a piece
     */
    public boolean isPromotion();

    /**
     * Check if this move is an en passent capture
     * @return true if and only if this move is an en passent capture
     */
    public boolean isEnPassent();
    
    /**
     * Retrieve the piece that is being promoted on this move
     * @return the piece that is being promoted on this move
     * @throws RuntimeException if this move does not promote a piece
     */
    public Piece promotedPiece() throws RuntimeException;
    
    /**
     * Retrieve the piece that was captured on this move
     * @return the piece that was captured on this move
     * @throws RuntimeException if this move does not capture a piece
     */
    public Piece capturedPiece() throws RuntimeException;
    
    /**
     * Check if this move captures a piece
     * @return true if and only if this move captures a piece
     */
    public boolean isCapture();
    
    /**
     * Check if this move is defined (or not) - i.e. moves a piece to a particular square
     * @return true if and only if this move can not be defined (i.e. moves a piece to a particular square)
     */
    public boolean isUndefined();
    
    /**
     * Retrieve the string representation of this move
     * @return 
     *         - if this move is castle move:
     *              - if castling kingside,  return 'O-O'
     *              - if castling queenside, return 'O-O-O'
     *         - if this move is a promotion: 
     *              - return a string in the form 'c=P', 
     *                
     *                where c is the coordinate that the pawn is being promoted on,
     *                P is the piece the pawn is promoting to
     *         - in all other cases, return a string in the form 'Pc'", 
     * 
     *           where P is the piece in chess notation being moved,
     *                c is the coordinate that this piece is being moved to
     *         
     *         - if this move is a capture move:
     *              - if the moved piece is a pawn:
     *                  - prepend the string 'fx' to normal output, where f is the name of the file containing
     *                    the coordinate the piece is being moved from.
     *                    (Note that the 'x' is a literal 'x')
     *              - in all other cases, insert a literal 'x' between the 'P' and 'c' in normal output
     */             
    @Override
    public String toString();
    
    @Override
    public boolean equals(Object other);
    
    @Override
    public int hashCode();
}
