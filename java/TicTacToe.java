
import java.util.*;

public class TicTacToe{

	static final int size = 3;
	static final float alpha = 0.5f;

	class Mark{
		static final char x='X';
		static final char o='O';
		static final char blank=0;
	}

	static class Position{
		int i,j;
		Position(int i,int j){
			this.i = i;
			this.j = j;
		}
	}


	static class Board{
		private char[][] board;
		private int lastJ,lastI,n;
		private boolean turn;
		Board(int n){
			this.n = n;
			board = new char[n][n];
			turn = new Random().nextBoolean();
		}
		Board(final Board b){
			n = b.n;
			turn = b.turn;
			board = new char[n][];
			for(int i=0;i<n;i++)
				board[i] = b.board[i].clone();
		}
		int getSize(){
			return n;
		}
		boolean getTurn(){
			return turn;
		}
		boolean isTurn(char mark){
			return (turn&&mark==Mark.x||mark==Mark.o&&!turn);
		}
		void clear(){
			turn = new Random().nextBoolean();
			for(char[] arr : board){
				Arrays.fill(arr,Mark.blank);
			}
		}
		char nextMove(){
			if(turn)
				return Mark.x;
			else
				return Mark.o;
		}
		boolean set(int i,int j,char mark){
			if(board[i][j]!=Mark.blank)
				return false;
			if(mark==Mark.x&&!turn||mark==Mark.o&&turn)
				return false;
			board[i][j] = mark;
			lastI = i;
			lastJ = j;
			turn = !turn;
			return true;
		}

		void unSet(){
			board[lastI][lastJ] = 0;
			turn = !turn;
		}
		void set(){
			board[lastI][lastJ] = nextMove();
		} 
		boolean isBlank(int i,int j){
			return board[i][j] == Mark.blank;

		}
		boolean isGameOver(){
			if(hasWon(Mark.x)||hasWon(Mark.o))
				return true;
			for(char[] arr:board)
				for(char b:arr)
					if(b==Mark.blank)
						return false;
			return true;
		}
		boolean hasWon(char mark){
			for(int i=0;i<n;i++){
				int j;
				for(j=0;j<n;j++)
					if(board[i][j]!=mark)
						break;
				if(j>=n)
					return true;
			}
			for(int i=0;i<n;i++){
				int j;
				for(j=0;j<n;j++)
					if(board[j][i]!=mark)
						break;
				if(j>=n)
					return true;
			}
			int i;
			for(i=0;i<n;i++)
				if(board[i][i]!=mark)
					break;
			if(i>=n)
				return true;
			for(i=0;i<n;i++)
				if(board[i][n-i-1]!=mark)
					break;
			if(i>=n)
				return true;
			return false;
		}
		void print(){
			for(char[] arr : board){
				System.out.print("|");
				for(char b:arr){
					if(b==0)
						b = ' ';
					System.out.print(" "+b+" |");
				}
				System.out.print("\n-");
				for(int i=0;i<n;i++)
					System.out.print("----");
				System.out.println();
			}
			// System.out.println("State : "+getState());
		}
		String getState(){
			StringBuilder sb = new StringBuilder();
			for(char[] arr : board){
				for(char b:arr){
					if(b==0)
						sb.append(' ');
					else
						sb.append(b);	
				}
			}
			return sb.toString();
		}
	}


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

		void increaseProb(){
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
						Board x=new Board(board);
						map[i][j]=findReward(x,new Position(i,j));
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
			// System.out.println("Action"+action.i+action.j);
			if(!lastState.isEmpty()){
				qMap.get(lastState)[lastAction.i][lastAction.j] = (int)(qMap.get(lastState)[lastAction.i][lastAction.j]*(1-ALPHA)+ALPHA*(lastReward+0.9*qMax));
				// System.out.println("Prev action"+lastAction.i+lastAction.j+"lastState"+lastState+"lastReward"+lastReward);
				// System.out.println(qMap.get(lastState)[lastAction.i][lastAction.j]);
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
			lastReward=findReward(new Board(board),action);
			lastState=state;
			lastAction=action;
			board.set(action.i,action.j,mark);
		}

		int findReward(Board board,Position action){
			if(!board.set(action.i,action.j,mark))
				return -200;
			if(board.hasWon(mark)){
				board.unSet();
				return 100;
			}
			for(int i=0;i<board.getSize();i++){
				for(int j=0;j<board.getSize();j++){
					if(board.set(i,j,mark2)){
						if(board.hasWon(mark2)){
							board.unSet();
							return -100;
						}
						board.unSet();
					}
				}
			}

			return 0;
		}
	}

	
	public static void main(String args[]){
		// new Test(3).testBoard();
		Board board = new Board(size);
		QPlayer x = new QPlayer(Mark.x);
		QPlayer o = new QPlayer(Mark.o);
		for(int i=0;i<500000;i++){
			board.clear();
			while(true){
				if(board.isTurn(Mark.x)){
					x.play(board);
					// board.print();
					if(board.hasWon(Mark.x)){
						System.out.println("X Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}else{
					o.play(board);
					// board.print();
					if(board.hasWon(Mark.o)){
						System.out.println("O Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}
			}
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
					board.set(h,m,board.nextMove());
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
				board.set(i,j,board.nextMove());
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
					board.set(i,j,board.nextMove());
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