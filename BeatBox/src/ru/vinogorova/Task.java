package ru.vinogorova;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Task implements ListSelectionListener {
	String[] list = {"Moscow", "Sochi", "Saint Petersburg", "Kazan", "Penza", "Novosibirsk", "Anapa", "Samara"};
	JFrame frame = new JFrame();
	JPanel eastPanel = new JPanel();
	JList jList = new JList(list);
	
	public static void main(String[] args) {
		Task gui = new Task();
		gui.go();
	}
	
	private void go() {
		
		JScrollPane scroller = new JScrollPane(jList);
		jList.addListSelectionListener(this);
		
		frame.getContentPane().add(BorderLayout.EAST, eastPanel);
		eastPanel.setBackground(Color.GRAY);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(scroller);
		jList.setVisibleRowCount(5);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		

		frame.setSize(200, 200);
		frame.setVisible(true);		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			String selection = (String)jList.getSelectedValue();
			System.out.println(selection);
		}
		
	}

}
