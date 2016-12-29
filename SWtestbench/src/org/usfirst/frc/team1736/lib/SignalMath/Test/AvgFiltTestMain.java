package org.usfirst.frc.team1736.lib.SignalMath.Test;

import org.usfirst.frc.team1736.lib.SignalMath.AveragingFilter;

import java.awt.Color;

import org.usfirst.frc.team1736.lib.FalconPathPlanner.FalconLinePlot;

public class AvgFiltTestMain {

    // DUT classes - filters
    static AveragingFilter avgFiltTest1;
    static AveragingFilter avgFiltTest2;
    static AveragingFilter avgFiltTest3;
    static AveragingFilter avgFiltTest4;

    
    //Testcase plot results
    static FalconLinePlot outputPlot;
    
    //Testcase constants
    final static double testCaseLength_s = 5;
    
    //Data vectors
    static TestTimeVector t;
    static double[] inputSignal;
    static double[] outputSignal1;
    static double[] outputSignal2;
    static double[] outputSignal3;
    static double[] outputSignal4;

    

    
    
    public static void main(String[] args) {
        
    	System.out.println("Setting up test averaging filter objects");
        //Set up DUT casses with a variety of inputs
        avgFiltTest1 = new AveragingFilter(15, 0);
        avgFiltTest2 = new AveragingFilter(10, 0);
        avgFiltTest3 = new AveragingFilter(5,  2);
        avgFiltTest4 = new AveragingFilter(3,  0);
    	
    	
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
        avgFiltTest1.reset();
        avgFiltTest2.reset();
        avgFiltTest3.reset();
        avgFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_step(i, 1);
        	outputSignal1[i] = avgFiltTest1.filter(inputSignal[i]);
        	outputSignal2[i] = avgFiltTest2.filter(inputSignal[i]);
        	outputSignal3[i] = avgFiltTest3.filter(inputSignal[i]);
        	outputSignal4[i] = avgFiltTest4.filter(inputSignal[i]);
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC1 Averaging Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 2 - Delta input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 2");
        //Initialize timebase vectors & data
        t = new TestTimeVector(3);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        avgFiltTest1.reset();
        avgFiltTest2.reset();
        avgFiltTest3.reset();
        avgFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_kroneckerdelta(i, 30);
        	outputSignal1[i] = avgFiltTest1.filter(inputSignal[i]);
        	outputSignal2[i] = avgFiltTest2.filter(inputSignal[i]);
        	outputSignal3[i] = avgFiltTest3.filter(inputSignal[i]);
        	outputSignal4[i] = avgFiltTest4.filter(inputSignal[i]);
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC2 Averaging Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 3 - Sinusoidal input
        ///////////////////////////////////////////////////////////////////////
        System.out.println("Running Testcase 3");
        //Initialize timebase vectors & data
        t = new TestTimeVector(3);
        
        
        //Allocate space for IO signals
        inputSignal = new double[t.numSamples];
        outputSignal1 = new double[t.numSamples];
        outputSignal2 = new double[t.numSamples];
        outputSignal3 = new double[t.numSamples];
        outputSignal4 = new double[t.numSamples];
        
        //Reset filters in prep for test
        avgFiltTest1.reset();
        avgFiltTest2.reset();
        avgFiltTest3.reset();
        avgFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_sine(i, 3, 0);
        	outputSignal1[i] = avgFiltTest1.filter(inputSignal[i]);
        	outputSignal2[i] = avgFiltTest2.filter(inputSignal[i]);
        	outputSignal3[i] = avgFiltTest3.filter(inputSignal[i]);
        	outputSignal4[i] = avgFiltTest4.filter(inputSignal[i]);
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC3 Averaging Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        ///////////////////////////////////////////////////////////////////////
        // Testcase 4 - Gaussian Noise input
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
        avgFiltTest1.reset();
        avgFiltTest2.reset();
        avgFiltTest3.reset();
        avgFiltTest4.reset();
        
        //Actually run test
        for(int i = 0; i < t.numSamples; i++){
        	inputSignal[i] = TestFunctions.f_gaussianNoise(i, 1, 5);
        	outputSignal1[i] = avgFiltTest1.filter(inputSignal[i]);
        	outputSignal2[i] = avgFiltTest2.filter(inputSignal[i]);
        	outputSignal3[i] = avgFiltTest3.filter(inputSignal[i]);
        	outputSignal4[i] = avgFiltTest4.filter(inputSignal[i]);
        }

        //Plot results
        outputPlot = new FalconLinePlot(t.time_vector, inputSignal, Color.RED);
        outputPlot.addData(t.time_vector, outputSignal1, Color.BLUE);
        outputPlot.addData(t.time_vector, outputSignal2, Color.GREEN);
        outputPlot.addData(t.time_vector, outputSignal3, Color.ORANGE);
        outputPlot.addData(t.time_vector, outputSignal4, Color.MAGENTA);
        outputPlot.setTitle("TC4 Averaging Filter");
        outputPlot.setXLabel("Time (s)");
        outputPlot.setYLabel("Value (arbitrary)");
        outputPlot.redraw();
        
        
        System.out.println("Done!");
        
    }
    


}
