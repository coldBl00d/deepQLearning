public interface QMap{
	double[] get(int[] state);
	void update(int[] lastState,Position lastAction,double qVal);
	void update(int[] state,double[] values);
}