// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Drivetrain {
        public static final int leftFront = 11;
        public static final int leftBack = 12;
        public static final int rightFront = 13;
        public static final int rightBack = 14;
        public static final double ksVolts = 0.56049;
        public static final double kvVoltSecondsPerMeter = 3.0424;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15556;
        public static final double kPDriveVel = 1.7169;
        public static final double kTrackwidthMeters = 0.584;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);
        public static final double kMaxSpeedMetersPerSecond = .1;
        public static final double kMaxAccelerationMetersPerSecondSquared = .1;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final double kWheelRadius = 0.0508;
        public static final int kEncoderResolution = 2048;
    }
    public static final class Vision{
        public static final double kVisionP = 0.1;
        public static final double kVisionLimit = 0.5;
    }
    public static final class XBoxButton{
        public static final XboxController XBOX_CONTROLLER = new XboxController(0);
        public static final JoystickButton XBOX_X = new JoystickButton(XBOX_CONTROLLER, Button.kX.value);
        public static final JoystickButton XBOX_Y = new JoystickButton(XBOX_CONTROLLER, Button.kY.value);
        public static final JoystickButton XBOX_A = new JoystickButton(XBOX_CONTROLLER, Button.kA.value);
        public static final JoystickButton XBOX_B = new JoystickButton(XBOX_CONTROLLER, Button.kB.value);
        public static final JoystickButton XBOX_L = new JoystickButton(XBOX_CONTROLLER, Button.kLeftBumper.value);
        public static final JoystickButton XBOX_R = new JoystickButton(XBOX_CONTROLLER, Button.kRightBumper.value);
        
    }

    public static final class Intake {
        public static final int intakeMotor = 0; // FIND DEVICE ID
        public static final int liftMotor = 0; // FIND DEVICE ID
        public static final double kIntakeSpeed = 1.0;
        public static final double kIntakeLiftUpSpeed = 1.0;
        public static final double kIntakeLiftDownSpeed = -1.0;
    }
}
