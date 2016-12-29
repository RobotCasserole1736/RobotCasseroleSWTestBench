package org.usfirst.frc.team1736.lib.SignalMath.Test;

public class TestTimeVector {
	
    public final static double Ts_s = 0.020; //20ms sample rate, fixed
    
    int numSamples;
    double[] time_vector;
    
    public TestTimeVector(double simLength_s_in){
    	setupTimeVector(simLength_s_in);
    }
    
    private void setupTimeVector(double simLength_s){
    	numSamples = (int)Math.ceil(simLength_s/Ts_s);
    	time_vector = new double[numSamples];
    	
    	for(int i = 0; i < numSamples; i++){
    		time_vector[i] = i * Ts_s;
    	}
    	
    }

}
