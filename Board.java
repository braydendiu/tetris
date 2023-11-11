package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Board {
    private Squares[][] boardArray;
    private Pane boardPane;

    public Board(Pane boardPane) {
        this.boardArray = new Squares[Constants.BOARD_ROWS][Constants.BOARD_COLUMNS];
        this.boardPane = boardPane;
        this.setUpBoard();
    }

    /**
     * Returns the boardPane.
     * @return
     */
    public Pane getBoardPane() {
        return this.boardPane;
    }

    /**
     * This getter method returns the color of each square in the board array.
     * It calls on a helper method in my Squares class that gets the color
     * of the individual square.
     * @param row
     * @param col
     * @return
     */
    public Color getColor(int row, int col) {
        return this.boardArray[row][col].getFirstColor();
    }

    /**
     * This method uses nested for-loops to iterate through the 2-D board
     * array in order to set up the board with the grey border.
     */
    private void setUpBoard() {
        for (int row = 0; row < Constants.BOARD_ROWS; row++) {
            for (int col = 0; col < Constants.BOARD_COLUMNS; col++) {
                if (row == 0 || row == Constants.BOARD_ROWS - 1 || col == 0 || col == Constants.BOARD_COLUMNS - 1) {
                    this.boardArray[row][col] = new Squares(row * Constants.SQUARE_WIDTH, col * Constants.SQUARE_WIDTH, this.boardPane, Color.GRAY);
                    this.boardArray[row][col].squareColor(Color.GRAY);
                } else {
                    this.boardArray[row][col] = new Squares(row * Constants.SQUARE_WIDTH, col * Constants.SQUARE_WIDTH, this.boardPane, Color.BLACK);
                    this.boardArray[row][col].squareColor(Color.BLACK);
                }
            }
        }
    }

    /**
     * This is a helper method that changes the color of the board to the color
     * of the piece that falls on that spot. This is used so that the full row
     * can be removed and shifted down based on color checking.
     * @param squares
     */
    public void addToBoard(Squares squares) {
        int row = squares.getRow();
        int col = squares.getCol();
        Color color = squares.getFirstColor();
        this.boardArray[row][col].squareColor(color);
    }

    /**
     * This method iterates through each row and calls on the 3 helper methods.
     * First, it checks if the row is full. If so, then it will set the row to
     * the color black and then shift the row down by calling on the methods
     * below.
     */
    public void removeRow() {
        for (int row = 1; row < Constants.BOARD_ROWS-1; row++) {
            Squares[] currRow = this.boardArray[row];
            if (isFull(currRow)) {
                this.setRowToBlack(row);
                this.shiftRowDown(row);
            }
        }
    }

    /**
     * This method is a boolean that checks if a row is full with
     * pieces by iterating through each square in the row and seeing
     * if the color is black or not.
     * @param row
     * @return
     */
    public boolean isFull(Squares[] row) {
        for (Squares squares : row) {
            if (squares.getFirstColor() == Color.BLACK) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method changes the color of the full row to black, but only
     * when it gets called after removeRow checks if the row is full.
     * @param row
     */
    private void setRowToBlack(int row) {
        for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
            this.boardArray[row][col].setFill(Color.BLACK);
        }
    }

    /**
     * This method sets the color of the row that just got cleared to the color
     * above it, which visually makes it seem like the row is shifting down.
     * @param row
     */
    private void shiftRowDown(int row) {
        for (int i = row; i > 1; i--) {
            for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
                this.boardArray[i][col].setFill(this.boardArray[i-1][col].getFirstColor());            }
        }
    }

    /**
     * This boolean iterates through the top row of the board that isn't the border
     * and checks if any of the colors aren't black. If the if-statement is true,
     * then it will return true and the game will end. This is continuously
     * checked in my Game class.
     * @return
     */
    public boolean isGameOver() {
        for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
            if (this.boardArray[1][col].getFirstColor() != Color.BLACK) {
                return true;
            }
        }
        return false;
    }
}