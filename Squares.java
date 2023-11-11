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
    public void squareColor(Color color) {
        this.square.setFill(color);
        this.color = color;
    }
    public Color getFirstColor() {
        return (Color) this.square.getFill();
    }
    public int getRow() {
        return (int)(this.square.getY() / Constants.SQUARE_WIDTH);
    }
    public int getCol() {
        return (int)(this.square.getX() / Constants.SQUARE_WIDTH);
    }
    public int getX() {
        return (int)(this.square.getX());
    }
    public int getY() {
        return (int)(this.square.getY());
    }
    public void moveXRight() {
        this.square.setX(this.square.getX() + Constants.SQUARE_OFFSET);
    }
    public void moveXLeft() {
        this.square.setX(this.square.getX() - Constants.SQUARE_OFFSET);
    }
    public void moveYDown() {
        this.square.setY(this.square.getY() + Constants.SQUARE_OFFSET);
    }
    public void rotate(int x, int y) {
        int newXLocation = x - y + (int)this.square.getY();
        int newYLocation = y + x - (int)this.square.getX();
        this.square.setX(newXLocation);
        this.square.setY(newYLocation);
    }
    public void setFill(Color color) {
        this.square.setFill(color);
    }
    public void deleteSquare() {
        this.boardPane.getChildren().remove(this.square);
    }

}
