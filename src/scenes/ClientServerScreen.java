package scenes;

import game.GameMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientServerScreen extends HeadLineScreen {

	GameMain listener;

	public ClientServerScreen(GameMain listener) {
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

		Button btnClient = new Button("Client");
		Button btnServer = new Button("Server");

		/**
		 * Listeners definition
		 */

		btnClient.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(AuxScreens.CLIENT);
			}
		});
		btnServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(AuxScreens.SERVER);
			}
		});

		/**
		 * Attaching objects in layouts
		 */
		btnWrapper.getChildren().addAll(btnClient, btnServer);
		vbox.getChildren().addAll(headLine(), btnWrapper, author());
		this.getChildren().addAll(vbox);

	}

}
