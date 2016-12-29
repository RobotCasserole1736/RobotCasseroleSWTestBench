package org.usfirst.frc.team1736.lib.SignalMath.Test;

import org.usfirst.frc.team1736.lib.SignalMath.AveragingFilter;
import org.usfirst.frc.team1736.lib.SignalMath.DerivativeCalculator;
import org.usfirst.frc.team1736.lib.SignalMath.FIRFilter;
import org.usfirst.frc.team1736.lib.SignalMath.IntegralCalculator;
import org.usfirst.frc.team1736.lib.SignalMath.MedianFilter;

public class UnitTestMain {
    
    public static final double Ts_s = 0.020; //20ms sample rate 

    /* DUT classes - filters*/
    AveragingFilter avgFiltTest1;
    MedianFilter    medFiltTest1;
    FIRFilter       firFiltTest1;
    FIRFilter       firFiltTest2;
    FIRFilter       firFiltTest3;
    
    DerivativeCalculator derivCalcTest1;
    IntegralCalculator   integCalcTest1;
    
    
    public static void main(String[] args) {
        System.out.println("Starting test of signal math operations");

    }
    
    
    private double f_step(double time_step, final double step_time_s){
        if((time_step * Ts_s) >= step_time_s){
            return 1;
        } else {
            return 0;
        }
    }
    
    private double f_kroneckerdelta(double time_step, final double delta_time_step){
        if( (time_step) == delta_time_step){
            return 1;
        } else {
            return 0;
        }
    }
    
    private double f_sine(double time_step, double freq_hz, double phase_delay){
        return Math.sin(2*Math.PI*freq_hz*((time_step*Ts_s)+phase_delay));
    }
    private double f_cosine(double time_step, double freq_hz, double phase_delay){
        return Math.cos(2*Math.PI*freq_hz*((time_step*Ts_s)+phase_delay));
    }

}
