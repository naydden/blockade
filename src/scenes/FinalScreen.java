package scenes;

import game.GameMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Final screen showing the result of the game.
 * 
 * @author Boyan Naydenov
 *
 */
public class FinalScreen extends Group {

	public FinalScreen(GameMain listener, String message, StackPane root) {

		/**
		 * Parameters definition
		 */
		Text text = new Text();
		text.setText(message);
		// Setting font to the text
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text.setFill(Color.RED);

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		Button buttonStartAgain = new Button("Play Again");
		Button buttonExit = new Button("Exit");

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		/**
		 * Listeners definition
		 */
		buttonExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		buttonStartAgain.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeScreen(root);
			}
		});

		/**
		 * Attaching objects in layouts
		 */

		vbox.getChildren().addAll(text, buttonExit, buttonStartAgain, author);
		this.getChildren().add(vbox);
	}

}
