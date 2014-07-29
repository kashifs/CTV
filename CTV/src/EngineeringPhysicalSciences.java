import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class EngineeringPhysicalSciences {

	// private JCheckBox agricultural_engineering = new JCheckBox(
	// "Agricultural Engineering");

	private JCheckBox lithography = new JCheckBox("Lithography");
	private JCheckBox mechanical = new JCheckBox("Mechanical");
	private JCheckBox mems = new JCheckBox("MEMS");
	private JCheckBox micromachining = new JCheckBox("Micromachining");
	private JCheckBox nano = new JCheckBox("Nano-");
	private JCheckBox propulsion = new JCheckBox("Propulsion");
	private JCheckBox rf_mems = new JCheckBox("RF-MEMS");
	private JCheckBox sls = new JCheckBox("SLS");

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public EngineeringPhysicalSciences() {

		
		JCheckBox[] categories = { lithography, mechanical, mems,
				micromachining, nano, propulsion, rf_mems, sls };

		int n = JOptionPane.showConfirmDialog(null, categories,
				"Engineering/Physical Sciences", JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected())
				System.out.println(temp.getActionCommand());
		}

	}

	public static void main(String[] args) {
		new EngineeringPhysicalSciences();
	}
}