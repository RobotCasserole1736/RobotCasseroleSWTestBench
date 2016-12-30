package org.usfirst.frc.team1736.lib.SignalMath.Test;


import org.usfirst.frc.team1736.lib.SignalMath.FIRFilter;
import org.usfirst.frc.team1736.lib.SignalMath.FilterType;
import org.usfirst.frc.team1736.lib.SignalMath.MedianFilter;

import java.awt.Color;

import org.usfirst.frc.team1736.lib.FalconPathPlanner.FalconLinePlot;

public class FIRFiltFreqSweepTest {

    // DUT classes - filters
    static FIRFilter FIRFiltTest1;
    static FIRFilter FIRFiltTest2;
    static FIRFilter FIRFiltTest3;
    static FIRFilter FIRFiltTest4;
    static FIRFilter FIRFiltTest5;
    static FIRFilter FIRFiltTest6;

    
    //Testcase plot results
    static FalconLinePlot outputPlot;
    
    //Data vectors
    static TestTimeVector t;
    static double[] inputSignal1;
    static double[] inputSignal2;
    static double[] inputSignal3;
    static double[] inputSignal4;
    static double[] inputSignal5;
    static double[] inputSignal6;
    
    static double[] outputSignal1;
    static double[] outputSignal2;
    static double[] outputSignal3;
    static double[] outputSignal4;
    static double[] outputSignal5;
    static double[] outputSignal6;

    

    
    
    public static void main(String[] args) throws InterruptedException {
        
        FilterType chosenType = FilterType.LOWPASS_2HZ_FAST;
        
    	System.out.println("Setting up test FIR filter objects");
        //Set up DUT casses with a variety of inputs
        FIRFiltTest1 = new FIRFilter(chosenType);
        FIRFiltTest2 = new FIRFilter(chosenType);
        FIRFiltTest3 = new FIRFilter(chosenType);
        FIRFiltTest4 = new FIRFilter(chosenType);
        FIRFiltTest5 = new FIRFilter(chosenType);
        FIRFiltTest6 = new FIRFilter(chosenType);
    	
    	
    	System.out.println("Starting test of FIR freq sweep");
        
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 1 - swept frequency input
        ///////////////////////////////////////////////////////////////////////
        //Initialize timebase vectors & data
        t = new TestTimeVector(5);
        
        
        //Allocate space for IO signals
        inputSignal1 = new double[t.numSamples];
        inputSignal2 = new double[t.numSamples];
        inputSignal3 = new double[t.numSamples];
        inputSignal4 = new double[t.numSamples];
        inputSignal5 = new double[t.numSamples];
        inputSignal6 = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        outputSignal5 = new double[t.numSamples];
        outputSignal6 = new double[t.numSamples];
        
        //Reset filters in prep for test
        FIRFiltTest1.reset();
        FIRFiltTest2.reset();
        FIRFiltTest3.reset();
        FIRFiltTest4.reset();
        FIRFiltTest5.reset();
        FIRFiltTest6.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
            inputSignal1[i] = TestFunctions.f_sine(i, 1, 0);
            inputSignal2[i] = TestFunctions.f_sine(i, 2, 0);
            inputSignal3[i] = TestFunctions.f_sine(i, 5, 0);
            inputSignal4[i] = TestFunctions.f_sine(i, 10, 0);
            inputSignal5[i] = TestFunctions.f_sine(i, 15, 0);
            inputSignal6[i] = TestFunctions.f_sine(i, 20, 0);
            
        	FIRFiltTest1.setInput(inputSignal1[i]);
        	FIRFiltTest2.setInput(inputSignal2[i]);
        	FIRFiltTest3.setInput(inputSignal3[i]);
        	FIRFiltTest4.setInput(inputSignal4[i]);
        	FIRFiltTest5.setInput(inputSignal5[i]);
        	FIRFiltTest6.setInput(inputSignal6[i]);
        	
            outputSignal1[i] = FIRFiltTest1.getFilterOutput();
            outputSignal2[i] = FIRFiltTest2.getFilterOutput();
            outputSignal3[i] = FIRFiltTest3.getFilterOutput();
            outputSignal4[i] = FIRFiltTest4.getFilterOutput();
            outputSignal5[i] = FIRFiltTest5.getFilterOutput();
            outputSignal6[i] = FIRFiltTest6.getFilterOutput();
            
            Thread.sleep((long) (TestTimeVector.Ts_s*1000));
            
            if(((int)(i / TestTimeVector.Ts_s)%1000) == 0){
                System.out.print(".");
            }
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal1, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.DARK_GRAY);
        outputPlot.addData(t.time_vector, outputSignal5, Color.PINK);
        outputPlot.addData(t.time_vector, outputSignal6, Color.CYAN);
        outputPlot.setTitle("Freq Sweep");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        System.out.println("Done!");
        
    }
    


}
