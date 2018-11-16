package scenes;

import game.GameMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainScreen extends Group {
	GameMain listener;
	
	public MainScreen(GameMain listener) {
		this.listener = listener;
		// TEXT EFFECT
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		Text welcomeText = new Text();
		welcomeText.setEffect(ds);
		welcomeText.setCache(true);
		welcomeText.setText("B L O C K A D E");
		// Setting font to the text
		welcomeText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 50));
		welcomeText.setFill(Color.BLUE);

		// Snake Options

		// Start Button
		VBox modeBtnWrapper = new VBox();
		modeBtnWrapper.setSpacing(10);
		modeBtnWrapper.setPadding(new Insets(5));
		modeBtnWrapper.setAlignment(Pos.CENTER);

		Button btnSingle = new Button("Single Instance");
		Button btnMultiplayer = new Button("Multi-instance (Reseau) ");

		btnSingle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(WelcomeScreens.SINGLE);
			}
		});
		btnMultiplayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.welcomeSubScreen(WelcomeScreens.MULTI);
			}
		});
		modeBtnWrapper.getChildren().addAll(btnSingle, btnMultiplayer);

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(welcomeText, modeBtnWrapper, author);
		this.getChildren().add(vbox);
	}

}
