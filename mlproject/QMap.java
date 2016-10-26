

public interface QMap{
	int[] get(int[] state);
	void update(int[] lastState,Position lastAction,int qVal);
}