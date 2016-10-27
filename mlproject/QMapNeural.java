import java.util.Arrays;

public class QMapNeural implements QMap{

	int boardSize;
	int stateSize;
	NNetwork network;

	public QMapNeural(int boardSize){
		this.boardSize = boardSize;
		stateSize = boardSize*boardSize;
		network = new NNetwork(stateSize,3,stateSize,0.7,0.9);
	}

	public double[] get(int[] state){
		double[] dState = new double[stateSize];
		for(int i=0;i<stateSize;i++)
			dState[i] = state[i]/2.0;
		double[] out = network.computeOutputs(dState);
		return out;
	}

	public void update(int[] state,Position action,double qVal){
		double[] dState = new double[stateSize];
		for(int i=0;i<stateSize;i++)
			dState[i] = ((double)state[i])/2;
		double[] out = network.computeOutputs(dState);
		out[action.i*boardSize+action.j] = qVal;
		network.calcError(out);
		network.learn();
	}
	double[] computeOutputs(double[] in){
		return network.computeOutputs(in);
	}

}
