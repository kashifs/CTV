import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Chemicals {

	private JCheckBox additives = new JCheckBox("Additives");
	private JCheckBox biochemical = new JCheckBox("Biochemical");
	private JCheckBox catalyst = new JCheckBox("Catalyst");
	private JCheckBox chemical_processing = new JCheckBox("Chemical Processing");
	private JCheckBox coatings = new JCheckBox("Coatings");
	private JCheckBox electrochemistry = new JCheckBox("Electrochemistry");
	private JCheckBox polymers = new JCheckBox("Polymers");
	private JCheckBox processing = new JCheckBox("Processing");
	private JCheckBox specialty_chemicals = new JCheckBox("Specialty");

	public Chemicals(HashMap<String, String> keywords) {
		JCheckBox[] categories = { additives, biochemical, catalyst,
				chemical_processing, coatings, electrochemistry, polymers,
				processing, specialty_chemicals };

		int n = JOptionPane.showConfirmDialog(null, categories, "Chemicals",
				JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()) {
				keywords.put(temp.getActionCommand(), temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		HashMap<String, String> keywords = new HashMap<String, String>();

		new Chemicals(keywords);
	}
}