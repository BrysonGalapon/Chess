package data_types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private Move lastMove;
    
    // Abstraction Function:
    // TODO: Do this.
    //  
    // Rep Invariant:
    //  - exactly two kings, one of opposite color, exist on the board
    //  - the opposite king is not in check
    //  - square at grid[x][y] has coordinate (x, y)
    // 
    // Safety from Rep Exposure:
    // TODO: Finish this. 
    // 

    /**
     * Create a standard chess Board
     */
    public Board() {
        createEmptyGrid();
        
        PieceColor black = PieceColor.BLACK;
        PieceColor white = PieceColor.WHITE;
        
        turn = white;
        lastMove = null;
        
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
     * @param turn the side to move next
     */
    public Board(Map<Piece, Set<Coordinate>> whitePieces, Map<Piece, Set<Coordinate>> blackPieces, PieceColor turn){
        createEmptyGrid();
        this.turn = turn;
        this.lastMove = null;
        
        // add all the white pieces
        for (Piece piece : whitePieces.keySet()) {
            for (Coordinate coord : whitePieces.get(piece)) {
                Square square = getSquare(coord);
                square.addPiece(piece);
                grid[coord.getX()][coord.getY()] = square;
            }
        }
        
        // add all the black pieces
        for (Piece piece : blackPieces.keySet()) {
            for (Coordinate coord : blackPieces.get(piece)) {
                Square square = getSquare(coord);
                square.addPiece(piece);
                grid[coord.getX()][coord.getY()] = square;
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
                assert grid[row][col].squareCoordinate().getX() == row;
                assert grid[row][col].squareCoordinate().getY() == col;
            }
        }
        Set<Coordinate> whiteKingPlacement = whitePieces().get(retrieveKing(PieceColor.WHITE));
        Set<Coordinate> blackKingPlacement = blackPieces().get(retrieveKing(PieceColor.BLACK));
        assert whiteKingPlacement.size() == 1;
        assert blackKingPlacement.size() == 1;
    }
    
    /**
     * Make a chess move on this Board. Flips the side to move.
     * @param chessMove move to make on this board
     * @throws IllegalArgumentException if this move is illegal
     */
    public void move(Move chessMove) throws IllegalArgumentException{
        Square squareFrom = chessMove.squareFrom();
        Piece pieceToMove = chessMove.getPiece();
        
        Square squareSource = getSquare(squareFrom.squareCoordinate());
        
        if (! squareSource.isOccupied())
            throw new IllegalArgumentException("Piece not found");
        
        if (! squareSource.getPiece().equals(pieceToMove))
            throw new IllegalArgumentException("Different piece is on that square");
        
        Set<Move> legalMoves = legalMoves();
        
        if (legalMoves.contains(chessMove)) {
            movePiece(chessMove);
        } else {
            throw new IllegalArgumentException("Move is illegal");
        }
        
        this.lastMove = chessMove;
        changeTurn();
        checkRep();
    }
    
   /**
    * Retrieve the placement of all black pieces currently on the board
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
                            blackPlacement.add(square.squareCoordinate());
                            blackPieces.put(piece, blackPlacement);
                        } else {
                            Set<Coordinate> blackPlacement = new HashSet<>();
                            blackPlacement.add(square.squareCoordinate());
                            blackPieces.put(piece, blackPlacement);
                        }
                    }
                }
            }
        }
        
        return blackPieces;
    }
    
    /**
     * Retrieve the placement of all white pieces currently on the board
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
                             whitePlacement.add(square.squareCoordinate());
                             whitePieces.put(piece, whitePlacement);
                         } else {
                             Set<Coordinate> whitePlacement = new HashSet<>();
                             whitePlacement.add(square.squareCoordinate());
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
     * Retrieve all legal moves the current player has
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
            if (piece.isPawn()) {
                for (Coordinate coordFrom : pieces.get(piece)) {
                    Set<Coordinate> moveSet = piece.moveSet(coordFrom);
                    for (Coordinate coordTo : moveSet) {
                        Square squareTo = getSquare(coordTo);
                        if (squareTo.isOccupied()) {
                            if (squareTo.getPiece().color().equals(turn())) // can't land on a spot that contains same color
                                continue;
                        }
                        
                        if (squareTo.isOccupied() && coordFrom.getX() == coordTo.getX()) // can't push a pawn to land on a piece
                            continue;
                        
                        boolean pushedTwoSquares = coordTo.getY() == coordFrom.getY()+2;
                        Square oneSquareAbove = getSquare(new Coordinate(coordFrom.getX(), coordFrom.getY()+1));
                        if (pushedTwoSquares && oneSquareAbove.isOccupied()) { // can't jump over pieces
                            continue;
                        }
                        
                        
                        boolean isCaptureMove = coordTo.getX() != coordFrom.getX();
                        Move lastMove = getLastMove();
                        if (lastMove != null) {
                            Piece lastMovePiece = lastMove.getPiece();
                            Coordinate lastMoveCoordFrom = lastMove.squareFrom().squareCoordinate();
                            Coordinate lastMoveCoordTo = lastMove.squareTo().squareCoordinate();
                            boolean pawnHadPushedTwoSquares = lastMoveCoordFrom.getX() == lastMoveCoordTo.getX() && lastMoveCoordFrom.getY()+2 == lastMoveCoordTo.getY();
                            boolean pawnHadPushedAdjacentFile = lastMoveCoordFrom.getX() == coordTo.getX();
                            boolean pawnCanCapture = coordInBetween(lastMoveCoordFrom, lastMoveCoordTo).contains(coordTo);
                            boolean canEnPassent = lastMovePiece.isPawn() && pawnHadPushedTwoSquares && pawnHadPushedAdjacentFile && pawnCanCapture;
                            
                            if (isCaptureMove && !squareTo.isOccupied() && !canEnPassent) {
                                continue;
                            }
                        }
                        
                        if (isCaptureMove && !squareTo.isOccupied()) {
                            continue;
                        }
                        
                        legalMoves.add(new Move(piece, getSquare(coordFrom), getSquare(coordTo)));
                    }
                }
            } else {
                for (Coordinate coordFrom : pieces.get(piece)) {
                    Set<Coordinate> moveSet = piece.moveSet(coordFrom);
                    for (Coordinate coordTo : moveSet) {
                        Square squareTo = getSquare(coordTo);
                        if (squareTo.isOccupied()) {
                            if (squareTo.getPiece().color().equals(turn())) // can't land on a spot that contains same color
                                continue;
                        }
                        
                        List<Coordinate> inBetween = coordInBetween(coordFrom, coordTo);
                        // TODO: Optimize this check using dynamic programming
                        boolean isValidMove = true;
                        for (int i = 0; i < inBetween.size(); i++) {
                            Coordinate coord = inBetween.get(i);
                            Square square = getSquare(coord);
                            if (getSquare(coord).isOccupied()) {
                                Piece pieceOnBoard = square.getPiece();
                                if (pieceOnBoard.color().equals(turn())) {
                                    // found a piece that is of the same color
                                    isValidMove = false;
                                    break;
                                } else {
                                    // found a piece that is the opposite color
                                    if (i == inBetween.size()-1) { // at end of list?
                                        isValidMove = true;
                                    } else {
                                        // not at end, can't jump over pieces
                                        isValidMove = false;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        if (isValidMove) {
                            legalMoves.add(new Move(piece, getSquare(coordFrom), getSquare(coordTo)));
                        }
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
     *          - if there is no last move, return null
     */
    public Move getLastMove() {
        return lastMove;
    }
    
    /**
     * Retrieve the square at the given coordinate
     * @param coord coordinate of square to obtain
     * @return the square at coordinate coord
     */
    public Square getSquare(Coordinate coord) {
        Square square = grid[coord.getX()][coord.getY()];
        
        Square squareCopy = new Square(coord);
        
        if (square.isOccupied()) {
            squareCopy.addPiece(square.getPiece());
        }
        
        return squareCopy;
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
        
        Square squareCopy = new Square(square.squareCoordinate());
        
        if (square.isOccupied()) {
            squareCopy.addPiece(square.getPiece());
        }
            
        checkRep();
        return squareCopy;
    }
    
    /**
     * Replace a square on the chess board
     * @param square to be placed on the chess board
     */
    public void setSquare(Square square) {
        Coordinate squareCoordinate = square.squareCoordinate();
        
        grid[squareCoordinate.getX()][squareCoordinate.getY()] = square;
        checkRep();
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
    
    private Set<Move> filterChecks(Set<Move> moveSet) {
        Set<Move> filteredMoves = new HashSet<Move>();
        for (Move move : moveSet) {
            Piece pieceBeingTaken = new EmptyPiece();
            if (move.squareTo().isOccupied())
                pieceBeingTaken = move.squareTo().getPiece();
            
            movePiece(move);
            
            if (!inCheck()) {
                filteredMoves.add(move);
            }
            
            takeBackMove(move, pieceBeingTaken);
        }
        
        return filteredMoves;
    }
    
    /**
     * Forcibly move a piece on this Board
     * @param move move to make on this board
     */
    private void movePiece(Move move) {
        Square squareFrom = getSquare(move.squareFrom().squareCoordinate());
        Square squareTo = getSquare(move.squareTo().squareCoordinate());
        Piece pieceToMove = move.getPiece();
        
        if (!squareFrom.isOccupied()) {
            throw new RuntimeException("No piece is there to move");
        }
        
        squareFrom.removePiece();
        
        if (pieceToMove.isPawn())
            pieceToMove = Piece.pawn(pieceToMove.color(), true);
        
        squareTo.addPiece(pieceToMove);
        
        Coordinate coordFrom = squareFrom.squareCoordinate();
        Coordinate coordTo = squareTo.squareCoordinate();
        
        grid[coordFrom.getX()][coordFrom.getY()] = squareFrom;
        grid[coordTo.getX()][coordTo.getY()] = squareTo;
    }
    
    /**
     * Take back a move
     *  - requires that move has been the last move that was played
     * @param move move to be taken back
     * @param piece piece that may have been taken on the move move
     */
    private void takeBackMove(Move move, Piece piece) {
        Square squareFrom = getSquare(move.squareFrom().squareCoordinate());
        Square squareTo = getSquare(move.squareTo().squareCoordinate());
        Piece pieceToMove = move.getPiece();
        
        if (!squareTo.isOccupied()) {
            throw new RuntimeException("Can't find the piece to move back");
        }
        
        
        if (!squareTo.getPiece().equals(pieceToMove)) {
            if (!pieceToMove.isPawn() || !squareTo.getPiece().isPawn())
                throw new RuntimeException("Can't find the piece to move back");
        }
        
        squareTo.removePiece();
        squareFrom.addPiece(pieceToMove);
        
        if (piece.exists()) {
            squareTo.addPiece(piece);
        }
        
        Coordinate coordFrom = squareFrom.squareCoordinate();
        Coordinate coordTo = squareTo.squareCoordinate();
        
        grid[coordFrom.getX()][coordFrom.getY()] = squareFrom;
        grid[coordTo.getX()][coordTo.getY()] = squareTo;
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
        
        Set<Coordinate> kingPlacement = currentPieces.get(kingOnBoard);

        // move the king to coord
        kingPlacement.remove(currentKingCoord);
        kingPlacement.add(coord);
        
        boolean kingInCheck = inCheck();
        
        // place the king back
        kingPlacement.remove(coord);
        kingPlacement.add(currentKingCoord);
    
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
                        boolean isLegalMove = true;
                        // TODO: Optimize using dynamic programming here too
                        List<Coordinate> inBetween = coordInBetween(coordFrom, coordTo);
                        for (int i = 0; i < inBetween.size(); i++) {
                            Coordinate coordInBetween = inBetween.get(i);
                            if (i != inBetween.size()-1 && getSquare(coordInBetween).isOccupied()) {
                                isLegalMove = false;
                                break;
                            }
                        }
                        
                        if (!isLegalMove) {
                            continue;
                        }
                        
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
}
