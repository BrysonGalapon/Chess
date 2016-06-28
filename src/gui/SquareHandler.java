package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import data_types.Coordinate;

public class SquareHandler implements ActionListener{
    private final Coordinate coordinate;
    private final BlockingQueue<Coordinate> coordQueue;
    
    // Abstraction Function:
    // - represents a button on a gui chess board
    //
    // Rep Invariant: 
    //  - true 
    //
    // Safety from Rep Exposure: 
    //  - all inputs are immutable
    //  - all outputs are immutable
    //  - all fields are private and final
    // 
    
    public SquareHandler(Coordinate coordinate, BlockingQueue<Coordinate> coordQueue) {
        this.coordinate = coordinate;
        this.coordQueue = coordQueue;
        checkRep();
    }
    
    /**
     * Assert the Rep invariant
     */
    private void checkRep() {
        assert true;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            coordQueue.put(coordinate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
