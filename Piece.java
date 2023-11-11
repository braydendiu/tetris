package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piece {
    public Squares[] squareArray;
    private Board board;
    private Pane boardPane;
    public Piece(int [][] coords, Pane boardPane, Color color, Board board) {
        this.boardPane = boardPane;
        this.squareArray = new Squares[4];
        this.board = board;
        this.generatePiece(coords, boardPane, color);
    }

    /**
     * Generates each piece by using a 1-D array of 4 squares based on
     * coordinates of each square.
     * @param coords
     * @param boardPane
     * @param color
     */
    private void generatePiece(int [][] coords, Pane boardPane, Color color) {
        for (int i = 0; i < 4; i++) {
            int x = coords[i][0];
            int y = coords[i][1];
            this.squareArray[i] = new Squares(x + 60, y + 120, boardPane, color);
        }
    }

    /**
     * Returns array of squares.
     * @return
     */
    public Squares[] getSquares () {
        return this.squareArray;
    }

    /**
     * Calls on the move method in Squares class to move each square in the piece.
     */
    public void moveRight() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveXRight();
        }
    }
    /**
     * Calls on the move method in Squares class to move each square in the piece.
     */
    public void moveLeft() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveXLeft();
        }
    }
    /**
     * Calls on the move method in Squares class to move each square in the piece.
     */
    public void moveDown() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveYDown();
        }
    }

    /**
     * Uses a while-loop to keep checking if the piece can move down, so that
     * it can instantly move down to the next available spot.
     */
    public void instantMoveDown() {
        while (this.checkValidity(1, 0)) {
            for (Squares currSquare : this.squareArray){
                currSquare.moveYDown();
            }
        }
    }

    /**
     * Uses the getRow and getCol methods to see if the color is black, and based off that
     * it returns a boolean value to see if the move is valid.
     * @param dRow
     * @param dCol
     * @return
     */
    public boolean checkValidity(int dRow, int dCol) {
        for (Squares squares : this.squareArray) {
            int currRow = squares.getRow();
            int currCol = squares.getCol();
            if (this.board.getColor(currRow + dRow, currCol + dCol) != Color.BLACK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the new x and y location of each square and sees if it will land in
     * a black square, returns a boolean value to determine if the rotation is valid.
     * @return
     */
    public boolean checkRotateValidity() {
        int centerOfRotationX = this.squareArray[0].getX();
        int centerOfRotationY = this.squareArray[0].getY();
        for (Squares squares : this.squareArray) {
            int oldXLocation = squares.getX();
            int oldYLocation = squares.getY();
            int newXLocation = centerOfRotationX - centerOfRotationY + oldYLocation;
            int newYLocation = centerOfRotationY + centerOfRotationX - oldXLocation;
            int newRow = newYLocation / Constants.SQUARE_WIDTH;
            int newCol = newXLocation / Constants.SQUARE_WIDTH;
            if (this.board.getColor(newRow, newCol) != Color.BLACK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Rotates each square in the array of pieces and checks if the rotation is valid,
     * if it is then it will call on the rotate method to actually get the piece to
     * rotate.
     */
    public void rotate() {
        int centerOfRotationX = this.squareArray[0].getX();
        int centerOfRotationY = this.squareArray[0].getY();
        if (checkRotateValidity()) {
            for (Squares currSquare : this.squareArray){
                currSquare.rotate(centerOfRotationX, centerOfRotationY);
            }
        }
    }

    /**
     * Sets the color of the board to the color of the piece that falls on top of it
     * by calling on the addToBoard method in Squares class. Also calls on deleteSquare
     * to remove the piece from the pane so that there aren't overlapping squares.
     * @param board
     */
    public void setBoardColor(Board board) {
        for (Squares squares : this.squareArray) {
            board.addToBoard(squares);
            squares.deleteSquare();
        }
    }
}
