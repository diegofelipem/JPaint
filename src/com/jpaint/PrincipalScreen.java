package com.jpaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class PrincipalScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	DrawPanel drawPanel;
	JColorChooser colorChooser;
	JFileChooser fileChooser;

	public PrincipalScreen() {
		initComponents();
	}

	private void initComponents() {
		
		JPanel colorPanel = new JPanel();

		colorChooser = new JColorChooser();
		fileChooser = new JFileChooser();

		for (AbstractColorChooserPanel c : colorChooser.getChooserPanels()) {
			String className = c.getClass().getName();
			if (!className.contains("DefaultSwatchChooserPanel")) {
				colorChooser.removeChooserPanel(c);
			}
		}

		colorChooser.setPreviewPanel(new JPanel());

		colorChooser.getSelectionModel().addChangeListener(e -> {
			Color c = getSelectedChooserColor();
			if (!drawPanel.getBackground().equals(c))
				drawPanel.setActiveColor(c);
		});
			
		colorPanel.add(colorChooser);
		colorPanel.setBorder(BorderFactory.createTitledBorder("Paleta"));
		
		setJMenuBar(getMenu());
		drawPanel = new DrawPanel();
		getContentPane().add(colorPanel, BorderLayout.NORTH);
		getContentPane().add(drawPanel, BorderLayout.CENTER);

		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private Color getSelectedChooserColor(){
		return colorChooser.getColor() != null ?  colorChooser.getColor() : Color.WHITE;
	}

	private JMenuBar getMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		JMenuItem itemNew = new JMenuItem("Open...");
		itemNew.addActionListener(e -> {
			String path = System.getProperty("user.home") + File.separator + "myPaintImages";
			if (!new File(path).exists()) {
				new File(path).mkdir();
			}
			fileChooser.setCurrentDirectory(new File(path));
			int returnVal = fileChooser.showOpenDialog(this);
			if(returnVal ==  JFileChooser.APPROVE_OPTION){
				drawPanel.openFile(fileChooser.getSelectedFile());
			}
		});
		
		JMenuItem itemSave = new JMenuItem("Save...");
		itemSave.addActionListener(e -> {
			String path = System.getProperty("user.home") + File.separator + "myPaintImages";
			if (!new File(path).exists()) {
				new File(path).mkdir();
			}
			fileChooser.setCurrentDirectory(new File(path));
			int returnVal = fileChooser.showSaveDialog(this);
			if(returnVal ==  JFileChooser.APPROVE_OPTION){
				drawPanel.saveFile(fileChooser.getSelectedFile());
			}
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
