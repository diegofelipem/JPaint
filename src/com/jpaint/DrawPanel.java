package com.jpaint;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage bfImage;
	private int WIDTH = 450;
	private int HEIGHT = 300;
	private Point oldPoint = null;
	private Point newPoint = null;
	private Color activeColor = Color.black;

	public DrawPanel() {
		
		bfImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
				
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				newPoint = e.getPoint();
				updateImage();;
				newPoint = null;
				oldPoint = null;
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(oldPoint == null){
					oldPoint = e.getPoint();
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				newPoint = e.getPoint();
				updateImage();	
				oldPoint = newPoint;
			}
		});
		
		setBackground(Color.white);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	private void updateImage() {

		Graphics2D g2 = bfImage.createGraphics();
		
		g2.setColor(activeColor);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		if(newPoint != null){
			g2.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);	
		}		
		g2.dispose();
		repaint();
	}
	

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(bfImage, 0, 0, null);
	}
	
	public void clear(){
		Graphics2D g2 = bfImage.createGraphics();
		g2.setBackground(new Color(255, 255, 255, 0));
		g2.clearRect(0, 0, WIDTH, HEIGHT);
		repaint();
	}
}