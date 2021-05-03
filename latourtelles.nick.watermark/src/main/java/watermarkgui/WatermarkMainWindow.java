package watermarkgui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import watermarkcreation.CreateWaterMark;

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
	
	/**
	 * JMenuItem for selecting an image
	 */
	private JMenuItem myOpenImageItem;
	
	/**
	 * JMenuItem for saving an image
	 */
	private JMenuItem mySaveImageItem;
	
	/**
	 * The text field for the user input
	 */
	private JTextField myWatermarkText;
	
	/**
	 * Slider for the opacity of the watermark
	 */
	private JSlider myOpacitySlider;
	
	/**
	 * Button to call the createImageWatermark method
	 */
	private JButton myCreateWatermarkButton;
	
	/**
	 * Holds the BufferedImage that has a watermark added
	 */
	private BufferedImage myWatermarkedImage;
	
	private String myImageExtension;
	
	public static void main(String[] Args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new WatermarkMainWindow();
				
			}
		});
	}
	
	
	public WatermarkMainWindow() {
		myInputImage = null;
		myImageDisplay = new JLabel();
		myFrame = new JFrame("Watermark Creation");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(1000, 750);
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(false);
        
        myMenuBar = new JMenuBar();
        
        myFileMenu = new JMenu("File");
        
        myOpenImageItem = new JMenuItem("Open Image");
        myOpenImageItem.addActionListener(e -> 
        	loadImage()
        );
        
        mySaveImageItem = new JMenuItem("Save Image");
        mySaveImageItem.addActionListener(e -> 
        	saveImage()
        );
        
        myFileMenu.add(myOpenImageItem);
        myFileMenu.add(mySaveImageItem);
        myMenuBar.add(myFileMenu);
        myFrame.setJMenuBar(myMenuBar);
        
        
        myButtonPanel = new JPanel();
        myButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        
        JLabel enterTextJLabel = new JLabel("Enter the text");
        myButtonPanel.add(enterTextJLabel);
        
        
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(10,0,0,0);
        
        myWatermarkText = new JTextField(30);
        myWatermarkText.setEditable(true);
        myWatermarkText.setMaximumSize(myWatermarkText.getPreferredSize());
        myButtonPanel.add(myWatermarkText, constraint);
        
        JLabel opacityLabel = new JLabel("Opacity slider (0 is invisible)");
        constraint.gridy = 2;
        myButtonPanel.add(opacityLabel,constraint);
        
        myOpacitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        myOpacitySlider.setMajorTickSpacing(20);
        myOpacitySlider.setPaintTicks(true);
        myOpacitySlider.setPaintLabels(true);
        constraint.gridy = 3;
        constraint.insets = new Insets(0, 0, 0, 0);
        
        myButtonPanel.add(myOpacitySlider, constraint);
        
        myCreateWatermarkButton = new JButton("Create Watermark");
        myCreateWatermarkButton.addActionListener(e -> 
        	addWatermark()
        );
        constraint.gridy = 4;
        constraint.insets = new Insets(10, 0, 10, 0);
        
        myButtonPanel.add(myCreateWatermarkButton, constraint);
        
        myImagePanel = new JPanel();
        myImagePanel.add(myImageDisplay);
        
        myFrame.getContentPane().add(BorderLayout.SOUTH, myButtonPanel);
        myFrame.getContentPane().add(BorderLayout.CENTER, myImagePanel);
        
        myFrame.setVisible(true);

	}
	
	/**
	 * Method to allow a user to choose the image they want to add the watermark to
	 */
	private void loadImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(myFrame);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			File imageFile = fileChooser.getSelectedFile();
			myImageExtension =  FilenameUtils.getExtension(imageFile.toString());
			
			
			try {
				myInputImage = ImageIO.read(imageFile);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(myFrame, "Something went wrong loading the image. Please try again");
			}
			
			if(myInputImage != null) {
				setLabelImage(myInputImage);
			}
		}
		
		
	}
	
	/**
	 * Method to save the image with the watermark on it
	 */
	private void saveImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int results = fileChooser.showSaveDialog(fileChooser);
		
		if(results == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			
			try {
				ImageIO.write(myWatermarkedImage, myImageExtension, saveFile);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(myFrame, "Something went wrong saving the image. Please try again");
			}
		}
		
	}
	
	/**
	 * Method to call the createImageWatermark method from CreateWaterMark.java class
	 */
	private void addWatermark() {
		if(myInputImage == null) {
			JOptionPane.showMessageDialog(myFrame, "Please Select an Image");
		} else if (myWatermarkText.getText().length() <= 0){
			JOptionPane.showMessageDialog(myFrame, "Please Enter Text");
		} else {
			CreateWaterMark cwm = new CreateWaterMark();
			String textString = myWatermarkText.getText();
			int opacityAmt = myOpacitySlider.getValue();
			
			myWatermarkedImage = cwm.createImageWatermark(deepCopyBufferedImage(myInputImage), opacityAmt, textString);
			setLabelImage(myWatermarkedImage);
		}
	}
	
	/**
	 * Scales the BufferedImage to fit on the screen and sets the myImageDisplay 
	 * JLabel icon the the scaled image
	 * @param theNewIconImage the new BufferedImage that is to be displayed on the screen
	 */
	private void setLabelImage(BufferedImage theNewIconImage) {
		BufferedImage newImage;
		
		if(theNewIconImage.getHeight() >= theNewIconImage.getWidth()) {
			newImage = Scalr.resize(theNewIconImage, Scalr.Mode.FIT_TO_HEIGHT, 
					myImagePanel.getWidth()-100, myImagePanel.getHeight()-100);
		} else {
			newImage = Scalr.resize(theNewIconImage, Scalr.Mode.FIT_TO_WIDTH, 
					myImagePanel.getWidth()-100, myImagePanel.getHeight()-100);
		}
		
		myImageDisplay.setIcon(new ImageIcon(newImage));
	}
	
	/**
	 * Creates a deep copy of a BufferedImage
	 * @param theImage The BufferedImage that you want copied
	 * @return a copy of theImage
	 */
	private BufferedImage deepCopyBufferedImage(BufferedImage theImage) {
		ColorModel newColorModel =  theImage.getColorModel();
		Boolean newIsAlpha = theImage.isAlphaPremultiplied();
		WritableRaster raster = theImage.copyData(null);
		
		return new BufferedImage(newColorModel, raster, newIsAlpha, null);
	}
	
}
