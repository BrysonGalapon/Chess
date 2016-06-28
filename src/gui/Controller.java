package gui;

import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import data_types.Board;
import data_types.Coordinate;
import data_types.Move;

public class Controller implements Runnable{
    
    private final BlockingQueue<Move> moveQueue;
    private final BlockingQueue<Coordinate> coordQueue;
    private final Board board;
    
    public Controller(BlockingQueue<Coordinate> coordQueue, BlockingQueue<Move> moveQueue, Board board) {
        this.board = board;
        this.coordQueue = coordQueue;
        this.moveQueue = moveQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Coordinate firstCoord = coordQueue.take();
                Coordinate secondCoord = coordQueue.take();
                
                Move move;
                
                if (board.isEnPassent(firstCoord, secondCoord)) {
                    move = Move.enPassent(board.getSquare(firstCoord), board.getSquare(secondCoord));
                } else {
                    move = Move.createMove(board.getSquare(firstCoord), board.getSquare(secondCoord));
                }
                
                moveQueue.put(move);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Invalid move, Please try again.");
                continue;
            }
        }
    }
}
