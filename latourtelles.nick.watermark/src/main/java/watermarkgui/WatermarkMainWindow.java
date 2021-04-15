package watermarkgui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class WatermarkMainWindow {
	
	/**
	 * Window Frame
	 */
	private JFrame myFrame;
	
	/**
	 * Button for loading the image
	 */
	private JButton myLoadImageButton;
	
	/**
	 * Button for saving the image
	 */
	private JButton mySaveImageButton;

	public static void main(String[] Args) {
		new WatermarkMainWindow();
	}
	
	
	private WatermarkMainWindow() {
		myFrame = new JFrame("Watermark Creation");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(1000, 500);
        myFrame.setLocationRelativeTo(null);
        
        
        
        
        myFrame.setVisible(true);

	}
}
