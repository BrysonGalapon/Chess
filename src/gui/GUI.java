package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import data_types.Board;

/**
 * Represents a Chess GUI
 * @author Bryson
 */
public class GUI extends JFrame{
    private final static String WINDOW_TITLE = "DVS Chess Program";
    private final static String GUI_IMAGES = "../gui_images/";
    private final static int COORDINATE_SYSTEM_MARGIN_LENGTH = 1;
    private final static int BOARD_SIZE = Board.DEFAULT_SIZE;
    private final static int GRID_SIZE = BOARD_SIZE + COORDINATE_SYSTEM_MARGIN_LENGTH;
    
    private final Board board;
    
    private final JButton[][] chessBoardSquares;
    private final JPanel chessBoard;
    
    // Abstraction Function:
    // 
    // Rep Invariant: 
    // 
    // Safety from Rep Exposure: 
    // 
    // TODO Complete all of these
    // 
    // 
    // 
    // 
    
    /**
     * Create a new GUI
     * @param board 
     */
    public GUI(Board board) {
        super(WINDOW_TITLE);
        setLayout(new FlowLayout());
        
        this.board = board;
        
        this.chessBoardSquares = new JButton[BOARD_SIZE][BOARD_SIZE];
        this.chessBoard = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        
        // create the chess buttons
        Insets buttonMargin = new Insets(0, 0, 0, 0); // default button margin is not visible
        for (int row = 0; row < chessBoardSquares.length; row++) {
            for (int col = 0; col < chessBoardSquares.length; col++) {
                JButton button = new JButton();
                button.setMargin(buttonMargin);
                
                ImageIcon icon = new ImageIcon(getClass().getResource(GUI_IMAGES + "black_queen.png"));
                button.setIcon(icon);
                
                button.setBackground(getSquareColor(row, col));
                
                chessBoardSquares[row][col] = button;
            }
        }
        
        // fill in the chessBoard
        chessBoard.add(new JLabel(""));
        
        // fill the top row
        for (int col = 0; col < BOARD_SIZE; col++) {
            chessBoard.add(new JLabel(getColumnLetter(col), SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                switch (col) {
                    case 0:
                        chessBoard.add(new JLabel(getRowNumber(row), SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[col][row]);
                }
            }
        }
        
        add(chessBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // ensure frame is minimized enough to hold all elements in frame
        setMinimumSize(getSize()); // ensures minimum size is enforced
        setVisible(true);
        
    }
    
    /**
     * Assert the Rep Invariant
     */
    private void checkRep() {
        // TODO implement this
    }
    
    /**
     * Retrieve the square color for this coordinate
     * @param row row of square
     * @param col column of square
     * @return Color.WHITE if the (row, col) coordinate is white on a normal chess board,
     *     and Color.BLACK if the (row, col) coordinate is black on a normal chess board
     *     
     *      - this coordinate system is shifted to right, to take into account for the 
     *         coordinate system axis. Thus, the a1 square is the (1, 0) coordinate
     *      - therefore, (1, 0) should return Color.BLACK, (1, 1) should return Color.WHITE, etc.
     */
    private Color getSquareColor(final int row, final int col) {
        boolean sameParity = row%2 == col%2;
        
        if (sameParity) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    /**
     * Retrieve the letter interpretation of a column on a chess board
     *  - requires 0 <= col <= Board.DEFAULT_SIZE
     * @param col column to interpret
     * @return the letter associated to a column on a chess board, using 0-indexing
     *          i.e., if col == 0, then this function will return 'A' 
     */
    private String getColumnLetter(int col) {
        assert 0 <= col && col <= Board.DEFAULT_SIZE;
        
        String cols = "ABCDEFGH";
        
        return cols.substring(col, col+1);
    }
    
    /**
     * Retrieve the number interpretation of a row on a chess board
     *  - requires 0 <= row <= Board.DEFAULT_SIZE 
     * @param col column to interpret
     * @return the number associated to a row on a chess board, using 0-indexing
     *          i.e., if row == 0, then this function will return 8 
     */
    private String getRowNumber(int row) {
        return String.valueOf(Math.abs(row - Board.DEFAULT_SIZE));
    }
}
