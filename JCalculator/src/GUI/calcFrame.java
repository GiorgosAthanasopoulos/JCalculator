package GUI;

import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import Events.MyKeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class calcFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private Color fgColor;
	private Color bgColor;
	private ArrayList<Double> memory;
	private Font font;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public calcFrame() throws IOException {
		font = new Font("arial",1,20);
		
		memory = new ArrayList<Double>();
		
		fgColor = Color.MAGENTA;
		bgColor = Color.BLACK;
		
		File file = new File("res\\config.txt");
		if(file.exists()) {
			FileReader fileReader = new FileReader(file);
			Scanner sc = new Scanner(fileReader);
			while(sc.hasNextLine()) {
				String inputText = sc.nextLine();
				
				String key = inputText.split("=")[0];
				String val = inputText.split("=")[1];
				
				Color tempColor = convertStrToColor(val);
				
				//check all possible settings
				if(key.equals("fgColor")) {
					if(tempColor!=null) fgColor = tempColor;
				}else if(key.equals("bgColor")) {
					if(tempColor!=null) bgColor = tempColor;
				}
			}
			sc.close();
		}else {
			if(file.createNewFile()) {
			
				String fgi = JOptionPane.showInputDialog("Enter foreground color");
				String bgi = JOptionPane.showInputDialog("Enter background color");
				JOptionPane.showMessageDialog(null, "In case you want to change these settings in the future,\nplease change the according settings in the res\\config.txt\nfile in the project directory");
			
				Color tempColor1 = convertStrToColor(fgi);
				Color tempColor2 = convertStrToColor(bgi);
				if(tempColor1!=null) fgColor = tempColor1;
				if(tempColor2!=null) bgColor = tempColor2;
				
				//save to file
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				fw.write("fgColor="+fgi+"\nbgColor="+bgi+"\n");
				fw.close();
			}
		}
		
		setFocusable(true);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(bgColor);
		setContentPane(contentPane);
		addKeyListener(new MyKeyAdapter());
		
		textField = new JTextField();
		textField.setForeground(fgColor);
		textField.setBackground(bgColor);
		textField.setFont(font);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBorder(new RoundedBorder(20));
		
		JButton btnNewButton = new JButton("+/-");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//check whether or not it has an operator, if no just add minus to the first index
				//if yes get add minus to the index after operator
				if(hasOperator()) {
					String op = getOperator();
					if(op!=null) {
						String text1 = textField.getText().split("\\"+op)[0];
						String text2 = textField.getText().split("\\"+op)[1];
						if(!text2.contains("-")) textField.setText(text1+op+"negative"+text2);
						else {
							String text3 = text2;
							for(int i=0; i<"negative()".length(); i++) {
								for(char c : text3.toCharArray()) {
									if(c==new String[]{"n","e","g","a","t","i","v","e"}[i].toCharArray()[0]) {
										text3.replace(c, (char) 0);
									}
								}
							}
							textField.setText(text1+op+text3);
						}
					}
				}else {
					if(!textField.getText().contains("negative")) {
						textField.setText("negative"+textField.getText());
					}else {
						String text = textField.getText();
						char[] arr = "negative".toCharArray();
						for(char c: arr) {
							if(text.contains(String.valueOf(c))) {
								text.replace(c, (char) 0);
								System.out.println(text);
							}
						}
						textField.setText(text);
					}
				}
			}
		});
		btnNewButton.setForeground(fgColor);
		btnNewButton.setBackground(bgColor);
		btnNewButton.setFont(font);
		
		JButton btnNewButton_1 = new JButton("0");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"0");
			}
		});
		btnNewButton_1.setForeground(fgColor);
		btnNewButton_1.setBackground(bgColor);
		btnNewButton_1.setFont(font);
		
		JButton btnNewButton_2 = new JButton(".");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+".");
			}
		});
		btnNewButton_2.setForeground(fgColor);
		btnNewButton_2.setBackground(bgColor);
		btnNewButton_2.setFont(font);
		
		JButton btnNewButton_3 = new JButton("=");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//equals
				textField.setText(String.valueOf(Equals(textField.getText())));
			}
		});
		btnNewButton_3.setForeground(fgColor);
		btnNewButton_3.setBackground(bgColor);
		btnNewButton_3.setFont(font);
		
		JButton btnNewButton_4 = new JButton("1");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"1");
			}
		});
		btnNewButton_4.setForeground(fgColor);
		btnNewButton_4.setBackground(bgColor);
		btnNewButton_4.setFont(font);
		
		JButton btnNewButton_5 = new JButton("4");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"4");
			}
		});
		btnNewButton_5.setBackground(bgColor);
		btnNewButton_5.setForeground(fgColor);
		btnNewButton_5.setFont(font);
		
		JButton btnNewButton_6 = new JButton("7");
		btnNewButton_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"7");
			}
		});
		btnNewButton_6.setForeground(fgColor);
		btnNewButton_6.setBackground(bgColor);
		btnNewButton_6.setFont(font);
		
		JButton btnNewButton_7 = new JButton("1/X");
		btnNewButton_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator()) {
					Double text = Double.parseDouble(textField.getText());
					textField.setText(String.valueOf(1/text));
				}
			}
		});
		btnNewButton_7.setForeground(fgColor);
		btnNewButton_7.setBackground(bgColor);
		btnNewButton_7.setFont(font);
		
		JButton btnNewButton_8 = new JButton("%");
		btnNewButton_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator() && hasNumber()) textField.setText(textField.getText()+"%");
			}
		});
		btnNewButton_8.setForeground(fgColor);
		btnNewButton_8.setBackground(bgColor);
		btnNewButton_8.setFont(new Font("Ink Free", Font.BOLD, 20));
		
		JButton btnNewButton_9 = new JButton("2");
		btnNewButton_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"2");
			}
		});
		btnNewButton_9.setForeground(fgColor);
		btnNewButton_9.setBackground(bgColor);
		btnNewButton_9.setFont(font);
		
		JButton btnNewButton_10 = new JButton("5");
		btnNewButton_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"5");
			}
		});
		btnNewButton_10.setForeground(fgColor);
		btnNewButton_10.setBackground(bgColor);
		btnNewButton_10.setFont(font);
		
		JButton btnNewButton_11 = new JButton("8");
		btnNewButton_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"8");
			}
		});
		btnNewButton_11.setForeground(fgColor);
		btnNewButton_11.setBackground(bgColor);
		btnNewButton_11.setFont(font);
		
		JButton btnNewButton_12 = new JButton("X^2");
		btnNewButton_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator()) {
					Double text = Double.parseDouble(textField.getText());
					textField.setText(String.valueOf(Math.pow(text, 2)));
				}
			}
		});
		btnNewButton_12.setForeground(fgColor);
		btnNewButton_12.setBackground(bgColor);
		btnNewButton_12.setFont(font);
		
		JButton btnNewButton_13 = new JButton("CE");
		btnNewButton_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String text = textField.getText();
				char[] arr = text.toCharArray();
				if(arr.length>0) textField.setText(text.replace(arr[arr.length-1], ' '));
			}
		});
		btnNewButton_13.setForeground(fgColor);
		btnNewButton_13.setBackground(bgColor);
		btnNewButton_13.setFont(font);
		
		JButton btnNewButton_14 = new JButton("3");
		btnNewButton_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"3");
			}
		});
		btnNewButton_14.setForeground(fgColor);
		btnNewButton_14.setBackground(bgColor);
		btnNewButton_14.setFont(font);
		
		JButton btnNewButton_15 = new JButton("6");
		btnNewButton_15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"6");
			}
		});
		btnNewButton_15.setForeground(fgColor);
		btnNewButton_15.setBackground(bgColor);
		btnNewButton_15.setFont(font);
		
		JButton btnNewButton_16 = new JButton("9");
		btnNewButton_16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText(textField.getText()+"9");
			}
		});
		btnNewButton_16.setForeground(fgColor);
		btnNewButton_16.setBackground(bgColor);
		btnNewButton_16.setFont(font);
		
		String SQRTSymbol = "\u221A";
		JButton btnNewButton_17 = new JButton(SQRTSymbol);
		btnNewButton_17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator()) {
					Double text = Double.parseDouble(textField.getText());
					textField.setText(String.valueOf(Math.sqrt(text)));
				}
			}
		});
		btnNewButton_17.setForeground(fgColor);
		btnNewButton_17.setBackground(bgColor);
		btnNewButton_17.setFont(font);
		
		JButton btnNewButton_18 = new JButton("C");
		btnNewButton_18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField.setText("");
			}
		});
		btnNewButton_18.setForeground(fgColor);
		btnNewButton_18.setBackground(bgColor);
		btnNewButton_18.setFont(font);
		
		JButton btnNewButton_19 = new JButton("+");
		btnNewButton_19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(!hasOperator() && hasNumber()) {
					
					textField.setText(textField.getText()+"+");
				}
			}
		});
		btnNewButton_19.setForeground(fgColor);
		btnNewButton_19.setBackground(bgColor);
		btnNewButton_19.setFont(font);
		
		JButton btnNewButton_20 = new JButton("-");
		btnNewButton_20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator() && hasNumber()) textField.setText(textField.getText()+"-");
			}
		});
		btnNewButton_20.setForeground(fgColor);
		btnNewButton_20.setBackground(bgColor);
		btnNewButton_20.setFont(font);
		
		JButton btnNewButton_21 = new JButton("*");
		btnNewButton_21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator() && hasNumber()) textField.setText(textField.getText()+"*");
			}
		});
		btnNewButton_21.setForeground(fgColor);
		btnNewButton_21.setBackground(bgColor);
		btnNewButton_21.setFont(font);
		
		JButton btnNewButton_22 = new JButton("/");
		btnNewButton_22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator() && hasNumber()) textField.setText(textField.getText()+"/");
			}
		});
		btnNewButton_22.setForeground(fgColor);
		btnNewButton_22.setBackground(bgColor);
		btnNewButton_22.setFont(font);
		
		JButton btnNewButton_23 = new JButton("<=");
		btnNewButton_23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String text = textField.getText();
				char[] arr = text.toCharArray();
				if(arr.length>0) textField.setText(text.replace(arr[arr.length-1], ' '));
			}
		});
		btnNewButton_23.setForeground(fgColor);
		btnNewButton_23.setBackground(bgColor);
		btnNewButton_23.setFont(font);
		
		JButton btnNewButton_24 = new JButton("MC");
		btnNewButton_24.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//clear memory
				memory.clear();
			}
		});
		btnNewButton_24.setBackground(bgColor);
		btnNewButton_24.setForeground(fgColor);
		btnNewButton_24.setFont(font);
		
		final int[] index = {0};
		JButton btnNewButton_24_1 = new JButton("MR");
		btnNewButton_24_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(memory.size()>index[0]) {
					textField.setText(String.valueOf(memory.get(index[0])));
					index[0]++;
					if(index[0]==memory.size()) {
						index[0]=0;
					}
				}
			}
		});
		btnNewButton_24_1.setForeground(fgColor);
		btnNewButton_24_1.setBackground(bgColor);
		btnNewButton_24_1.setFont(font);
		
		JButton btnNewButton_24_2 = new JButton("M+");
		btnNewButton_24_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator()) {
					Double text = Double.parseDouble(textField.getText());
					if(!memory.contains(text)) memory.add(text);
				}
			}
		});
		btnNewButton_24_2.setForeground(fgColor);
		btnNewButton_24_2.setBackground(bgColor);
		btnNewButton_24_2.setFont(font);
		
		JButton btnNewButton_24_3 = new JButton("M-");
		btnNewButton_24_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!hasOperator()) {
					Double text = Double.parseDouble(textField.getText());
					if(memory.contains(text)) memory.remove(text);
				}
			}
		});
		btnNewButton_24_3.setForeground(fgColor);
		btnNewButton_24_3.setBackground(bgColor);
		btnNewButton_24_3.setFont(font);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_9, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_14, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_19, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(16))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_10, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_15, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_20, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(16))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_11, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_16, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_21, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(16))
				.addComponent(textField, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_8, GroupLayout.PREFERRED_SIZE, 76, Short.MAX_VALUE)
						.addComponent(btnNewButton_7, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 76, Short.MAX_VALUE)
						.addComponent(btnNewButton_24, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(btnNewButton_12, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton_17, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton_22, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnNewButton_13, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_18, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_23, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnNewButton_24_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_24_2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_24_3, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
							.addGap(16))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_24)
						.addComponent(btnNewButton_24_1)
						.addComponent(btnNewButton_24_2)
						.addComponent(btnNewButton_24_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_13, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_18, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_23, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_8, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_7, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_12, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_17, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_22, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_11, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_16, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnNewButton_10, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnNewButton_15, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton_21, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_20, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_9, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_14, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_19, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

	private Color convertStrToColor(String string) {
		// TODO Auto-generated method stub
		
		//Color List: BLACK, BLUE, CYAN, DARK_GRAY, GRAY, GREEN, LIGHT_GRAY, MAGENTA, ORANGE,
		//		      PINK, RED, WHITE, YELLOW
		
		if(!isStringUpperCase(string)) string = string.toUpperCase();
		
		switch(string) {
			case "BLACK":
				return Color.BLACK;
			case "BLUE":
				return Color.BLUE;
			case "CYAN":
				return Color.CYAN;
			case "DARK_GRAY":
				return Color.DARK_GRAY;
			case "GRAY":
				return Color.GRAY;
			case "GREEN":
				return Color.GREEN;
			case "LIGHT_GRAY":
				return Color.LIGHT_GRAY;
			case "MAGENTA":
				return Color.MAGENTA;
			case "ORANGE":
				return Color.ORANGE;
			case "PINK":
				return Color.PINK;
			case "RED":
				return Color.RED;
			case "WHITE":
				return Color.WHITE;
			case "YELLOW":
				return Color.YELLOW;
			default:
				return null;
		}
		
	}
	
	private static boolean isStringUpperCase(String str){
        
        //convert String to char array
        char[] charArray = str.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            
            //if any character is not in upper case, return false
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }
        
        return true;
    }
	
	private static final String[] operators = new String[] {"-","+","/","*","%"};
	
	private boolean hasOperator() {
		for(String operator : operators) if(textField.getText().contains(operator)) return true;
		return false;
	}
	
	private String getOperator() {
		for(String operator : operators) {
			if(textField.getText().contains(operator)) return operator;
		}
		return null;
	}
	
	private Double Equals(String text) {
		CharSequence sq = "negative";
		CharSequence sq1 = "";
		
		Double n1;
		if(textField.getText().split("\\"+getOperator())[0].contains("negative")) {
			textField.setText(textField.getText().replace(sq, sq1));
			n1 = -Double.parseDouble(textField.getText().split("\\"+getOperator())[0]);
		}else {
			n1 = Double.parseDouble(textField.getText().split("\\"+getOperator())[0]);
		}
		Double n2;
		if(textField.getText().split("\\"+getOperator())[1].contains("negative")) {
			textField.setText(textField.getText().replace(sq, sq1));
			n2 = -Double.parseDouble(textField.getText().split("\\"+getOperator())[1]);
		}else {
			n2 = Double.parseDouble(textField.getText().split("\\"+getOperator())[1]);
		}
		String op = getOperator();
		
		switch(op) {
		case "+":
			return n1+n2;
		case "-":
			return n1-n2;
		case "*":
			return n1*n2;
		case "/":
			return n1/n2;
		case "%":
			return n1%n2;
		}
		
		return null;
	}
	
	private boolean hasNumber() {
		char[] text = textField.getText().toCharArray();
		for(char c : text) {
			if(Character.isDigit(c)) return true;
		}
		return false;
	}
}










