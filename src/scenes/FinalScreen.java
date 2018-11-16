package scenes;

import java.util.ArrayList;

import game.GameMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FinalScreen extends Group {
	GameMain listener;
	
	public FinalScreen(GameMain listener, String message,	StackPane root) {
		this.listener = listener;
		
		Text text = new Text();
		text.setText(message);
		// Setting font to the text
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text.setFill(Color.RED);

		Button buttonExit = new Button("Exit");
		buttonExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		Button buttonStartAgain = new Button("Start Again");

		buttonStartAgain.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeScreen(root);
			}
		});

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(text, buttonExit, buttonStartAgain, author);
		this.getChildren().add(vbox);
	}

}
