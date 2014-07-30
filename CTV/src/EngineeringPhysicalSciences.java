import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class EngineeringPhysicalSciences {

	private JCheckBox lithography = new JCheckBox("Lithography");
	private JCheckBox mechanical = new JCheckBox("Mechanical");
	private JCheckBox mems = new JCheckBox("MEMS");
	private JCheckBox micromachining = new JCheckBox("Micromachining");
	private JCheckBox nano = new JCheckBox("Nano-");
	private JCheckBox propulsion = new JCheckBox("Propulsion");
	private JCheckBox rf_mems = new JCheckBox("RF-MEMS");
	private JCheckBox sls = new JCheckBox("SLS");

	public EngineeringPhysicalSciences(TreeSet<String> keywords) {

		JCheckBox[] categories = { lithography, mechanical, mems,
				micromachining, nano, propulsion, rf_mems, sls };

		int n = JOptionPane.showConfirmDialog(null, categories,
				"Engineering/Physical Sciences", JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()) {
				keywords.add(temp.getActionCommand());
			}
		}

	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();

		new EngineeringPhysicalSciences(keywords);
	}
}