/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2905.robot;

import edu.wpi.first.wpilibj.AnalogGyro;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2905.robot.commands.ExampleCommand;
import org.usfirst.frc.team2905.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;






public class Robot extends TimedRobot {
	public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	private SpeedControllerGroup left = new SpeedControllerGroup(new VictorSP(0), new VictorSP(1));
	private SpeedControllerGroup right = new SpeedControllerGroup(new VictorSP(2), new VictorSP(3));
	AnalogGyro gyro = new AnalogGyro(0);
	DifferentialDrive  myRobot = new DifferentialDrive(right , left);
	Joystick stick = new Joystick(0);
	Joystick gamepad = new Joystick(1);
	private Timer timer = new Timer();
	//DigitalOutput rightArmValve = new DigitalOutput(0);
	//DigitalOutput leftArmValve = new DigitalOutput(1);
	//DoubleSolenoid rightArmValve = new DoubleSolenoid(0,1 );
	//DoubleSolenoid leftArmValve = new DoubleSolenoid(2,3);
	DigitalInput limitSwitchSlow;
	DigitalInput limitSwitchSstop;
	VictorSP armMechanismUpDownMotor = new VictorSP(4);
	VictorSP rightLiftMotor = new VictorSP(5);
	VictorSP leftLiftMotor = new VictorSP(6);	
	JoystickButton button1;
	JoystickButton button2;
	Alliance color  = DriverStation.getInstance().getAlliance();
	double Kp = 0.03;
	double leftSlow = 0.24;
	double rightSlow = -0.24;
	double rotateSpeed = 0.35;
	double rotateSpeedSlow = 0.25;
	limitSwitchSstop = new DigitalInput(1) ;
	double myRobot;
	
	

	
	
	
	
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		gyro.reset();
		
		
		
		
	}

	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	@Override
	
	
	public void autonomousInit() 
	{
		m_autonomousCommand = m_chooser.getSelected();
		timer.reset();
		timer.start();
		gyro.reset();
		
		/*
		  String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		  switch(autoSelected) 
		  { case "My Auto": autonomousCommand
		  = new MyAutoCommand(); break; case "Default Auto": default:
		  autonomousCommand = new ExampleCommand(); break; }
		  */
		 
		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) 
		{
			m_autonomousCommand.start();
		}
	}

	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		int autoLoopCounter = 0;
		
		//boolean gyromsg = true;
		
		while(isAutonomous() && isEnabled())
		{
		myRobot (0.5,0.5);
		}
			
			/*if(Math.abs(gyro.getAngle()) <=3)
			{
				left.set(leftSlow - (gyro.getAngle())/15);
				//right.set(right - gyro);
			}/*
			
			
			
			
			
			
			
			//double angle = gyro.getAngle();
			//double setangle = 1.0;
			//myRobot.setExpiration(setangle);
			//DriverStation.reportError("Gyro derece degeri" + angle, gyromsg);
			
			//String gameData = DriverStation.getInstance().getGameSpecificMessage();
			
			/*if(gameData.length()>0)
			{
				if(gameData.charAt(0) == 'L')
				{
					if(autoLoopCounter < 1000) //Check if we've completed 100 loops (approximately 2 seconds)
					{
						leftLiftMotor.set(-0.5); 	// drive forwards half speed
						autoLoopCounter++;
					} else {
						leftLiftMotor.set(0.0); 
						
					}				}
				else
				{
					leftLiftMotor.set(0.0);
				}
			}*/
			
			
			
		}
		
	private void myRobot(double d, double e) {
		// TODO Auto-generated method stub
		
	}

	}

	@Override
	public void teleopInit() {
		
		
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		
		while(isOperatorControl() && isEnabled())
		{
			if(limitSwitchSstop.get()) //Stopper robotun kolu belirli bir yere kadar kalktiginda switch tarafýndan algilandiginda kendisini durdurmasý icin.
		    {
		          rightLiftMotor.set(0.0);// kolu durdur
		          leftLiftMotor.set(0.0);}
			myRobot.arcadeDrive(stick.getY(),stick.getX());
			boolean colormsg = true;
			}
					
					
					
				
			
			
			// Getting Team Color
			/*Alliance color;
			color = DriverStation.getInstance().getAlliance(); 
			
			if(color == DriverStation.Alliance.Blue)
			{
				DriverStation.reportWarning("Team color is Blue", colormsg);
			}
			else
			{
				DriverStation.reportWarning("Team color is Red", colormsg);
			}*/
			//Getting Location Number
			
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
					/*if(gamepad.getRawButton(5)== true && gamepad.getRawButton(6) == false)
					{
						rightArmValve.set(true);
						leftArmValve.set(true);
					}
					
					//Down
					
					else{
						
						rightArmValve.set(false);
						leftArmValve.set(false);
					}
		}*/
	}
		
		
	

	
	@Override
	public void testPeriodic() {
	}/*
}
