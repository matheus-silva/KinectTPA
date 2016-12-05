import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Janela extends JFrame {
	
	public Janela(String titulo){
		super(titulo);
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setCamera(Camera camera){
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(1, 0));
		c.add(camera);
		c.revalidate();
		c.repaint();
	}
	
	public static void main(String args[]){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Janela colorida = new Janela("Colorida");
				Janela profundidade = new Janela("Profundidade");
				
				Conexao con = new Conexao();
				Camera cameraColorida = con.getCameraColorida();
				Camera cameraProf = con.getCameraProfundidade();
				
				colorida.setCamera(cameraColorida);
				profundidade.setCamera(cameraProf);
				
				colorida.setVisible(true);
				profundidade.setVisible(true);
			}
		});
	}
	
	
}
