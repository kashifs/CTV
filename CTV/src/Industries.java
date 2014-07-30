import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class Industries {

	// private JCheckBox agricultural_engineering = new JCheckBox(
	// "Agricultural Engineering");

	private JCheckBox aerospace = new JCheckBox("Aerospace");
	private JCheckBox automation = new JCheckBox("Automation");
	private JCheckBox automotive = new JCheckBox("Automotive");
	private JCheckBox bioinformatics = new JCheckBox("Bioinformatics");
	private JCheckBox building = new JCheckBox("Building");
	private JCheckBox chemical_processing = new JCheckBox("Chemical Processing");
	private JCheckBox chemical_synthesis = new JCheckBox("Chemical Synthesis");
	private JCheckBox communications = new JCheckBox("Communications");
	private JCheckBox computer_security = new JCheckBox("Computer Security");
	private JCheckBox computer_aided_design_engineering = new JCheckBox(
			"Computer-Aided Design & Engineering");
	private JCheckBox construction = new JCheckBox("Construction");
	private JCheckBox consumer_product = new JCheckBox("Consumer Product");
	private JCheckBox electronics = new JCheckBox("Electronics");
	private JCheckBox energy = new JCheckBox("Energy");
	private JCheckBox finance = new JCheckBox("Finance");
	private JCheckBox folio = new JCheckBox("Folio");
	private JCheckBox graphics = new JCheckBox("Graphics");
	private JCheckBox healthcare = new JCheckBox("Healthcare");
	private JCheckBox imaging = new JCheckBox("Imaging");
	private JCheckBox information_science = new JCheckBox("Information Science");
	private JCheckBox manufacturing = new JCheckBox("Manufacturing");
	private JCheckBox network_infrastructure = new JCheckBox(
			"Network Infrastructure");
	private JCheckBox pharmaceuticals = new JCheckBox("Pharmaceuticals");
	private JCheckBox printing = new JCheckBox("Printing");
	private JCheckBox robotic = new JCheckBox("Robotic");
	private JCheckBox security = new JCheckBox("Security");
	private JCheckBox simulation_modeling = new JCheckBox(
			"Simulation & Modeling");
	private JCheckBox software = new JCheckBox("Software");
	private JCheckBox specialty_materials = new JCheckBox("Specialty Materials");
	private JCheckBox training_education = new JCheckBox("Training & Education");
	private JCheckBox web_internet = new JCheckBox("Web & Internet");

	public Industries(TreeSet<String> keywords) {
		JCheckBox[] categories = { aerospace, automation, automotive,
				bioinformatics, building, chemical_processing,
				chemical_synthesis, communications, computer_security,
				computer_aided_design_engineering, construction,
				consumer_product, electronics, energy, finance, folio,
				graphics, healthcare, imaging, information_science,
				manufacturing, network_infrastructure, pharmaceuticals,
				printing, robotic, security, simulation_modeling, software,
				specialty_materials, training_education, web_internet };

		int n = JOptionPane.showConfirmDialog(null, categories,
				"Industries", JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()){
				keywords.add(temp.getActionCommand());
			}
		}

	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();
		
		new Industries(keywords);
	}
}