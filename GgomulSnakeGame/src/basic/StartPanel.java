package basic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class StartPanel extends JPanel {
	GameFrame F;
	StartPanel(GameFrame f){
		F=f;
		setLayout(null);
		//new ImageIcon("./res/game_start.PNG")
		JButton startButton = new JButton(new ImageIcon("./res/game_start.PNG"));
		startButton.setBounds(830,580,450,225);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		add(startButton);
		startButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"pressed");
		startButton.getActionMap().put("pressed", new MyAction());
		startButton.addActionListener(new MyAction());
	}
	
	class MyAction extends AbstractAction implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			F.change("SettingPanel");
		}
	}
	
	public void paintComponent(Graphics g) {
		ImageIcon background = new ImageIcon("./res/title.PNG");
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, this);
	}
}
