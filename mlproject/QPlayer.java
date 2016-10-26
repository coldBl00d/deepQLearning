import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class QPlayer{
	int mark,mark2;
	QMap qMap;
	Position lastAction;
	int lastReward=0;
	int[] lastState;
	static double prob = 0.5;
	static final double ALPHA = 0.5;
	static final double GAMMA = 0.9;
	Board board;

	QPlayer(Board board,int mark){
		this.mark = mark;
		this.mark2 = Mark.getOpposite(mark);
		// qMap = new HashMap<String,int[][]>();
		// qMap = new QMapArray(board.getSize());
		qMap = new QMapNeural(board.getSize());
		this.board = board;
	}
	Board getBoard(){
		return board;
	}
	boolean chooseToPlay(){
		return new Random().nextDouble() < prob;
	}

	static void increaseProb(){
		if(prob<1)
			prob+=0.1;
	}

	void play(){
		int[] state = board.getState();
		Position action = new Position();
		Random random = new Random();
		int qMax = -500;
		int[] qValues = qMap.get(state);
		for(int i=0;i<board.getSize();i++){
			for(int j=0;j<board.getSize();j++){
				if(board.isBlank(i,j)){
					if(qValues[i*board.getSize()+j]>qMax){
						qMax = qValues[i*board.getSize()+j];
						action = new Position(i,j);
					}
				}
			}
		}

		if(lastState!=null){
			int[] lastQvals = qMap.get(lastState);
			int lastQval = lastQvals[lastAction.i*board.getSize()+lastAction.j];
			int newQVal = (int) (lastQval*(1-ALPHA)+ALPHA*(lastReward+GAMMA*qMax));
			qMap.update(lastState,lastAction,newQVal);
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
		lastReward=findReward(action);
		lastState=state;
		lastAction=action;
		board.play(action.i,action.j);
	}

	int findReward(Position action){
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