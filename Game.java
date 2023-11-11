package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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

    private void generateRandomPiece() {
        int rand = (int)(Math.random() * 6);
        switch (rand) {
            case 0:
                this.piece = new Piece(Constants.OPP_E_PIECE_COORDS, boardPane, Color.PINK, this.board);
                break;
            case 1:
                this.piece = new Piece(Constants.T_PIECE_COORDS, boardPane, Color.PURPLE, this.board);
                break;
            case 2:
                this.piece = new Piece(Constants.CUBE_PIECE_COORDS, boardPane, Color.YELLOW, this.board);
                break;
            case 3:
                this.piece = new Piece(Constants.I_PIECE_COORDS, boardPane, Color.SKYBLUE, this.board);
                break;
            case 4:
                this.piece = new Piece(Constants.L_PIECE_COORDS, boardPane, Color.ORANGE, this.board);
                break;
            case 5:
                this.piece = new Piece(Constants.OPP_L_PIECE_COORDS, boardPane, Color.BLUE, this.board);
                break;
            default:
                this.piece = new Piece(Constants.E_PIECE_COORDS, boardPane, Color.LIME, this.board);
                break;
        }
    }

    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(0.6), (ActionEvent) -> this.alwaysCheck());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }
    /*private void setUpSpacebarTimeline(Pane boardPane) {
        KeyFrame kf2 = new KeyFrame(Duration.millis(1), (ActionEvent) -> this.spacebarGoDown(boardPane));
        this.spacebarTimeline = new Timeline(kf2);
        this.spacebarTimeline.setCycleCount(Animation.INDEFINITE);
        this.spacebarTimeline.play();
    }
    private void spacebarGoDown(Pane boardPane) {
        if (this.piece.checkValidity(1, 0) == true) {
            this.piece.moveDown();
        }
        else {
            this.spacebarTimeline.stop();
            this.piece.setBoardColor(this.board);
            this.generateRandomPiece(boardPane);
        }
    }*/

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

    private void goDown() {
        if (this.piece.checkValidity(1, 0) == true) {
            this.piece.moveDown();
        }
    }
    private void pieceHitBottom() {
        if (this.piece.checkValidity(1,0) == false) {
                this.piece.setBoardColor(this.board);
                this.generateRandomPiece();
        }
    }
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
                /*label.setText("Game is paused. Press 'p' to resume!");
                labelPane.getChildren().add(label);*/
                //Label gamePaused = new Label("Game is paused. Press 'p' to resume!");
                //this.root.setCenter(label);
                //this.root.setCenter(gamePaused);
                if (this.paused < 0){
                    this.timeline.stop();
                    this.label = new Label("Game is paused, press 'p' to resume!");
                    this.boardPane.getChildren().add(this.label);
                    //Label gamePaused = new Label("Game is paused. Press 'p' to resume!");
                    //this.root.setCenter(gamePaused);
                    //gamePaused.toFront();
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
