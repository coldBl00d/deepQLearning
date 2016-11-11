import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class QPlayer{
	int mark,mark2;
	QMap qMap;
	Position lastAction;
	double lastReward=0;
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
	void setQMap(QMap map){
		qMap = map;
	}
	QMap getQMap(){
		return qMap;
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
		double qMax = -1;
		double[] qValues = qMap.get(state);
		if(qValues==null){
			qValues = new double[board.getSize()*board.getSize()];
			for(int i=0;i<board.getSize();i++)
				for(int j=0;j<board.getSize();j++){
					qValues[i*board.getSize()+j] = findReward(new Position(i,j));
				}
			qMap.update(state,qValues);
		}
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
			double[] lastQvals = qMap.get(lastState);
			double lastQval = lastQvals[lastAction.i*board.getSize()+lastAction.j];
			double newQVal = lastQval*(1-ALPHA)+ALPHA*(lastReward+GAMMA*qMax);
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

	double findReward(Position action){
		if(!board.isBlank(action))
			return -0.2;
		if(board.willWin(action)){
			return 0.1;
		}
		board.play(action.i,action.j);
		for(int i=0;i<board.getSize();i++){
			for(int j=0;j<board.getSize();j++){
				if(board.willWin(new Position(i,j))){
					board.undo();
					return -0.1;
				}
			}
		}
		board.undo();
		return 0;
	}
}