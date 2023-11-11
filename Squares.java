package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
public class Squares {
    private Rectangle square;
    private Color color;
    private Pane boardPane;

    public Squares(int row, int col, Pane boardPane, Color color) {
        this.boardPane = boardPane;
        this.square = new Rectangle(col, row, Constants.SQUARE_WIDTH  , Constants.SQUARE_WIDTH );
        this.square.setStroke(Color.BLACK);
        this.square.setFill(color);
        boardPane.getChildren().add(this.square);
    }

    /**
     * Sets the color of each square to a color variable.
     * @param color
     */
    public void squareColor(Color color) {
        this.square.setFill(color);
        this.color = color;
    }

    /**
     * Getter method that gets the color of each individual square,
     * gets called in getColor in my Board class.
     * @return
     */
    public Color getFirstColor() {
        return (Color) this.square.getFill();
    }

    /**
     * Getter method that returns the place of each square in the
     * row, essentially returning the whole row.
     * @return
     */
    public int getRow() {
        return (int)(this.square.getY() / Constants.SQUARE_WIDTH);
    }
    /**
     * Getter method that returns the place of each square in the
     * column, essentially returning the whole column.
     * @return
     */
    public int getCol() {
        return (int)(this.square.getX() / Constants.SQUARE_WIDTH);
    }

    /**
     * Returns the x-value of each square to be used in the getRow
     * method.
     * @return
     */
    public int getX() {
        return (int)(this.square.getX());
    }

    /**
     * Returns the y-value of each square to be used in the getCol
     * method.
     * @return
     */
    public int getY() {
        return (int)(this.square.getY());
    }

    /**
     * Moves the square right by the same amount everytime.
     */
    public void moveXRight() {
        this.square.setX(this.square.getX() + Constants.SQUARE_OFFSET);
    }

    /**
     * Moves the square left by the same amount everytime.
     */
    public void moveXLeft() {
        this.square.setX(this.square.getX() - Constants.SQUARE_OFFSET);
    }

    /**
     * Moves the square down by the same y-amount everytime.
     */
    public void moveYDown() {
        this.square.setY(this.square.getY() + Constants.SQUARE_OFFSET);
    }

    /**
     * Sets the coordinates of each square to a new x and y in order to visually
     * have the piece rotate.
     * @param x
     * @param y
     */
    public void rotate(int x, int y) {
        int newXLocation = x - y + (int)this.square.getY();
        int newYLocation = y + x - (int)this.square.getX();
        this.square.setX(newXLocation);
        this.square.setY(newYLocation);
    }

    /**
     * Sets the color of each square to a color variable that can be changed
     * based on the helper method like getFirstColor.
     * @param color
     */
    public void setFill(Color color) {
        this.square.setFill(color);
    }

    /**
     * Removes the piece from the pane after it falls down.
     */
    public void deleteSquare() {
        this.boardPane.getChildren().remove(this.square);
    }

}
