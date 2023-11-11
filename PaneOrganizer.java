package tetris;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private BorderPane root;
    private Label label;
    public PaneOrganizer() {
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        this.root.setCenter(gamePane);
        Board board = new Board(gamePane);
        HBox buttonPane = new HBox();
        this.root.setBottom(buttonPane);
        buttonPane.setAlignment(Pos.CENTER);
        this.setUpButton(buttonPane);
        //HBox labelPane = new HBox();
        //this.root.setTop(labelPane);
        Game game = new Game(board.getBoardPane());
        // Add the boardContainer to the center of the root BorderPane
        //this.root.setCenter(boardContainer);
        //Board board = new Board(this.root);
        //this.root.setCenter(board.getBoardPane());
        //Pane gamePane = new Pane();
        //this.root.setCenter(gamePane);
        //new Game(gamePane);
        //this.createLabelPane();
    }
    /**
     * This method sets up my quit button and adds it to the buttonPane.
     * @param buttonPane
     */
    private void setUpButton(HBox buttonPane) {
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        buttonPane.getChildren().addAll(quitButton);
    }
    private void createLabelPane() {
        Pane labelPane = new Pane();
        this.label = new Label("Game is paused. Press 'p' to resume!");
        labelPane.setPrefSize(tetris.Constants.LABEL_PANE_WIDTH, tetris.Constants.LABEL_PANE_HEIGHT);
        //labelPane.setStyle(Constants.LABEL_PANE_COLOR);
        this.label.setOpacity(0.0);
        labelPane.getChildren().add(label);
        this.root.setTop(labelPane);
        //labelPane.setAlignment(Pos.CENTER);
        labelPane.setVisible(false);
    }
    public BorderPane getRoot() {
        return this.root;
    }

}
