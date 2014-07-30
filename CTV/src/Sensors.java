import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Sensors {

	private JCheckBox biologic = new JCheckBox("Biologic");
	private JCheckBox chemical_gas = new JCheckBox("Chemical & Gas");
	private JCheckBox motion = new JCheckBox("Motion");
	private JCheckBox particle_radiation = new JCheckBox("Particle & Radiation");

	public Sensors(TreeSet<String> keywords) {
		JCheckBox[] categories = { biologic, chemical_gas, motion,
				particle_radiation };

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
		
		new Sensors(keywords);
	}
}