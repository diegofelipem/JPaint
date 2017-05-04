package com.jpaint;

import java.awt.BorderLayout;
import java.awt.Color;

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

	public PrincipalScreen() {
		initComponents();
	}

	private void initComponents() {
		setJMenuBar(getMenu());
		drawPanel = new DrawPanel();
		getContentPane().add(getColorPanel(), BorderLayout.NORTH);
		getContentPane().add(drawPanel, BorderLayout.CENTER);

		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	private JPanel getColorPanel() {

		JPanel colorPanel = new JPanel();

		JColorChooser chooser = new JColorChooser();

		for (AbstractColorChooserPanel c : chooser.getChooserPanels()) {
			String className = c.getClass().getName();
			if (!className.contains("DefaultSwatchChooserPanel")) {
				chooser.removeChooserPanel(c);
			}
		}

		chooser.setPreviewPanel(new JPanel());

		chooser.getSelectionModel().addChangeListener(e -> {
			Color c = chooser.getColor();
			if (!drawPanel.getBackground().equals(c))
				drawPanel.setActiveColor(c);
		});

		colorPanel.add(chooser);
		colorPanel.setBorder(BorderFactory.createTitledBorder("Paleta"));

		return colorPanel;

	}

	private JMenuBar getMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("Opções");
		menuBar.add(menu);
		
		JMenuItem itemNew = new JMenuItem("New...");
		itemNew.addActionListener(e -> {
			
		});
		
		JMenuItem itemSave = new JMenuItem("Save...");
		itemNew.addActionListener(e -> {
			
		});		

		JMenuItem itemClear = new JMenuItem("Clear...");
		itemClear.addActionListener(e -> {
			drawPanel.clear();
		});
		
		menu.add(itemNew);
		menu.add(itemSave);
		menu.add(itemClear);

		return menuBar;
	}

	public static void main(String[] args) {

		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName())) {
				try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		SwingUtilities.invokeLater(() -> {
			new PrincipalScreen().setVisible(true);
		});
	}
}
