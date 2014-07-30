import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.apache.fop.util.bitmap.DitherUtil;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Electronics {

	private JCheckBox analog = new JCheckBox("Analog [Signal, Circuits]");
	private JCheckBox digital = new JCheckBox("Digital [Signal, Circuits]");
	private JCheckBox antenna = new JCheckBox("Antenna");
	private JCheckBox assembly_packaging = new JCheckBox("Assembly & Packaging");
	private JCheckBox asynchronous = new JCheckBox("Asynchronous");
	private JCheckBox circuit_design = new JCheckBox("Circuit Design");
	private JCheckBox computer_hardware = new JCheckBox("Computer Hardware");
	private JCheckBox data_storage = new JCheckBox("Data Storage");
	private JCheckBox eda = new JCheckBox("EDA");
	private JCheckBox fiberoptic = new JCheckBox("Fiberoptic");
	private JCheckBox gps = new JCheckBox("GPS");
	private JCheckBox low_power = new JCheckBox("Low Power");
	private JCheckBox low_voltage = new JCheckBox("Low Voltage");
	private JCheckBox mixed_signal_processing = new JCheckBox(
			"Mixed Signal Processing");
	private JCheckBox quantum_computer = new JCheckBox("Quantum Computer");
	private JCheckBox robotics = new JCheckBox("Robotics");
	private JCheckBox wireless = new JCheckBox("Wireless");

	public Electronics(HashMap<String, String> keywords) {
		JCheckBox[] categories = { analog, digital, antenna,
				assembly_packaging, asynchronous, circuit_design,
				computer_hardware, data_storage, eda, fiberoptic, gps,
				low_power, low_voltage, mixed_signal_processing,
				quantum_computer, robotics, wireless };

		int n = JOptionPane.showConfirmDialog(null, categories, "Instrumentation",
				JOptionPane.OK_CANCEL_OPTION);

		for (int i = 0; i < categories.length; i++) {
			JCheckBox temp = (JCheckBox) categories[i];
			if (temp.isSelected()){
				keywords.put(temp.getActionCommand(), temp.getActionCommand());
			}
		}
	}

	public static void main(String[] args) {
		HashMap<String, String> keywords = new HashMap<String, String>();
		
		new Electronics(keywords);
	}
}