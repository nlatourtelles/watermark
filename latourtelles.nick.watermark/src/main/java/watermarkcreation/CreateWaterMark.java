package watermarkcreation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreateWaterMark {

	
	public CreateWaterMark() {
		
	}
	
	/**
	 * Adds a watermark to an image
	 * @param theImage a BufferedImage 
	 * @param theOpacity
	 * @param theText
	 * @return
	 */
	public BufferedImage createImageWatermark(BufferedImage theImage, float theOpacity, String theText) {
		
		int imageWidth = theImage.getWidth();
		int imageHeight = theImage.getHeight();
		
		float opacityModifier = theOpacity/100;

		
		Graphics2D graphicObject = theImage.createGraphics();
		
		Font textFont = new Font("TimesRoman", Font.BOLD, 50);
		
		Composite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacityModifier);
		
		graphicObject.setComposite(newComposite);
		
		graphicObject.setFont(textFont);
		
		FontMetrics fontMetrics = graphicObject.getFontMetrics();
	    int stringWidth = fontMetrics.stringWidth(theText);
	    int stringHeight = fontMetrics.getAscent();
		
	   // graphicObject.rotate(-Math.toRadians(45));
	    
	    graphicObject.setPaint(Color.gray);
	    
	    //graphicObject.drawString(theText, (imageWidth - stringWidth)/2 , imageHeight / 2 + stringHeight / 4);
		
		for(int i = 0; i < imageWidth; i = i + stringWidth + 300) {
			for(int j = 0 + stringHeight; j< imageHeight; j = j + stringHeight + 200) {
				graphicObject.drawString(theText, i , j);
			}
		}
		
		
		graphicObject.dispose();
		
		return theImage;
	}
}
