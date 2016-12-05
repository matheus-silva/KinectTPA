import java.nio.ByteBuffer;

public class ColoridaCamera extends Camera {

	@Override
	public int[] getPixels(ByteBuffer data, int width, int height) {
		int[] pixels = new int[data.limit() / 3];
		int pos = 0;
		
		data.rewind();
		while(data.remaining() > 0){
			int red = data.get() & 0xFF;
			int green = data.get() & 0xFF;
			int blue = data.get() & 0xFF;
			
			pixels[pos] = 0xFF000000 | (red << 16) | (green << 8) | blue; 
			
			pos++;
		}
		return pixels;
	}

}
