import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

/*
 * ComboBoxDemo.java is a 1.4 application that uses these additional files:
 *   images/Bird.gif
 *   images/Cat.gif
 *   images/Dog.gif
 *   images/Rabbit.gif
 *   images/Pig.gif
 */
public class SelectOGC {
	
	private static String ogcInitials;
	public SelectOGC() throws InterruptedException, InvocationTargetException {
//		EventQueue.invokeLater(new Runnable() {
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JPanel panel = new JPanel();
				panel.add(new JLabel("What type of technology is it?"));
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				model.addElement("physical sciences or medical devices"); //JS
				model.addElement("life sciences"); //GM/TB
				JComboBox comboBox = new JComboBox(model);
				panel.add(comboBox);

				int result = JOptionPane.showConfirmDialog(null, panel,
						"Office of General Council", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				switch (result) {
				case JOptionPane.OK_OPTION:
					System.out.println("You selected "
							+ comboBox.getSelectedItem());
					if(comboBox.getSelectedItem().toString().equals("life sciences"))
						ogcInitials = "GM/TB";
					else {
						ogcInitials = "JS";
					}
					break;
				}

			}
		});
	}
	
	public String getInitials(){
		return ogcInitials;
	}
	
}