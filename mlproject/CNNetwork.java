/**
 * Neural Network
 * Feedforward Backpropagation Neural Network
 * Written in 2016 by Jayadeep KM
 *
 * This class is released under the limited GNU public
 * license (LGPL).
 *
 * @author Jayadeep KM
 * @version 1.0
 */

import java.io.Serializable;

public class CNNetwork implements Serializable{

	double learningRate;
	double momentum;
	double inputs[];
	double outputs[];
	int inputCount,outputCount,hiddenCount;

	public CNNetwork(int noInputs,int nHidden,int noOutputs,double lRate,double mom){
		learningRate = lRate;
		momentum = mom;
		inputCount = noInputs;
		outputCount = noOutputs;
		hiddenCount = nHidden;
		inputs = new double[inputCount+1];
		outputs = new double[outputCount];
	}

	double[] computeOutputs(double[] in){
		return null;
	}

	double[] calcError(double[] out){
		return null;
	}

	void learn(){

	}
}