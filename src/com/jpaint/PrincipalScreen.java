package com.jpaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class PrincipalScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	DrawPanel drawPanel;
	JColorChooser chooser;

	public PrincipalScreen() {
		initComponents();
	}

	private void initComponents() {
		
		JPanel colorPanel = new JPanel();

		chooser = new JColorChooser();

		for (AbstractColorChooserPanel c : chooser.getChooserPanels()) {
			String className = c.getClass().getName();
			if (!className.contains("DefaultSwatchChooserPanel")) {
				chooser.removeChooserPanel(c);
			}
		}

		chooser.setPreviewPanel(new JPanel());

		chooser.getSelectionModel().addChangeListener(e -> {
			Color c = getSelectedChooserColor();
			if (!drawPanel.getBackground().equals(c))
				drawPanel.setActiveColor(c);
		});

		colorPanel.add(chooser);
		colorPanel.setBorder(BorderFactory.createTitledBorder("Paleta"));
		
		setJMenuBar(getMenu());
		drawPanel = new DrawPanel();
		getContentPane().add(colorPanel, BorderLayout.NORTH);
		getContentPane().add(drawPanel, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(435, 450));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private Color getSelectedChooserColor(){
		return chooser.getColor() != null ?  chooser.getColor() : Color.BLACK;
	}

	private JMenuBar getMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem itemNew = new JMenuItem("New...");
		itemNew.addActionListener(e -> {
			
		});
		
		JMenuItem itemSave = new JMenuItem("Save...");
		itemNew.addActionListener(e -> {
			
		});
		
		menu.add(itemNew);
		menu.add(itemSave);
		
		JMenu optionsMenu = new JMenu("Tools");
		menuBar.add(optionsMenu);
		
		JMenuItem itemChangeBg = new JMenuItem("Change Background");
		itemChangeBg.addActionListener(e -> {
			drawPanel.setBackground(getSelectedChooserColor());
		});

		JMenuItem itemClear = new JMenuItem("Clear...");
		itemClear.addActionListener(e -> {
			drawPanel.clear();
		});
		
		optionsMenu.add(itemChangeBg);
		optionsMenu.add(itemClear);

		return menuBar;
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			new PrincipalScreen().setVisible(true);
		});
	}
}
