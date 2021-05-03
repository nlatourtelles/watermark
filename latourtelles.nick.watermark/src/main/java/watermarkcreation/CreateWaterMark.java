/**
 * Nicholas La Tour-Telles
 * 5-3-21
 */
package watermarkcreation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class to create a watermark
 */
public class CreateWaterMark {

	/**
	 * Adds a watermark to an image
	 * @param theImage a BufferedImage that the watermark will be applied to
	 * @param theOpacity the opacity of the text 
	 * @param theText the text to be applied to the image
	 * @return a BufferedImage with the watermark added
	 */
	public static BufferedImage createImageWatermark(BufferedImage theImage, float theOpacity, String theText) {
		
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
	    
	    graphicObject.setPaint(Color.gray);
		
	    //write the text on the image at specific intervals
		for(int i = 0; i < imageWidth; i = i + stringWidth + 300) {
			for(int j = 0 + stringHeight; j< imageHeight; j = j + stringHeight + 200) {
				graphicObject.drawString(theText, i , j);
			}
		}
		
		
		graphicObject.dispose();
		
		return theImage;
	}
}
