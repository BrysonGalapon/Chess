package data_types;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an immutable Normal chess move that moves a single piece from one square to another
 * @author Bryson
 */
public class Normal implements Move {
    private final Piece piece;
    private final Coordinate coordFrom;
    private final Coordinate coordTo;
    private final Piece capturedPiece;
    
    // Abstraction Function:
    //  - represents the move of piece piece from the square at coordinate coordFrom to 
    //        the square at coordinate coordTo on a chess board, possibly taking capturedPiece 
    //        on the arrival square
    // 
    // Rep Invariant:
    //  - moving the piece from coordFrom to coordTo is a valid chess move
    // 
    // Safety From Rep Exposure:
    //  - all fields are private and final
    //  - all mutable inputs are defensively copied
    //  - all mutable outputs are defensively copied
    // 
    
    /**
     * Create a new normal move
     * @param piece piece that is being moved
     * @param coordFrom coordinate that this piece is being moved from
     * @param coordTo coordinate that this piece is being moved to
     * @throws IllegalArgumentException if move is an invalid chess move
     */
    public Normal(Piece piece, Coordinate coordFrom, Coordinate coordTo, Piece capturedPiece) throws IllegalArgumentException{
        this.piece = piece;
        this.coordFrom = coordFrom;
        this.coordTo = coordTo;
        this.capturedPiece = capturedPiece;
        
        if (!piece.moveSet(coordFrom).contains(coordTo)) {
            throw new IllegalArgumentException("Illegal move attempted");
        }
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        Set<Coordinate> validTargets = piece.moveSet(coordFrom);
        
        assert validTargets.contains(coordTo);
    }

    @Override
    public Set<Piece> movedPieces() {
        Set<Piece> pieces = new HashSet<Piece>();
        pieces.add(piece);
        
        checkRep();
        return pieces;
    }

    @Override
    public Coordinate coordFrom() {
        return coordFrom;
    }

    @Override
    public Coordinate coordTo() {
        return coordTo;
    }

    @Override
    public boolean isCastle() {
        return false;
    }
    
    @Override
    public Set<Coordinate> coordinatesChanged(Board board) {
        Board boardCopy = new Board(board.whitePieces(), board.blackPieces(), board.turn(), board.getLastMove());

        Set<Coordinate> coordsChanged = new HashSet<>();
        coordsChanged.add(coordFrom);
        coordsChanged.add(coordTo);
        
        // this move could be an enPassent move
        if (isEnPassent(boardCopy)) {
            int scalar = board.turn().equals(PieceColor.WHITE) ? -1 : 1;
            
            // en passent capture square is one below coordTo on white and one above coordTo on black
            coordsChanged.add(new Coordinate(coordTo.getX(), coordTo.getY()+scalar));
        }
        
        checkRep();
        return coordsChanged;
    }
    
    @Override
    public String toString() {
        return piece + ": " + coordFrom + " -> " + coordTo;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Normal)) {return false;}
        
        Normal otherNormal = (Normal) other;
        
        boolean piecesEquivalent = this.movedPieces().equals(otherNormal.movedPieces());
        boolean coordToEquivalent = this.coordTo().equals(otherNormal.coordTo());
        boolean coordFromEquivalent = this.coordFrom().equals(otherNormal.coordFrom());
        
        return piecesEquivalent && coordToEquivalent && coordFromEquivalent;
    }
    
    @Override
    public int hashCode() {
        return piece.hashCode() + coordTo.hashCode() + coordFrom.hashCode();
    }

    @Override
    public boolean isCapture() {
        if (!capturedPiece.exists()) {return false;}
        
        // return true if capturing opposite color
        return !(capturedPiece.color().equals(piece.color()));
    }
    
    /**
     * Check if this move is an enPassent move on a given board
     * @param board on which this move is being played
     * @return true if this move is an enPassent capture on this board
     */
    private boolean isEnPassent(Board board) {
        Coordinate coordFrom = coordFrom();
        Coordinate coordTo = coordTo();
        
        Move lastMove = board.getLastMove();
        if (lastMove == null || lastMove.isCastle()) {return false;}
        
        Coordinate lastMoveCoordFrom = lastMove.coordFrom();
        Coordinate lastMoveCoordTo = lastMove.coordTo();
        Piece lastMovePiece = lastMove.movedPieces().iterator().next();
        
        boolean pawnHadPushedTwoSquares = lastMoveCoordFrom.getX() == lastMoveCoordTo.getX() && 
                Math.abs(lastMoveCoordFrom.getY() - lastMoveCoordTo.getY()) == 2;
        boolean pawnHadPushedAdjacentFile = Math.abs(lastMoveCoordFrom.getX() - coordFrom.getX()) == 1;
        
        int scalar = (board.turn().equals(PieceColor.WHITE)) ? 1 : -1;
        // pawn should capture into the square between lastMoveCoordFrom and lastMoveCoordTo
        boolean pawnCanCapture = new Coordinate(lastMoveCoordFrom.getX(), lastMoveCoordFrom.getY()-scalar).equals(coordTo);
        
        return lastMovePiece.isPawn() && pawnHadPushedTwoSquares && pawnHadPushedAdjacentFile && pawnCanCapture;
    }
}
