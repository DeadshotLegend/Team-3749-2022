// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.Drivetrain.leftFront);
  public WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.Drivetrain.leftBack);
  public MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftBack);

  public WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.Drivetrain.rightFront);
  public WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.Drivetrain.rightBack);
  public MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightBack);

  public DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


  public Drivetrain() {

  }

  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }
  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);
  }

}
