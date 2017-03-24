import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Plot extends Canvas{
	
	public boolean data, train, test;
	public ArrayList<Double> x;
	public ArrayList<Double> y;
	public ArrayList<Integer> group;
	public int mid;
	public double temp1 ,temp2;
	public double[] weight = new double[3];
	
	public Plot() {
		setBounds(0, 0, 500, 500);
		data = false;
		train = false;
		test = false;
	}
	
	public void paint(Graphics g) {
		//draw x and y axis
		Graphics2D  g2d = (Graphics2D) g; 
		g.translate(250, 250);
		g.drawLine(250, 0, -250, 0);
		g.drawLine(250, 0, 240, -10);
		g.drawLine(250, 0, 240, 10);
		g.drawLine(0, 250, 0, -250);
		g.drawLine(0, -250, 10, -240);
		g.drawLine(0, -250, -10, -240);
		
		//draw test data
		if(data && test) {
			if(x.size() > 10) {
				for(int i = mid * 2/3 ; i < mid ; i++) {
					g.setColor(Color.YELLOW);
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
				for(int i = mid + (x.size() - mid) * 2/3 ; i < x.size() ; i++) {
					g.setColor(Color.BLUE);
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
			} else {
				for(int i = 0 ; i < x.size() ; i++) {
					if(group.get(i) < 0) {
						g.setColor(Color.YELLOW);
					} else {
						g.setColor(Color.BLUE);
					}
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
			}
			g2d.setColor(Color.BLACK);
			Line2D.Double line = new Line2D.Double(200, -temp1, -200, -temp2);
			g2d.draw(line); 
		}
		//draw train data
		else if(data && train) {
			temp1 = ((-200) * weight[1] + weight[0]) / weight[2];
			temp2 = (200 * weight[1] + weight[0]) / weight[2];
			if(x.size() > 10) {
				for(int i = 0 ; i < mid * 2/3 ; i++) {
					g.setColor(Color.RED);
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
				for(int i = mid ; i < mid + (x.size() - mid) * 2/3 ; i++) {
					g.setColor(Color.GREEN);
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
			} else {
				for(int i = 0 ; i < x.size() ; i++) {
					if(group.get(i) < 0) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.GREEN);
					}
					Rectangle2D.Double rect = new Rectangle2D.Double(x.get(i) - 1.5, -(y.get(i) - 1.5), 3, 3);
					g2d.fill(rect);
				}
			}
			g2d.setColor(Color.BLACK);
			Line2D.Double line = new Line2D.Double(200, -temp1, -200, -temp2);
			g2d.draw(line); 
		}
	}
	
	public void read_result(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Integer> group, int mid) {
		this.x = new ArrayList<Double>(x);
		this.y = new ArrayList<Double>(y);
		this.group = new ArrayList<Integer>(group);
		this.mid = mid;
		data = true;
	}
	
	public void train_result(double[] weight) {
		this.weight = weight;
		test = false;
		train = true;
		this.repaint();
	}
	
	public void test_result() {
		train = false;
		test = true;
		this.repaint();
	}
}
