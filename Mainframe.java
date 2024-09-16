import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

class Mainframe {
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());

		Coords mouse = new Coords(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
		JPanel renderPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				// Paint background
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, getWidth(), getHeight());

				Coords c = new Coords(getWidth()/2, getHeight()/2);	// Center coordinates

				// Axis
				g2d.setColor(Color.darkGray);
				Path2D X = new Path2D.Double();
				X.moveTo(0, c.y);
				X.lineTo(getWidth(), c.y);
				X.closePath();
				g2d.draw(X);
				Path2D Y = new Path2D.Double();
				Y.moveTo(c.x, 0);
				Y.lineTo(c.x, getHeight());
				Y.closePath();
				g2d.draw(Y);
				// Center dot
				g2d.setColor(Color.white);
				Path2D dot = new Path2D.Double();
				dot.moveTo(c.x, c.y);
				dot.lineTo(c.x, c.y);
				dot.closePath();
				g2d.draw(dot);

				// 
				g2d.setColor(Color.white);
				double xhalf = getWidth()/2;
				double x;	
				double m = 0.5;
				for (int i = 1; i <= 18; i++) {
					x = Math.pow(i, 2);
					// Slopes
					Path2D q1 = new Path2D.Double();	// Quandrant 1
					q1.moveTo(c.x, c.y);
					q1.lineTo(getWidth(), c.y - (xhalf * m) + x);
					q1.closePath();
					g2d.draw(q1);
					Path2D q4 = new Path2D.Double();	// Quandrant 4
					q4.moveTo(c.x, c.y);
					q4.lineTo(getWidth(), c.y + (xhalf * m) - x);
				       	q4.closePath();
					g2d.draw(q4);
					Path2D q2 = new Path2D.Double();	// Quandrant 2
					q2.moveTo(c.x, c.y);
					q2.lineTo(0, c.y - (xhalf * m) + x);
				       	q2.closePath();
					g2d.draw(q2);
					Path2D q3 = new Path2D.Double();	// Quandrant 3
					q3.moveTo(c.x, c.y);
					q3.lineTo(0, c.y + (xhalf * m) - x);
				       	q3.closePath();
					g2d.draw(q3);
				
					x = Math.pow(i, 2.5);	
					// Verticals
					Path2D vr = new Path2D.Double();	// Right side
					vr.moveTo(c.x + x, c.y - (x * m));
					vr.lineTo(c.x + x, c.y + (x * m));
					vr.closePath();
					g2d.draw(vr);
					Path2D vl = new Path2D.Double();	// Left side
					vl.moveTo(c.x - x, c.y - (x * m));
					vl.lineTo(c.x - x, c.y + (x * m));
					vl.closePath();
					g2d.draw(vl);
				}

				Path2D cm = new Path2D.Double();
				cm.moveTo(c.x, c.y);
				cm.lineTo(mouse.x, mouse.y);
				cm.closePath();
				g2d.draw(cm);

				Path2D tlm = new Path2D.Double();	// Top left -> Mouse
				tlm.moveTo(0, 0);
				tlm.lineTo(mouse.x, mouse.y);
				tlm.closePath();
				g2d.draw(tlm);
				Path2D trm = new Path2D.Double();	// Top right -> Mouse
				trm.moveTo(getWidth(), 0);
				trm.lineTo(mouse.x, mouse.y);
				trm.closePath();
				g2d.draw(trm);
				Path2D blm = new Path2D.Double();	// Bottom left -> Mouse
				blm.moveTo(0, getHeight());
				blm.lineTo(mouse.x, mouse.y);
				blm.closePath();
				g2d.draw(blm);
				Path2D brm = new Path2D.Double();	// Bottom right -> Mouse
				brm.moveTo(getWidth(), getHeight());
				brm.lineTo(mouse.x, mouse.y);
				brm.closePath();
				g2d.draw(brm);
			}
		};
		renderPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouse.x = MouseInfo.getPointerInfo().getLocation().getX();
				mouse.y = MouseInfo.getPointerInfo().getLocation().getY();
				renderPanel.repaint();
			}

		});
		pane.add(renderPanel, BorderLayout.CENTER);

		JLabel labelTop = new JLabel("Test Graphic");
		labelTop.setHorizontalAlignment(SwingConstants.CENTER);
		labelTop.setForeground(Color.white);
		JPanel topPane = new JPanel();
		topPane.setBackground(Color.black);
		topPane.add(labelTop);
		pane.add(topPane, BorderLayout.NORTH);

		frame.setSize(screen);
		frame.setVisible(true);
		device.setFullScreenWindow(frame);
	}
}
