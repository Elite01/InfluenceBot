package mainPackage;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSplitPane;

public class MainTab extends JPanel {
	private static JTextPane console;
	private static Document doc;
	private static final long serialVersionUID = 4528708630828481145L;
	private static MutableAttributeSet style;
	final JFileChooser fileChooser = new JFileChooser();
	private static String lastDate = "";

	public static void appendConsole(String text, Color color) {
		StyleConstants.setForeground(style, color);
		String date = new SimpleDateFormat("[HH:mm:ss]: ").format(new Date());
		String finalInput;
		if (lastDate.equals(date))
			finalInput = date.replaceAll(".", " ") + text + '\n';
		else
			finalInput = date + text + '\n';
		try {
			doc.insertString(doc.getLength(), finalInput, style);
		} catch (BadLocationException e) {
		}
		console.setCaretPosition(console.getDocument().getLength());
		if (GlobalVariables.pushbullet != null)
			GlobalVariables.pushbullet.pushNote("", text);
		lastDate = date;
	}

	/**
	 * Create the panel.
	 */
	public MainTab() {
		setLayout(new MigLayout("", "[grow]", "[23px][23px][23px][grow][]"));

		JLabel lblAdbLoc = new JLabel("ADB Location:");
		add(lblAdbLoc, "flowx,cell 0 0");

		GlobalVariables.txtAdbLoc = new JTextField();
		GlobalVariables.txtAdbLoc.setText("C:\\Users\\Cohen\\AppData\\Local\\Android\\sdk\\platform-tools\\adb.exe");
		add(GlobalVariables.txtAdbLoc, "cell 0 0,growx");
		GlobalVariables.txtAdbLoc.setColumns(10);

		JLabel lblVBoxManageLoc = new JLabel("VBoxManage Location:");
		add(lblVBoxManageLoc, "flowx,cell 0 1");

		GlobalVariables.txtVBoxManageLoc = new JTextField();
		GlobalVariables.txtVBoxManageLoc.setText("C:\\Program Files\\Oracle\\VirtualBox\\VBoxManage.exe");
		add(GlobalVariables.txtVBoxManageLoc, "cell 0 1,growx");
		GlobalVariables.txtVBoxManageLoc.setColumns(10);

		JLabel lblVmName = new JLabel("VM Name:");
		add(lblVmName, "flowx,cell 0 2");

		GlobalVariables.txtVmName = new JTextField();
		GlobalVariables.txtVmName.setText("CM13");
		add(GlobalVariables.txtVmName, "cell 0 2,growx");
		GlobalVariables.txtVmName.setColumns(10);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, "cell 0 3,grow");

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		console = new JTextPane();
		scrollPane.setViewportView(console);
		console.setFont(new Font("Monospaced", Font.BOLD, 12));
		console.setEditable(false);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Monospaced", Font.BOLD, 12));
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);

		JLabel lblNewLabel = new JLabel("Date&Time  Win/Lose  Earned Influence  Bonus  New Influence  Map%");
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane_1.setColumnHeaderView(lblNewLabel);

		doc = console.getStyledDocument();
		style = console.addStyle(null, null);

		JButton btnBrowswADB = new JButton("...");
		btnBrowswADB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setCurrentDirectory(new File(GlobalVariables.txtAdbLoc.getText()));
				fileChooser.addChoosableFileFilter(new fileFilter("adb.exe"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					GlobalVariables.txtAdbLoc.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		add(btnBrowswADB, "cell 0 0");
		JButton btnBrowseVBox = new JButton("...");
		btnBrowseVBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setCurrentDirectory(new File(GlobalVariables.txtVBoxManageLoc.getText()));
				fileChooser.addChoosableFileFilter(new fileFilter("VBoxManage.exe"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					GlobalVariables.txtVBoxManageLoc.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		add(btnBrowseVBox, "cell 0 1");
		JButton btnEcportConsole = new JButton("Export Console");
		btnEcportConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.addChoosableFileFilter(new fileFilter("*.txt"));
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						PrintWriter file = new PrintWriter(new File(fileChooser.getSelectedFile().getParentFile(),
								fileChooser.getSelectedFile().getName()));
						appendConsole("Saveing console text...", Color.gray);
						file.write(console.getText());
						file.flush();
						file.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

			}
		});
		add(btnEcportConsole, "cell 0 2");
	}

	public class fileFilter extends FileFilter {
		String fileToFind;

		public fileFilter(String file) {
			fileToFind = file;
		}

		@Override
		public String getDescription() {
			return fileToFind;
		}

		@Override
		public boolean accept(File f) {
			if (fileToFind.contains("*"))
				return f.getName().endsWith(fileToFind.replace("*", ""));
			return (f.getName().equals(fileToFind));
		}
	}
}
