package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import scenes.FinalScreen;
import scenes.MainScreen;
import scenes.ServerScreen;
import scenes.WaitingScreen;
import scenes.ClientScreen;
import scenes.ClientServerScreen;
import scenes.SingleScreen;
import scenes.AuxScreens;

public class GameMain extends GameEngine {
	private Role role; 
	private ArrayList<Node> allNodes;
	private List<Snake> snakes;
	private List<Movement> movements;
	private Socket socketClient;
	private ObjectOutputStream outCtoS;
	private ObjectInputStream inCfromS;	
	private Socket socketServer;
	private ObjectOutputStream outStoC;
	private ObjectInputStream inSfromC;
	
	StackPane root;

	public GameMain() {
		/**
		 * snakes and movements are in a fixed size array.
		 */
		this.snakes = Arrays.asList(new Snake[2]);
		this.movements = Arrays.asList(new Movement[2]);
		this.allNodes = new ArrayList<Node>();
	}

	/** Application main method */
	public static void main(String[] args) {
		Application.launch(args);
	}
	public void initializeSnake(int i, String name) {
		double propX;
		double propY;
		
		if(i==0) {
			propX = 0.25;
			propY = 0.25;
		}
		else {
			propX = 0.75;
			propY = 0.75;
		}
		
		Position startPos = new Position(WINDOW_SIZE*propX, WINDOW_SIZE *propY);
		Random rand = new Random();
		float r = rand.nextFloat(); float g = rand.nextFloat(); float b = rand.nextFloat();
		Color randomColor = Color.color(r,g,b);
		Snake snake = new Snake(name, startPos, randomColor, i);
		this.snakes.set(i,snake);
	}

	public ArrayList<Node> gameStep() throws Exception {
		switch (role) {
			case SINGLE:
				gameStepSingle();
				break;
			case CLIENT:
				gameStepCS(this.outCtoS,this.inCfromS);
				break;
			case SERVER:
				gameStepCS(this.outStoC,this.inSfromC);
				break;
			default:
				break;
		}
		return allNodes;
	}

	public void welcomeScreen(StackPane root) {
		this.snakes = Arrays.asList(new Snake[2]);
		this.allNodes = new ArrayList<Node>();	
		this.root = root;
		MainScreen ms = new MainScreen(this);
		root.getChildren().setAll(ms);
		StackPane.setAlignment(root, Pos.CENTER);
	}

	public void welcomeSubScreen(AuxScreens option) {
		if(option == AuxScreens.SINGLE) {
			SingleScreen wss = new SingleScreen(this);
			root.getChildren().setAll(wss);
		}
		else if (option == AuxScreens.CLIENTSERVER) {
			ClientServerScreen css = new ClientServerScreen(this);
			root.getChildren().setAll(css);
		}
		else if (option == AuxScreens.CLIENT) {
			ClientScreen cs = new ClientScreen(this);
			root.getChildren().setAll(cs);
		}
		else if (option == AuxScreens.SERVER) {
			ServerScreen ss = new ServerScreen(this);
			root.getChildren().setAll(ss);
		}
		else if (option == AuxScreens.WAITING) {
			WaitingScreen ws = new WaitingScreen(this, role);
			root.getChildren().setAll(ws);
		}		

		StackPane.setAlignment(root, Pos.CENTER);
	}
	
	public void finalScreen(StackPane root,String message) {
		this.root = root;
		FinalScreen fs = new FinalScreen(this,message,root);
		root.getChildren().setAll(fs);
		StackPane.setAlignment(root, Pos.CENTER);
	}
	public void gameStepSingle() throws Exception{
		for (Snake snake : snakes) {
			if(snake != null)
				snake.move(allNodes);
			if(snake.isCrashed())
				throw new Exception("Crash");
		}
		for (Snake snake : snakes) {
			if(snake != null) {
				ArrayList<Node> allAddedNodes = snake.getAllParts();
				for (Node x : allAddedNodes) {
					if (!allNodes.contains(x))
						allNodes.add(x);
				}		
			}
		}
	}
	public void gameStepCS(ObjectOutputStream out, ObjectInputStream in) throws Exception{
		try {
			int ownedInt = role == Role.CLIENT ? 1 : 0;
			int shownInt = role == Role.CLIENT ? 0 : 1; 
			Snake owned = snakes.get(ownedInt);	
			Snake shown = snakes.get(shownInt);
			owned.move(allNodes);
			out.writeObject(owned.nextPosition);
			out.flush();
			MovementConfig nextPosition = (MovementConfig) in.readObject();
			shown.controlledMove(allNodes, nextPosition);
					
			if(owned.isCrashed() || shown.isCrashed())
				throw new Exception("Crash");
			for (Snake snake : snakes) {
				if(snake != null) {
					ArrayList<Node> allAddedNodes = snake.getAllParts();			
					for (Node x : allAddedNodes) {
						if (!allNodes.contains(x))
							allNodes.add(x);
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			this.stopClient();
			this.stopServer();
			throw e;
		}

	}
	public void startServer(int port) throws IOException {
        ServerSocket s = new ServerSocket(port);
        this.socketServer = s.accept();      
        this.outStoC = new ObjectOutputStream(this.socketServer.getOutputStream());
        this.outStoC.flush();
        this.inSfromC = new ObjectInputStream(this.socketServer.getInputStream());
        gameScreen();    
	}
	public void startClient(int serverPort) throws UnknownHostException, IOException {
        this.socketClient = new Socket("localhost", serverPort);
        this.outCtoS = new ObjectOutputStream(this.socketClient.getOutputStream());
        this.outCtoS.flush();
        this.inCfromS = new ObjectInputStream(this.socketClient.getInputStream());
        gameScreen();
	}
	
	public void stopServer() throws IOException {
		this.outStoC.close();
		this.inSfromC.close();
		this.socketServer.close();
	}
	public void stopClient() throws IOException {
		this.outCtoS.close();
		this.inCfromS.close();;
		this.socketClient.close();
	}
	/**
	 * Setters
	 */
	public void setMovement(int i, Movement mov) { this.movements.set(i, mov); }
	public void setRole(Role role) { this.role = role; }
	/**
	 * Getters
	 */
	public Movement getMovement(int i) { return this.movements.get(i) ; }
	public Snake getSnake(int i) { return this.snakes.get(i) ; }
	public Role getRole() { return role; }



}
