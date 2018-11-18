package game;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class GameEngine extends Application implements Runnable {

	/** Speed of the game */
	static public final long DELAY_MS = 250L;

	/** Graphical window title */
	static public String TITLE = "BLOCKADE";

	/** Background Color */
	static public final Color BG_COLOR = Color.WHITE;

	/** The size of all the elements building up the grid */
	static public final double ELEMENT_SIZE = 24;

	/** The number of lines/columns in the grid */
	static public final int GRID_SIZE = 24;

	/** Graphical window size (square) */
	static public final double WINDOW_SIZE = GRID_SIZE * ELEMENT_SIZE;

	/** Root of the Java FX scene graph containing all the elements to display */
	private StackPane root;
	private Group rootGame;
	
	protected Scene scene;
	private Scene game;
	protected Stage primaryStage;
	
	/** The timer for scheduling next game step */
	private Timer timer;
	
	/**
	 * Initialize the graphical display.
	 *
	 * In a Java FX application, this is a mandatory replacement of the constructor.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// Create the root of the graph scene, and all its children
		root = new StackPane();
		rootGame = new Group();

		// create the scene graph
		scene = new Scene(root, WINDOW_SIZE, WINDOW_SIZE, BG_COLOR);
		game = new Scene(rootGame, WINDOW_SIZE, WINDOW_SIZE, BG_COLOR);
		
		// add a KeyEvent listener to the scene
		game.addEventHandler(KeyEvent.KEY_PRESSED, event -> Keyboard.storeLastKeyCode(event.getCode()));

		// terminate the Application when the window is closed
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		welcomeScreen(root);
		// Show a graphical window with all the graph scene content
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:snake.png"));
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	private class GameTimerTask extends TimerTask {
		private Runnable gameStep;

		public GameTimerTask(Runnable gameStep) {
			this.gameStep = gameStep;
		}

		// The JavaFX thread is the only one that should draw on screen.
		// This run method will be executed by a timer thread,
		// so we gently ask the JavaFX to run our gameStep when he has
		// finished its other duties (hopefully as soon as possible)
		public void run() {
			Platform.runLater(gameStep);
		}

	}

	// This is the run method that will be executed roughly periodically by the
	// JavaFX thread.
	// It could be much more optimized wrt to the graph scene updating,
	// but this should be enough for simple games.
	final public void run() {
		try {
			rootGame.getChildren().setAll(gameStep());
			timer.schedule(new GameTimerTask(this), DELAY_MS);
		} catch (Exception e) {
			primaryStage.setScene(scene);
			primaryStage.setTitle(TITLE);
			primaryStage.setResizable(false);
			primaryStage.show();
			String message = e.getMessage();
			if(getSnake(0).isCrashed() && getSnake(1).isCrashed())
				message = "Game nul. Both snakes crashed.";
			else if(getSnake(0).isCrashed())
				message = "Snake "+ getSnake(0).snakeName + " has crashed!";
			else if(getSnake(1).isCrashed())
				message = "Snake "+ getSnake(1).snakeName + " has crashed!";
			else
				message = "Invalid Game. Check code.";
			finalScreen(root,message);
		}
	}


	
	public void gameScreen() {
		rootGame.getChildren().clear();
		primaryStage.setScene(game);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();
		// Create the timer
		timer = new Timer();
		// Schedule the first game step.
		// Usually a JavaFX game would use a TimeLine object in order to define the
		// KeyFrames
		// of an animation.
		// However here we anticipate the fact that a Game would have to be synchronized
		// on
		// two separate computers : they have their own clocks, and there's always a
		// drift
		// (or skew) between two clocks.
		// So, we decide to use the good old Timer instead, and don't use a strictly
		// periodic
		// TimerTask : the next delay will only be scheduled at the end of a game step,
		// allowing the timers on two different computers to roughly synchronize when
		// exchanging messages.
		timer.schedule(new GameTimerTask(this), DELAY_MS);
		
	}



	/**
	 * Implement your own gameStep method for your game.
	 * 
	 * @return a Collection of Node that will form the new JavaFX graph scene. All
	 *         theses nodes will be displayed on screen, except if their coordinates
	 *         are outside the window bounds...
	 * @throws Exception
	 */
	public abstract Collection<Node> gameStep() throws Exception;
	public abstract void welcomeScreen(StackPane root);
	public abstract void finalScreen(StackPane root,String snake);
	public abstract Snake getSnake(int i);

}
