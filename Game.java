package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Game {
    private Piece piece;
    private Timeline timeline;
    private Board board;
    private Pane boardPane;
    private int paused;
    private Label label;

    public Game(Pane boardPane) {
        this.paused = 1;
        this.boardPane = boardPane;
        this.board = new Board(boardPane);
        this.generateRandomPiece();
        this.setUpTimeline();
        boardPane.setFocusTraversable(true);
        boardPane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
    }

    /**
     * Generates pieces randomly using switch cases. Uses the Piece class to generate the
     * piece with different coordinates and colors.
     */
    private void generateRandomPiece() {
        int rand = (int)(Math.random() * 6);
        switch (rand) {
            case 0:
                this.piece = new Piece(Constants.OPP_E_PIECE_COORDS, this.boardPane, Color.PINK, this.board);
                break;
            case 1:
                this.piece = new Piece(Constants.T_PIECE_COORDS, this.boardPane, Color.PURPLE, this.board);
                break;
            case 2:
                this.piece = new Piece(Constants.CUBE_PIECE_COORDS, this.boardPane, Color.YELLOW, this.board);
                break;
            case 3:
                this.piece = new Piece(Constants.I_PIECE_COORDS, this.boardPane, Color.SKYBLUE, this.board);
                break;
            case 4:
                this.piece = new Piece(Constants.L_PIECE_COORDS, this.boardPane, Color.ORANGE, this.board);
                break;
            case 5:
                this.piece = new Piece(Constants.OPP_L_PIECE_COORDS, this.boardPane, Color.BLUE, this.board);
                break;
            default:
                this.piece = new Piece(Constants.E_PIECE_COORDS, this.boardPane, Color.LIME, this.board);
                break;
        }
    }

    /**
     * Sets up the timeline and calls on the methods in alwaysCheck.
     */
    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(0.6), (ActionEvent) -> this.alwaysCheck());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * This method is always being called to continuously check if the game is over,
     * if the piece can go down, if it hit the bottom, or if the row needs to be removed
     * when it is full.
     */
    private void alwaysCheck() {
        if (this.board.isGameOver()) {
            this.label = new Label("Game Over!");
            this.timeline.stop();
            this.boardPane.getChildren().add(this.label);
        }
        this.goDown();
        this.pieceHitBottom();
        this.board.removeRow();
    }

    /**
     * Checks if piece can move down using an if-statement, if it is true
     * then it will call in moveDown to visually move it down.
     */
    private void goDown() {
        if (this.piece.checkValidity(1, 0) == true) {
            this.piece.moveDown();
        }
    }

    /**
     * This is continuously checked to see if the piece hit the bottom
     * so that a new piece can be generated.
     */
    private void pieceHitBottom() {
        if (this.piece.checkValidity(1,0) == false) {
                this.piece.setBoardColor(this.board);
                this.generateRandomPiece();
        }
    }

    /**
     * This method handles the key presses. The left, right, and down will move the piece
     * to the left, right, and down respectively. The up key rotates the piece, and spacebar
     * moves it all the way down. The p key pauses the game. These are all done by calling on
     * helper methods.
     * @param e
     */
    private void handleKeyPress(KeyEvent e) {
        switch(e.getCode()){
            case LEFT:
                if (this.piece.checkValidity(0, -1) && this.paused > 0) {
                    this.piece.moveLeft();
                }
                break;
            case RIGHT:
                if (this.piece.checkValidity(0, 1) && this.paused > 0) {
                    this.piece.moveRight();
                }
                break;
            case DOWN:
                if (this.piece.checkValidity(1, 0) && this.paused > 0) {
                    this.piece.moveDown();
                }
                break;
            case UP:
                if (this.piece.getSquares()[0].getFirstColor() != Color.YELLOW && this.paused > 0) {
                    if (this.piece.checkRotateValidity()) {
                        this.piece.rotate();
                    }
                }
                break;
            case P:
                this.paused = this.paused * -1;
                if (this.paused < 0){
                    this.timeline.stop();
                    this.label = new Label("Game is paused, press 'p' to resume!");
                    this.boardPane.getChildren().add(this.label);
                }
                else {
                    this.timeline.play();
                    this.boardPane.getChildren().remove(this.label);
                }
                break;
            case SPACE:
                if (this.piece.checkValidity(1, 0) && this.paused > 0) {
                    this.piece.instantMoveDown();
                }
            default:
                break;
        }
        e.consume();
    }
}
