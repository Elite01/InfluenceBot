package mainPackage;

import javax.swing.JPanel; 
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class GameSettingsTab extends JPanel {
	private static final long serialVersionUID = 301796410060131400L;

	public GameSettingsTab() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));

		JLabel lblMapSize = new JLabel("Map Size:");
		add(lblMapSize, "cell 0 0 6 1");

		JPanel panel = new JPanel();
		add(panel, "cell 0 1 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[]"));

		ButtonGroup mapSizeGroup = new ButtonGroup();

		JRadioButton rdbtnXXL = new JRadioButton("XXL");
		panel.add(rdbtnXXL, "cell 0 0");
		rdbtnXXL.setSelected(true);
		mapSizeGroup.add(rdbtnXXL);

		JRadioButton rdbtnXL = new JRadioButton("XL");
		panel.add(rdbtnXL, "cell 0 0");
		mapSizeGroup.add(rdbtnXL);

		JRadioButton rdbtnL = new JRadioButton("L");
		panel.add(rdbtnL, "cell 0 0");
		mapSizeGroup.add(rdbtnL);

		JRadioButton rdbtnM = new JRadioButton("M");
		panel.add(rdbtnM, "cell 0 0");
		mapSizeGroup.add(rdbtnM);

		JRadioButton rdbtnS = new JRadioButton("S");
		panel.add(rdbtnS, "cell 0 0");
		mapSizeGroup.add(rdbtnS);

		JLabel lblEnemies = new JLabel("Enemies:");
		add(lblEnemies, "cell 0 2");

		JLabel lblGreen = new JLabel("Green: ");
		add(lblGreen, "flowx,cell 0 3");

		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(2);
		slider.setSnapToTicks(true);
		slider.setMaximum(5);
		slider.setMinimum(1);
		add(slider, "cell 1 3,growx");

		JLabel lblYellow = new JLabel("Yellow: ");
		add(lblYellow, "flowx,cell 0 4");

		JSlider slider_1 = new JSlider();
		slider_1.setValue(2);
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setMinimum(1);
		slider_1.setMaximum(5);
		add(slider_1, "cell 1 4,growx");

		JLabel lblRed = new JLabel("Red: ");
		add(lblRed, "flowx,cell 0 5");

		JSlider slider_2 = new JSlider();
		slider_2.setValue(2);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setMinimum(1);
		slider_2.setMaximum(5);
		add(slider_2, "cell 1 5,growx");

		JLabel lblPurple = new JLabel("Purple: ");
		add(lblPurple, "flowx,cell 0 6");

		JSlider slider_3 = new JSlider();
		slider_3.setValue(2);
		slider_3.setSnapToTicks(true);
		slider_3.setPaintTicks(true);
		slider_3.setPaintLabels(true);
		slider_3.setMinimum(1);
		slider_3.setMaximum(5);
		add(slider_3, "cell 1 6,growx");

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		labels.put(1, new JLabel("OFF"));
		labels.put(2, new JLabel("FREAK"));
		labels.put(3, new JLabel("DUMMY"));
		labels.put(4, new JLabel("HUNTER"));
		labels.put(5, new JLabel("MASTER"));
		slider_3.setLabelTable(labels);
		slider_2.setLabelTable(labels);
		slider_1.setLabelTable(labels);
		slider.setLabelTable(labels);

	}

}
