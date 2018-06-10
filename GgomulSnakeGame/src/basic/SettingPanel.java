package basic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import basic.StartPanel.MyAction;

public class SettingPanel extends JPanel {
	JRadioButton mode1, mode2, mode3;
	GameFrame F;
	SettingPanel(GameFrame f){
		F = f;
		setLayout(null);
		JButton OKBtn = new JButton(new ImageIcon("./res/ok.PNG"));
		
		mode1 = new JRadioButton(new ImageIcon("./res/1no_basic.PNG")); // solo, no ggo
		mode2 = new JRadioButton(new ImageIcon("./res/1ggo_basic.PNG")); // solo, yes ggo
		mode3 = new JRadioButton(new ImageIcon("./res/2ggo_basic.PNG")); // multi
		mode1.setSelectedIcon(new ImageIcon("./res/1no_selected.PNG"));
		mode2.setSelectedIcon(new ImageIcon("./res/1ggo_selected.PNG"));
		mode3.setSelectedIcon(new ImageIcon("./res/2ggo_selected.PNG"));

		mode1.setSelected(true);
		
		OKBtn.setBorderPainted(false);
		OKBtn.setContentAreaFilled(false);
		OKBtn.setFocusPainted(false);
		OKBtn.setBounds(1000,550,300,250);
		mode1.setBorderPainted(false);
		mode1.setContentAreaFilled(false);
		mode1.setFocusPainted(false);
		mode1.setBounds(490,230,400,150);
		mode2.setBorderPainted(false);
		mode2.setContentAreaFilled(false);
		mode2.setFocusPainted(false);
		mode2.setBounds(490,430,400,150);
		mode3.setBorderPainted(false);
		mode3.setContentAreaFilled(false);
		mode3.setFocusPainted(false);
		mode3.setBounds(490,630,400,150);
		
		ButtonGroup mode = new ButtonGroup();
		mode.add(mode1);
		mode.add(mode2);
		mode.add(mode3);

		add(OKBtn);
		add(mode1);
		add(mode2);
		add(mode3);
		// 익명클래스
		mode1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),"down");
		mode1.getActionMap().put("down", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){
				if(mode1.isSelected()==true) mode2.setSelected(true);
				else if(mode2.isSelected()==true) mode3.setSelected(true);}
			});
		mode1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"up");
		mode1.getActionMap().put("up", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){
				if(mode2.isSelected()==true) mode1.setSelected(true);
				else if(mode3.isSelected()==true) mode2.setSelected(true);}
			});
		
		OKBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"),"next");
		OKBtn.getActionMap().put("next", new MyAction());
		OKBtn.addActionListener(new MyAction());
	}
	
	void pageTurn() {
		int playerNumber=0, ggoNumber=0;
		if(mode1.isSelected()) {
			playerNumber = 1;
			ggoNumber = 0;
		}
		else if(mode2.isSelected()) {
			playerNumber = 1;
			ggoNumber = 2;
		}
		else if(mode3.isSelected()) {
			playerNumber = 2;
			ggoNumber = 2;
		}

		GameControl c = new GameControl(playerNumber, ggoNumber);
		F.change("GamePanel", c);
	}
	
	class MyAction extends AbstractAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			pageTurn();
		}
	}

	public void paintComponent(Graphics g) {
		ImageIcon background = new ImageIcon("./res/setting.PNG");
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, this);
	}

	class MyKeyListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				pageTurn();
			}
		}
		
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
