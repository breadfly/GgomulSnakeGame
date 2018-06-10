package basic;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.*;

import basic.StartPanel.MyAction;
//import java.awt.Font;

public class GameFrame extends JFrame {
	int WindowWidth = 1405; // 1400 + 5
	int WindowHeight = 935; // 900 + 35
	GameFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WindowWidth, WindowHeight);
		setTitle("≤øπ∞≤øπ∞ πÏ∞‘¿”");
		setResizable(false);
		setVisible(true);
		change("StartPanel");
	}
	
	public void change(String panelName) {
		if(panelName == "StartPanel"){
			getContentPane().removeAll();
			StartPanel p = new StartPanel(this);
			getContentPane().add(p);
			revalidate();
			repaint();
		} else if(panelName == "SettingPanel") {
			SettingPanel p = new SettingPanel(this);
			getContentPane().removeAll();
			getContentPane().add(p);
			revalidate();
			repaint();
		}
	}
	
	public void change(String panelName, GameControl c) {
		if(panelName == "GamePanel") {
			GamePanel p = new GamePanel(this, c);
			p.newGame.T.start();
			getContentPane().removeAll();
			getContentPane().add(p);
			revalidate();
			repaint();
		} else if(panelName == "PausePanel") {
			PausePanel p = new PausePanel(this, c);
			getContentPane().removeAll();
			getContentPane().add(p);
			revalidate();
			repaint();
			p.setFocusable(true);
			p.requestFocusInWindow();
		} else if(panelName == "EndPanel") {
			EndPanel p = new EndPanel(this, c);
			getContentPane().removeAll();
			getContentPane().add(p);
			revalidate();
			repaint();
		}
	}
}
