package basic;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import basic.GameControl.Coordinate;

public class Game{
	GameControl C;
	ScoreBoard S;
	GameFrame F;
	GameThread T;
	GamePanel P;
	GameBoard rightBoard, leftBoard;
	int maxY = 26, maxX = 17;
	int fixVal = 10;
	int squareSize = 30;
	
	Game(GameFrame f, GamePanel p, GameControl c){
		C = c;
		F = f;
		P = p;
		
		if(C.playerNumber == 1) {
			C.firstDone = true;
			leftBoard = new GameBoard(0);
		}
		else leftBoard = new GameBoard(1);
		rightBoard = new GameBoard(2);
		S = new ScoreBoard();
		
		leftBoard.setBounds(65,50,530,800); // 530=30*17+20, 800=30*26+20
		rightBoard.setBounds(810, 50, 530, 800);
		p.add(leftBoard);
		p.add(rightBoard);

		T = new GameThread();
		
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"quit");
		p.getActionMap().put("quit", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){
				T.interrupt();
				F.change("EndPanel", C);
			}});
		p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P,0),"pause");
		p.getActionMap().put("pause", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){
				T.interrupt();
				F.change("PausePanel", C);
			}});
		
		if(C.playerNumber == 2) {
			leftBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0),"up_1P");
			leftBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0),"left_1P");
			leftBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0),"down_1P");
			leftBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0),"right_1P");

			leftBoard.getActionMap().put("up_1P", new AbstractAction() {
				public void actionPerformed(ActionEvent arg0){if(C.nowDir1 != 2) C.nextDir1 = 0;}});
			leftBoard.getActionMap().put("left_1P", new AbstractAction() {
				public void actionPerformed(ActionEvent arg0){if(C.nowDir1 != 3) C.nextDir1 = 1;}});
			leftBoard.getActionMap().put("down_1P", new AbstractAction() {
				public void actionPerformed(ActionEvent arg0){if(C.nowDir1 != 0) C.nextDir1 = 2;}});
			leftBoard.getActionMap().put("right_1P", new AbstractAction() {
				public void actionPerformed(ActionEvent arg0){if(C.nowDir1 != 1) C.nextDir1 = 3;}});
		}
		rightBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"up_2P");
		rightBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),"left_2P");
		rightBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),"down_2P");
		rightBoard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),"right_2P");

		rightBoard.getActionMap().put("up_2P", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){if(C.nowDir2 != 2) C.nextDir2 = 0;}});
		rightBoard.getActionMap().put("left_2P", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){if(C.nowDir2 != 3)C.nextDir2 = 1;}});
		rightBoard.getActionMap().put("down_2P", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){if(C.nowDir2 != 0)C.nextDir2 = 2;}});
		rightBoard.getActionMap().put("right_2P", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0){if(C.nowDir2 != 1)C.nextDir2 = 3;}});
	}
	
	class GameBoard extends JPanel{
		JLabel[][] comp;
		int boardType;

		GameBoard(int boardType){
			comp = new JLabel[maxX][maxY];
			for(int i=0; i<maxX; i++) {
				for(int j=0; j<maxY; j++) {
					comp[i][j] = new JLabel();
					comp[i][j].setName("empty");
					comp[i][j].setBounds(i*30+fixVal, j*30+fixVal, 30,30);
				}
			}
			this.setLayout(null);
			this.boardType = boardType;
			if(boardType == 0) {
				setLayout(null);
				JLabel newBack = new JLabel(new ImageIcon("./res/cutesnake.PNG"));
				newBack.setBounds(0,0,530,800);
				add(newBack);
				return;
			}
			
			comp[C.player[boardType].food.x][C.player[boardType].food.y].setIcon(new ImageIcon("./res/apple.PNG"));
			comp[C.player[boardType].food.x][C.player[boardType].food.y].setName("food");
			add(comp[C.player[boardType].food.x][C.player[boardType].food.y]);
			
			for(Coordinate i : C.player[boardType].userSnake) {
				comp[i.x][i.y].setIcon(new ImageIcon("./res/square_green.PNG"));
				comp[i.x][i.y].setName("snake");
				add(comp[i.x][i.y]);
			}
			
			for(int i=0; i<C.player[boardType].ggoNumber; i++) {
				for(Coordinate t : C.player[boardType].ggomulList.get(i)) {
					if(t.equals(C.player[boardType].food)) {
						comp[t.x][t.y].setIcon(new ImageIcon("./res/ggo_apple.PNG"));
						comp[t.x][t.y].setName("ggo_food");
					}
					else{
						comp[t.x][t.y].setIcon(new ImageIcon("./res/square_yellow.PNG"));
						comp[t.x][t.y].setName("ggo");
					}
					add(comp[t.x][t.y]);
				}
			}
		}
		
		void remove(Coordinate t, String removeName) {
			remove(comp[t.x][t.y]);
			String name = comp[t.x][t.y].getName();
			comp[t.x][t.y].setName("empty");
			
			if(removeName == "ggo") {
				if(name == "ggo_food" || name == "food") add(t, "food");
				else if(name == "ggo_snake") add(t, "snake");
			}
			else if(removeName == "food") {
				if(name == "ggo_food") add(t, "ggo");
			}
		}
		
		void add(Coordinate t, String addName) {
			String name = comp[t.x][t.y].getName();
			if(name == "empty") {
				if(addName == "ggo") {
					comp[t.x][t.y].setName("ggo");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/square_yellow.png"));
				}
				else if(addName == "snake") {
					comp[t.x][t.y].setName("snake");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/square_green.png"));
				}
				else if(addName == "food") {
					comp[t.x][t.y].setName("food");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/apple.png"));
				}
			}
			else if(name == "snake") {
				if(addName == "ggo") {
					comp[t.x][t.y].setName("ggo_snake");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/square_yellow.png"));
				}
			}
			else if(name == "ggo") {
				if(addName == "snake") {
					comp[t.x][t.y].setName("ggo_snake");
				}
				else if(addName == "food") {
					comp[t.x][t.y].setName("ggo_food");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/ggo_apple.png"));
				}
			}
			else if(name == "food") {
				if(addName == "ggo") {
					comp[t.x][t.y].setName("ggo_food");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/ggo_apple.png"));
				}
				else if(addName == "snake") {
					comp[t.x][t.y].setName("snake");
					comp[t.x][t.y].setIcon(new ImageIcon("./res/square_green.png"));
				}
			}
			add(comp[t.x][t.y]);
		}
		
		public void paintComponent(Graphics g) {
			ImageIcon background = null;
			if(boardType != 0) {
				if(C.player[boardType].fever > 0) background = new ImageIcon("./res/feverboard.PNG");
				else background = new ImageIcon("./res/gameboard.PNG");
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, this);
			}
		}
	}
	
	class GameThread extends Thread implements Runnable{
		public void run() {
			try {
				while(true) {
					Thread.sleep(150);
					// first snake
					if(!C.firstDone) {
						for(int i=0; i<C.player[1].ggoNumber; i++) {
							for(Coordinate co : C.player[1].ggomulList.get(i)) {
								leftBoard.remove(co, "ggo");
							}
						}
						leftBoard.remove(C.player[1].userSnake.peekLast(), "snake");
						C.player[1].moveggo();
						C.nowDir1 = C.nextDir1;
						int res = C.player[1].moveUserSnake(C.nowDir1);
						if(res == -100) C.firstDone = true;
						else {
							C.firstScore += res;
							if(C.firstScore < 0) C.firstScore = 0;
							if(res == 1 || res == 4 || res == -2) {
								leftBoard.remove(C.player[1].food, "food");
								C.player[1].createFood();
								leftBoard.add(C.player[1].food, "food");
								leftBoard.add(C.player[1].userSnake.peekFirst(), "snake");
								leftBoard.add(C.player[1].userSnake.peekLast(), "snake");
							}
							else{
								leftBoard.add(C.player[1].userSnake.peekFirst(), "snake");
							}
							for(int i=0; i<C.player[1].ggoNumber; i++) {
								for(Coordinate co : C.player[1].ggomulList.get(i)) {
									leftBoard.add(co, "ggo");
								}
							}
						}
						C.player[1].fever --;
					}
					if(!C.secondDone) {
						for(int i=0; i<C.player[2].ggoNumber; i++) {
							for(Coordinate co : C.player[2].ggomulList.get(i)) {
								rightBoard.remove(co, "ggo");
							}
						}
						rightBoard.remove(C.player[2].userSnake.peekLast(), "snake");
						C.player[2].moveggo();
						C.nowDir2 = C.nextDir2;
						int res = C.player[2].moveUserSnake(C.nowDir2);
						if(res == -100) C.secondDone = true;
						else {
							C.secondScore += res;
							if(C.secondScore < 0) C.secondScore = 0;
							if(res == 1 || res == 4 || res == -2) {
								rightBoard.remove(C.player[2].food, "food");
								C.player[2].createFood();
								rightBoard.add(C.player[2].food, "food");
								rightBoard.add(C.player[2].userSnake.peekFirst(), "snake");
								rightBoard.add(C.player[2].userSnake.peekLast(), "snake");
							}
							else{
								rightBoard.add(C.player[2].userSnake.peekFirst(), "snake");
							}
							for(int i=0; i<C.player[2].ggoNumber; i++) {
								for(Coordinate co : C.player[2].ggomulList.get(i)) {
									rightBoard.add(co, "ggo");
								}
							}
						}
						C.player[2].fever --;
					}
					if(C.firstDone && C.secondDone) {
						sleep(1000);
						F.change("EndPanel", C);
					}
					S.change();
					F.revalidate();
					F.repaint();
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ScoreBoard{
		JLabel[] firstScore, secondScore;
		ScoreBoard(){
			firstScore = new JLabel[5];
			for(int i=1; i<=4; i++) {
				firstScore[i] = new JLabel(new ImageIcon("./res/0.png"));
				firstScore[i].setBounds(580+i*40, 95, 50, 50);
				P.add(firstScore[i]);
			}
			secondScore = new JLabel[5];
			for(int i=1; i<=4; i++) {
				secondScore[i] = new JLabel(new ImageIcon("./res/0.png"));
				secondScore[i].setBounds(580+i*40, 770, 50, 50);
				P.add(secondScore[i]);
			}
		}
		void change() {
			String first = String.valueOf(C.firstScore+10000);
			for(int i=1; i<=4; i++) {
				String fileName = "./res/" + first.substring(i, i+1) + ".png";
				if(C.firstScore > 19999) fileName = "./res/9.png";
				P.remove(firstScore[i]);
				firstScore[i].setIcon(new ImageIcon(fileName));
				firstScore[i].setBounds(580+i*40, 95, 50, 50);
				P.add(firstScore[i]);
			}
			String second = String.valueOf(C.secondScore+10000);
			for(int i=1; i<=4; i++) {
				String fileName = "./res/" + second.substring(i, i+1) + ".png";
				if(C.secondScore > 19999) fileName = "./res/9.png";
				P.remove(secondScore[i]);
				secondScore[i].setIcon(new ImageIcon(fileName));
				secondScore[i].setBounds(580+i*40, 770, 50, 50);
				P.add(secondScore[i]);
			}
		}
	}
}