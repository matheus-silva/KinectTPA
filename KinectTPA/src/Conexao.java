import java.nio.ByteBuffer;
import java.util.List;

import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;
import org.openni.SensorType;
import org.openni.VideoFrameRef;
import org.openni.VideoStream;

import com.primesense.nite.NiTE;
import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTrackerFrameRef;

public class Conexao implements VideoStream.NewFrameListener, UserTracker.NewFrameListener {
	
	static{
		System.load("C:\\Program Files\\OpenNI2\\Tools\\OpenNI2.dll");
		System.load("C:\\Program Files\\PrimeSense\\NiTE2\\Redist\\NiTE2.dll");
		
		OpenNI.initialize();
		NiTE.initialize();
	}
	
	private Camera colorida = new ColoridaCamera();
	private Camera profundidade = new ProfundidadeCamera();
	
	public Conexao(){
		List<DeviceInfo> devices = 
				OpenNI.enumerateDevices();
		Device kinect = 
				Device.open(devices.get(0).getUri());
		
		VideoStream colorida = 
				VideoStream.create(kinect, SensorType.COLOR);
		colorida.addNewFrameListener(this);
		colorida.start();
		
		UserTracker userTracker = UserTracker.create();
		userTracker.addNewFrameListener(this);
	}

	@Override
	public void onFrameReady(VideoStream stream) {
		VideoFrameRef frame = stream.readFrame();
		
		ByteBuffer data = frame.getData();
		int width = frame.getWidth();
		int height = frame.getHeight();
		
		colorida.setData(data, width, height);
		colorida.repaint();
	}

	@Override
	public void onNewFrame(UserTracker userStream) {
		UserTrackerFrameRef frameUser = userStream.readFrame();
		VideoFrameRef frame = frameUser.getDepthFrame();
		
		ByteBuffer data = frame.getData();
		int width = frame.getWidth();
		int height = frame.getHeight();
		
		profundidade.setData(data, width, height);
		profundidade.repaint();
	}
	
	public Camera getCameraColorida(){
		return colorida;
	}
	
	public Camera getCameraProfundidade(){
		return profundidade;
	}
}
