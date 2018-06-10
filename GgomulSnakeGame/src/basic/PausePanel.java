package basic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PausePanel extends JPanel {
	// TO-DO
	// music play or not
	GameFrame F;
	GameControl C;
	PausePanel(GameFrame f, GameControl c){
		F=f;
		C=c;
		addKeyListener(new MyKeyListener());
	}
	
	public void paintComponent(Graphics g) {
		ImageIcon background = new ImageIcon("./res/pause.PNG");
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, this);
	}
	
	class MyKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
				F.change("GamePanel", C);
			}
		}
		
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
