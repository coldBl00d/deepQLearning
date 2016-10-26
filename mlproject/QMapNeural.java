public class QMapNeural implements QMap{

	int boardSize;

	public QMapNeural(int boardSize){
		this.boardSize = boardSize;
	}

	public int[] get(int[] state){
		return new int[boardSize*boardSize];
	}

	public void update(int[] lastState,Position lastAction,int qVal){

	}

}
