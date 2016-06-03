package brain;

import data_types.Board;
import gui.GUI;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        
        configureGUI(board);
    }
    
    public static void configureGUI(Board board) {
        GUI gui = new GUI(board);
    }
    
}
