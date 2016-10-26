import java.util.Random;
import java.util.Arrays;

public class Board{

	private int[][] board;
	private int lastJ,lastI,n;
	private boolean turn;
	Board(int n){
		this.n = n;
		board = new int[n][n];
		turn = new Random().nextBoolean();
	}
	int getSize(){
		return n;
	}
	boolean getTurn(){
		return turn;
	}
	boolean isTurn(int mark){
		return (turn&&mark==Mark.x||mark==Mark.o&&!turn);
	}
	void clear(){
		turn = new Random().nextBoolean();
		board = new int[n][n];
	}
	char nextMove(){
		if(turn)
			return Mark.x;
		else
			return Mark.o;
	}
	boolean play(int i,int j){
		if(board[i][j]!=Mark.blank)
			return false;
		board[i][j] = nextMove();
		lastI = i;
		lastJ = j;
		turn = !turn;
		return true;
	}

	void undo(){
		board[lastI][lastJ] = 0;
		turn = !turn;
	}
	boolean isBlank(int i,int j){
		return board[i][j] == Mark.blank;

	}
	boolean isBlank(Position pos){
		return board[pos.i][pos.j] == Mark.blank;
	}
	boolean isGameOver(){
		if(hasWon())
			return true;
		for(int[] arr:board)
			for(int b:arr)
				if(b==Mark.blank)
					return false;
		return true;
	}
	boolean willWin(Position action){
		if(!isBlank(action))
			return false;
		int mark = nextMove();
		board[action.i][action.j] = mark;
		boolean willWin = false;
		if(hasWon(mark))
			willWin = true;
		board[action.i][action.j] = Mark.blank;
		return willWin;
	}
	boolean hasWon(){
		int mark = nextMove();
		mark = Mark.getOpposite(mark);
		return hasWon(mark);
	}
	boolean hasWon(int mark){
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
		for(int[] arr : board){
			System.out.print("|");
			for(int a:arr){
				char b;
				if(a==Mark.blank)
					b = ' ';
				else if(a==Mark.x)
					b = 'X';
				else
					b = 'O';
				System.out.print(" "+b+" |");
			}
			System.out.print("\n-");
			for(int i=0;i<n;i++)
				System.out.print("----");
			System.out.println();
		}
	}
	int[] getState(){
		int[] state = new int[n*n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				state[i*n+j] = board[i][j];
			}
		return state;
	}
}