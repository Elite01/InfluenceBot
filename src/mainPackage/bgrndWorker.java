package mainPackage;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class bgrndWorker<T, V> extends SwingWorker<T, V> {

	public static Runtime runtime = Runtime.getRuntime();
	public static BufferedImage scrnsht;
	public char[][] array = new char[25][20];
	public int noEnemiesFoundCounter = 0;
	
	private static void Screenshot() throws IOException {
		while (GlobalVariables.PauseFlag)
			; // pause on user request
		Process proc = runtime.exec(GlobalVariables.vBoxScreenshot);
		BufferedReader std = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String s = null;
		while ((s = std.readLine()) != null) // get normal output
			MainTab.appendConsole(s, Color.BLACK);
		std = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		while ((s = std.readLine()) != null) // get error output
			MainTab.appendConsole(s, Color.BLACK);
		scrnsht = ImageIO.read(new File(GlobalVariables.screenshotLoc));
	}
	
	private Point coord2Point(int x, int y) { // turn X,Y of array to screen
		// coordinates
		int chooseX = (int) (28 + x * 20.25);
		if (y % 2 != 0)
			chooseX += 11;
		return new Point(chooseX, (int) (86 + y * 21.5));
	}

	@Override
	protected T doInBackground() throws Exception {
		GlobalVariables.StopFlag = false;
		int 
		tempInt = 0;
		while (!GlobalVariables.StopFlag) {
			while (GlobalVariables.PauseFlag);
			String state = getGameState();
			if (state.equals("unknown")) {
				System.out.println("UNKNOWN GAME STATE!");
				Thread.sleep(500);
				continue;
			}
			if (state.equals("HomeScreen")) {
				startApp();
				continue;
			}
			if (state.equals("Loading")) {
				Thread.sleep(3000);
				continue;
			}
			if (state.equals("AUTO-WIN")) {
				ImageIO.write(scrnsht, "png", new File(new File(GlobalVariables.screenshotLoc).getParent() + "/influenceWins/" + (tempInt++) + ".png"));
				tapCoord(220, 420); // tap finish
				tapCoord(222, 666);// restart game
				tapCoord(222, 666);
				tapCoord(222, 666);
				continue;
			}
			if (state.equals("Enemys Turn")) {
				continue;
			}
			if (state.equals("My Turn")) {
				myTurn();
				continue;
			}
		}
		return null;
	}

	private String getGameState() throws IOException {
		Screenshot();
		if (scrnsht.getRGB(55, 120) == -11678231) // Influence Icon
			return "HomeScreen"; 
		if (scrnsht.getRGB(255, 375) == -1 && scrnsht.getRGB(300, 410) == -1) // Teremok Games Logo
			return "Loading"; 
		if (scrnsht.getRGB(190, 350) == -13519138 && scrnsht.getRGB(270, 400) == -16738726
			&& scrnsht.getRGB(200, 444) == -1086118)// Loading sign
			return "Loading";
		if (scrnsht.getRGB(200, 50) == -16242639) // AUTO-WIN
			return "AUTO-WIN";
		if (scrnsht.getRGB(200, 50) == -16739750 || scrnsht.getRGB(200, 50) == -1086118
				|| scrnsht.getRGB(200, 50) == -10406 || scrnsht.getRGB(200, 50) == -7050753) // Enemys turn
			return "Enemys Turn";
		if (scrnsht.getRGB(200, 50) == -13519138) // my turn
			return "My Turn";
		return "unknown";
	}
	
	public void myTurn() throws Exception {
		readBoard();
		 Point p = findBrightBlueCell(); // look for bright blue pixel
		if (p == null) {
			MainTab.appendConsole("No active blue pixel found! Finishing turn.", Color.RED);
			tapCoord(222, 666);
			tapCoord(222, 666);
			Thread.sleep(1000);
			noEnemiesFoundCounter++;
			if (noEnemiesFoundCounter > 10) {// protect against game freeze
				noEnemiesFoundCounter = 0;
				restartApp();
			}
		} else {
			noEnemiesFoundCounter = 0;
			MainTab.appendConsole("Found blue pixel at (" + p.x + ", " + p.y + ")", Color.BLACK);
			tapArray(p);
			p = findEnemyCell(p);
			if (p == null)
				return;
			MainTab.appendConsole("Found enemy at (" + p.x + ", " + p.y + ")", Color.BLACK);
			tapArray(p);
		}
	}

	public void readBoard() throws IOException {
		tapCoord(220, 725);
		Screenshot();
		int xlength;
		for (int y = 0; y < 25; y++) {
			if (y % 2 != 0)
				xlength = 20;
			else
				xlength = 20;
			for (int x = 0; x < xlength; x++) {
				int chooseX = (int) (28 + x * 20.25);
				if (y % 2 != 0)
					chooseX += 11;
				int cell = scrnsht.getRGB(chooseX, (int) (86 + y * 21.5));
				switch (cell) {
				case (-65536): // Black
				case (-16776192): // Black
				case (-16252928): // Black
				case (-8716288): // Black
				case (-16777216): // Black
				case (-15728640): // Black
				case (-15138816): // Black
				case (-16775168): // Black
				case (-16774144): // Black
					GlobalVariables.decoderArray[y][x].setText("--");
					GlobalVariables.decoderArray[y][x].setBackground(Color.BLACK);
					array[y][x] = '-';
					break;
				case (-784890): // Grey
				case (-1177847): // Grey
				case (-10855078): // Grey
				case (-11315629): // Grey
					GlobalVariables.decoderArray[y][x].setText("++");
					GlobalVariables.decoderArray[y][x].setBackground(Color.GRAY);
					array[y][x] = '+';
					break;
				case (-8657921): // Blue Active Selected
					GlobalVariables.decoderArray[y][x].setText("B");
					GlobalVariables.decoderArray[y][x].setBackground(Color.BLUE);
					array[y][x] = 'B';
					break;
				case (-4898486): // Blue Active
				case (-8658945): // Blue Active
				case (-13519138): // Blue Active
					GlobalVariables.decoderArray[y][x].setText("B");
					GlobalVariables.decoderArray[y][x].setBackground(Color.BLUE);
					array[y][x] = 'b';
					break;
				case (-16748148): // Blue Inactive
				case (-11387574): // Blue Inactive
				case (-16226940): // Blue Inactive
				case (-16749172): // Blue Inactive
					GlobalVariables.decoderArray[y][x].setText("8");
					GlobalVariables.decoderArray[y][x].setBackground(Color.BLUE);
					array[y][x] = '8';
					break;
				case (-15086972): // Green Active
				case (-16738726): // Green Active
				case (-16739750): // Green Active
					GlobalVariables.decoderArray[y][x].setText("G");
					GlobalVariables.decoderArray[y][x].setBackground(Color.GREEN);
					array[y][x] = 'G';
					break;
				case (-16228038): // Green Inactive
					GlobalVariables.decoderArray[y][x].setText("g");
					GlobalVariables.decoderArray[y][x].setBackground(Color.GREEN);
					array[y][x] = 'g';
					break;
				case (-6519247): // Yellow Inactive
					GlobalVariables.decoderArray[y][x].setText("y");
					GlobalVariables.decoderArray[y][x].setBackground(Color.YELLOW);
					array[y][x] = 'y';
					break;
				case (-61690): // Yellow Active
				case (-9382): // Yellow Active
					GlobalVariables.decoderArray[y][x].setText("Y");
					GlobalVariables.decoderArray[y][x].setBackground(Color.YELLOW);
					array[y][x] = 'Y';
					break;
				case (-6534854): // Red Inactive
					GlobalVariables.decoderArray[y][x].setText("r");
					GlobalVariables.decoderArray[y][x].setBackground(Color.RED);
					array[y][x] = 'r';
					break;
				case (-1086118): // Red Active
					GlobalVariables.decoderArray[y][x].setText("R");
					GlobalVariables.decoderArray[y][x].setBackground(Color.RED);
					array[y][x] = 'R';
					break;
				case (-10273380): // Purple Inactive
					GlobalVariables.decoderArray[y][x].setText("p");
					GlobalVariables.decoderArray[y][x].setBackground(Color.MAGENTA);
					array[y][x] = 'p';
					break;
				case (-7050753): // Purple Active
				case (-6526465): // Purple Active
					GlobalVariables.decoderArray[y][x].setText("P");
					GlobalVariables.decoderArray[y][x].setBackground(Color.MAGENTA);
					array[y][x] = 'P';
					break;
				default:
					GlobalVariables.decoderArray[y][x].setText("??");
					GlobalVariables.decoderArray[y][x].setBackground(Color.WHITE);
					array[y][x] = '?';
					UnknownColor(chooseX, (int) (86 + y * 21.5), cell);
					break;
				}
			}
		}
	}

	private Point findBrightBlueCell() {
		for (int y = 0; y < 25; y++)
			for (int x = 0; x < 20; x++)
				if (array[y][x] == 'B' || array[y][x] == 'b')
					return new Point(x, y);
		return null;
	}

	private Point findEnemyCell(Point p) {
		char checkCell;
		if (p.x > 0) {
			checkCell = array[p.y][p.x - 1];
			if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
				return new Point(p.x - 1, p.y);
		}
		if (p.y > 0) {
			checkCell = array[p.y - 1][p.x];
			if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
				return new Point(p.x, p.y - 1);
		}
		if (p.x < 19) {
			checkCell = array[p.y][p.x + 1];
			if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
				return new Point(p.x + 1, p.y);
		}
		if (p.y < 24) {
			checkCell = array[p.y + 1][p.x];
			if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
				return new Point(p.x, p.y + 1);
		}
		if (p.y % 2 != 0) {
			if (p.x < 20) {
				if (p.y > 0) {
					checkCell = array[p.y - 1][p.x + 1];
					if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
						return new Point(p.x + 1, p.y - 1);

				}
				if (p.y < 24) {
					checkCell = array[p.y + 1][p.x + 1];
					if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
						return new Point(p.x + 1, p.y + 1);
				}
			}
		} else {
			if (p.y > 0 && p.x > 0) {
				checkCell = array[p.y - 1][p.x - 1];
				if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
					return new Point(p.x - 1, p.y - 1);

			}
			if (p.y < 24 && p.x < 20) {
				checkCell = array[p.y + 1][p.x - 1];
				if (checkCell != '-' && checkCell != 'B' && checkCell != 'b' && checkCell != '8')
					return new Point(p.x - 1, p.y + 1);
			}
		}
		MainTab.appendConsole("NO ENEMY FOUND! ABORT.", Color.RED);
		return null;
	}

	public void restartApp() throws Exception {
		MainTab.appendConsole("KILLING APP", Color.red);
		tapCoord(360, 760); // tap "recent" nav button
		Thread.sleep(1000);
		swipeCoord(100, 400, 350, 400);
		Thread.sleep(3000);
		startApp();
	}

	public void startApp() throws Exception {
		MainTab.appendConsole("STARTING APP", Color.green);
		tapCoord(55, 120);
		Thread.sleep(15000);
		tapCoord(220, 375);
	}

	private void swipeCoord(int x1, int y1, int x2, int y2) throws IOException {
		while (GlobalVariables.PauseFlag)
			; // pause on user request
		MainTab.appendConsole("swiping (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")", Color.BLACK);
		Process proc = runtime.exec(GlobalVariables.adbSwipe + x1 + " " + y1 + " " + x2 + " " + y2 + " 100");
		BufferedReader std = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String s = null;
		while ((s = std.readLine()) != null) // get normal output
			MainTab.appendConsole(s, Color.BLACK);
		std = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		while ((s = std.readLine()) != null) // get error output
			MainTab.appendConsole(s, Color.BLACK);
	}

	private void tapArray(Point p) throws IOException {
		tapCoord(coord2Point(p.x, p.y));// get X,Y pointing at the array
	}

	private void tapCoord(int x, int y) throws IOException {
		tapCoord(new Point(x, y));
	}

	private void tapCoord(Point p) throws IOException {
		while (GlobalVariables.PauseFlag)
			; // pause on user request
		MainTab.appendConsole("Tapping at (" + p.x + ", " + p.y + ")", Color.BLACK);
		Process proc = runtime.exec(GlobalVariables.adbTap + p.x + " " + p.y);
		BufferedReader std = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String s = null;
		while ((s = std.readLine()) != null) // get normal output
			MainTab.appendConsole(s, Color.BLACK);
		std = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		while ((s = std.readLine()) != null) // get error output
			MainTab.appendConsole(s, Color.BLACK);
	}

	@SuppressWarnings("unused")
	private void temp() throws IOException {
		Screenshot();
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 20; x++) {
				int chooseX = (int) (28 + x * 20.25);
				if (y % 2 != 0)
					chooseX += 11;
				tapCoord(chooseX, (int) (86 + y * 21.5));
				System.out.println(scrnsht.getRGB(chooseX, (int) (86 + y * 21.5)));
			}
		}
	}

	public void UnknownColor(int x, int y, int rgb) {
		MainTab.appendConsole("Unknown Color! at (" + x + ", " + y + ") - color is " + rgb + ", ("
				+ ((rgb >> 16) & 0x0ff) + ", " + ((rgb >> 8) & 0x0ff) + ", " + ((rgb) & 0x0ff) + ")", Color.RED);
	}
}