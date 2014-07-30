import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kashif Smith
 *
 */
public class Software {


	private JCheckBox algorithms = new JCheckBox("Algorithms");
	private JCheckBox audio = new JCheckBox("Audio");
	private JCheckBox graphics_technology = new JCheckBox("Graphics Technology");
	private JCheckBox machine_learning = new JCheckBox("Machine Learning");
	private JCheckBox peer_to_peer = new JCheckBox("Peer to peer");
	private JCheckBox photo_imaging = new JCheckBox("Photo Imaging");
	private JCheckBox user_interface = new JCheckBox("User Interface");
	private JCheckBox video_imaging = new JCheckBox("Video Imaging");
	private JCheckBox voip = new JCheckBox("VOIP");
	private JCheckBox opensource = new JCheckBox("Opensource");

	public Software(TreeSet<String> keywords) {
		JCheckBox[] categories = { algorithms, audio, graphics_technology,
				machine_learning, peer_to_peer, photo_imaging, user_interface,
				video_imaging, voip, opensource };

		int n = JOptionPane.showConfirmDialog(null, categories, "Sensors",
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
		
		new Software(keywords);
	}
}