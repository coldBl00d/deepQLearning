import java.util.Random;
import java.util.Arrays;

public class Board{

	private char[][] board;
	private int lastJ,lastI,n;
	private boolean turn;
	Board(int n){
		this.n = n;
		board = new char[n][n];
		turn = new Random().nextBoolean();
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
		for(char[] arr:board)
			for(char b:arr)
				if(b==Mark.blank)
					return false;
		return true;
	}
	boolean willWin(Position action){
		if(!isBlank(action))
			return false;
		char mark = nextMove();
		board[action.i][action.j] = mark;
		boolean willWin = false;
		if(hasWon(mark))
			willWin = true;
		board[action.i][action.j] = Mark.blank;
		return willWin;
	}
	boolean hasWon(){
		char mark = nextMove();
		mark = mark==Mark.x ? Mark.o : Mark.x;
		return hasWon(mark);
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
	}
	String getState(){
		StringBuilder sb = new StringBuilder();
		for(char[] arr : board){
			for(char b:arr){
				if(b==0)
					sb.append('-');
				else
					sb.append(b);	
			}
		}
		return sb.toString();
	}
}