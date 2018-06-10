package basic;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel{
	GameFrame F;
	GameControl C;
	Game newGame;
	GamePanel(GameFrame f, GameControl c){
		F=f;
		C=c;
		
		setLayout(null);
		JLabel firstScore = new JLabel(new ImageIcon("./res/1Pscore.PNG"));
		JLabel secondScore = new JLabel(new ImageIcon("./res/2Pscore.PNG"));
		JLabel explain = new JLabel(new ImageIcon("./res/explain.PNG"));
		
		firstScore.setBounds(605, 27, 200, 100);
		explain.setBounds(605, 180, 200, 500);
		secondScore.setBounds(605, 700, 200, 100);
		newGame = new Game(f, this, c);
		add(firstScore);
		add(secondScore);
		add(explain);
	}
	public void paintComponent(Graphics g) {
		ImageIcon background = new ImageIcon("./res/empty.PNG");
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, this);
	}
}
