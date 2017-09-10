package mainPackage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.github.sheigutn.pushbullet.Pushbullet;

import net.miginfocom.swing.MigLayout;

public class PushbulletTab extends JPanel {
	private static final long serialVersionUID = -2026303882184754750L;
	private JTextField passwordField;
	private JTextField tokenField;

	/**
	 * Create the panel.
	 */
	public PushbulletTab() {
		setLayout(new MigLayout("", "[grow]", "[][][]"));

		JLabel lblToken = new JLabel("Token:");
		add(lblToken, "flowx,cell 0 0");

		tokenField = new JTextField();
		tokenField.setText("o.9GmQZnSNW7xYlEWWehdXfCfOxQlHfsdR");
		add(tokenField, "cell 0 0,growx");

		JToggleButton tglbtnPushbulletEnabled = new JToggleButton("Pushbullet Enabled");
		tglbtnPushbulletEnabled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnPushbulletEnabled.isSelected()) {
					tglbtnPushbulletEnabled.setText("Disable Pushbullet");
					MainTab.appendConsole("Pushbullet Enabled", Color.GREEN);
					tokenField.setEnabled(false);
					passwordField.setEnabled(false);
					initializePushbullet(tokenField.getText(), passwordField.getText());
				} else {
					tglbtnPushbulletEnabled.setText("Enable Pushbullet");
					MainTab.appendConsole("Pushbullet Disabled", Color.RED);
					tokenField.setEnabled(true);
					passwordField.setEnabled(true);
					GlobalVariables.pushbullet = null;
				}
			}
		});

		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "flowx,cell 0 1");
		add(tglbtnPushbulletEnabled, "cell 0 2");

		passwordField = new JTextField();
		add(passwordField, "cell 0 1,growx");
	}

	public void initializePushbullet(String token, String pswrd) {
		if (pswrd.length() == 0)
			GlobalVariables.pushbullet = new Pushbullet(token);
		else
			GlobalVariables.pushbullet = new Pushbullet(token, pswrd);
		GlobalVariables.pushbullet.pushNote("", "InfluenceBot is Connected");
	}
}
