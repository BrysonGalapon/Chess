package engine;

import data_types.Board;
import data_types.Move;

/**
 * Represents a Chess Engine running on a Board
 * @author Bryson
 */
public class Engine {
    private final Board board;
    
    // Abstraction Function:
    // 
    // Representation Invariant: 
    // 
    // Safety from Rep Exposure: 
    //
    // TODO Do all of these
    // 
    // 
    // 
    
    /**
     * Create a new Engine that is running on a board
     * @param board board to run engine on
     */
    public Engine(Board board) {
        this.board = board;
        checkRep();
    }
    
    /**
     * Assert the Rep Invariant.
     */
    private void checkRep() {
        // TODO finish this
    }
    
    public void start() {
        // finish this, plus the specification
    }
    
    public Move getBestMove() {
        // finish this, plus the specification
        throw new RuntimeException("Unimplemented");
    }
}
