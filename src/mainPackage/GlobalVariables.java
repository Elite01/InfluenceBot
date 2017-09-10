package mainPackage;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.sheigutn.pushbullet.Pushbullet;

public class GlobalVariables {

	static String adbLoc;
	static String adbStartInfluence;
	static String adbSwipe;
	static String adbTap;
	static final Rectangle board = new Rectangle(10, 66, 420, 555);
	static JButton btnPause;
	static JButton btnStartStop;
	static JLabel[][] decoderArray;
	static boolean PauseFlag = false;
	public static Pushbullet pushbullet;
	static final Rectangle screen = new Rectangle(0, 0, 441, 784);
	static String screenshotLoc = System.getProperty("java.io.tmpdir") + "\\InfluenceBotScreenshot.png";
	static boolean StopFlag = false;
	public static JTextField txtAdbLoc;
	public static JTextField txtVBoxManageLoc;
	public static JTextField txtVmName;
	static String VBoxManageLoc;
	static String vBoxScreenshot;
	static String VmName;
}