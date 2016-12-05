import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class ProfundidadeCamera extends Camera {

	private float[] getHistogram(ByteBuffer data) {
		float histogram[] = new float[10_000];

		data.rewind();

		int points = 0;
		while (data.remaining() > 0) {
			int prof = data.getShort() & 0xFFFF;
			if (prof != 0) {
				histogram[prof]++;
				points++;
			}
		}

		for (int i = 1; i < histogram.length; i++) {
			histogram[i] += histogram[i - 1];
		}

		if (points > 0) {
			for (int i = 1; i < histogram.length; i++) {
				histogram[i] = (int) (256 * (1.0f - (histogram[i] / (float) points)));
			}
		}
		return histogram;
	}

	
	@Override
	public int[] getPixels(ByteBuffer data, int width, int height) {
		int pixels[] = new int[data.limit()];
		int pos = 0;
		
		float histogram[] = getHistogram(data);
		
		ShortBuffer buff = data.asShortBuffer();
		
		buff.rewind();
		
		while(buff.remaining() > 0){
			int depth = buff.get();
			short pixel = (short) histogram[depth & 0xFFFF];

			pixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8) | pixel;
			
			pos++;
		}
		
		return pixels;
	}

}
