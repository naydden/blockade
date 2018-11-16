package scenes;

import game.ControledMovement;
import game.GameMain;
import game.SuperIntelligentMovement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WelcomeScreenMulti extends Group {
	
	GameMain listener;
	
	public WelcomeScreenMulti(GameMain listener) {
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

		HBox snakeConfigWrapper = new HBox();
		snakeConfigWrapper.setSpacing(10);
		snakeConfigWrapper.setPadding(new Insets(5));
		snakeConfigWrapper.setAlignment(Pos.CENTER);

		VBox snakeConfigWrapperVertical = new VBox();
		snakeConfigWrapperVertical.setSpacing(10);
		snakeConfigWrapperVertical.setPadding(new Insets(5));
		snakeConfigWrapperVertical.setAlignment(Pos.CENTER);

		Text snakeNameLabel = new Text();
		snakeNameLabel.setText("Snake Name:");
		TextField snakeName = new TextField();
		ObservableList<String> options = FXCollections.observableArrayList("Controlled ASDW", "Controlled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode1 = new Text();
		mode1.setText("Choose your mode:");
		ComboBox comboBoxSnake1 = new ComboBox(options);
		snakeConfigWrapperVertical.getChildren().addAll(snakeName, mode1, comboBoxSnake1);

		VBox snakeConfigWrapperVertical2 = new VBox();
		snakeConfigWrapperVertical2.setSpacing(10);
		snakeConfigWrapperVertical2.setPadding(new Insets(5));
		snakeConfigWrapperVertical2.setAlignment(Pos.CENTER);

		Text port = new Text();
		port.setText("Port number: (A server must be waiting!) ");
		TextField snakeName2 = new TextField();
		snakeName2.setPromptText("0000");
		snakeConfigWrapperVertical2.getChildren().addAll(port, snakeName2);

		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		// Start Button
//		Button btnStart = new Button("Start Game");
//		this.movement1 = new ControledMovement("L");
//		this.movement2 = new SuperIntelligentMovement();
//		btnStart.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				initializeSnake(snakeName.getText(),movement1);
//				initializeSnake(snakeName2.getText(),movement2);
//				gameScreen();
//			}
//		});

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(welcomeText, snakeConfigWrapper, author);
		this.getChildren().add(vbox);
	}

}
