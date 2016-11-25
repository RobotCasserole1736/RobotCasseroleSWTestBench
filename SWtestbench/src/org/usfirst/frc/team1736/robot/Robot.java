
package org.usfirst.frc.team1736.robot;

import org.usfirst.frc.team1736.lib.BatteryParam.BatteryParamEstimator;
import org.usfirst.frc.team1736.lib.LoadMon.RIOLoadMonitor;
import org.usfirst.frc.team1736.lib.WebServer.CasseroleWebServer;
import org.usfirst.frc.team1736.lib.WebServer.CassesroleWebStates;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

    // Physical Devices on Board in need of control
    PowerDistributionPanel pdp;

    // Software helpers
    CasseroleWebServer webserver;
    RIOLoadMonitor loadmon;
    BatteryParamEstimator bpe;



    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public void robotInit() {
        pdp = new PowerDistributionPanel(0);
        webserver = new CasseroleWebServer();
        bpe = new BatteryParamEstimator(100);
        loadmon = new RIOLoadMonitor();

        webserver.startServer();
    }



    /**
     * Runs once, right before disabled starts
     */
    public void disabledInit() {

    }


    /**
     * This function is called periodically during disabled. Note, you cannot change any robot
     * outputs during this mode.
     */
    public void disabledPeriodic() {

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

    }


    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //Check if system voltage goes too low
        if (pdp.getVoltage() < 7.0) {
            System.out.println("AAAGUGUGUGAGUGAGUAAAHHH!!!!!!!!!1!!!");
        }
        
        //update estimator for batery parameters
        bpe.updateEstimate(pdp.getVoltage(), pdp.getTotalCurrent());
        
        //Update what is displayed to the web server
        updateWebStates();

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
    }

}
