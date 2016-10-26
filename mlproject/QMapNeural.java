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

	public double[] get(int[] state){
		double[] dState = Arrays.stream(state).asDoubleStream().toArray();
		double[] out = network.computeOutputs(dState);
		return out;
	}

	public void update(int[] state,Position action,double qVal){
		double[] dState = Arrays.stream(state).asDoubleStream().toArray();
		network.computeOutputs(dState);
		dState[action.i*boardSize+action.j] = qVal;
		network.calcError(dState);
		network.learn();
	}

}
