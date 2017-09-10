package mainPackage;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class DecoderTab extends JPanel {
	private static final long serialVersionUID = 8792336957899055872L;

	/**
	 * Create the panel.
	 */
	public DecoderTab() {
		setLayout(new MigLayout("",
				"[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]",
				"[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

		for (int x = 0; x < 20; x++) {
			add(new JLabel("" + x), "cell " + (x + 1) + " 0,grow");
			add(new JLabel("" + x), "cell " + (x + 1) + " 26,grow");
		}
		GlobalVariables.decoderArray = new JLabel[25][20];
		for (int y = 0; y < 25; y++) {
			add(new JLabel("" + y), "cell 0 " + (y + 1) + ",grow");
			add(new JLabel("" + y), "cell 21 " + (y + 1) + ",grow");
			for (int x = 0; x < 20; x++) {
				GlobalVariables.decoderArray[y][x] = new JLabel();
				GlobalVariables.decoderArray[y][x].setOpaque(true);
				;
				GlobalVariables.decoderArray[y][x].setForeground(Color.BLACK);
				add(GlobalVariables.decoderArray[y][x], "cell " + (x + 1) + " " + (y + 1) + ",grow");
			}
		}
	}
}