/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2905.robot;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	int internalCounter = 0;
	//RobotDrive myArm;


	VictorSP frontLeft;
	VictorSP rearLeft;
	VictorSP frontRight;
	VictorSP rearRight;
	VictorSP armMechanismUpDownMotor;
	VictorSP rightLiftMotor;
	VictorSP leftLiftMotor;
	RobotDrive myRobot;
	DigitalOutput rightArmValve ;
	DigitalOutput leftArmValve ;
	Joystick stick;
	Joystick gamepad;
	JoystickButton button1;
	JoystickButton button2;
	int autoLoopCounter;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		frontLeft = new VictorSP(7);
		rearLeft = new VictorSP(6);
		frontRight = new VictorSP(9);
		rearRight = new VictorSP(8);
		armMechanismUpDownMotor = new VictorSP(5);
		rightLiftMotor = new VictorSP(4);
		leftLiftMotor = new VictorSP(3);
		rightArmValve = new DigitalOutput(0);
		leftArmValve = new DigitalOutput(1);
		myRobot = new RobotDrive(rearRight ,frontRight, rearLeft , frontLeft );
		//myRobot.setInvertedMotor(RobotDrive.frontLeft, true);
		//myRobot.setInvertedMotor(RobotDrive.rearLeft, true);
		
		stick = new Joystick(0);
		gamepad = new Joystick(1);
		
		
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {
		autoLoopCounter = 0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
		} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated mode
	 */
	public void teleopInit(){
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		/**if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
		} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}*/
		
		myRobot.arcadeDrive(stick);
		
		//myRobot.arcadeDrive(0.5, 0.0);
		
		//***** Arm Mechanism Up/Down Motor - GamePad Button 3 and 4 *****//
		
		//Up
		if(gamepad.getRawButton(4)== true && gamepad.getRawButton(3) == false)
		{
			armMechanismUpDownMotor.set(0.1);
		}
		
		//Down
		else if(gamepad.getRawButton(3)== true && gamepad.getRawButton(4) == false)
		{
			armMechanismUpDownMotor.set(-0.1);
		}
		else{
			armMechanismUpDownMotor.stopMotor();
		}
		
		//***** Lift Mechanism Up/Down Motor - GamePad Button 1 and 2 *****//
		
				//Up
				if(gamepad.getRawButton(2)== true && gamepad.getRawButton(1) == false)
				{
					rightLiftMotor.set(0.1);
					leftLiftMotor.set(-0.1);
				}
				
				//Down
				else if(gamepad.getRawButton(1)== true && gamepad.getRawButton(2) == false)
				{
					rightLiftMotor.set(-0.1);
					leftLiftMotor.set(0.1);
				}
				else{
					
					rightLiftMotor.stopMotor();
					leftLiftMotor.stopMotor();
				}
				
				//***** Arm Open/Close - GamePad Button 5 and 6 *****//
				
				//Up
				if(gamepad.getRawButton(5)== true && gamepad.getRawButton(6) == false)
				{
					rightArmValve.set(true);
					leftArmValve.set(true);
				}
				
				//Down
				
				else{
					
					rightArmValve.set(false);
					leftArmValve.set(false);
				}
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

}