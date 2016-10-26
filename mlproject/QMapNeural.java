import java.util.Arrays;

public class QMapNeural implements QMap{

	int boardSize;
	int stateSize;
	NNetwork network;

	public QMapNeural(int boardSize){
		this.boardSize = boardSize;
		stateSize = boardSize*boardSize;
		network = new NNetwork(stateSize,stateSize+1,stateSize,0.7,0.9);
	}

	public int[] get(int[] state){
		double[] dState = Arrays.stream(state).asDoubleStream().toArray();
		double[] out = network.computeOutputs(dState);
		int[] res = new int[stateSize];
		for(int i=0;i<stateSize;i++){
			res[i] = (int)(out[i]);
		}
		return res;
	}

	public void update(int[] state,Position action,int qVal){
		double[] dState = Arrays.stream(state).asDoubleStream().toArray();
		network.computeOutputs(dState);
		dState[action.i*boardSize+action.j] = qVal;
		network.calcError(dState);
		network.learn();
	}

}
