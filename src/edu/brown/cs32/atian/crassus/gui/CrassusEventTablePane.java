package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class CrassusEventTablePane extends JPanel {

	public CrassusEventTablePane(){
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		JTable table = new JTable();
		table.setBackground(Color.WHITE);
		table.setTableHeader(null);//Disable table header
		
		CrassusEventTableModel model = new CrassusEventTableModel();
		table.setModel(model);
		table.setShowGrid(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//allow only one row to be selected at a time
		table.setFillsViewportHeight(true);//makes extra space below table entries white
		
		for(int i=0; i<3; i++){
			TableColumn column = table.getColumnModel().getColumn(i);
			int colWidth = (i==2) ? 120 : 20;
			column.setPreferredWidth(colWidth);
			column.setMaxWidth(colWidth);
			column.setMinWidth(colWidth);
			
			if(i==2){
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				column.setCellRenderer(renderer);
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10,20,10,0));//right border (0) taken care of by increased table size (to deal with scroll-bar)
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200,250));
		
		JLabel title = new JLabel("Events",JLabel.CENTER);
		title.setFont(new Font("SansSerif",Font.BOLD,18));
		this.add(title, BorderLayout.NORTH);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20,20,20,20),
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
	}
}
