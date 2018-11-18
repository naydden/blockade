package scenes;

import game.GameMain;
import game.Role;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WaitingScreen extends HeadLineScreen {
	GameMain listener;

	public WaitingScreen(GameMain listener, Role role) {
		this.listener = listener;

		/**
		 * Parameters definition
		 */
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		HBox btnWrapper = new HBox();
		btnWrapper.setSpacing(10);
		btnWrapper.setPadding(new Insets(5));
		btnWrapper.setAlignment(Pos.CENTER);
		String message = "";

		switch (role) {
		case SERVER:
			message = "Waiting for a client...";
			break;
		case CLIENT:
			message = "Looking for a server...";
		default:
			break;
		}

		Text waiting = new Text(message);
		ProgressBar pb = new ProgressBar(-1.0f);

		/**
		 * Listeners definition
		 */

		/**
		 * Attaching objects in layouts
		 */
		vbox.getChildren().addAll(headLine(), waiting, pb, author());
		this.getChildren().addAll(vbox);
	}

}
