import java.util.Scanner;

public class TicTacToe{

	static final int size = 3;
	static final float alpha = 0.5f;

	public static void main(String args[]){
		Board board = new Board(size);
		QPlayer x = new QPlayer(board,Mark.x);
		QPlayer o = new QPlayer(board,Mark.o);
		for(int i=0;i<50000;i++){
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
					x.play();
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
			// Scanner scanner = new Scanner(System.in);
			// while (!board.isGameOver()) {
			// 	if(board.isTurn(Mark.x)){
			// 		System.out.print("Enter player "+1 + " : ");
			// 	}else
			// 		System.out.print("Enter player "+2 + " : ");
			// 	int i,j;
			// 	i = scanner.nextInt()-1;
			// 	j = scanner.nextInt()-1;
			// 	board.play(i,j);
			// 	if(board.hasWon(Mark.x)){
			// 		System.out.println("X wins");
			// 		board.print();
			// 		return;
			// 	}else if(board.hasWon(Mark.o)){
			// 		System.out.println("O wins");
			// 		board.print();
			// 		return;
			// 	}
			// 	board.print();
			// }
			// System.out.println("Draw!");
		}

		void testQ(){
			// QPlayer.prob=1;
			// board.clear();
			// qPlayer = new QPlayer(Mark.x);
			// Scanner scanner = new Scanner(System.in);
			// while (!board.isGameOver()) {
			// 	if(board.isTurn(Mark.x)){
			// 		System.out.println("Player 1 : ");
			// 		qPlayer.play(board);
			// 	}else{
			// 		System.out.print("Enter player "+2 + " : ");
			// 		int i,j;
			// 		i = scanner.nextInt()-1;
			// 		j = scanner.nextInt()-1;
			// 		board.play(i,j);
			// 	}
			// 	if(board.hasWon(Mark.x)){
			// 		System.out.println("X wins");
			// 		board.print();
			// 		return;
			// 	}else if(board.hasWon(Mark.o)){
			// 		System.out.println("O wins");
			// 		board.print();
			// 		return;
			// 	}
			// 	board.print();
			// }
			// System.out.println("Draw!");
		}

	}
}