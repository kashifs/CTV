import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Instrumentation {



	private JCheckBox chromatography = new JCheckBox("Chromatography");
	private JCheckBox laser = new JCheckBox("Laser");
	private JCheckBox microscopy = new JCheckBox("Microscopy");
	private JCheckBox optics = new JCheckBox("Optics");
	private JCheckBox pcr = new JCheckBox("PCR");
	private JCheckBox separation = new JCheckBox("Separation");
	private JCheckBox spectroscopy = new JCheckBox("Spectroscopy");
	private JCheckBox thermal = new JCheckBox("Thermal");

	public Instrumentation(TreeSet<String> keywords) {
		JCheckBox[] categories = {chromatography, laser, microscopy, optics, pcr, separation, spectroscopy, thermal};

		int n = JOptionPane.showConfirmDialog(null, categories, "Sensors",
				JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()) {
				keywords.add(temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();
		
		new Instrumentation(keywords);
	}
}