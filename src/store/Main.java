package store;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
	
		//Initialize the UIManager
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UIManager();
            }
        });

		
	}
}
