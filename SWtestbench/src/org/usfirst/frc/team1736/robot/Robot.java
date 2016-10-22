
package org.usfirst.frc.team1736.robot;

import org.usfirst.frc.team1736.lib.WebServer.CasseroleWebServer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Physical Devices on Board in need of control  
	PowerDistributionPanel pdp;
	
	//Software helpers
	CasseroleWebServer webserver;
	
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	pdp = new PowerDistributionPanel();
    	webserver = new CasseroleWebServer();
    	
    	webserver.startServer();
    }
    
    
    
    
	/**
	 * Runs once, right before disabled starts
	 */
    public void disabledInit() {

    }
    
    /**
     * This function is called periodically during disabled.
     * Note, you cannot change any robot outputs during this mode.
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
    	if(pdp.getVoltage() < 7.0){
    		System.out.println("AAAGUGUGUGAGUGAGUAAAHHH!!!!!!!!!1!!!");
    	}
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
