
package org.usfirst.frc.team1736.robot;

import org.usfirst.frc.team1736.lib.BatteryParam.BatteryParamEstimator;
import org.usfirst.frc.team1736.lib.CoProcessor.VisionListener;
import org.usfirst.frc.team1736.lib.LoadMon.CasseroleRIOLoadMonitor;
import org.usfirst.frc.team1736.lib.Logging.CsvLogger;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleWebServer;
import org.usfirst.frc.team1736.lib.WebServer.CassesroleWebStates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {
	
	Joystick joystick;

    // Physical Devices on Board in need of control
    PowerDistributionPanel pdp;
    Compressor compressor;
    Solenoid solenoid;
    Spark spark;
    Jaguar jaguar;

    // Software helpers
    CasseroleWebServer webserver;
    CasseroleRIOLoadMonitor loadmon;
    BatteryParamEstimator bpe;
    VisionListener bbbVisionProcesssor;
    
    //Performance tracking variables
    double loopStartTime;
    double loopEndTime;
    double lastLoopTime;




    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public void robotInit() {
        pdp = new PowerDistributionPanel(0);
        webserver = new CasseroleWebServer();
        bpe = new BatteryParamEstimator(100);
        loadmon = new CasseroleRIOLoadMonitor();
        solenoid = new Solenoid(4);
        spark = new Spark(0);
        jaguar = new Jaguar(1);
        joystick = new Joystick(0);
        compressor = new Compressor();
        bbbVisionProcesssor = new VisionListener("10.17.36.20", 5800);

        CsvLogger.addLoggingFieldDouble("TIME", "sec", "getFPGATimestamp", Timer.class);
        CsvLogger.addLoggingFieldDouble("PDBMeasVoltage", "V", "getVoltage", pdp);
        CsvLogger.addLoggingFieldDouble("PDBMeasCurrent", "I", "getTotalCurrent", pdp);
        CsvLogger.addLoggingFieldDouble("BattEstVoc", "V", "getEstESR", bpe);
        CsvLogger.addLoggingFieldDouble("BattEstESR", "ohm", "getEstVoc", bpe);
        CsvLogger.addLoggingFieldBoolean("SolenoidCommand", "On", "get", solenoid);
        CsvLogger.addLoggingFieldDouble("SparkCommand", "percent", "getSpeed", spark);
        CsvLogger.addLoggingFieldDouble("JaguarCommand", "percent", "getSpeed", jaguar);

        webserver.startServer();
        bbbVisionProcesssor.start();
    }



    /**
     * Runs once, right before disabled starts
     */
    public void disabledInit() {
    	//Ensure any opened log is closed out and saved to disk
    	CsvLogger.close();
    }


    /**
     * This function is called periodically during disabled. Note, you cannot change any robot
     * outputs during this mode.
     */
    public void disabledPeriodic() {
    	//Lock in the latest data from the vision coprocessor
    	bbbVisionProcesssor.sampleLatestData();
    	
    	//Update what is displayed to the web server
        updateWebStates();

    }



    /**
     * Runs once, right before autonomous starts
     */
    public void autonomousInit() {

    }


    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }



    /**
     * Runs once, right before teleop starts
     */
    public void teleopInit() {
    	CsvLogger.init();
    	compressor.setClosedLoopControl(false);

    }


    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//mark start time of loop
    	loopStartTime = Timer.getFPGATimestamp();
    	
    	//Lock in the latest data from the vision coprocessor
    	bbbVisionProcesssor.sampleLatestData();
    	
        //Check if system voltage goes too low
        if (pdp.getVoltage() < 7.0) {
            System.out.println("AAAGUGUGUGAGUGAGUAAAHHH!!!!!!!!!1!!!");
        }
        
        solenoid.set(joystick.getRawButton(1));
        spark.set(joystick.getRawAxis(1));
        
        //update estimator for batery parameters
        bpe.updateEstimate(pdp.getVoltage(), pdp.getTotalCurrent());
        
        //Update what is displayed to the web server
        updateWebStates();
        
        //Log Data
        CsvLogger.logData(false);
        
        //mark end time of control loop
        loopEndTime = Timer.getFPGATimestamp();
        
        //Calculate time taken
        lastLoopTime = loopEndTime - loopStartTime;

    }


    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }
    
    
    /**
     * This function updates the current state of the robot for the web server's display 
     */
    public void updateWebStates(){
        CassesroleWebStates.putDouble("PDP Input Voltage (V)",       pdp.getVoltage());
        CassesroleWebStates.putDouble("PDP Input Current (A)",       pdp.getTotalCurrent());
        CassesroleWebStates.putDouble("Battery estimated ESR (ohm)", bpe.getEstESR());
        CassesroleWebStates.putDouble("Battery estimated Voc (V)",   bpe.getEstVoc());
        CassesroleWebStates.putDouble("RoboRIO CPU Load (Pct)",      loadmon.getCPULoadPct());
        CassesroleWebStates.putDouble("RoboRIO Memory Load (Pct)",   loadmon.getMemLoadPct());
        CassesroleWebStates.putString("BBB CoProcessor Alive",   bbbVisionProcesssor.isCoProcessorAlive()?"True":"False");
        CassesroleWebStates.putDouble("BBB CoProcessor CPU Load (Pct)",   bbbVisionProcesssor.getCpuLoad());
        CassesroleWebStates.putDouble("BBB CoProcessor Memory Load (Pct)",   bbbVisionProcesssor.getMemLoad());
        CassesroleWebStates.putDouble("BBB CoProcessor FPS (frames/sec)",   bbbVisionProcesssor.getFPS());
        CassesroleWebStates.putDouble("BBB CoProcessor Num Taragets",   bbbVisionProcesssor.getNumTargetsObserved());
        CassesroleWebStates.putDouble("BBB CoProcessor Frame Counter",   bbbVisionProcesssor.getFrameCounter());
        
        String tmp_str = "";
        for(int i = 0; i < bbbVisionProcesssor.getNumTargetsObserved(); i++ ){
            tmp_str += "[" + Double.toString(bbbVisionProcesssor.getX(i)) + "," + Double.toString(bbbVisionProcesssor.getY(i)) + "] ";
        }
        CassesroleWebStates.putString("BBB CoProcessor Target Centroids", tmp_str);
    }

}
