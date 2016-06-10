package data_types;

/**
 * Represents a mutable Square on a chess board
 * @author Bryson
 */
public class Square {
    private final Coordinate coordinate;
    private boolean occupied;
    private Piece piece;
    
    // Abstraction Function: 
    //  - represents a square at coordinate coordinate that, 
    //    if occupied == true, contains piece on top of it. 
    //    In any other case, no piece is on the square
    // 
    // Rep Invariant: 
    //  - if occupied is true, then piece.exists() is true;
    // 
    // Safety from Rep Exposure:
    //  - all fields are private
    //  - all inputs are immutable
    //  - all outputs are immutable
    //
    
    /**
     * Crate an emptySquare
     * @param coordinate coordinate at which this Square is located
     */
    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.occupied = false;
        this.piece = new EmptyPiece();
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        if (occupied) {
            assert piece.exists();
        }
    }
    
    /**
     * Place a piece on this square
     *      - if this square is already occupied, then
     *           - if the Piece already on this square has the same color as piece:
     *               - do nothing
     *           - if the Piece already on this square is a different color as piece:
     *               - replace the piece on this square
     * @param piece to be placed
     */
    public void addPiece(Piece piece) {
        if (isOccupied()) {
            if (this.piece.color().equals(piece.color())) {
                // chill
            } else {
                this.piece = piece;
                this.occupied = true;
            }
        } else {
            this.piece = piece;
            this.occupied = true;
        }
        
        checkRep();
    }
    
    /**
     * Remove the piece from this square, if it exists. 
     * @param piece
     */
    public void removePiece() {
        this.piece = new EmptyPiece();
        this.occupied = false;
        checkRep();
    }
    
    /**
     * Retrieve the piece currently occupying this square
     * @return the piece currently occupying this square
     */
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * Retrieve the coordinate of this square
     * @return the coordinate of this square
     */
    public Coordinate coordinate() {
        return coordinate;
    }
    
    /**
     * Return true if this square is occupied by a piece
     * @return true if and only if this square is occupied by a piece
     */
    public boolean isOccupied() {
        return this.occupied;
    }
    
    
    /**
     * Retrieve the string representation of this square
     * 
     * @return the chess notation for the coordinate that this square is placed at
     */
    @Override
    public String toString() {
        String firstChar;
        String secondChar;
        
        switch(coordinate.getX()) {
        case 0:
            firstChar = "a";
            break;
        case 1:
            firstChar = "b";
            break;
        case 2:
            firstChar = "c";
            break;
        case 3:
            firstChar = "d";
            break;
        case 4:
            firstChar = "e";
            break;
        case 5:
            firstChar = "f";
            break;
        case 6:
            firstChar = "g";
            break;
        case 7:
            firstChar = "h";
            break;
        default:
            throw new RuntimeException("Should not be here");
        }
        
        secondChar = String.valueOf(coordinate.getY()+1); 
        
        return firstChar + secondChar;
    }
    
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Square)) {return false;}
        
        Square otherSquare = (Square) other;
        
        boolean coordinateSame = this.toString().equals(otherSquare.toString());
        boolean pieceSame = this.getPiece().equals(otherSquare.getPiece());
        
        return coordinateSame && pieceSame;
    }
}
