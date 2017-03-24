import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener {
	
	private Plot plot;
	
	private JPanel input, result;
	private JLabel text1, text2, text3, text4, text4_, text5, text6, text7;
	private JTextField learn, accuracy, iteration, filename, TrainAc, TestAc, WeightVt;
	private JTextArea read_input, output;
	private JButton btn1, btn2, btn3;
	private int index, mid, p, temp;
	private double ac;
	private DecimalFormat df = new DecimalFormat("##.000000");
	private BufferedReader br;
	public ArrayList<Double> x = new ArrayList<Double>();
	public ArrayList<Double> y = new ArrayList<Double>();
	public ArrayList<Double> train_x;
	public ArrayList<Double> train_y;
	public ArrayList<Integer> group = new ArrayList<Integer>();
	public ArrayList<Integer> train_group;
	public double[] weight = new double[3]; 
	
	public Main() {
		setLayout(null);
		setSize(800, 670);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		create_input();
		create_result();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main perc = new Main();
		perc.setVisible(true);
	}
	
	public void create_input() {
		input = new JPanel();
		input.setBounds(520, 0, 280, 620);
		input.setBorder(BorderFactory.createLineBorder(Color.black));
		btn1 = new JButton("Open File");
		filename= new JTextField(12);
		filename.setEditable(false);
		text1 = new JLabel("Learning Rate");
		learn = new JTextField(12);
		learn.setText("0.8");
		text2 = new JLabel("Accuracy(%)");
		accuracy = new JTextField(14);
		accuracy.setText("80");
		text3 = new JLabel("Iteration");
		iteration = new JTextField(17);
		iteration.setText("300");
		text4_ = new JLabel("Input");
		read_input = new JTextArea(12, 18);
		read_input.setEditable(false);
		text4 = new JLabel("Output");
		output = new JTextArea(12, 18);
		output.setEditable(false);
		btn2 = new JButton("Train");
		btn3 = new JButton("Test");
		input.add(btn1);
		input.add(filename);
		input.add(text1);
		input.add(learn);
		input.add(text2);
		input.add(accuracy);
		input.add(text3);
		input.add(iteration);
		input.add(text4_);
		input.add(new JScrollPane(read_input));
		input.add(text4);
		input.add(new JScrollPane(output));
		input.add(btn2);
		input.add(btn3);
		this.add(input);
		
		plot = new Plot();
		this.add(plot);
		
		btn1.setActionCommand("b1");
        btn1.addActionListener(this);
        btn2.setActionCommand("b2");
        btn2.addActionListener(this);
        btn3.setActionCommand("b3");
        btn3.addActionListener(this);
	}
	
	public void create_result() {
		result = new JPanel();
		result.setBounds(0, 520, 519, 100);
		result.setBorder(BorderFactory.createLineBorder(Color.black));
		text5 = new JLabel("Training Accuracy");
		TrainAc = new JTextField(30);
		TrainAc.setEditable(false);
		text6 = new JLabel("Testing Accuracy");
		TestAc = new JTextField(30);
		TestAc.setEditable(false);
		text7 = new JLabel("Weight Vector");
		WeightVt = new JTextField(30);
		WeightVt.setEditable(false);
		result.add(text5);
		result.add(TrainAc);
		result.add(text6);
		result.add(TestAc);
		result.add(text7);
		result.add(WeightVt);
		this.add(result);
	}
	
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        //open a txt file
        if (cmd == "b1") {
	        //clear the old data
        	if(!x.isEmpty()) {
        		x.clear();
        		y.clear();
        		group.clear();
        	}
        	//choose a file and read the data
        	try {
        		JFileChooser chooser = new JFileChooser();
        		int ret = chooser.showOpenDialog(null);
        		if(ret == JFileChooser.APPROVE_OPTION) {
        			filename.setText(chooser.getSelectedFile().getName());
        		}
        		FileReader fr = new FileReader(chooser.getSelectedFile().getPath());
        		br = new BufferedReader(fr);
        		String line;
        		String[] tempstring;
        		read_input.setText("");
        		//read every line
        		while ((line = br.readLine()) != null) {
        			//split the data. ex.1 0 1
        			tempstring = line.split("\\s+");  
        			//some lines' first character is ' ' or a number
        			if(tempstring[0].isEmpty()) {
        				index = 1;
        			} else {
        				index = 0;
        			}
        			//show the data in the "input" TextArea
        			read_input.setText(read_input.getText() + tempstring[index] + "," + tempstring[index + 1] + "," + tempstring[index + 2] + "\n");
        			//store data
        		    x.add(Double.valueOf(tempstring[index]));
        		    y.add(Double.valueOf(tempstring[index + 1]));
        		    group.add(Integer.valueOf(tempstring[index + 2]));
        		    //find the number of two classes
        		    if(group.size() > 1) {
        		    	if(group.get(group.size() - 1) != group.get(group.size() - 2)) {
        		    		mid = group.size() - 1;
        		    	}	
        		    }
        		}
        		//check "mid" split two classes
        		temp = mid;
        		for(int i = mid ; i < x.size() ; i++) {
        			if(group.get(temp) != group.get(i)) {
        				Collections.swap(x, temp, i);
        				Collections.swap(y, temp, i);
        				Collections.swap(group, temp, i);
        				temp++;
        			}
        		}
        		//change the expectations into (-1, 1)
        		for(int i = 0 ; i < mid ; i++) {
        			x.set(i, x.get(i) * 10);
        			y.set(i, y.get(i) * 10);
        			group.set(i, -1);
        		}
        		for(int i = mid ; i < group.size() ; i++) {
        			x.set(i, x.get(i) * 10);
        			y.set(i, y.get(i) * 10);
        			group.set(i, 1);
        		}
        		//if the number of data is too small, then all the data will be trained.
        		//otherwise, 2/3 of data will be trained and 1/3 of data will be tested.
        		if(x.size() > 10) {
        			train_x = new ArrayList<Double>(x.subList(0, mid * 2/3));
            		train_x.addAll(x.subList(mid, mid + (x.size() - mid) * 2/3));
            		train_y = new ArrayList<Double>(y.subList(0, mid * 2/3));
            		train_y.addAll(y.subList(mid, mid + (y.size() - mid) * 2/3));
            		train_group = new ArrayList<Integer>(group.subList(0, mid * 2/3));
            		train_group.addAll(group.subList(mid, mid + (group.size() - mid) * 2/3));
        		} else {
        			train_x = new ArrayList<Double>(x);
            		train_y = new ArrayList<Double>(y);
            		train_group = new ArrayList<Integer>(group);
        		}
        		//transmit data into Plot
        		plot.read_result(x, y, group, mid);
        	} catch (IOException e1) {
        		System.out.println(e1);
        	}
        }
        //train the data
        else if(cmd == "b2") {
        	//check whether file was opened and every TextField was typed or not 
        	if(filename.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Please open the txt file first");
        	} else if(learn.getText().isEmpty() || accuracy.getText().isEmpty() || iteration.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Please type the all textfield on the upper right");
        	} else {
        		//initialize the weight
        		weight[0] = Math.random();
            	weight[1] = Math.random();
            	weight[2] = Math.random();
            	//count stands for the iteration currently
            	int count = 0;
            	//show the initialized weight in the "output" TextArea
            	output.setText(weight[0] + "," + weight[1] + "," + weight[2] + "\n");
            	while(count < Integer.valueOf(iteration.getText())) {
            		p = count % train_x.size();
            		ac = 0;
            		//adjust the weight
            		if(weight[0] * (-1) + weight[1] * train_x.get(p) + weight[2] * train_y.get(p) > 0) {
            			if(train_group.get(p) < 0) {
            				weight[0] = weight[0] - Double.parseDouble(learn.getText()) * (-1);
            				weight[1] = weight[1] - Double.parseDouble(learn.getText()) * train_x.get(p);
            				weight[2] = weight[2] - Double.parseDouble(learn.getText()) * train_y.get(p);
            			}
            		} else {
            			if(train_group.get(p) > 0) {
            				weight[0] = weight[0] + Double.parseDouble(learn.getText()) * (-1);
            				weight[1] = weight[1] + Double.parseDouble(learn.getText()) * train_x.get(p);
            				weight[2] = weight[2] + Double.parseDouble(learn.getText()) * train_y.get(p);
            			}
            		}
            		//round the weight off to the 6th decimal place
            		weight[0] = Double.parseDouble(df.format(weight[0]));
            		weight[1] = Double.parseDouble(df.format(weight[1]));
            		weight[2] = Double.parseDouble(df.format(weight[2]));
            		//show the weight in the "output" TextArea
            		output.setText(output.getText() + weight[0] + "," + weight[1] + "," + weight[2] + "\n");
            		//calculate the training accuracy. If the training accuracy above the target, then ends the iteration
            		for(int i = 0 ; i < train_x.size(); i++) {
            			if(weight[0] * (-1) + weight[1] * train_x.get(i) + weight[2] * train_y.get(i) > 0 && train_group.get(i) > 0) {
            				ac++;
            			} else if(weight[0] * (-1) + weight[1] * train_x.get(i) + weight[2] * train_y.get(i) < 0 && train_group.get(i) < 0) {
            				ac++;
            			}
            		}
            		if(ac / train_x.size() * 100  >= Integer.valueOf(accuracy.getText())) {
        				break;
        			}
            		count++;
            	}
            	//transmit data into Plot
            	plot.train_result(weight);
            	//show the training accuracy and weight
            	TestAc.setText("");
            	TrainAc.setText(String.valueOf(ac / train_x.size() * 100));
            	WeightVt.setText(weight[0] + ", " + weight[1] + ", " + weight[2]);
        	}
        }
        //test the data
        else if(cmd == "b3") {
        	//check whether the data was trained or not
        	if(plot.train) {
        		TrainAc.setText("");
        		ac = 0;
        		//calculate the testing accuracy.
        		if(x.size() > 10) {
        			for(int i = mid * 2/3 ; i < mid ; i++) {
            			if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) > 0 && group.get(i) > 0) {
            				ac++;
            			} else if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) < 0 && group.get(i) < 0) {
            				ac++;
            			}
            		}
            		for(int i = mid + (x.size() - mid) * 2/3 ; i < x.size() ; i++) {
            			if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) > 0 && group.get(i) > 0) {
            				ac++;
            			} else if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) < 0 && group.get(i) < 0) {
            				ac++;
            			}
            		}
            		//show the testing accuracy and weight
            		TestAc.setText(String.valueOf(ac / (x.size() - train_x.size()) * 100));
        		} else {
        			for(int i = 0 ; i < x.size() ; i++) {
            			if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) > 0 && group.get(i) > 0) {
            				ac++;
            			} else if(weight[0] * (-1) + weight[1] * x.get(i) + weight[2] * y.get(i) < 0 && group.get(i) < 0) {
            				ac++;
            			}
            		}
        			//show the testing accuracy and weight
            		TestAc.setText(String.valueOf(ac / x.size() * 100));
        		}
        		plot.test_result();
        	} else {
        		JOptionPane.showMessageDialog(this, "Please train the input data first");
        	}
        }
	}
}
