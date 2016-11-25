package org.usfirst.frc.team1736.lib.LoadMon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RIOLoadMonitor {
	
	/** Rate of update of the load variables in milliseconds. 1s should be enough? */
	public static final int UPDATE_RATE_MS = 1000;
	
	/** Overall (all-cpu) load percentage (non-idle time) */
	public double totalCPULoadPct = 0;
	/** Memory used percentage */
	public double totalMemUsedPct = 0;
	
	//To ensure we only calculate load between the last measurement and this one, we must store the
	//previous values measured from the kernel, since the kernel reports aggregate time counts
	double prevUserTime = 0;
	double prevNicedTime = 0;
	double prevSystemTime = 0;
	double prevIdleTime = 0;
	
	
	//Set to true if we can't read the file (wrong os, or something else weird)
	//Will prevent burning processor cycles if we can't actually get any info
	boolean giveUp = false;
	
	// These "files" contain the load info on a linux system
	static final String CPU_LOAD_VIRT_FILE = "/proc/stat";
	static final String MEM_LOAD_VIRT_FILE = "/proc/meminfo";
	
	/**
	 * Constructor. Initalizes measurement system and starts a slow
	 * background thread to gather load info
	 */
	public RIOLoadMonitor(){
		
		//Reset give up flag
		giveUp = false;
		
		// Kick off monitor in brand new thread.
	    // Thanks to Team 254 for an example of how to do this!
	    Thread monitorThread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            try {
	            	while(giveUp == false){
	            		periodicUpdate();
	            		Thread.sleep(UPDATE_RATE_MS);
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        }
	    });
	    
	    //Set up thread properties and start it off
	    monitorThread.setName("CasseroleRIOLoadMonitor");
	    monitorThread.setPriority(Thread.MIN_PRIORITY+1);
	    monitorThread.start();
	}
	
	/**
	 * Updates the present loads based on info from the /proc virtual
	 * filesystem. Should be called in the background. This takes up
	 * some number of resources (opening and closing files), so it's
	 * worthwhile not running it super fast. will be called internally
	 * by the thread started in the constructor
	 */
	private void periodicUpdate(){

		String CPUTotalLoadRawLine = new String();
		File file;
		
		if(giveUp == false){
			
			//////////////////////////////////////////////////////////////////////////////
			//// CPU LOAD PARSING & CALCULATION
			//////////////////////////////////////////////////////////////////////////////
			//Get meaningful line from CPU load virtual file
			file = new File(CPU_LOAD_VIRT_FILE);
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				CPUTotalLoadRawLine = br.readLine();
				while(CPUTotalLoadRawLine != null){
					if(CPUTotalLoadRawLine.startsWith("cpu ")){
						break;
					}
					CPUTotalLoadRawLine = br.readLine();
				}
				br.close();
			} catch(IOException e){
				System.out.println("WARNING: cannot get raw CPU load data. Giving up future attempts to read.");
				giveUp = true;
			}
			
			//line now contains cpu load text info
			//separated by spaces in the format:
			// "cpu <user> <nice> <system> <idle> ..."
			// Time units are system dependent, but we don't care
			// since we are calculating a percentage.
			String[] tokens = CPUTotalLoadRawLine.split(" ");

			double curUserTime = 0;
			double curNicedTime = 0;
			double curSystemTime = 0;
			double curIdleTime = 0;
			try{
				curUserTime = Double.parseDouble(tokens[1]);
				curNicedTime = Double.parseDouble(tokens[2]);
				curSystemTime = Double.parseDouble(tokens[3]);
				curIdleTime = Double.parseDouble(tokens[4]);
			}catch(Exception e) {
				System.out.println("WARNING: cannot parse CPU load. Giving up future attempts to read.");
				giveUp = true;
			}
			
			//Calculate change in time counters since last measurement
			double deltaUserTime = curUserTime - prevUserTime;
			double deltaNicedTime = curNicedTime - prevNicedTime;
			double deltaSystemTime = curSystemTime - prevSystemTime;
			double deltaIdleTime = curIdleTime - prevIdleTime;
			
			//Add up totals
			double totalInUseTime = (deltaUserTime + deltaNicedTime + deltaSystemTime);
			double totalTime = totalInUseTime + deltaIdleTime;
			
			//Calculate CPU load
			totalCPULoadPct = totalInUseTime/totalTime * 100.0;
			
			
			
			//////////////////////////////////////////////////////////////////////////////
			//// MEMORY LOAD PARSING & CALCULATION
			//////////////////////////////////////////////////////////////////////////////
			String memTotalStr = new String();
			String memFreeStr =  new String();
			String line = new String();
			
			//Get meaningful line from CPU load virtual file
			file = new File(MEM_LOAD_VIRT_FILE);
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				line = br.readLine();
				while(line != null){
					if(line.startsWith("MemTotal: ")){
						memTotalStr = line;
					} else if(line.startsWith("MemFree:")){
						memFreeStr = line;
						break;
					}
					line = br.readLine();
				}
				br.close();
			} catch(IOException e){
				System.out.println("WARNING: cannot get raw memory load data. Giving up future attempts to read.");
				giveUp = true;
			}
			
			//Split up the string which should be in the format
			// "<title> <value> <units>" and we only care about value.
			String[] memTotalTokens = memTotalStr.split("\\s+");
			String[] memFreeTokens = memFreeStr.split("\\s+");
			
			//Parse values from proper tokens
			double curTotalMem = 0;
			double curFreeMem = 0;
			try{
				curTotalMem = Double.parseDouble(memTotalTokens[1]);
				curFreeMem = Double.parseDouble(memFreeTokens[1]);
			}catch(Exception e) {
				System.out.println("WARNING: cannot parse memory load. Giving up future attempts to read.");
				giveUp = true;
			}
			
			//Mathy math math
			totalMemUsedPct = (1.0 - curFreeMem/curTotalMem) * 100.0;
			
			
		} 
		

		if(giveUp == true){
			//Indicate we're not getting the load values
			totalCPULoadPct = -1;
			totalMemUsedPct = -1;
		}
		
		
	}
	
	/**
	 * Getter for load percentage on CPU. Aggregate of all cores on the system, including
	 * both system and user processes.
	 * @return percentage of non-idle time, or -1 if percentage unavailable
	 */
	public double getCPULoadPct(){
		return totalCPULoadPct;
	}
	
	/**
	 * Getter for the load percentage on memory. 
	 * @return percentage of available system RAM, or -1 if percentage unavailable.
	 */
	public double getMemLoadPct(){
		return totalMemUsedPct;
	}
    

}
