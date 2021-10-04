package Main;

import java.awt.EventQueue;

import GUI.calcFrame;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * Launches the application
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					calcFrame frame = new calcFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
