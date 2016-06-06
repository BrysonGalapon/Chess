package brain;

import data_types.Board;
import engine.Engine;
import gui.GUI;

public class Main {
    private final static int ENGINE_WAIT_TIME = 5000; // milliseconds 
    
    public static void main(String[] args) {
        Board board = new Board();
        
        GUI gui = configureGUI(board);
        runEngine(board);
    }
    
    public static GUI configureGUI(Board board) {
        GUI gui = new GUI(board);
        return gui;
    }
    
    public static void runEngine(Board board) {
        Thread engineThread = new Thread(new Runnable() {
            public void run() {
                Engine engine = new Engine(board);
                engine.start();
            }
        });
        
        engineThread.start();
    }
}
