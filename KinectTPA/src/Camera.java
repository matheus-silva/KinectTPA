import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.swing.JComponent;

public abstract class Camera extends JComponent {
	
	ByteBuffer data;
	int width, height;
	
	public void setData(ByteBuffer data, int width, int height){
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public abstract int[] getPixels(ByteBuffer data, int width, int height);
	
	@Override
	public void paint(Graphics g){
		if(data == null){
			return;
		}
		int[] pixels = getPixels(data, width, height);
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, width, height, pixels, 0, width);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);		
	}
	
}
