import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 
 * @author modified from code found at: www.codejava.net
 *
 */
public class LifeScienceDiseases extends JFrame {


	private static HashMap<String, String>  keywords;

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
	private JCheckBox respiratory = new JCheckBox("Allergy");
	private JCheckBox schizophrenia = new JCheckBox("Respiratory");
	private JCheckBox sleep = new JCheckBox("Allergy");
	private JCheckBox spinal_injury = new JCheckBox("Sleep");
	private JCheckBox stroke = new JCheckBox("Stroke");


	private int sum = 0; // sum of 3 numbers

	public LifeScienceDiseases() {
		super("Life Science: Diseases");
		// setLayout(new FlowLayout());
		setLayout(new GridLayout(5, 5));
		keywords = new HashMap<String, String>();
		
		add(aids);
		add(allergy);
		add(alzheimer);
		add(asthma);
		add(autoimmune);
		add(chf);
		add(copd);
		add(dementia);
		add(diabetes);
		add(dystrophy);
		add(epilepsy);
		add(hiv);
		add(hypertension);
		add(infectious_disease);
		add(inflammatory_disease);
		add(ischemia);
		add(multiple_sclerosis);
		add(obesity);
		add(metabolic_disorders);
		add(parkinson);
		add(respiratory);
		add(schizophrenia);
		add(sleep);
		add(spinal_injury);
		add(stroke);
		
		JButton button = new JButton("Continue");
	    add(button, BorderLayout.AFTER_LAST_LINE);
	

	    ActionListener buttonListener = new ContinueHandler();
	    button.addActionListener(buttonListener);
	    

		// add action listener for the check boxes
		ActionListener actionListener = new ActionHandler();
		aids.addActionListener(actionListener);
		allergy.addActionListener(actionListener);
		alzheimer.addActionListener(actionListener);
		asthma.addActionListener(actionListener);
		autoimmune.addActionListener(actionListener);
		chf.addActionListener(actionListener);
		copd.addActionListener(actionListener);
		dementia.addActionListener(actionListener);
		diabetes.addActionListener(actionListener);
		dystrophy.addActionListener(actionListener);
		epilepsy.addActionListener(actionListener);
		hiv.addActionListener(actionListener);
		hypertension.addActionListener(actionListener);
		infectious_disease.addActionListener(actionListener);
		inflammatory_disease.addActionListener(actionListener);
		ischemia.addActionListener(actionListener);
		multiple_sclerosis.addActionListener(actionListener);
		obesity.addActionListener(actionListener);
		metabolic_disorders.addActionListener(actionListener);
		parkinson.addActionListener(actionListener);
		respiratory.addActionListener(actionListener);
		schizophrenia.addActionListener(actionListener);
		sleep.addActionListener(actionListener);
		spinal_injury.addActionListener(actionListener);
		stroke.addActionListener(actionListener);
		
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
			ExecutorService service = Executors.newFixedThreadPool(1);
			service.execute(new Runnable() {

				@Override
				public void run() {
					new Agriculture().setVisible(true);
				}
			});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LifeScienceDiseases().setVisible(true);
			}
		});
	}
}