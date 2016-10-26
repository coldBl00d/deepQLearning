import java.util.Scanner;

public class TicTacToe{

	public static void main(String args[]){
		Board board = new Board(3);
		QPlayer x = new QPlayer(board,Mark.x);
		QPlayer o = new QPlayer(board,Mark.o);
		// x.setQMap(new QMapNeural(3));
		// o.setQMap(new QMapArray(3));
		int testSize = 5000000;
		for(int i=0;i<testSize;i++){
			board.clear();
			while(true){
				if(board.isTurn(Mark.x)){
					x.play();
					if(board.hasWon(Mark.x)){
						System.out.println("X Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}else{
					o.play();
					if(board.hasWon(Mark.o)){
						System.out.println("O Wins!!");
						break;
					}else if(board.isGameOver()){
						System.out.println("Draw!!");
						break;
					}
				}
			}
			if(i%(testSize/10)==0)
				QPlayer.increaseProb();
		}
		QPlayer.increaseProb();
		System.out.println();
		Test test = new Test(x);
		while(true)
			test.testQ();
	}

	static class Test{
		Board board;
		QPlayer qPlayer;
		Test(int n){
			board = new Board(n);
		}
		Test(QPlayer qPlayer){
			this.qPlayer = qPlayer;
			board = qPlayer.getBoard();
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
			QPlayer.prob=1;
			board.clear();
			Scanner scanner = new Scanner(System.in);
			while (!board.isGameOver()) {
				if(board.isTurn(Mark.x)){
					System.out.println("Player 1 : ");
					qPlayer.play();
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