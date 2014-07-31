import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;


/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class Agriculture {

	
	private JCheckBox agricultural_engineering = new JCheckBox(
			"Agricultural Engineering");
	private JCheckBox agribusiness = new JCheckBox("Agribusiness");
	private JCheckBox agronomics = new JCheckBox("Agronomics");
	private JCheckBox aquaculture = new JCheckBox("Aquaculture");
	private JCheckBox crop_improvement = new JCheckBox("Crop Improvement");
	private JCheckBox entomology = new JCheckBox("Entomology");
	private JCheckBox food_science_nutrition = new JCheckBox(
			"Food Science & Nutrition");
	private JCheckBox forestry = new JCheckBox("Forestry");
	private JCheckBox gmo = new JCheckBox("GMO");
	private JCheckBox non_gmo = new JCheckBox("Non-GMO");
	private JCheckBox paper_pulp = new JCheckBox("Paper & Pulp");
	private JCheckBox plant_hormones = new JCheckBox("Plant Hormones");
	private JCheckBox plant_pathogens = new JCheckBox("Plant Pathogens");
	private JCheckBox plant_storage = new JCheckBox("Plant Storage");
	private JCheckBox plant_varieties = new JCheckBox("Plant Varieties");

	public Agriculture(TreeSet<String> keywords) {
		JCheckBox[] categories = { agricultural_engineering, agribusiness,
				agronomics, aquaculture, crop_improvement, entomology,
				food_science_nutrition, forestry, gmo, non_gmo, paper_pulp,
				plant_hormones, plant_pathogens, plant_storage, plant_varieties };

		int n = JOptionPane.showConfirmDialog(null, categories, "Agriculture",
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

		new Agriculture(keywords);
	}
}