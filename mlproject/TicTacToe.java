import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class TicTacToe{

	static final int size = 3;
	static final float alpha = 0.5f;

	static class QPlayer{
		char mark,mark2;
		Map<String,int[][]> qMap;
		Position lastAction;
		int lastReward=0;
		String lastState="";
		static double prob = 0.5;
		static final double ALPHA = 0.5;

		QPlayer(char mark){
			this.mark = mark;
			this.mark2 = mark==Mark.x ? Mark.o : Mark.x;
			qMap = new HashMap<String,int[][]>();
		}

		boolean chooseToPlay(){
			return new Random().nextDouble() < prob;
		}

		static void increaseProb(){
			if(prob<1)
				prob+=0.1;
		}

		void play(Board board){
			String state = board.getState();
			Position action = new Position(0,0);
			Random random = new Random();
			if(!qMap.containsKey(state)){
				int[][] map = new int[board.getSize()][board.getSize()];
				for(int i=0;i<board.getSize();i++)
					for(int j=0;j<board.getSize();j++){
						map[i][j]=findReward(board,new Position(i,j));
					}
				qMap.put(state,map);
			}
			int qMax = -500;
			int[][] qValues = qMap.get(state);
			for(int i=0;i<board.getSize();i++){
				for(int j=0;j<board.getSize();j++){
					if(board.isBlank(i,j)){
						if(qValues[i][j]>qMax){
							qMax = qValues[i][j];
							action = new Position(i,j);
						}
					}
				}
			}
			if(!lastState.isEmpty()){
				qMap.get(lastState)[lastAction.i][lastAction.j] = (int)(qMap.get(lastState)[lastAction.i][lastAction.j]*(1-ALPHA)+ALPHA*(lastReward+0.9*qMax));
			}
			if(!chooseToPlay()){
				int l = random.nextInt(board.getSize());
				int m = random.nextInt(board.getSize());
				while(!board.isBlank(l,m)){
					l = random.nextInt(board.getSize());
					m = random.nextInt(board.getSize());
				}
				action = new Position(l,m);
			}
			lastReward=findReward(board,action);
			lastState=state;
			lastAction=action;
			board.play(action.i,action.j);
		}

		int findReward(Board board,Position action){
			if(!board.isBlank(action))
				return -200;
			if(board.willWin(action)){
				return 100;
			}
			board.play(action.i,action.j);
			for(int i=0;i<board.getSize();i++){
				for(int j=0;j<board.getSize();j++){
					if(board.willWin(new Position(i,j))){
						board.undo();
						return -100;
					}
				}
			}
			board.undo();
			return 0;
		}
	}

	
	public static void main(String args[]){
		Board board = new Board(size);
		QPlayer x = new QPlayer(Mark.x);
		QPlayer o = new QPlayer(Mark.o);
		for(int i=0;i<50000;i++){
			board.clear();
			while(true){
				if(board.isTurn(Mark.x)){
					x.play(board);
					if(board.hasWon(Mark.x)){
						System.out.println("X Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}else{
					o.play(board);
					if(board.hasWon(Mark.o)){
						System.out.println("O Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}
			}
			if(i%5000==0)
				QPlayer.increaseProb();
		}
		System.out.println();
		QPlayer.prob=1.0;
		Scanner scanner = new Scanner(System.in);
		for(int i=0;i<10;i++){
			board.clear();
			while(true){
				if(board.isTurn(Mark.x)){
					x.play(board);
					board.print();
					System.out.println();
					if(board.hasWon(Mark.x)){
						System.out.println("X Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}else{
					int h,m;
					h = scanner.nextInt()-1;
					m = scanner.nextInt()-1;
					board.play(h,m);
					board.print();
					System.out.println();
				}
			}
		}
		Test test = new Test(x,3);
		while(true)
			test.testQ();
	}

	static class Test{
		Board board;
		QPlayer qPlayer;
		Test(int n){
			board = new Board(n);
		}
		Test(QPlayer qPlayer,int n){
			this.qPlayer = qPlayer;
			board = new Board(n);
		}
		void testBoard(){
			Scanner scanner = new Scanner(System.in);
			while (!board.isGameOver()) {
				if(board.isTurn(Mark.x)){
					System.out.print("Enter player "+1 + " : ");
				}else
					System.out.print("Enter player "+2 + " : ");
				int i,j;
				i = scanner.nextInt()-1;
				j = scanner.nextInt()-1;
				board.play(i,j);
				if(board.hasWon(Mark.x)){
					System.out.println("X wins");
					board.print();
					return;
				}else if(board.hasWon(Mark.o)){
					System.out.println("O wins");
					board.print();
					return;
				}
				board.print();
			}
			System.out.println("Draw!");
		}

		void testQ(){
			// QPlayer.prob=1;
			board.clear();
			qPlayer = new QPlayer(Mark.x);
			Scanner scanner = new Scanner(System.in);
			while (!board.isGameOver()) {
				if(board.isTurn(Mark.x)){
					System.out.println("Player 1 : ");
					qPlayer.play(board);
				}else{
					System.out.print("Enter player "+2 + " : ");
					int i,j;
					i = scanner.nextInt()-1;
					j = scanner.nextInt()-1;
					board.play(i,j);
				}
				if(board.hasWon(Mark.x)){
					System.out.println("X wins");
					board.print();
					return;
				}else if(board.hasWon(Mark.o)){
					System.out.println("O wins");
					board.print();
					return;
				}
				board.print();
			}
			System.out.println("Draw!");
		}

	}
}