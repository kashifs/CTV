import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class CategoriesPrompt {

	private static TreeSet<String> categories;
	private static int skip = 0;

	private static final String[] CONTENT_WORKS_OF_DESIGN = {
			"Content, Works of Design –	Educational Tools",
			"Content, Works of Design –	Design",
			"Content, Works of Design – Multimedia" };

	private static final String[] ENERGY_INFRASTRUCTURE_ENVIRONMENT = {
			"Energy, Infrastructure, Environment – Carbon Capture",
			"Energy, Infrastructure, Environment – Biofuels",
			"Energy, Infrastructure, Environment – Waste Management",
			"Energy, Infrastructure, Environment – Infrastructure" };

	private static final String[] IT_COMMUNICATIONS_NETWORKING = {
			"IT, Communications & Networking – Communications",
			"IT, Communications & Networking – Security",
			"IT, Communications & Networking – Network",
			"IT, Communications & Networking – Storage" };

	private static final String[] ELECTRONICS = { "Electronics – Circuits",
			"Electronics – Sensors", "Electronics – Signal Processing" };

	private static final String[] IMAGING = { "Imaging – Systems",
			"Imaging – Software", "Imaging – Tracers, Probes, Reporters" };

	private static final String[] DATA_ALGS_SOFTWARE_APPS = {
			"Data, Algorithms, Sofware, Apps – Medical, Clinical Applications",
			"Data, Algorithms, Sofware, Apps – Life Sciences Research",
			"Data, Algorithms, Sofware, Apps – Physical, Engineering Research",
			"Data, Algorithms, Sofware, Apps – Consumer Applications" };

	private static final String[] DEVICES_INSTRUMENTATION_HARDWARE = {
			"Devices & Instrumentation (Hardware) – Medical, Clinical Applications",
			"Devices & Instrumentation (Hardware) – Life Sciences Research Platforms",
			"Devices & Instrumentation (Hardware) – Industrial Applications",
			"Devices & Instrumentation (Hardware) – Physical, Engineering Research",
			"Devices & Instrumentation (Hardware) – Consumer Applications" };

	private static final String[] CHEMICALS_ADVANCED_MATERIALS = {
			"Chemicals & Advanced Materials – Chemicals",
			"Chemicals & Advanced Materials – Nanomaterials",
			"Chemicals & Advanced Materials – Polymers, Ceramics, Metals",
			"Chemicals & Advanced Materials – Industrial Applications",
			"Chemicals & Advanced Materials – Medical, Clinical Applications" };

	private static final String[] BIO_LIFE_SCIENCE_RESEARCH_TOOLS = {
			"Biological, Life Sciences Research Tools – Animal Models",
			"Biological, Life Sciences Research Tools – Cell Lines",
			"Biological, Life Sciences Research Tools – Assays",
			"Biological, Life Sciences Research Tools – Modeling, Simulation Tools",
			"Biological, Life Sciences Research Tools – Probes",
			"Biological, Life Sciences Research Tools – Vectors & Plasmids" };

	private static final String[] BIOPHARMA = { "Biopharma – Drug delivery",
			"Biopharma – Diagnostics", "Biopharma – Therapeutics",
			"Biopharma – Vaccines" };

	private static final String[] VETERINARY_ANIMAL = {
			"Veterinary, Animal – Veterinary Applications",
			"Veterinary, Animal – Animal models" };

	private static final String[] CLINICAL_SPECIALTIES = {
			"Clinical Specialties – Anesthesiology",
			"Clinical Specialties – Blood & Lymphatic Disease",
			"Clinical Specialties – Cardiovascular",
			"Clinical Specialties – Dental, Oral Health",
			"Clinical Specialties – Dermatology, Hair",
			"Clinical Specialties – Diabetes, Metabolism, Endocrinology & Obesity",
			"Clinical Specialties – Ear, Nose, & Throat",
			"Clinical Specialties – Emergency Medicine",
			"Clinical Specialties – Gastroenterology & Digestive Disease",
			"Clinical Specialties – General & Plastic Surgery",
			"Clinical Specialties – Immunology, Autoimmune & Inflammation",
			"Clinical Specialties – Infectious Diseases",
			"Clinical Specialties – Musculoskeletal Disorders, Orthopedics, Bone",
			"Clinical Specialties – Nephrology, Renal",
			"Clinical Specialties – Neurodegenerative",
			"Clinical Specialties – Oncology",
			"Clinical Specialties – Ophthalmology",
			"Clinical Specialties – Pediatrics",
			"Clinical Specialties – Physical Medicine & Rehabilitation",
			"Clinical Specialties – Psychiatric",
			"Clinical Specialties – Radiology",
			"Clinical Specialties – Regenerative Medicine ,  Tissue Engineering",
			"Clinical Specialties – Reproductive Health",
			"Clinical Specialties – Respiratory & Pulmonary",
			"Clinical Specialties – Sleep Disorders",
			"Clinical Specialties – Transplantation",
			"Clinical Specialties – Urology",
			"Clinical Specialties – Orphan and Rare Diseases", };

	public CategoriesPrompt(TreeSet<String> userCategories) {
		categories = userCategories;
		runPrompt(CONTENT_WORKS_OF_DESIGN, "Content, Works of Design");
		runPrompt(ENERGY_INFRASTRUCTURE_ENVIRONMENT,
				"Energy, Infrastructure, Environment");
		runPrompt(IT_COMMUNICATIONS_NETWORKING,
				"IT, Communications & Networking");
		runPrompt(ELECTRONICS, "Electronics");
		runPrompt(IMAGING, "Imaging");
		runPrompt(DATA_ALGS_SOFTWARE_APPS, "Data, Algorithms, Sofware, Apps");
		runPrompt(DEVICES_INSTRUMENTATION_HARDWARE, "Devices & Instrumentation (Hardware)");
		runPrompt(CHEMICALS_ADVANCED_MATERIALS, "Chemicals & Advanced Materials");
		runPrompt(BIO_LIFE_SCIENCE_RESEARCH_TOOLS, "Biological, Life Sciences Research Tools");
		runPrompt(BIOPHARMA, "Biopharma");
		runPrompt(VETERINARY_ANIMAL, "Veterinary, Animal");
		runPrompt(CLINICAL_SPECIALTIES, "Clinical Specialties", true);

	}

	private static void runPrompt(String[] promptCategories, String title) {
		runPrompt(promptCategories, title, false);
	}

	private static void runPrompt(String[] promptCategories, String title,
			boolean last) {

		if (skip != 0)
			return;

		int numCategs = promptCategories.length;
		JCheckBox[] categs = new JCheckBox[numCategs];

		for (int i = 0; i < numCategs; i++) {
			categs[i] = new JCheckBox(promptCategories[i]);
		}

		String[] options = null;

		if (!last) {
			options = new String[] { "Continue", "Done" };
		} else {
			options = new String[] { "Done" };
		}

		skip = JOptionPane.showOptionDialog(null, categs, title,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

//		System.out.println("skip: " + skip);

		for (int i = 0; i < categs.length; i++) {
			JCheckBox temp = (JCheckBox) categs[i];
			if (temp.isSelected()) {
				categories.add(temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		TreeSet<String> categories = new TreeSet<String>();
		new CategoriesPrompt(categories);

	}
}