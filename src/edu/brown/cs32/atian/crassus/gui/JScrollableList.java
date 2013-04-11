/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneLayout;

/**
 * @author Matthew
 *
 */
public class JScrollableList extends JComponent {

	private JPanel panel;
	
	public JScrollableList(){
		super.setLayout(new ScrollPaneLayout());
		
		panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		super.add(panel);
	}
	
	@Override
	public Component add(Component comp){
		return panel.add(comp);
	}
	
	@Override
	public LayoutManager getLayout(){
		return panel.getLayout();
	}
	
}
