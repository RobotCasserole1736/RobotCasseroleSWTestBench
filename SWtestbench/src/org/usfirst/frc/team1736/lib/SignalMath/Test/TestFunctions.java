package org.usfirst.frc.team1736.lib.SignalMath.Test;

import java.util.Random;

public class TestFunctions {
	
	  
    public static double f_step(double time_step, final double step_time_s){
        if((time_step * TestTimeVector.Ts_s) >= step_time_s){
            return 1;
        } else {
            return 0;
        }
    }
    
    public static double f_kroneckerdelta(double time_step, final double delta_time_step){
        if( (time_step) == delta_time_step){
            return 1;
        } else {
            return 0;
        }
    }
    
    public static double f_sine(double time_step, double freq_hz, double phase_delay){
        return Math.sin(2*Math.PI*freq_hz*((time_step*TestTimeVector.Ts_s)+phase_delay));
    }
    public static double f_cosine(double time_step, double freq_hz, double phase_delay){
        return Math.cos(2*Math.PI*freq_hz*((time_step*TestTimeVector.Ts_s)+phase_delay));
    }
    
    public static double f_gaussianNoise(double time_step, double noise_amp_pkpk, double offset){
        return (Math.random()-0.5)*2*noise_amp_pkpk + offset;
    }

}
