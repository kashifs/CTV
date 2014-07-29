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

	public SelectOGC() {

		String[] choices = { "physical sciences or medical devices",
				"life sciences" };
		String input = (String) JOptionPane.showInputDialog(null,
				"What type of technology is it?", "Office of General Council",
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

		if (input.equals("life sciences"))
			ogcInitials = "GM/TB";
		else {
			ogcInitials = "JS";
		}

	}

	public String getInitials() {
		return ogcInitials;
	}

	public static void main(String[] args){
		new SelectOGC();
		
	}
}