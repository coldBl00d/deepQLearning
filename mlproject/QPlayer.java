import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class QPlayer{
	char mark,mark2;
	Map<String,int[][]> qMap;
	Position lastAction;
	int lastReward=0;
	String lastState="";
	static double prob = 0.5;
	static final double ALPHA = 0.5;
	Board board;

	QPlayer(Board board,char mark){
		this.mark = mark;
		this.mark2 = mark==Mark.x ? Mark.o : Mark.x;
		qMap = new HashMap<String,int[][]>();
		this.board = board;
	}

	boolean chooseToPlay(){
		return new Random().nextDouble() < prob;
	}

	static void increaseProb(){
		if(prob<1)
			prob+=0.1;
	}

	void play(){
		String state = board.getState();
		Position action = new Position(0,0);
		Random random = new Random();
		if(!qMap.containsKey(state)){
			int[][] map = new int[board.getSize()][board.getSize()];
			for(int i=0;i<board.getSize();i++)
				for(int j=0;j<board.getSize();j++){
					map[i][j]=findReward(new Position(i,j));
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