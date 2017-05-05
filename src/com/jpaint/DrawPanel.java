package com.jpaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage bfImage;
	private Point oldPoint = null;
	private Point newPoint = null;
	private Color activeColor = Color.black;

	public DrawPanel() {

		initComponents();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				newPoint = e.getPoint();
				updateImage();
				newPoint = null;
				oldPoint = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (oldPoint == null) {
					oldPoint = e.getPoint();
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				newPoint = e.getPoint();
				updateImage();
				oldPoint = newPoint;
			}
		});

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				Dimension d = e.getComponent().getSize();
				resizeBufferredImage(d.width, d.height);
			}
		});
	}

	private void initComponents() {
		bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

		setBackground(Color.white);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	private void resizeBufferredImage(int newWidth, int newHeight) {
		BufferedImage newbfImage = new BufferedImage(newWidth, newHeight, bfImage.getType());
		Graphics2D g = newbfImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bfImage, 0, 0, newWidth, newHeight, 0, 0, bfImage.getWidth(), bfImage.getHeight(), null);
		g.dispose();
		bfImage = newbfImage;
		repaint();

	}

	private void updateImage() {

		if (newPoint != null) {

			Graphics2D g2 = bfImage.createGraphics();
			g2.setColor(activeColor);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
			g2.dispose();
			repaint();
		}
	}

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(bfImage, 0, 0, null);
	}

	public void saveFile(String output) {

		File outputImage = new File(output + ".png");

		try {
			ImageIO.write(bfImage, "png", outputImage);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public void clear() {
		this.setBackground(new Color(255, 255, 255));
		Graphics2D g2 = bfImage.createGraphics();
		g2.setBackground(new Color(255, 255, 255, 0));
		g2.clearRect(0, 0, bfImage.getWidth(), bfImage.getHeight());
		repaint();
	}
}