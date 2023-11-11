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
    public PaneOrganizer() {
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        this.root.setCenter(gamePane);
        Board board = new Board(gamePane);
        HBox buttonPane = new HBox();
        this.root.setBottom(buttonPane);
        buttonPane.setAlignment(Pos.CENTER);
        this.setUpButton(buttonPane);
        Game game = new Game(board.getBoardPane());
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

    /**
     * Returns root.
     * @return
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
