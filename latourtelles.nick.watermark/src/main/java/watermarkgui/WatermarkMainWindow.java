package watermarkgui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

public class WatermarkMainWindow {
	
	/**
	 * Window Frame
	 */
	private JFrame myFrame;
	
	/**
	 * JPanel to hold the buttons
	 */
	private JPanel myButtonPanel;
	
	/**
	 * JPanel to hold the image the user selects
	 */
	private JPanel myImagePanel;
	
	/**
	 * JLabel to display the image
	 */
	private JLabel myImageDisplay;
	
	/**
	 * BufferedImage with the input file selected by the user
	 */
	private BufferedImage myInputImage;
	
	/**
	 * JMenuBar
	 */
	private JMenuBar myMenuBar;
	
	/**
	 * Menu for load/save image
	 */
	private JMenu myFileMenu;
	
	private JMenuItem myOpenImageItem;
	
	private JMenuItem mySaveImageItem;
	
	private JMenuItem myCloseWindowItem;
	
	
	public static void main(String[] Args) {
		new WatermarkMainWindow();
	}
	
	
	private WatermarkMainWindow() {
		myInputImage = null;
		myImageDisplay = new JLabel();
		myFrame = new JFrame("Watermark Creation");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(1000, 750);
        myFrame.setLocationRelativeTo(null);
        
        myMenuBar = new JMenuBar();
        
        myFileMenu = new JMenu("File");
        
        myOpenImageItem = new JMenuItem("Open Image");
        myOpenImageItem.addActionListener(e -> 
        	loadImage()
        );
        
        mySaveImageItem = new JMenuItem("Save Image");
        
        myFileMenu.add(myOpenImageItem);
        myFileMenu.add(mySaveImageItem);
        myMenuBar.add(myFileMenu);
        myFrame.setJMenuBar(myMenuBar);
        
        
        myButtonPanel = new JPanel();
        
        myImagePanel = new JPanel();
        myImagePanel.add(myImageDisplay);
        
        myFrame.getContentPane().add(BorderLayout.SOUTH, myButtonPanel);
        myFrame.getContentPane().add(BorderLayout.CENTER, myImagePanel);
        
        myFrame.setVisible(true);

	}
	
	private void loadImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(myFrame);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			File imageFile = fileChooser.getSelectedFile();
			
			try {
				myInputImage = ImageIO.read(imageFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(myInputImage != null) {
				BufferedImage newImage = Scalr.resize(myInputImage, Scalr.Mode.AUTOMATIC, myFrame.getWidth()-100, myFrame.getHeight()-100);
				myImageDisplay.setIcon(new ImageIcon(newImage));

			}
		}
		
		
	}
	
	
	
}
