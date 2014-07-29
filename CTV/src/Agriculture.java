import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class Agriculture extends JFrame {


	private static HashMap<String, String>  keywords;

	
	private JCheckBox agricultural_engineering = new JCheckBox("Agricultural Engineering"); 
	private JCheckBox agribusiness = new JCheckBox("Agribusiness");
	private JCheckBox agronomics = new JCheckBox("Agronomics");
	private JCheckBox aquaculture  = new JCheckBox("Aquaculture");
	private JCheckBox crop_improvement  = new JCheckBox("Crop Improvement");
	private JCheckBox entomology = new JCheckBox("Entomology");
	private JCheckBox food_science_nutrition  = new JCheckBox("Food Science & Nutrition");
	private JCheckBox forestry = new JCheckBox("Forestry");
	private JCheckBox gmo = new JCheckBox("GMO");
	private JCheckBox non_gmo = new JCheckBox("Non-GMO");
	private JCheckBox paper_pulp = new JCheckBox("Paper & Pulp");
	private JCheckBox plant_hormones = new JCheckBox("Plant Hormones");
	private JCheckBox plant_pathogens  = new JCheckBox("Plant Pathogens");
	private JCheckBox plant_storage = new JCheckBox("Plant Storage");
	private JCheckBox plant_varieties = new JCheckBox("Plant Varieties");
	
	

	private int sum = 0; // sum of 3 numbers

	public Agriculture() {
		super("Agriculture");
		// setLayout(new FlowLayout());
		setLayout(new GridLayout(5, 5));
		keywords = new HashMap<String, String>();
		
		add(agricultural_engineering);
		add(agribusiness);
		add(agronomics);
		add(aquaculture);
		add(crop_improvement);
		add(entomology);
		add(food_science_nutrition);
		add(forestry);
		add(gmo);
		add(non_gmo);
		add(paper_pulp);
		add(plant_hormones);
		add(plant_pathogens);
		add(plant_storage);
		add(plant_varieties);

		
		JButton button = new JButton("Continue");
	    add(button, BorderLayout.SOUTH);
	

	    ActionListener buttonListener = new ContinueHandler();
	    button.addActionListener(buttonListener);
	    

		// add action listener for the check boxes
		ActionListener actionListener = new ActionHandler();
		
		 agricultural_engineering.addActionListener(actionListener);
		 agribusiness.addActionListener(actionListener);
		 agronomics.addActionListener(actionListener);
		 aquaculture.addActionListener(actionListener);
		 crop_improvement.addActionListener(actionListener);
		 entomology.addActionListener(actionListener);
		 food_science_nutrition.addActionListener(actionListener);
		 forestry.addActionListener(actionListener);
		 gmo.addActionListener(actionListener);
		 non_gmo.addActionListener(actionListener);
		 paper_pulp.addActionListener(actionListener);
		 plant_hormones.addActionListener(actionListener);
		 plant_pathogens.addActionListener(actionListener);
		 plant_storage.addActionListener(actionListener);
		 plant_varieties.addActionListener(actionListener);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.dispose();
	}
	
	public void CloseFrame(){
	    super.dispose();
	}
	
	public static HashMap<String, String> getKeywords(){
		return keywords;
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JCheckBox checkbox = (JCheckBox) event.getSource();
			if (checkbox.isSelected()) {
				System.out.println(checkbox.getActionCommand());
				keywords.put(checkbox.getActionCommand(), checkbox.getActionCommand());
			} else {
				System.out.println("unchecked: " + checkbox.getActionCommand());
				keywords.remove(checkbox.getActionCommand());
				
			}
		}
	}
	
	class ContinueHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("button pressed!");
			CloseFrame();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Agriculture().setVisible(true);
			}
		});
	}
}