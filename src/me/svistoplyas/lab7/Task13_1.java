package me.svistoplyas.lab7;

import com.sun.javafx.tk.PlatformImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Task13_1 implements ActionListener {
	
	private static int zoomRadius = 20;
	
	private static ActionListener listener = new Task13_1();
	private static ImagePanel panel;
	private static JTextField size;
	private static JTextField area;
	private static boolean useColor = true;
	
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("lab13_java_1");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
		
		panel = new ImagePanel();
		frame.add(panel);

		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		frame.add(controls);
		frame.add(Box.createRigidArea(new Dimension(0, 30)));
		
//		JButton color = new JButton("Цветное изображение");
//		Dimension d=new Dimension(191,25);
//		color.setPreferredSize( d);
//		color.setMinimumSize( d);
//		color.setMaximumSize( d);
//		color.addActionListener(listener);
//		color.setActionCommand("color");
//		controls.add(color);
//		controls.add(Box.createRigidArea(new Dimension(0, 30)));
//
//		JButton black = new JButton("Черно-белое изображение");
//		black.addActionListener(listener);
//		black.setActionCommand("black");
//		controls.add(black);
//		controls.add(Box.createRigidArea(new Dimension(0, 30)));
		
//		JButton level = new JButton("Выровнять по гистограмме");
//		level.addActionListener(listener);
//		level.setActionCommand("level");
//		controls.add(level);
//		controls.add(Box.createRigidArea(new Dimension(0, 30)));
		
		size = new JTextField("4");
		setFixedSize(size, 45, 27);
		controls.add(size);

		area = new JTextField("20");
		setFixedSize(area, 45, 27);
		controls.add(area);
		
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "color": useColor = true; panel.repaint(); break;
			case "black": useColor = false; panel.repaint(); break;
//			case "level": panel.setHistogram(); panel.repaint(); break;
		}
	}
	
	@SuppressWarnings("serial")
	private static class ImagePanel extends JPanel {
		
		private BufferedImage color, black;
		private BufferedImage part = null, neighbor, interpolation, histogram = null;
		
		public ImagePanel() throws Exception {
			this.setPreferredSize(new Dimension(650, 268 * 2));
			this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			
			color = ImageIO.read(new File("resources/color.jpg"));
			black = ImageIO.read(new File("resources/black.jpg"));
			
			this.addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getX() >= color.getWidth() || e.getY() >= color.getHeight()) return;

                    zoomRadius = 20;
                    try {
                        zoomRadius = Integer.parseInt(area.getText());
                    } catch(Exception ex) { }

					int x = normalize(zoomRadius, e.getX(), color.getWidth() - zoomRadius);
					int y = normalize(zoomRadius, e.getY(), color.getHeight() - zoomRadius);
					
					BufferedImage source = useColor ? color : black;
					part = source.getSubimage(x - zoomRadius, y - zoomRadius, zoomRadius * 2 + 1, zoomRadius * 2 + 1);
					
					int k = 4;
					try {
						k = Integer.parseInt(size.getText());
					} catch(Exception ex) { }
					
					neighbor = zoomNeighborInterpolation(part, k);
					interpolation = zoomBilinearInterpolation(part, k);
					panel.repaint();
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.BLACK);
            g.drawImage(useColor ? color : black, 0, 0, null);
            g.drawLine(color.getWidth(), 0, color.getWidth(), this.getHeight());

            if (part != null) {
                int l = color.getWidth() + 11;
                g.drawImage(part, l, 10, null);
                g.drawImage(neighbor, l, part.getHeight() + 20, null);
                g.drawImage(interpolation, l, part.getHeight() + neighbor.getHeight() + 30, null);
            }

            if (histogram != null) g.drawImage(histogram, 0, color.getHeight(), null);
        }
	}
	
	private static void setFixedSize(JComponent c, int w, int h) {
		Dimension d = new Dimension(w, h);
		c.setMaximumSize(d);
		c.setPreferredSize(d);
		c.setMinimumSize(d);
	}
	
	private static int normalize(int a, int b, int c) {
		if(b < a) return a;
		if(b > c - 1) return c - 1;
		return b;
	}
	
	private static BufferedImage zoomBilinearInterpolation(BufferedImage source, int k) {
		BufferedImage res = new BufferedImage(source.getWidth() * k, source.getHeight() * k, source.getType());
		for(int i = 0; i < res.getWidth(); i++)
			for(int j = 0; j < res.getHeight(); j++) {
				double x = i * 1.0 / k, y = j * 1.0 / k;
				
				int xL = (int)x, xR = xL + 1;
				int yL = (int)y, yR = yL + 1;
				
				Color ll = new Color(source.getRGB(xL, yL));
				Color rl = xR != source.getWidth() ? new Color(source.getRGB(xR, yL)) : ll;
				Color lr = yR != source.getHeight() ? new Color(source.getRGB(xL, yR)) : ll;
				Color rr = yR != source.getHeight() ? xR != source.getWidth() ? new Color(source.getRGB(xR, yR)) : lr : rl;
				
				int r1 = calcBetween(xL, x, xR, ll.getRed(), rl.getRed());
				int r2 = calcBetween(xL, x, xR, lr.getRed(), rr.getRed());
				int red = calcBetween(yL, y, yR, r1, r2);
				
				r1 = calcBetween(xL, x, xR, ll.getGreen(), rl.getGreen());
				r2 = calcBetween(xL, x, xR, lr.getGreen(), rr.getGreen());
				int green = calcBetween(yL, y, yR, r1, r2);
				
				r1 = calcBetween(xL, x, xR, ll.getBlue(), rl.getBlue());
				r2 = calcBetween(xL, x, xR, lr.getBlue(), rr.getBlue());
				int blue = calcBetween(yL, y, yR, r1, r2);
				
				Color c = new Color(red, green, blue);
				res.setRGB(i, j, c.getRGB());
			}
		
		return res;
	}

	private static BufferedImage zoomNeighborInterpolation(BufferedImage source, int k) {
		int w1 = source.getWidth();
		int h1 = source.getHeight();
		int w2 = w1*k;
		int h2 = h1*k;
        int x_ratio = (int)((w1<<16)/w2) +1;
        int y_ratio = (int)((h1<<16)/h2) +1;
		BufferedImage res = new BufferedImage(w2, h2, source.getType());
		for(int i = 0; i < w2; i++)
			for(int j = 0; j < h2; j++) {
				int x2 = ((i*x_ratio)>>16) ;
                int y2 = ((j*y_ratio)>>16) ;
				res.setRGB(i, j, source.getRGB(x2,y2));
			}

		return res;
	}
	
	private static int calcBetween(int x1, double x, int x2, int a, int b) {
		return (int)(((x2 - x)/(x2 - x1)) * a + ((x - x1)/(x2 - x1)) * b);
	}
}
