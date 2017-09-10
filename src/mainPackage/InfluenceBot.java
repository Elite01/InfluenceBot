package mainPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class InfluenceBot {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new InfluenceBot();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JFrame frame;

	InfluenceBot() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		JFrame frame = new JFrame("InfluenceBot 1.0 By Michel Cohen");
		frame.setBounds(100, 100, 500, 700);
		frame.setMinimumSize(new Dimension(500, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(InfluenceBot.class.getResource("/stocks/Icon.png")).getImage());
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JLabel InfluenceBotBar = new JLabel(new ImageIcon(InfluenceBot.class.getResource("/stocks/bar.png")),
				SwingConstants.CENTER);
		frame.getContentPane().add(InfluenceBotBar, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel mainTab = new MainTab();
		tabbedPane.addTab("Main", new ImageIcon(InfluenceBot.class.getResource("/tabIcons/MainIcon.png")), mainTab,
				null);

		JPanel gameSettingsTab = new GameSettingsTab();
		tabbedPane.addTab("Game Settings", new ImageIcon(InfluenceBot.class.getResource("/tabIcons/SettingsIcon.png")),
				gameSettingsTab, null);

		JPanel decoderTab = new DecoderTab();
		tabbedPane.addTab("Decoder", null, decoderTab, null);

		JPanel pushbulletPanel = new PushbulletTab();
		tabbedPane.addTab("Pushbullet", new ImageIcon(InfluenceBot.class.getResource("/tabIcons/PushbulletIcon.png")),
				pushbulletPanel, null);

		JPanel aboutTab = new AboutTab();
		tabbedPane.addTab("About", new ImageIcon(InfluenceBot.class.getResource("/tabIcons/AboutIcon.png")), aboutTab,
				null);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnPause.getText().equals("Pause")) {
					btnPause.setText("Unpause");
					GlobalVariables.PauseFlag = true;
				} else {
					btnPause.setText("Pause");
					GlobalVariables.PauseFlag = false;
				}
			}
		});
		btnPause.setEnabled(false);
		panel.add(btnPause, "cell 0 4");

		JButton btnStartStop = new JButton("Start Bot");
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnStartStop.getText().equals("Start Bot")) {
					btnStartStop.setText("Stop Bot");
					AssignVariables();
					btnPause.setEnabled(true);
					new bgrndWorker<>().execute();

				} else
					 BotStop();
			}
		});
		panel.add(btnStartStop, "cell 0 4");

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnThemes = new JMenu("Themes");
		menuBar.add(mnThemes);

		JRadioButtonMenuItem radioMenuDefault = new JRadioButtonMenuItem("Default");
		radioMenuDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Dimension d = frame.getSize();
				SwingUtilities.updateComponentTreeUI(frame);
				frame.pack();
				frame.setSize(d);
			}
		});
		radioMenuDefault.setSelected(true);

		ButtonGroup buttonGroupThemes = new ButtonGroup();
		buttonGroupThemes.add(radioMenuDefault);
		mnThemes.add(radioMenuDefault);

		LookAndFeelInfo[] LAFI = UIManager.getInstalledLookAndFeels();
		JRadioButtonMenuItem[] radioMenuThemes = new JRadioButtonMenuItem[LAFI.length];
		for (int i = 0; i < LAFI.length; i++) {
			radioMenuThemes[i] = new JRadioButtonMenuItem(LAFI[i].getName());
			String LAFname = LAFI[i].getClassName();
			radioMenuThemes[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						UIManager.setLookAndFeel(LAFname);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Dimension d = frame.getSize();
					SwingUtilities.updateComponentTreeUI(frame);
					frame.pack();
					frame.setSize(d);
				}
			});
			buttonGroupThemes.add(radioMenuThemes[i]);
			mnThemes.add(radioMenuThemes[i]);
		}

		JCheckBox chckbxAlwaysOnTop = new JCheckBox("Always On Top");
		chckbxAlwaysOnTop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setAlwaysOnTop(chckbxAlwaysOnTop.isSelected());
			}
		});
		menuBar.add(chckbxAlwaysOnTop);
	}

	private void AssignVariables() {
		GlobalVariables.adbLoc = GlobalVariables.txtAdbLoc.getText();
		GlobalVariables.adbTap = GlobalVariables.adbLoc + " shell input tap ";
		GlobalVariables.adbSwipe = GlobalVariables.adbLoc + " shell input swipe ";
		GlobalVariables.VBoxManageLoc = GlobalVariables.txtVBoxManageLoc.getText();
		GlobalVariables.VmName = GlobalVariables.txtVmName.getText();
		GlobalVariables.vBoxScreenshot = GlobalVariables.VBoxManageLoc + " controlvm " + GlobalVariables.VmName
				+ " screenshotpng " + GlobalVariables.screenshotLoc;
		GlobalVariables.adbStartInfluence = GlobalVariables.adbLoc + " shell monkey -p com.teremok.influence 1";
	}
	
	public void BotStop() {
		GlobalVariables.StopFlag = true;
		GlobalVariables.btnStartStop.setText("Start Bot");
		GlobalVariables.PauseFlag = false;
		GlobalVariables.btnPause.setText("Pause");
		GlobalVariables.btnPause.setEnabled(false);
	}

}
