package basic;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndPanel extends JPanel {
	GameFrame F;
	GameControl C;
	EndPanel(GameFrame f, GameControl c){
		setLayout(null);
		F=f;
		C=c;
	}
	public void paintComponent(Graphics g) {
		ImageIcon background = null;
		super.paintComponent(g);
		if(C.playerNumber == 1) background = new ImageIcon("./res/end_solo.PNG");
		else if(C.firstScore > C.secondScore) background = new ImageIcon("./res/end_first.PNG"); 
		else if(C.secondScore > C.firstScore) background = new ImageIcon("./res/end_second.PNG");
		else background = new ImageIcon("./res/end_equal.PNG");
		g.drawImage(background.getImage(), 0, 0, this);
		if(C.playerNumber == 1) {
			String second = String.valueOf(C.secondScore+10000);
			for(int i=1; i<=4; i++) {
				String fileName = "./res/" + second.substring(i, i+1) + ".png";
				if(C.secondScore > 19999) fileName = "./res/9.png";
				JLabel score = new JLabel(new ImageIcon(fileName));
				score.setBounds(580+i*40, 440, 50, 50);
				add(score);
			}
		}
		else {
			String first = String.valueOf(C.firstScore+10000);
			for(int i=1; i<=4; i++) {
				String fileName = "./res/" + first.substring(i, i+1) + ".png";
				if(C.firstScore > 19999) fileName = "./res/9.png";
				JLabel score = new JLabel(new ImageIcon(fileName));
				score.setBounds(250+i*40, 420, 50, 50);
				add(score);
			}
			String second = String.valueOf(C.secondScore+10000);
			for(int i=1; i<=4; i++) {
				String fileName = "./res/" + second.substring(i, i+1) + ".png";
				if(C.secondScore > 19999) fileName = "./res/9.png";
				JLabel score = new JLabel(new ImageIcon(fileName));
				score.setBounds(920+i*40, 420, 50, 50);
				add(score);
			}
		}
	}
}
