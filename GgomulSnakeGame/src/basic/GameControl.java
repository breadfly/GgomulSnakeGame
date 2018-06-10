package basic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

public class GameControl {
	int nowDir1, nowDir2, nextDir1, nextDir2;
	int firstScore, secondScore;
	boolean firstDone, secondDone;
	int maxX = 17, maxY = 26; // 0~16, 0~25
	public int playerNumber;
	UserControl[] player;
	
	public GameControl(int playerNumber, int ggoNumber){
		nowDir1 = nextDir1 = nowDir2 = nextDir2 = 0;
		firstDone = secondDone = false;
		this.playerNumber = playerNumber;
		player = new UserControl[3];
		player[2] = new UserControl(ggoNumber);
		if(playerNumber == 2) player[1] = new UserControl(ggoNumber);
	}
	
	class UserControl {
		int fever;
		Random rand;
		Deque<Coordinate> userSnake;
		int ggoNumber;
		ArrayList<Deque<Coordinate>> ggomulList;
		Coordinate food;
		
		UserControl(int ggoNumber){
			fever = 0;
			this.ggoNumber = ggoNumber;
			rand = new Random();
			
			userSnake = new ArrayDeque<Coordinate>();
			userSnake.addLast(new Coordinate(8,12));
			userSnake.addLast(new Coordinate(8,13));
			userSnake.addLast(new Coordinate(8,14));
			
			ggomulList = new ArrayList<Deque<Coordinate>>();
			for(int i=0; i<ggoNumber; i++) {
				createggo();
			}
			
			food = new Coordinate(0,0);
			createFood();
		}
		
		int collision(Coordinate head) {
			// bound collision
			if(head.x >= maxX || head.x < 0 || head.y >= maxY || head.y < 0) return -100;
			// self collision
			for(Coordinate a:userSnake) {
				if(a.equals(userSnake.peekLast())) break;
				if(a.equals(head)) return -100;
			}
			// ggo collision
			for(Deque<Coordinate> a: ggomulList) {
				for(Coordinate b:a) {
					if(b.equals(head) && head.equals(food)) { // ggo and food
						createFood();
						if(fever > 0) return 4;
						else return -2;
					}
					else if(b.equals(head)) {
						if(fever > 0) return 3;
						else return -3;
					}
				}
			}
			// food collision
			if(food.equals(head)) {
				createFood();
				fever = 10;
				return 1;
			}
			return 0;
		}
		
		void createFood() {
			while(true) {
				int nx = rand.nextInt(maxX);
				int ny = rand.nextInt(maxY);
				boolean same = false;
				for(Coordinate a:userSnake) {
					if(a.x == nx && a.y == ny) {
						same = true;
						break;
					}
				}
				if(!same) {
					food.x = nx;
					food.y = ny;
					break;
				}
			}
		}
		
		// return get point 
		int moveUserSnake(int dir) {
			Coordinate nextCoordinate = new Coordinate(userSnake.peekFirst().x, userSnake.peekFirst().y);
			if(dir==0) nextCoordinate.y = nextCoordinate.y - 1;
			else if(dir==1) nextCoordinate.x = nextCoordinate.x - 1;
			else if(dir==2) nextCoordinate.y = nextCoordinate.y + 1;
			else if(dir==3) nextCoordinate.x = nextCoordinate.x + 1;
			int score = collision(nextCoordinate);
			if(score == -100) return score;
			if(score != 1) userSnake.removeLast(); // delete rear
			userSnake.addFirst(nextCoordinate);
			return score;
		}
		
		void createggo() {
			while(true) {
				int nx = rand.nextInt(maxX-2), ny = rand.nextInt(maxY);
				Coordinate head = userSnake.peek();
				if(head.x <= nx+3 && head.x >= nx-1 && head.y <= ny+1 && head.y >= ny-1) {
					continue;
				}
				Deque<Coordinate> newggo = new ArrayDeque<Coordinate>();
				for(int i=0; i<3; i++) {
					newggo.addLast(new Coordinate(nx+i, ny));
				}
				ggomulList.add(newggo);
				break;
			}
		}
		
		void deleteggo(int idx) {
			ggomulList.remove(idx);
		}
		
		void moveggo() {
			for(int i=0; i<ggoNumber; i++) moveggo(i);
		}
		void moveggo(int idx) {
			Coordinate head = ggomulList.get(idx).peek();
			ggomulList.get(idx).removeLast();
			Coordinate headNext = ggomulList.get(idx).peekLast();
			
			boolean[] obstacle = new boolean[4];
			Arrays.fill(obstacle, Boolean.FALSE);
			if(headNext.y == head.y-1 || head.y == 0) obstacle[0]=true; //up
			if(headNext.x == head.x-1 || head.x == 0) obstacle[1]=true; //left
			if(headNext.y == head.y+1 || head.y == maxY-1) obstacle[2]=true; // down
			if(headNext.x == head.x+1 || head.x == maxX-1) obstacle[3]=true; // right
			
			while(true) {
				int dir = rand.nextInt(4);
				if(obstacle[dir]==true) continue;
				if(dir == 0) ggomulList.get(idx).addFirst(new Coordinate(head.x, head.y-1));
				if(dir == 1) ggomulList.get(idx).addFirst(new Coordinate(head.x-1, head.y));
				if(dir == 2) ggomulList.get(idx).addFirst(new Coordinate(head.x, head.y+1));
				if(dir == 3) ggomulList.get(idx).addFirst(new Coordinate(head.x+1, head.y));
				break;
			}
		}
	}

	class Coordinate{
		int x, y;
		Coordinate(int a, int b){
			x = a;
			y = b;
		}
		boolean equals(Coordinate other) {
			if(other.x == this.x && other.y == this.y) return true;
			else return false;
		}
	}
}
