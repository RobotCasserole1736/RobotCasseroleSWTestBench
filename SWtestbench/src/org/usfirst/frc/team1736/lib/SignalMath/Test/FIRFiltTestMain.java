package org.usfirst.frc.team1736.lib.SignalMath.Test;


import org.usfirst.frc.team1736.lib.SignalMath.FIRFilter;
import org.usfirst.frc.team1736.lib.SignalMath.FilterType;
import org.usfirst.frc.team1736.lib.SignalMath.MedianFilter;

import java.awt.Color;

import org.usfirst.frc.team1736.lib.FalconPathPlanner.FalconLinePlot;

public class FIRFiltTestMain {

    // DUT classes - filters
    static FIRFilter FIRFiltTest1;
    static FIRFilter FIRFiltTest2;
    static FIRFilter FIRFiltTest3;
    static FIRFilter FIRFiltTest4;

    
    //Testcase plot results
    static FalconLinePlot outputPlot;
    
    //Data vectors
    static TestTimeVector t;
    static double[] inputSignal;
    static double[] outputSignal1;
    static double[] outputSignal2;
    static double[] outputSignal3;
    static double[] outputSignal4;

    

    
    
    public static void main(String[] args) throws InterruptedException {
        
    	System.out.println("Setting up test median filter objects");
        //Set up DUT casses with a variety of inputs
        FIRFiltTest1 = new FIRFilter(FilterType.LOWPASS_2HZ_FAST);
        FIRFiltTest2 = new FIRFilter(FilterType.LOWPASS_15HZ);
        FIRFiltTest3 = new FIRFilter(FilterType.HIGHPASS_2HZ);
        FIRFiltTest4 = new FIRFilter(FilterType.HIGHPASS_5HZ);
    	
    	
    	System.out.println("Starting test of signal math operations");
        
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 1 - Step input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 1");
        //Initialize timebase vectors & data
        t = new TestTimeVector(3);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        FIRFiltTest1.reset();
        FIRFiltTest2.reset();
        FIRFiltTest3.reset();
        FIRFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_step(i, 1);
        	FIRFiltTest1.setInput(inputSignal[i]);
        	FIRFiltTest2.setInput(inputSignal[i]);
        	FIRFiltTest3.setInput(inputSignal[i]);
        	FIRFiltTest4.setInput(inputSignal[i]);
        	
            outputSignal1[i] = FIRFiltTest1.getFilterOutput();
            outputSignal2[i] = FIRFiltTest2.getFilterOutput();
            outputSignal3[i] = FIRFiltTest3.getFilterOutput();
            outputSignal4[i] = FIRFiltTest4.getFilterOutput();
            
            Thread.sleep((long) (TestTimeVector.Ts_s*1000));
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC1 Median Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 2 - Delta input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 2");
        //Initialize timebase vectors & data
        t = new TestTimeVector(2);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        FIRFiltTest1.reset();
        FIRFiltTest2.reset();
        FIRFiltTest3.reset();
        FIRFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_kroneckerdelta(i, 30);
            FIRFiltTest1.setInput(inputSignal[i]);
            FIRFiltTest2.setInput(inputSignal[i]);
            FIRFiltTest3.setInput(inputSignal[i]);
            FIRFiltTest4.setInput(inputSignal[i]);
            
            outputSignal1[i] = FIRFiltTest1.getFilterOutput();
            outputSignal2[i] = FIRFiltTest2.getFilterOutput();
            outputSignal3[i] = FIRFiltTest3.getFilterOutput();
            outputSignal4[i] = FIRFiltTest4.getFilterOutput();
            
            Thread.sleep((long) (TestTimeVector.Ts_s*1000));
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC2 Median Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 3 - Sinusoidal input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 3");
        //Initialize timebase vectors & data
        t = new TestTimeVector(2);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        FIRFiltTest1.reset();
        FIRFiltTest2.reset();
        FIRFiltTest3.reset();
        FIRFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_sine(i, 3, 0);
            FIRFiltTest1.setInput(inputSignal[i]);
            FIRFiltTest2.setInput(inputSignal[i]);
            FIRFiltTest3.setInput(inputSignal[i]);
            FIRFiltTest4.setInput(inputSignal[i]);
            
            outputSignal1[i] = FIRFiltTest1.getFilterOutput();
            outputSignal2[i] = FIRFiltTest2.getFilterOutput();
            outputSignal3[i] = FIRFiltTest3.getFilterOutput();
            outputSignal4[i] = FIRFiltTest4.getFilterOutput();
            
            Thread.sleep((long) (TestTimeVector.Ts_s*1000));
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC3 Median Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 4 - Noise input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 4");
        //Initialize timebase vectors & data
        t = new TestTimeVector(3);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        FIRFiltTest1.reset();
        FIRFiltTest2.reset();
        FIRFiltTest3.reset();
        FIRFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_gaussianNoise(i, 1, 5);
            FIRFiltTest1.setInput(inputSignal[i]);
            FIRFiltTest2.setInput(inputSignal[i]);
            FIRFiltTest3.setInput(inputSignal[i]);
            FIRFiltTest4.setInput(inputSignal[i]);
            
            outputSignal1[i] = FIRFiltTest1.getFilterOutput();
            outputSignal2[i] = FIRFiltTest2.getFilterOutput();
            outputSignal3[i] = FIRFiltTest3.getFilterOutput();
            outputSignal4[i] = FIRFiltTest4.getFilterOutput();
            
            Thread.sleep((long) (TestTimeVector.Ts_s*1000));
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC4 Median Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        System.out.println("Done!");
        
    }
    


}
