import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class LifeScienceDiseases {

	private JCheckBox aids = new JCheckBox("AIDS");
	private JCheckBox allergy = new JCheckBox("Allergy");
	private JCheckBox alzheimer = new JCheckBox("Alzheimer");
	private JCheckBox asthma = new JCheckBox("Asthma");
	private JCheckBox autoimmune = new JCheckBox("Autoimmune");
	private JCheckBox chf = new JCheckBox("CHF");
	private JCheckBox copd = new JCheckBox("COPD");
	private JCheckBox dementia = new JCheckBox("Dementia");
	private JCheckBox diabetes = new JCheckBox("Diabetes");
	private JCheckBox dystrophy = new JCheckBox("Dystrophy");
	private JCheckBox epilepsy = new JCheckBox("Epilepsy");
	private JCheckBox hiv = new JCheckBox("HIV");
	private JCheckBox hypertension = new JCheckBox("Hypertension");
	private JCheckBox infectious_disease = new JCheckBox("Infectious disease");
	private JCheckBox inflammatory_disease = new JCheckBox(
			"Inflammatory Disease");
	private JCheckBox ischemia = new JCheckBox("Ischemia");
	private JCheckBox multiple_sclerosis = new JCheckBox("Multiple Sclerosis");
	private JCheckBox obesity = new JCheckBox("Obesity");
	private JCheckBox metabolic_disorders = new JCheckBox("Metabolic Disorders");
	private JCheckBox parkinson = new JCheckBox("Parkinson");
	private JCheckBox respiratory = new JCheckBox("Respiratory");
	private JCheckBox schizophrenia = new JCheckBox("Schizophrenia");
	private JCheckBox sleep = new JCheckBox("Sleep");
	private JCheckBox spinal_injury = new JCheckBox("Spinal Injury");
	private JCheckBox stroke = new JCheckBox("Stroke");

	public LifeScienceDiseases() {

		JCheckBox[] categories = { aids, allergy, alzheimer, asthma,
				autoimmune, chf, copd, dementia, diabetes, dystrophy, epilepsy,
				hiv, hypertension, infectious_disease, inflammatory_disease,
				ischemia, multiple_sclerosis, obesity, metabolic_disorders,
				parkinson, respiratory, schizophrenia, sleep, spinal_injury,
				stroke };

		int n = JOptionPane.showConfirmDialog(null, categories,
				"Life Science: Diseases", JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected())
				System.out.println(temp.getActionCommand());
		}

	}

	public static void main(String[] args) {
		new LifeScienceDiseases();
	}
}