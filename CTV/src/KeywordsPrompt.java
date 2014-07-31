import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class KeywordsPrompt {

	private static TreeSet<String> keywords = new TreeSet<String>();
	private static int skip = 0;

	private static final String[] LIFE_SCIENCE_DISEASES = { "AIDS", "Allergy",
			"Alzheimer", "Asthma", "Autoimmune", "CHF", "COPD", "Dementia",
			"Diabetes", "Dystrophy", "Epilepsy", "HIV", "Hypertension",
			"Infectious disease", "Inflammatory Disease", "Ischemia",
			"Multiple Sclerosis", "Obesity", "Metabolic Disorders",
			"Parkinson", "Respiratory", "Schizophrenia", "Sleep",
			"Spinal Injury", "Stroke" };

	private static final String[] AGRICULTURE = { "Agricultural Engineering",
			"Agribusiness", "Agronomics", "Aguaculture", "Crop Improvement",
			"Entomology", "Food Science & Nutrition", "Forestry", "GMO",
			"Non-GMO", "Paper & Pulp", "Plant Hormones", "Plant Pathogens",
			"Plant Storage", "Plant Varieties" };

	private static final String[] ENGINEERING_PHYSICAL_SCI = { "Lithography",
			"Mechanical", "MEMS", "Micromachining", "Nano-", "Propulsion",
			"RF-MEMS", "SLS" };

	private static final String[] INDUSTRIES = { "Aerospace", "Automation",
			"Automotive", "Bioinformatics", "Building", "Chemical Processing",
			"Chemical Synthesis", "Communications", "Computer Security",
			"Computer-Aided Design & Engineering", "Construction",
			"Consumer Product", "Electronics", "Energy", "Finance", "Folio",
			"Graphics", "Healthcare", "Imaging", "Information Science",
			"Manufacturing", "Network Infrastructure", "Pharmaceuticals",
			"Printing", "Robotic", "Security", "Simulation & Modeling",
			"Software", "Specialty Materials", "Training & Education",
			"Web & Internet" };

	private static final String[] SENSORS = { "Biologic", "Chemical & Gas",
			"Motion", "Particle & Radiation" };

	private static final String[] CHEMICAL = { "Additives", "Biochemical",
			"Catalyst", "Chemical Processing", "Coatings", "Electrochemistry",
			"Polymers", "Processing", "Specialty Chemicals" };

	private static final String[] SOFTWARE = { "Algorithms", "Audio",
			"Graphics Technology", "Machine Learning", "P2P", "Peer to peer",
			"Photo Imaging", "User Interface", "Video Imaging", "VOIP",
			"Opensource" };

	private static final String[] INSTRUMENTATION = { "Chromatography",
			"Laser", "Microscopy", "Optics", "PCR", "Separation",
			"Spectroscopy", "Thermal" };

	private static final String[] ELECTRONICS = { "Analog [Signal, Circuits]",
			"Digital [Signal, Circuits]", "Antenna", "Assembly & Packaging",
			"Asynchronous", "Circuit design", "Computer Hardware",
			"Data Storage", "EDA", "Fiberoptic", "GPS", "Low Power",
			"Low voltage", "Mixed Signal Processing", "Quantum Computer",
			"Robotics", "Wireless" };

	private static final String[] MATERIALS = { "Biomaterials", "Cementitious",
			"Ceramics", "Composites", "Imaging Materials", "Lubricant",
			"Polymers", "Superconductors", "Thin Films" };

	private static final String[] CLEAN_TECHNOLOGY = { "Green [Technology]",
			"Clean [Technology]", "Battery", "Coal", "Energy Storage",
			"Fuel Cells", "Hydrogen", "Natural Gas", "Nuclear",
			"Petrochemical", "Photonics", "Remediation", "Renewable Energy",
			"Solar", "Sulfur", "Sustainable", "Urban", "Voltaic", "Weather" };

	public KeywordsPrompt(TreeSet<String> keywords) {

		runPrompt(LIFE_SCIENCE_DISEASES, "Life Science Diseases");
		runPrompt(AGRICULTURE, "Agriculture");
		runPrompt(ENGINEERING_PHYSICAL_SCI, "Engineering Physical Sciences");
		runPrompt(INDUSTRIES, "Industries");
		runPrompt(SENSORS, "Sensors");
		runPrompt(CHEMICAL, "Chemical");
		runPrompt(SOFTWARE, "Software");
		runPrompt(INSTRUMENTATION, "Instrumentation");
		runPrompt(ELECTRONICS, "Electronics");
		runPrompt(MATERIALS, "Materials");
		runPrompt(CLEAN_TECHNOLOGY, "Clean Technology", true);
	}

	private static void runPrompt(String[] promptKeywords, String title) {
		runPrompt(promptKeywords, title, false);
	}

	private static void runPrompt(String[] promptKeywords, String title,
			boolean last) {
		
		if (skip != 0)
			return;
		
		int numCategs = promptKeywords.length;
		JCheckBox[] categories = new JCheckBox[numCategs];

		for (int i = 0; i < numCategs; i++) {
			categories[i] = new JCheckBox(promptKeywords[i]);
		}

		String[] options = null;

		if (!last) {
			options = new String[] { "Continue", "Done" };
		} else {
			options = new String[] { "Done" };
		}

		skip = JOptionPane.showOptionDialog(null, categories, title,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		
		System.out.println("skip: " + skip);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()) {
				keywords.add(temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		TreeSet<String> keywords = new TreeSet<String>();
		new KeywordsPrompt(keywords);

	}
}