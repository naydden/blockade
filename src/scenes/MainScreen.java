package scenes;

import game.GameMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainScreen extends HeadLineScreen {
	GameMain listener;
	
	public MainScreen(GameMain listener) {
		this.listener = listener;

		/**
		 * Parameters definition
		 */
		VBox modeBtnWrapper = new VBox();
		modeBtnWrapper.setSpacing(10);
		modeBtnWrapper.setPadding(new Insets(5));
		modeBtnWrapper.setAlignment(Pos.CENTER);

		Button btnSingle = new Button("Single Instance");
		Button btnMultiplayer = new Button("Multi-instance (Reseau) ");

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);
		
		/**
		 * Listeners definition
		 */
		btnSingle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(AuxScreens.SINGLE);
			}
		});
		btnMultiplayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(AuxScreens.CLIENTSERVER);
			}
		});
		
		/**
		 * Attaching objects in layouts
		 */
		modeBtnWrapper.getChildren().addAll(btnSingle, btnMultiplayer);
		vbox.getChildren().addAll(headLine(), modeBtnWrapper, author());
		this.getChildren().add(vbox);
	}
	

}
