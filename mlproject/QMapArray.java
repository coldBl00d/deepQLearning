import java.util.Map;
import java.util.HashMap;

public class QMapArray implements QMap{

	private Map<Integer,int[]> qTable;
	int n;

	public QMapArray(int n){
		qTable = new HashMap<Integer,int[]>();
		this.n = n;
	}

	private static int getKey(int[] state){
		int key = 0;
		for(int v:state)
			key = key*10+v;
		return key;
	}

	public int[] get(int[] state){
		int key = getKey(state);
		if(!qTable.containsKey(key)){
			int[] map = new int[n*n];
			for(int i=0;i<n*n;i++)
				map[i] = 0;
			qTable.put(key,map);
		}
		return qTable.get(key);
	}

	public void update(int[] lastState,Position lastAction,int qVal){
		int[] values = get(lastState);
		values[lastAction.i*n+lastAction.j] = qVal;
	}

}