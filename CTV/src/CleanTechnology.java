import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class CleanTechnology {

	private JCheckBox green_technology = new JCheckBox("Green [Technology]");
	private JCheckBox clean_technology = new JCheckBox("Clean [Technology]");
	private JCheckBox battery = new JCheckBox("Battery");
	private JCheckBox coal = new JCheckBox("Coal");
	private JCheckBox energy_storage = new JCheckBox("Energy Storage");
	private JCheckBox fuel_cells = new JCheckBox("Fuel Cells");
	private JCheckBox hydrogen = new JCheckBox("Hydrogen");
	private JCheckBox natural_gas = new JCheckBox("Natural Gas");
	private JCheckBox nuclear = new JCheckBox("Nuclear");
	private JCheckBox petrochemical = new JCheckBox("Petrochemical");
	private JCheckBox photonics = new JCheckBox("Photonics");
	private JCheckBox remediation = new JCheckBox("Remediation");
	private JCheckBox renewable_energy = new JCheckBox("Renewable Energy");
	private JCheckBox solar = new JCheckBox("Solar");
	private JCheckBox sulfur = new JCheckBox("Sulfur");
	private JCheckBox sustainable = new JCheckBox("Sustainable");
	private JCheckBox urban = new JCheckBox("Urban");
	private JCheckBox voltaic = new JCheckBox("Voltaic");
	private JCheckBox weather = new JCheckBox("Weather");

	public CleanTechnology(TreeSet<String> keywords) {
		JCheckBox[] categories = { green_technology, clean_technology, battery,
				coal, energy_storage, fuel_cells, hydrogen, natural_gas,
				nuclear, petrochemical, photonics, remediation,
				renewable_energy, solar, sulfur, sustainable, urban, voltaic,
				weather };

		int n = JOptionPane.showConfirmDialog(null, categories,
				"Clean Technology", JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()){
				keywords.add(temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();
		
		new CleanTechnology(keywords);
	}
}