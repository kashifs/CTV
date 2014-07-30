import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Materials {

	private JCheckBox biomaterials = new JCheckBox("Biomaterials");
	private JCheckBox cementitious = new JCheckBox("Cementitous");
	private JCheckBox ceramics = new JCheckBox("Ceramics");
	private JCheckBox composites = new JCheckBox("Composites");
	private JCheckBox imaging_materials = new JCheckBox("Imaging Materials");
	private JCheckBox lubricant = new JCheckBox("Lubricant");
	private JCheckBox polymers = new JCheckBox("Polymers");
	private JCheckBox superconductors = new JCheckBox("Superconductors");
	private JCheckBox thin_films = new JCheckBox("Thin Films");

	public Materials(TreeSet<String> keywords) {
		JCheckBox[] categories = { biomaterials, cementitious, ceramics,
				composites, imaging_materials, lubricant, polymers,
				superconductors, thin_films };

		int n = JOptionPane.showConfirmDialog(null, categories, "Materials",
				JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()){
				keywords.add(temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();
		
		new Materials(keywords);
	}
}