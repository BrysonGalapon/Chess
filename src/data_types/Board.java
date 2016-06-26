package data_types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Represents a mutable 8x8 Chess Board, with Pieces on it and with one side to move
 * @author bgalapon
 *
 */
public class Board {
    
    public final static int DEFAULT_SIZE = 8;
    
    private final Square[][] grid = new Square[DEFAULT_SIZE][DEFAULT_SIZE];
    private PieceColor turn;
    private List<Move> movesPlayed = new ArrayList<>();
    
    // TODO list:
    //
    // throw IllegalArgumentExceptions in Move variant constructors to help with main method
    // make legalMoves part of rep so you don't need to recalculate it every time
    // add in stalemate, 3 move repetition, 50 move rule, and insufficient material
    // add a resign button/ draw offer button
    // adjust spec of move to not allow any more moves on board after checkmate, and add tests to test that
    // add 'kingIsCastled(PieceColor side)' observer to check if a certain side castled
    // change heuristic in main so that rooks like open files, and that comp likes castling
    // add 'isUnprotected(Square square)' observer to check if a certain square on the board is unprotected
    // add 'getUnProtectedSquares()' observer
    // add 'Set<Piece> canCapture(Square square, PieceColor side)' to obtain
    //        all pieces that the player on 'side' can capture a piece on square
    
    // Abstraction Function:
    //  - represents a chess board filled with the squares in grid, such that the square
    //     grid[x][y] 0 <= x, y < DEFAULT_SIZE is the square at Coordinate (x,y) on the chess board.
    //  - turn is the side (white or black) to move next
    //  - lastMove is the last move that was played in the game
    //  
    // Rep Invariant:
    //  - exactly two kings, one of opposite color, exist on the board
    //  - the opposite king is not in check
    //  - square at grid[x][y] has coordinate (x, y)
    // 
    // Safety from Rep Exposure:
    //  - all fields are private and final
    //  - all mutable inputs are defensively copied
    //  - all mutable outputs are defensively copied
    //

    /**
     * Create a standard chess Board
     */
    public Board() {
        createEmptyGrid();
        
        PieceColor black = PieceColor.BLACK;
        PieceColor white = PieceColor.WHITE;
        
        turn = white;
        
        // add all black pieces
        addInitialPawns(black);
        addInitialRooks(black);
        addInitialKnights(black);
        addInitialBishops(black);
        addInitialQueen(black);
        addInitialKing(black);
        
        // add all white pieces
        addInitialPawns(white);
        addInitialRooks(white);
        addInitialKnights(white);
        addInitialBishops(white);
        addInitialQueen(white);
        addInitialKing(white);
        
        checkRep();
    }
    
    /**
     * Create a chess board containing pieces and a side to move
     * @param whitePieces a map from a white piece to the set of coordinates that contain that piece
     * @param blackPieces a map from a black piece to the set of coordinates that contain that piece
     * @param lastMove the last move that was played in the game
     * @param turn the side to move next
     */
    public Board(Map<Piece, Set<Coordinate>> whitePieces, Map<Piece, Set<Coordinate>> blackPieces, PieceColor turn, Move lastMove){
        createEmptyGrid();
        this.turn = turn;
        if (!lastMove.isUndefined()) {
            movesPlayed.add(lastMove);
        }
        
        Map<Piece, Set<Coordinate>> whitePiecesCopy = new HashMap<>(whitePieces);
        Map<Piece, Set<Coordinate>> blackPiecesCopy = new HashMap<>(blackPieces);
        
        // add all the white pieces
        for (Piece piece : whitePiecesCopy.keySet()) {
            for (Coordinate coord : whitePiecesCopy.get(piece)) {
                grid[coord.getX()][coord.getY()].addPiece(piece);
            }
        }
        
        // add all the black pieces
        for (Piece piece : blackPiecesCopy.keySet()) {
            for (Coordinate coord : blackPiecesCopy.get(piece)) {
                grid[coord.getX()][coord.getY()].addPiece(piece);
            }
        }
        
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant.
     */
    private void checkRep() {
        assert !oppositeKingCheck();
        for (int row = 0; row < DEFAULT_SIZE; row++) {
            for (int col = 0; col < DEFAULT_SIZE; col++) {
                assert grid[row][col].coordinate().getX() == row;
                assert grid[row][col].coordinate().getY() == col;
            }
        }
        Set<Coordinate> whiteKingPlacement = whitePieces().get(retrieveKing(PieceColor.WHITE));
        Set<Coordinate> blackKingPlacement = blackPieces().get(retrieveKing(PieceColor.BLACK));
        assert whiteKingPlacement.size() == 1;
        assert blackKingPlacement.size() == 1;
    }
    
    /**
     * Make a chess move on this Board. Flips the side to move.
     *  - requires that chessMove is contained in this.legalMoves()
     * @param chessMove move to make on this board
     * @throws IllegalArgumentException if chessMove is not a legal move on this board
     */
    public void move(Move chessMove) throws IllegalArgumentException{
        Square squareFrom = getSquare(chessMove.coordFrom());
        
        if (! squareFrom.isOccupied())
            throw new IllegalArgumentException("Piece not found");
        
        if (!legalMoves().contains(chessMove)) {
            throw new IllegalArgumentException("Illegal move attempted");
        }
        
        movePiece(chessMove);
        
        if (chessMove.isCastle()) {
            Coordinate rookCoord = getRookCoordinate(chessMove.coordTo());
            Square rookSquareFrom = getSquare(rookCoord);
            Square rookSquareTo = getSquare(getRookDestination(rookCoord));
            Move rookMove = Move.createMove(rookSquareFrom, rookSquareTo);
            movePiece(rookMove);
        }
        
        this.movesPlayed.add(chessMove);
        changeTurn();
        checkRep();
    }
    
    /**
     * Check if a move of a piece at coordinate coordFromto coordinate coordTo
     *  would be an en passent capture
     * @param coordFrom coordinate to move pawn from
     * @param coordTo coordinate to move pawn to
     * @return true if a pawn move from coordFrom to coordTo is an enPassent capture
     */
    public boolean isEnPassent(Coordinate coordFrom, Coordinate coordTo) {
        // may not en passent without moving a pawn
        if (!getSquare(coordFrom).getPiece().isPawn()) {
            return false;
        }
        
        Move lastMove = getLastMove();
        if (lastMove.isUndefined() || lastMove.isCastle()) {return false;}
        
        Coordinate lastMoveCoordFrom = lastMove.coordFrom();
        Coordinate lastMoveCoordTo = lastMove.coordTo();
        Piece lastMovePiece = lastMove.movedPieces().iterator().next();
        
        boolean pawnHadPushedTwoSquares = lastMoveCoordFrom.getX() == lastMoveCoordTo.getX() && 
                Math.abs(lastMoveCoordFrom.getY() - lastMoveCoordTo.getY()) == 2;
        boolean pawnHadPushedAdjacentFile = Math.abs(lastMoveCoordFrom.getX() - coordFrom.getX()) == 1;
        
        int scalar = (turn().equals(PieceColor.WHITE)) ? 1 : -1;
        // pawn should capture into the square between lastMoveCoordFrom and lastMoveCoordTo
        boolean pawnCanCapture = new Coordinate(lastMoveCoordFrom.getX(), lastMoveCoordFrom.getY()-scalar).equals(coordTo);
        
        return lastMovePiece.isPawn() && pawnHadPushedTwoSquares && pawnHadPushedAdjacentFile && pawnCanCapture;
    }

    
    /**
     * Take back the last move played 
     *  - if last move is undefined, then don't change the board
     */
    public void takeBackLastMove() {
        Move lastMove = getLastMove();
        
        if (lastMove.isUndefined()) {
            // don't do anything 
            return;
        } 
        
        takeBackMove(lastMove);
        
        // flip the turn to the other side
        flipTurn();
        
        // remove the last element from movesPlayed
        movesPlayed.remove(lastMove); 
    }
    
    /**
     * Retrieve a list of the moves played on this board
     * @return an in-order list of the moves played on this board up
     *          until the last move played
     *         - if no moves have been played, return an empty list
     */
    public List<Move> movesPlayed() {
        return new ArrayList<>(movesPlayed);
    }
    
    /**
     * Retrieve the legal moves that are checks and captures of this position
     * @return the legal checks and captures the current player can make
     */
    public Set<Move> getChecksAndCaptures() {
        Set<Move> legalMoves = legalMoves();
        Set<Move> checksAndCaptures = new HashSet<>();
        
        Set<Move> checks = getChecks(legalMoves);
        
        for (Move move : legalMoves) {
            // add captures
            if (move.isCapture()) {
                checksAndCaptures.add(move);
                continue;
            }
            
            // and checks
            if (checks.contains(move)) {
                checksAndCaptures.add(move);
                continue;
            }
        }
        
        checkRep();
        return checksAndCaptures;
    }
    
   /**
    * Retrieve the placement of all existing black pieces currently on the board
    * @return a map that maps a particular black piece to all the coordinates that contains that piece 
    */
    public Map<Piece, Set<Coordinate>> blackPieces() {
        PieceColor black = PieceColor.BLACK;
        Map<Piece, Set<Coordinate>> blackPieces = new HashMap<>();
        
        for (int row = 0; row < Board.DEFAULT_SIZE; row++) {
            for (int col = 0; col < Board.DEFAULT_SIZE; col++) {
                Square square = grid[row][col];
                if (square.isOccupied()) {
                    Piece piece = square.getPiece();
                    if (piece.color().equals(black)) {
                        if (blackPieces.keySet().contains(piece)) {
                            Set<Coordinate> blackPlacement = blackPieces.get(piece);
                            blackPlacement.add(square.coordinate());
                            blackPieces.put(piece, blackPlacement);
                        } else {
                            Set<Coordinate> blackPlacement = new HashSet<>();
                            blackPlacement.add(square.coordinate());
                            blackPieces.put(piece, blackPlacement);
                        }
                    }
                }
            }
        }
        
        return blackPieces;
    }
    
    /**
     * Retrieve the placement of all existing white pieces currently on the board
     * @return a map that maps a particular white piece to all the coordinates that contains that piece 
     */
     public Map<Piece, Set<Coordinate>> whitePieces() {
         PieceColor white = PieceColor.WHITE;
         Map<Piece, Set<Coordinate>> whitePieces = new HashMap<>();
         
         for (int row = 0; row < Board.DEFAULT_SIZE; row++) {
             for (int col = 0; col < Board.DEFAULT_SIZE; col++) {
                 Square square = grid[row][col];
                 if (square.isOccupied()) {
                     Piece piece = square.getPiece();
                     if (piece.color().equals(white)) {
                         if (whitePieces.keySet().contains(piece)) {
                             Set<Coordinate> whitePlacement = whitePieces.get(piece);
                             whitePlacement.add(square.coordinate());
                             whitePieces.put(piece, whitePlacement);
                         } else {
                             Set<Coordinate> whitePlacement = new HashSet<>();
                             whitePlacement.add(square.coordinate());
                             whitePieces.put(piece, whitePlacement);
                         }
                     }
                 }
             }
         }
         
         return whitePieces;
     }
    
    /**
     * Retrieve whose turn it is to move
     * @return whose turn it is to move
     */
    public PieceColor turn() {
        return this.turn;
    }
    
    /**
     * Retrieve all legal moves the current player has, including castle moves
     * @return a set of all legal moves the current player has
     */
    public Set<Move> legalMoves() {
        
        Set<Move> legalMoves = new HashSet<>();
        
        Map<Piece, Set<Coordinate>> pieces;
        
        if (turn().equals(PieceColor.WHITE)) {
            pieces = whitePieces();
        } else if (turn().equals(PieceColor.BLACK)) {
            pieces = blackPieces();
        } else {
            throw new RuntimeException("turn is neither black or white");
        }
        
        for (Piece piece : pieces.keySet()) {
            for (Coordinate coordFrom : pieces.get(piece)) {
                Map<Direction, Coordinate> farthestCoordinateCleared = new HashMap<>();
                
                for (Coordinate coordTo : piece.moveSet(coordFrom)) {
                    Square squareFrom = getSquare(coordFrom);
                    Square squareTo = getSquare(coordTo);
                    Piece squareFromPiece = squareFrom.getPiece();
                    Piece squareToPiece = squareTo.getPiece();
                    
                    // can't land on a square which contains a piece of the same color
                    if (squareToPiece.color().equals(turn())) {continue;}
                    
                    if (piece.isPawn()) {
                        // can't push a pawn to land on a piece
                        if (squareTo.isOccupied() && coordFrom.getX() == coordTo.getX()) {continue;}
                       
                        // can't jump over pieces
                        if (pawnJumpedOverPiece(coordFrom, coordTo)) {continue;}
                        
                        boolean isCaptureMove = coordTo.getX() != coordFrom.getX();
                        boolean isEnPassent = isEnPassent(coordFrom, coordTo);
                        boolean isPromotion = coordTo.getY() == 7 || coordTo.getY() == 0;
                        
                        // can't capture on an unoccupied square
                        if (!isEnPassent && isCaptureMove && !squareTo.isOccupied()) {continue;}
                        
                        if (isEnPassent) {
                            legalMoves.add(Move.enPassent(squareFrom, squareTo));
                        } else if (isPromotion) {
                            legalMoves.add(Move.promote(squareFrom, squareTo, Piece.knight(piece.color(), true)));
                            legalMoves.add(Move.promote(squareFrom, squareTo, Piece.queen(piece.color(), true)));
                            legalMoves.add(Move.promote(squareFrom, squareTo, Piece.bishop(piece.color(), true)));
                            legalMoves.add(Move.promote(squareFrom, squareTo, Piece.rook(piece.color(), true)));
                        } else {
                            legalMoves.add(Move.createMove(squareFrom, squareTo));
                        }
                    } else {
                        Piece unmovedWhiteKing = Piece.king(PieceColor.WHITE, false);
                        Piece unmovedBlackKing = Piece.king(PieceColor.BLACK, false);
                        boolean pieceIsUnmovedKing = piece.equals(unmovedWhiteKing) || piece.equals(unmovedBlackKing);
                        boolean moveIsCastlingMove = pieceIsUnmovedKing && Math.abs(coordTo.getX() - coordFrom.getX()) == 2;
                        
                        if (moveIsCastlingMove) {
                            Coordinate rookCoord = getRookCoordinate(coordTo);
                            Square rookSquare = getSquare(rookCoord);
                            
                            // can't castle without a rook
                            if (!rookSquare.isOccupied()) {continue;}
                            // squares between king and rook must be clear
                            if (!squaresCleared(coordFrom, rookCoord)) {continue;}
                            // rook may not have moved before
                            if (rookSquare.getPiece().hasMoved()) {continue;}
                            // king may not castle out of, through, or into check
                            if (!kingAvoidsCheck(coordFrom, coordTo)) {continue;}
                        } else {
                            
                            Direction direction = getCardinalDirection(coordFrom, coordTo);

                            if (!farthestCoordinateCleared.keySet().contains(direction)) {
                                if (!squaresCleared(coordFrom, coordTo)) {continue;}
                                farthestCoordinateCleared.put(direction, coordTo);
                            } else {
                                Coordinate farthestCoord = farthestCoordinateCleared.get(direction);
                                
                                if (fartherAway(coordFrom, coordTo, farthestCoord).equals(coordTo)) {
                                    
                                    // each square, (including the square at farthestCoord must be clear), since
                                    // every square in the cardinal direction from coordFrom to farthestCoord exclusive
                                    // has been checked to be clear
                                    if (!squaresCleared(farthestCoord, coordTo) || getSquare(farthestCoord).isOccupied()) {continue;}
                                    
                                    farthestCoordinateCleared.put(direction, coordTo);
                                }
                            }
                            
                            // can't land on same color piece
                            if (squareToPiece.color().equals(squareFromPiece.color())) {continue;}
                        }
                        
                        legalMoves.add(Move.createMove(getSquare(coordFrom), getSquare(coordTo)));
                    }
                }
            }
        }
        
        legalMoves = filterChecks(legalMoves);
        
        checkRep();
        return legalMoves;
    }
    
    /**
     * Check if the current player is in checkmate
     * @return true if the current player is in checkmate
     */
    public boolean checkMate() {
        return legalMoves().size() == 0;
    }
    
    /**
     * Retrieve the last move that was played
     * @return the last move that was played
     *          - if there is no last move, return an undefined move
     */
    public Move getLastMove() {
        if (movesPlayed.size() == 0) {
            return Move.undefined();
        } 
        
        int finalIndex = movesPlayed.size()-1;
        return movesPlayed.get(finalIndex);
    }
    
    /**
     * Retrieve the square at the given coordinate
     * @param x x-coordinate of square to obtain
     * @param y y-coordinate of square to obtain
     * @return the square at coordinate (x, y)
     */
    public Square getSquare(int x, int y) {
        Square square = grid[x][y];
        
        return square.squareCopy();
    }
    
    /**
     * Retrieve the square at the given coordinate
     * @param coord coordinate of square to obtain
     * @return the square at coordinate coord
     */
    public Square getSquare(Coordinate coord) {
        Square square = grid[coord.getX()][coord.getY()];
        
        return square.squareCopy();
    }
    
    /**
     * Retrieve the square at the given location
     * @param coord square, in chess notation, to obtain
     * @return the square at location coord
     */
    public Square getSquare(String coord) {
        String coordinateRegex = "[a-h][1-8]";
        
        if (! coord.matches(coordinateRegex)) {
            throw new IllegalArgumentException("input not a valid chess coordinate");
        }
        
        String firstLetter = coord.substring(0, 1);
        String number = coord.substring(1, 2);
        
        int xCoordinate;
        
        switch(firstLetter) {
        case "a":
            xCoordinate = 0;
            break;
        case "b":
            xCoordinate = 1;
            break;
        case "c":
            xCoordinate = 2;
            break;
        case "d":
            xCoordinate = 3;
            break;
        case "e":
            xCoordinate = 4;
            break;
        case "f":
            xCoordinate = 5;
            break;
        case "g":
            xCoordinate = 6;
            break;
        case "h":
            xCoordinate = 7;
            break;
        default:
            throw new RuntimeException("could not recognize first letter");
        }
        
        int yCoordinate = Integer.valueOf(number)-1;
        
        Square square = grid[xCoordinate][yCoordinate];
        
        return square.squareCopy();
    }
    
    /**
     * Replace a square on the chess board
     * @param square to be placed on the chess board
     */
    public void setSquare(Square square) {
        Square squareCopy = new Square(square.coordinate());
        if (square.isOccupied()) {
            squareCopy.addPiece(square.getPiece());
        }
        
        forceSetSquare(squareCopy);
        checkRep();
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {return false;}
        
        Board otherBoard = (Board) other;
        
        boolean whitePiecesSame = this.whitePieces().equals(otherBoard.whitePieces());
        boolean blackPiecesSame = this.blackPieces().equals(otherBoard.blackPieces());
        boolean legalMovesSame = this.legalMoves().equals(otherBoard.legalMoves());
        boolean turnSame = this.turn().equals(otherBoard.turn());
        boolean lastMoveSame = this.getLastMove().equals(otherBoard.getLastMove());
        
        return legalMovesSame && turnSame && lastMoveSame && whitePiecesSame && blackPiecesSame;
    }
    
    /**
     * Flip the current turn to the opposing side (or pass the turn), without changing the 
     *  physical board
     */
    public void flipTurn() {
        if (this.turn.equals(PieceColor.BLACK)) {
            this.turn = PieceColor.WHITE;
        } else {
            this.turn = PieceColor.BLACK;
        }
    }
    
    /**
     * Retrieve the string representation of this board, from white's perspective
     * 
     * @return a board where uppercase letters denote white pieces
     *          and lowercase letters denote black pieces.
     *          - squares that don't contain pieces contain a '-'
     */
    @Override
    public String toString() {
        String rep = "";
        for (int row = Board.DEFAULT_SIZE-1; row >= 0; row--) { // print from top to bottom
            for (int col = 0; col < Board.DEFAULT_SIZE; col++) {
                Piece squarePiece = grid[col][row].getPiece();
                if (!squarePiece.exists()) {
                    rep += "- ";
                    continue;
                }

                if (squarePiece.isPawn()) {
                    if (squarePiece.color().equals(PieceColor.WHITE)) {
                        rep += "P";
                    } else if (squarePiece.color().equals(PieceColor.BLACK)) {
                        rep += "p";
                    } else {
                        throw new RuntimeException("Piece is neither one of black or white");
                    }
                } else {
                    if (squarePiece.color().equals(PieceColor.WHITE)) {
                        rep += squarePiece.toString();
                    } else if (squarePiece.color().equals(PieceColor.BLACK)) {
                        rep += squarePiece.toString().toLowerCase();
                    } else {
                        throw new RuntimeException("Piece is neither one of black or white");
                    }
                }
                rep += " ";
            }
            rep += "\n";
        }
        
        return rep;
    }
    
    //////////////////////////////////////////////////////////////////////
    /////////////////////// PRIVATE HELPER METHODS ///////////////////////
    //////////////////////////////////////////////////////////////////////
    
    /**
     * Retrieve the coordinate furthest away from a point
     * @param coordFrom reference coordinate
     * @param coordEnd1 first end coordinate
     * @param coordEnd2 second end coordinate
     * @return which of coordEnd1 or coordEnd2 that is furthest in Euclidean distance
     *          away from coordFrom
     *           - if coordEnd1 and coordEnd2 are the same distance from coordFrom,
     *              return coordEnd2 arbitrarily
     */
    private Coordinate fartherAway(Coordinate coordFrom, Coordinate coordEnd1, Coordinate coordEnd2) {
        int xStart = coordFrom.getX();
        int yStart = coordFrom.getY();
        int xEnd1 = coordEnd1.getX();
        int yEnd1 = coordEnd1.getY();
        int xEnd2 = coordEnd2.getX();
        int yEnd2 = coordEnd2.getY();
        
        double distance1 = Math.pow(xStart - xEnd1, 2) + Math.pow(yStart - yEnd1, 2);
        double distance2 = Math.pow(xStart - xEnd2, 2) + Math.pow(yStart - yEnd2, 2);
        
        return distance1 > distance2 ? coordEnd1 : coordEnd2; 
    }
    
    /**
     * Check if a pawn jumps over a piece
     *      - requires that coordFrom and coordTo share a file (i.e. coordFrom.getX() == coordTo.getX())
     * @param coordFrom starting coordinate of pawn
     * @param coordTo ending coordinate of pawn
     * @return true if this pawn jumped over a piece
     */
    private boolean pawnJumpedOverPiece(Coordinate coordFrom, Coordinate coordTo) {
        boolean pushedTwoSquares = Math.abs(coordTo.getY() - coordFrom.getY()) == 2;
        int scalar = turn().equals(PieceColor.WHITE) ? 1 : -1;
        Square oneSquareAbove = getSquare(coordFrom.getX(), coordFrom.getY()+1*scalar);
        
        return pushedTwoSquares && oneSquareAbove.isOccupied();
    }
    
    /**
     * Retrieve the rook coordinate destination when castling 
     * @param coordFrom coordinate that the rook is being moved from when castling
     * @return the coordinate the rook will land on after castling
     */
    private Coordinate getRookDestination(Coordinate coordFrom) {
        if (coordFrom.equals(new Coordinate("a1"))) {
            return new Coordinate("d1");
        } else if (coordFrom.equals(new Coordinate("h1"))) {
            return new Coordinate("f1");
        } else if (coordFrom.equals(new Coordinate("a8"))) {
            return new Coordinate("d8");
        } else if (coordFrom.equals(new Coordinate("h8"))) {
            return new Coordinate("f8");
        } else {
            throw new RuntimeException("Rook can not start from: " + coordFrom);
        }
    }
    
    /**
     * Retrieve the coordinate of the rook of the side to castle, if an unmoved king castles
     * and lands on coordTo
     * @param coordTo coordinate that an unmoved king will land on, given that the 
     *  current move is a castling move
     * @return the rook coordinate of the side to castle, if an unmoved king castles
     *  and lands on coordTo.
     *      - if coordTo is not a coordinate that a king can land on when castling, this method
     *      throws an illegal argument exception
     */
    private Coordinate getRookCoordinate(Coordinate coordTo) {
        if (coordTo.equals(new Coordinate("g1"))) {
            return new Coordinate("h1");
        } else if (coordTo.equals(new Coordinate("c1"))) {
            return new Coordinate("a1");
        } else if (coordTo.equals(new Coordinate("g8"))) {
            return new Coordinate("h8");
        } else if (coordTo.equals(new Coordinate("c8"))){
            return new Coordinate("a8");
        } else {
            throw new IllegalArgumentException("Can not retrieve rook coordinate for that coordTo");
        }
    }
    
    /**
     * Check if the squares between coordFrom and coordTo exclusive are occupied
     * @param coordFrom source coordinate
     * @param coordTo target coordinate
     * @return true if and only if all the squares between coordFrom and coordTo
     *          exclusive are unoccupied
     */
    private boolean squaresCleared(Coordinate coordFrom, Coordinate coordTo) {
        for (Coordinate coord : coordInBetween(coordFrom, coordTo)) {
            if (coord.equals(coordTo)) {continue;}
            
            if (getSquare(coord).isOccupied()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check if the king can pass through squares coordFrom to coordTo without being checked
     *  - requires coordFrom and coordTo share a diagonal, column, or row
     * @param coordFrom start coordinate of king
     * @param coordTo end coordinate of king
     * @return true if and only if the king can walk from coordFrom to coordTo **inclusive** without
     *  being in check
     */
    private boolean kingAvoidsCheck(Coordinate coordFrom, Coordinate coordTo) {
        if (inCheck()) {return false;}
        
        for (Coordinate coord : coordInBetween(coordFrom, coordTo)) {
            if (inCheck(coord)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Obtain the cardinal direction that a vector points in
     *   - requires that coordFrom and coordTo share a diagonal, row, or column
     *     on a grid
     *   - requires that coordFrom != coordTo
     * @param coordFrom starting coordinate of vector
     * @param coordTo ending coordinate of vector
     * @return the cardinal direction that the vector formed from coordTo - coordFrom points in
     */
    private Direction getCardinalDirection(Coordinate coordFrom, Coordinate coordTo) {
        int xStart = coordFrom.getX();
        int yStart = coordFrom.getY();
        int xEnd = coordTo.getX();
        int yEnd = coordTo.getY();
        
        if (xStart == xEnd) { // coordinates share a column
            return yStart < yEnd ? Direction.NORTH : Direction.SOUTH;
        } else if (yStart == yEnd) { // coordinates share a row
            return xStart < xEnd ? Direction.EAST : Direction.WEST;
        } else { // coordinates share a diagonal
            if (xStart < xEnd && yStart < yEnd) {
                return Direction.NORTH_EAST;
            } else if (xStart > xEnd && yStart < yEnd) {
                return Direction.NORTH_WEST;
            } else if (xStart < xEnd && yStart > yEnd) {
                return Direction.SOUTH_EAST;
            } else if (xStart > xEnd && yStart > yEnd) {
                return Direction.SOUTH_WEST;
            } else {
                throw new IllegalArgumentException("coordFrom and coordTo must share a column, row, or diagonal");
            }
        }
    }
    
    /**
     * Retrieve the piece of color color on this board
     * @param color color of king to retrieve
     * @return the king of this color on this board
     */
    private Piece retrieveKing(PieceColor color) {
        Map<Piece, Set<Coordinate>> pieces;
        
        if (color.equals(PieceColor.WHITE)) {
            pieces = whitePieces();
        } else if (color.equals(PieceColor.BLACK)) {
            pieces = blackPieces();
        } else {
            throw new RuntimeException("Color is not one of white or black");
        }
        
        Piece movedKing = Piece.king(color, true);
        Piece unmovedKing = Piece.king(color, false);
        
        boolean movedKingOnBoard;
        boolean unmovedKingOnBoard;
        
        if (pieces.keySet().contains(movedKing)) {
            movedKingOnBoard = pieces.get(movedKing).size() == 1;
        } else {
            movedKingOnBoard = false;
        }
        
        if (pieces.keySet().contains(unmovedKing)) {
            unmovedKingOnBoard = pieces.get(unmovedKing).size() == 1; 
        } else {
            unmovedKingOnBoard = false;
        }
        
        if (movedKingOnBoard && unmovedKingOnBoard) {
            throw new RuntimeException("May not have two kings on board at same time");
        } else if (movedKingOnBoard) {
            return movedKing;
        } else if (unmovedKingOnBoard) {
            return unmovedKing;
        } else {
            throw new RuntimeException("Must have at least one king on the board");
        }
    }
    
    /**
     * Take back a move played on this board
     * @param lastMove to be taken back
     */
    private void takeBackMove(Move lastMove) {
        PieceColor side = getArbitrary(lastMove.movedPieces()).color();
        
        if (lastMove.isCastle()) {
            if (side.equals(PieceColor.WHITE)) {
                if (lastMove.coordTo().equals(new Coordinate("g1"))) {
                    Square kingSquare = new Square(new Coordinate("e1"));
                    kingSquare.addPiece(Piece.king(PieceColor.WHITE, false));
                    Square rookSquare = new Square(new Coordinate("h1"));
                    rookSquare.addPiece(Piece.rook(PieceColor.WHITE, false));
                    
                    // set the appropriate squares
                    forceSetSquare(kingSquare);
                    forceSetSquare(new Square(new Coordinate("f1")));
                    forceSetSquare(new Square(new Coordinate("g1")));
                    forceSetSquare(rookSquare);
                    return;
                }
                
                if (lastMove.coordTo().equals(new Coordinate("c1"))) {
                    Square kingSquare = new Square(new Coordinate("e1"));
                    kingSquare.addPiece(Piece.king(PieceColor.WHITE, false));
                    Square rookSquare = new Square(new Coordinate("a1"));
                    rookSquare.addPiece(Piece.rook(PieceColor.WHITE, false));
                    
                    // set the appropriate squares
                    forceSetSquare(kingSquare);
                    forceSetSquare(new Square(new Coordinate("d1")));
                    forceSetSquare(new Square(new Coordinate("c1")));
                    forceSetSquare(new Square(new Coordinate("b1")));
                    forceSetSquare(rookSquare);
                    return;
                }
            } else if (side.equals(PieceColor.BLACK)){
                if (lastMove.coordTo().equals(new Coordinate("g8"))) {
                    Square kingSquare = new Square(new Coordinate("e8"));
                    kingSquare.addPiece(Piece.king(PieceColor.BLACK, false));
                    Square rookSquare = new Square(new Coordinate("h8"));
                    rookSquare.addPiece(Piece.rook(PieceColor.BLACK, false));
                    
                    // set the appropriate squares
                    forceSetSquare(kingSquare);
                    forceSetSquare(new Square(new Coordinate("f8")));
                    forceSetSquare(new Square(new Coordinate("g8")));
                    forceSetSquare(rookSquare);
                    return;
                }
                
                if (lastMove.coordTo().equals(new Coordinate("c8"))) {
                    Square kingSquare = new Square(new Coordinate("e8"));
                    kingSquare.addPiece(Piece.king(PieceColor.BLACK, false));
                    Square rookSquare = new Square(new Coordinate("a8"));
                    rookSquare.addPiece(Piece.rook(PieceColor.BLACK, false));
                    
                    // set the appropriate squares
                    forceSetSquare(kingSquare);
                    forceSetSquare(new Square(new Coordinate("d8")));
                    forceSetSquare(new Square(new Coordinate("c8")));
                    forceSetSquare(new Square(new Coordinate("b8")));
                    forceSetSquare(rookSquare);
                    return;
                }
            } else {
                throw new RuntimeException("Turn is not either one of white or black");
            }
        } else if (lastMove.isEnPassent()) {
            if (side.equals(PieceColor.WHITE)) {
                Coordinate coordTo = lastMove.coordTo();
                
                Square pawnSquare = new Square(lastMove.coordFrom());
                pawnSquare.addPiece(Piece.pawn(PieceColor.WHITE, true));
                Square oppPawnSquare = new Square(new Coordinate(coordTo.getX(), coordTo.getY()-1));
                oppPawnSquare.addPiece(Piece.pawn(PieceColor.BLACK, true));
                Square captureSquare = new Square(coordTo);
                
                forceSetSquare(pawnSquare);
                forceSetSquare(oppPawnSquare);
                forceSetSquare(captureSquare);
            } else if (side.equals(PieceColor.BLACK)) {
                Coordinate coordTo = lastMove.coordTo();
                
                Square pawnSquare = new Square(lastMove.coordFrom());
                pawnSquare.addPiece(Piece.pawn(PieceColor.BLACK, true));
                Square oppPawnSquare = new Square(new Coordinate(coordTo.getX(), coordTo.getY()+1));
                oppPawnSquare.addPiece(Piece.pawn(PieceColor.WHITE, true));
                Square captureSquare = new Square(coordTo);
                
                forceSetSquare(pawnSquare);
                forceSetSquare(oppPawnSquare);
                forceSetSquare(captureSquare);
            } else {
                throw new RuntimeException("Turn is not either one of white or black");
            }
        } else if (lastMove.isCapture()){
            Piece capturedPiece = lastMove.capturedPiece();
            Piece movedPiece = getArbitrary(lastMove.movedPieces());
            
            Square pieceSquare = new Square(lastMove.coordFrom());
            pieceSquare.addPiece(movedPiece);
            Square captureSquare = new Square(lastMove.coordTo());
            captureSquare.addPiece(capturedPiece);
            
            forceSetSquare(pieceSquare);
            forceSetSquare(captureSquare);
        } else {
            Piece movedPiece = getArbitrary(lastMove.movedPieces());
            
            Square pieceSquare = new Square(lastMove.coordFrom());
            pieceSquare.addPiece(movedPiece);
            Square landingSquare = new Square(lastMove.coordTo());
            
            forceSetSquare(pieceSquare);
            forceSetSquare(landingSquare);
        }
    }
    
    /**
     * Filter out the moves that would result in an illegal position via landing in check
     * @param moveSet set of moves to filter for checks
     * @return the moves in moveSet that don't result in an illegal position via check
     */
    private Set<Move> filterChecks(Set<Move> moveSet) {
        Set<Move> filteredMoves = new HashSet<Move>();
        
        for (Move move : moveSet) {
            movePiece(move);
            
            if (!inCheck()) {
                filteredMoves.add(move);
            }
            
            takeBackMove(move);
        }
        
        return filteredMoves;
    }
    
    /**
     * Obtain the moves that are checks in a given set
     * @param moveSet set of moves to filter for checks
     * @return the moves in moveSet that give check to the opposing king
     */
    private Set<Move> getChecks(Set<Move> moveSet) {
        Set<Move> checks = new HashSet<>();
        
        for (Move move : moveSet) {
            move(move);
            
            if (inCheck()) {
                checks.add(move);
            }
            
            takeBackLastMove();
        }
        
        return checks;
    }
    
    /**
     * Forcibly move a piece on this Board
     * @param move move to make on this board
     */
    private void movePiece(Move move) {
        Square squareFrom = getSquare(move.coordFrom());
        Square squareTo = getSquare(move.coordTo());
        Piece pieceToMove = squareFrom.getPiece();
        PieceColor color = pieceToMove.color();
        
        Coordinate coordFrom = squareFrom.coordinate();
        Coordinate coordTo = squareTo.coordinate();
        
        if (!squareFrom.isOccupied()) {
            throw new RuntimeException("No piece is there to move");
        }
        
        squareFrom.removePiece();
        
        // take into account enPassent
        if (isEnPassent(move)) {
            if (color.equals(PieceColor.WHITE)) {
                grid[coordTo.getX()][coordTo.getY()-1].removePiece();
            } else if (color.equals(PieceColor.BLACK)){
                grid[coordTo.getX()][coordTo.getY()+1].removePiece();
            } else {
                throw new RuntimeException("Color is not one of white or black");
            }
        }
        
        pieceToMove = pieceToMove.getMovedVersion();
        
        if (move.isCapture()) {
            Coordinate removeCoord = move.captureCoordinate();
            grid[removeCoord.getX()][removeCoord.getY()].removePiece();            
        }
        
        if (move.isPromotion()) {
            squareTo.addPiece(move.promotedPiece());
        } else {
            squareTo.addPiece(pieceToMove);
        }
        
        grid[coordFrom.getX()][coordFrom.getY()] = squareFrom;
        grid[coordTo.getX()][coordTo.getY()] = squareTo;
    }
    
    /**
     * Check if a move is an enPassent move
     * @param move move to consider
     * @return true if move is an enPassent capture
     */
    private boolean isEnPassent(Move move) {
        // move is not en passent if castling
        if (move.isCastle()) {
            return false;
        } 
        
        // move is not en passent if not moving a pawn
        if (!move.movedPieces().iterator().next().isPawn()) {
            return false;
        }
        
        Coordinate coordFrom = move.coordFrom();
        Coordinate coordTo = move.coordTo();
        
        return isEnPassent(coordFrom, coordTo);
    }
    
    /**
     * Restore the board to an initial state
     * @param squareState set of original squares to restore
     */
    private void restoreBoard(Set<Square> squareState) {
        for (Square square : squareState) {
            grid[square.coordinate().getX()][square.coordinate().getY()] = square;
        }
    }
    
    /**
     * Check if a coordinate is strictly in between two other coordinates
     * @param x x-coordinate of point to check
     * @param y y-coordinate of point to check
     * @param xStart x-coordinate of source node
     * @param yStart y-coordinate of source node
     * @param xEnd x-coordinate of end node
     * @param yEnd y-coordinate of end node
     * @return true if (x, y) lies on the line drawn between (xStart, yStart)
     *          and (xEnd, yEnd) on the plane
     */
    private boolean liesInBetween(int x, int y, int xStart, int yStart, int xEnd, int yEnd) {
        if ((xStart >= x || x >= xEnd) && (xEnd >= x || x >= xStart)) {return false;}
        if ((yStart >= y || y >= yEnd) && (yEnd >= y || y >= yStart)) {return false;}

        double m1 = (y - yStart)/(x - xStart);
        double m2 = (y - yEnd)/(x - xEnd);
        
        return m1 == m2;
    }
    
    /**
     * Obtain the coordinates between startCoord and endCoord
     *  - if startCoord and endCoord do not share a column, diagonal, or row, 
     *     return an empty List
     *  - requires that startCoord != endCoord
     * 
     * @param startCoord coordinate to start at 
     * @param endCoord coordinate to end at
     * @return a list of coordinates, starting from startCoord **exclusive** to endCoord
     *          **inclusive**
     *          
     *          i.e.
     *          
     *          if  startCoord = (1, 1)
     *          and endCoord   = (4, 4),
     *          
     *          this function will return (2, 2), (3, 3), (4, 4)
     */
    private List<Coordinate> coordInBetween(Coordinate startCoord, Coordinate endCoord) {
        List<Coordinate> inBetween = new ArrayList<Coordinate>();
        
        int startX = startCoord.getX();
        int startY = startCoord.getY();
        
        int deltaX = endCoord.getX() - startX;
        int deltaY = endCoord.getY() - startY;
        
        int scaleX = (deltaX > 0) ? 1 : -1;
        int scaleY = (deltaY > 0) ? 1 : -1;
        
        if (deltaX == 0) { // coordinates share a column
            for (int row = 1; row <= Math.abs(deltaY); row++) {
                inBetween.add(new Coordinate(startX, startY+scaleY*row));
            }
        } else if (deltaY == 0){ // coordinates share a row
            for (int col = 1; col <= Math.abs(deltaX); col++) {
                inBetween.add(new Coordinate(startX+scaleX*col, startY));
            }
        } else if (Math.abs(deltaX) == Math.abs(deltaY)){ // coordinates share a diagonal
            for (int diag = 1; diag <= Math.abs(deltaY); diag++) {
                inBetween.add(new Coordinate(startX+scaleX*diag, startY+scaleY*diag));
            }
        } else {
            // chill, coordinates do not share a column, diagonal, or row
        }
        
        return inBetween;
    }
    
    /**
     * Switches the turn to the other player
     */
    private void changeTurn() {
        if (turn.equals(PieceColor.WHITE)) {
            turn = PieceColor.BLACK;
        } else if (turn.equals(PieceColor.BLACK)) {
            turn = PieceColor.WHITE;
        } else {
            throw new RuntimeException("PieceColor is not binary");
        }
    }
    
    /**
     * Initialize the grid to contain empty squares
     */
    private void createEmptyGrid() {
        for (int row = 0; row < DEFAULT_SIZE; row++) {
            for (int col = 0; col < DEFAULT_SIZE; col++) {
                grid[row][col] = new Square(new Coordinate(row, col));
            }
        }
    }
    
    /**
     * Check to see if the opposite king is in check
     * @return true if the opposite king is in check
     */
    private boolean oppositeKingCheck() {
        changeTurn();
        boolean oppKingInCheck = inCheck();
        // change the turn back to what it was originally
        changeTurn();
        return oppKingInCheck;
    }
    
    /**
     * Check if the king were placed at coordinate coord, will he be in check?
     * @param coord of the king
     * @return true if the king is in check if placed on coordinate coord
     */
    private boolean inCheck(Coordinate coord) {
        Map<Piece, Set<Coordinate>> currentPieces;
        
        if (turn().equals(PieceColor.WHITE)) {
            currentPieces = whitePieces();
        } else if (turn().equals(PieceColor.BLACK)) {
            currentPieces = blackPieces();
        } else {
            throw new RuntimeException("Color is neither one of black or white");
        }
        
        Piece kingOnBoard = retrieveKing(turn());
        
        // pop an arbitrary element from the set of king coordinates (it's size is 1, so it does not matter)
        Coordinate currentKingCoord = currentPieces.get(kingOnBoard).iterator().next();
        
        // move the king to coord
        Square squareFrom = getSquare(currentKingCoord);
        Square newSquareFrom = new Square(squareFrom.coordinate());
        forceSetSquare(newSquareFrom);
        Square squareTo = getSquare(coord);
        Square newSquareTo = new Square(squareTo.coordinate());
        newSquareTo.addPiece(kingOnBoard);
        forceSetSquare(newSquareTo);
        
        //save original state
        Set<Square> originalState = new HashSet<>();
        originalState.add(squareFrom);
        originalState.add(squareTo);
        
        boolean kingInCheck = inCheck();
        
        // place the king back
        restoreBoard(originalState);
    
        return kingInCheck;
    }
    
    /**
     * Check if the king is in check in the current position
     * @return true if the king is in check in the current position
     */
    private boolean inCheck() {
        Map<Piece, Set<Coordinate>> currentPieces;
        Map<Piece, Set<Coordinate>> otherPieces;
        
        if (turn().equals(PieceColor.WHITE)) {
            currentPieces = whitePieces();
            otherPieces = blackPieces();
        } else if (turn().equals(PieceColor.BLACK)) {
            currentPieces = blackPieces();
            otherPieces = whitePieces();
        } else {
            throw new RuntimeException("Color is neither one of black or white");
        }
        
        Piece kingOnBoard = retrieveKing(turn());
        
        // pop an arbitrary element from the set of king coordinates (it's size is 1, so it does not matter)
        Coordinate currentKingCoord = currentPieces.get(kingOnBoard).iterator().next();
        
        for (Piece piece : otherPieces.keySet()) {
            if (piece.isPawn()) {
                for (Coordinate coordFrom : otherPieces.get(piece)) {
                    for (Coordinate coordTo : piece.moveSet(coordFrom)) {
                        if (coordFrom.getX() != coordTo.getX() && coordTo.equals(currentKingCoord)) {
                            return true;
                        }
                    }
                }
            } else {
                for (Coordinate coordFrom : otherPieces.get(piece)) {
                    for (Coordinate coordTo : piece.moveSet(coordFrom)) {
                        if (!squaresCleared(coordFrom, coordTo)) {continue;}
                        if (coordTo.equals(currentKingCoord)) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Foribly Replace a square on the chess board
     * @param square to be placed on the chess board
     */
    private void forceSetSquare(Square square) {
        Coordinate coordinate = square.coordinate();
        
        grid[coordinate.getX()][coordinate.getY()] = square;
    }
    
    /**
     * Retrieve the number of legal king moves that the color king has
     * @param color of the king in question
     * @return the number of legal king moves that the color king has
     */
    private int numKingMoves(PieceColor color) {
        // TODO: Implement this method
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Add initial pawn setup to Board
     * @param color color of the pawns
     */
    private void addInitialPawns(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 1 : Board.DEFAULT_SIZE - 2; 
        
        for (int x = 0; x < Board.DEFAULT_SIZE; x++) {
            Square square = grid[x][rank];
            Piece pawn = Piece.pawn(color, false);
            
            square.addPiece(pawn);
            
            grid[x][rank] = square;
        }
    }
    
    /**
     * Add initial Knight setup to Board
     * @param color color of the knights
     */
    private void addInitialKnights(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE - 1; 
        
        Square square1 = grid[1][rank];
        Square square2 = grid[6][rank];

        Piece knight = Piece.knight(color, false);

        square1.addPiece(knight);
        square2.addPiece(knight);
        
        grid[1][rank] = square1;
        grid[6][rank] = square2;
    }
    
    /**
     * Add initial Rook setup to Board
     * @param color color of the rooks
     */
    private void addInitialRooks(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE - 1; 
        
        Square square1 = grid[0][rank];
        Square square2 = grid[7][rank];

        Piece rook = Piece.rook(color, false);

        square1.addPiece(rook);
        square2.addPiece(rook);
        
        grid[0][rank] = square1;
        grid[7][rank] = square2;
    }
    
    /**
     * Add initial Bishop setup to Board
     * @param color color of the bishops
     */
    private void addInitialBishops(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE - 1; 
        
        Square square1 = grid[2][rank];
        Square square2 = grid[5][rank];

        Piece bishop = Piece.bishop(color, false);

        square1.addPiece(bishop);
        square2.addPiece(bishop);
        
        grid[2][rank] = square1;
        grid[5][rank] = square2;
    }
    
    /**
     * Add initial King setup to Board
     * @param color color of the king
     */
    private void addInitialKing(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE - 1; 
        
        Square square = grid[4][rank];

        Piece king = Piece.king(color, false);

        square.addPiece(king);
        
        grid[4][rank] = square;
    }
    
    /**
     * Add initial Queen setup to Board
     * @param color color of the queen
     */
    private void addInitialQueen(PieceColor color) {
        int rank = (color.equals(PieceColor.WHITE)) ? 0 : Board.DEFAULT_SIZE - 1; 
        
        Square square = grid[3][rank];

        Piece queen = Piece.queen(color, false);

        square.addPiece(queen);
        
        grid[3][rank] = square;
    }
    
    /**
     * Retrieve an arbitrary element out of a set
     * @param set Set of elements to remove from
     * @return any element contained in set
     */
    private <T> T getArbitrary(Set<T> set) {
        Set<T> setCopy = new HashSet<>(set);
        if (set.size() == 0) {
            throw new RuntimeException("Can not remove an arbitrary element out of empty set");
        }
        
        Random generator = new Random();
        int pick = generator.nextInt(set.size());
        
        int i = 0;
        for (T item : setCopy) {
            if (i == pick) {
                return item;
            }
            i++;
        }
        
        throw new RuntimeException("Item must have been picked");
    }
}
