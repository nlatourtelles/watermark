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
	
	public BufferedImage createImageWatermark() {
		File imageFile = new File("C:\\Users\\Nick PC\\Desktop\\test.jpeg");
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(imageFile);
		} catch (Exception e) {
			System.out.println("File not found");
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		Graphics2D graphicObject = image.createGraphics();
		
		Font textFont = new Font("TimesRoman", Font.BOLD, 100);
		
		Composite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f);
		
		graphicObject.setComposite(newComposite);
		
		graphicObject.setFont(textFont);
		String messageString = "Test Watermark";
		
		FontMetrics fontMetrics = graphicObject.getFontMetrics();
	    int stringWidth = fontMetrics.stringWidth(messageString);
	    int stringHeight = fontMetrics.getAscent();
		
	    graphicObject.setPaint(Color.black);
	    graphicObject.drawString(messageString, (width - stringWidth)/2 , height / 2 + stringHeight / 4);
		
		
		
		
		graphicObject.dispose();
		
		return image;
	}
}
