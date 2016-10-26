import java.util.Map;
import java.util.HashMap;

public class QMapArray implements QMap{

	private Map<Integer,double[]> qTable;
	int n;

	public QMapArray(int n){
		qTable = new HashMap<Integer,double[]>();
		this.n = n;
	}

	private static int getKey(int[] state){
		int key = 0;
		for(int v:state)
			key = key*10+v;
		return key;
	}

	public double[] get(int[] state){
		int key = getKey(state);
		if(!qTable.containsKey(key)){
			double[] map = new double[n*n];
			for(int i=0;i<n*n;i++)
				map[i] = 0;
			qTable.put(key,map);
		}
		return qTable.get(key);
	}

	public void update(int[] lastState,Position lastAction,double qVal){
		double[] values = get(lastState);
		values[lastAction.i*n+lastAction.j] = qVal;
	}

}