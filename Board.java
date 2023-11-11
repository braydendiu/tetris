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
        //new Piece(this.boardPane, this.boardArray);
    }

    public Pane getBoardPane() {
        return boardPane;
    }

    public Color getColor(int row, int col) {
        return this.boardArray[row][col].getFirstColor();
    }

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

    public void addToBoard(Squares squares) {
        int row = squares.getRow();
        int col = squares.getCol();
        Color color = squares.getFirstColor();
        this.boardArray[row][col].squareColor(color);
        //System.out.println("boardsquare color " + this.boardArray[row][col].getFirstColor());
    }

    public void removeRow() {
        for (int row = 1; row < Constants.BOARD_ROWS-1; row++) {
            Squares[] currRow = boardArray[row];
            System.out.println("isFull for row " + row + " " + this.isFull(currRow));
            if (isFull(currRow)) {
                this.setRowToBlack(row);
                this.shiftRowDown(row);
                System.out.println("calling methods");
            }
        }
    }

    public boolean isFull(Squares[] row) {
        for (Squares squares : row) {
                if (squares.getFirstColor() == Color.BLACK) {
                    return false;
                }
            }
        return true;
    }

    private void setRowToBlack(int row) {
        for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
            this.boardArray[row][col].setFill(Color.BLACK);
        }
    }
    private void shiftRowDown(int row) {
        System.out.println("shift");
        for (int i = row - 1; i > 1; i--) {
            for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
                this.boardArray[row][col].setFill(this.boardArray[row-1][col].getFirstColor());
            }
        }
    }
    public boolean isGameOver() {
        for (int col = 1; col < Constants.BOARD_COLUMNS-1; col++) {
            if (boardArray[1][col].getFirstColor() != Color.BLACK) {
                return true; // Game over if any square in the top row is occupied
            }
        }
        return false;
    }
}
