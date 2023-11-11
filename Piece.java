package tetris;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Piece {

    public Squares[] squareArray;
    private Board board;
    private Pane boardPane;
    public Piece(int [][] coords, Pane boardPane, Color color, Board board) {
        this.boardPane = boardPane;
        this.squareArray = new Squares[4];
        //Color color = null;
        this.board = board;
        this.generatePiece(coords, boardPane, color);
    }
    private void generatePiece(int [][] coords, Pane boardPane, Color color) {
        for (int i = 0; i < 4; i++) {
            int x = coords[i][0];
            int y = coords[i][1];
            this.squareArray[i] = new Squares(x + 60, y + 120, boardPane, color);
        }
    }

    public Squares[] getSquares () {
        return this.squareArray;
    }

    public void moveRight() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveXRight();
        }
    }
    public void moveLeft() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveXLeft();
        }
    }
    public void moveDown() {
        for (Squares currSquare : this.squareArray){
            currSquare.moveYDown();
        }
    }
    public void instantMoveDown() {
        while (this.checkValidity(1, 0)) {
            for (Squares currSquare : this.squareArray){
                currSquare.moveYDown();
            }
        }
    }
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

    public void rotate() {
        int centerOfRotationX = this.squareArray[0].getX();
        int centerOfRotationY = this.squareArray[0].getY();
        if (checkRotateValidity()) {
            for (Squares currSquare : this.squareArray){
                currSquare.rotate(centerOfRotationX, centerOfRotationY);
            }
        }
    }
    public void setBoardColor(Board board) {
        for (Squares squares : this.squareArray) {
            board.addToBoard(squares);
            this.removeSquare();
        }
    }
    public void removeSquare() {
        for (Squares squares : this.squareArray) {
            squares.deleteSquare();
        }
    }
}
