package frc.robot.commands.auton;

import java.io.IOException;
import java.nio.file.Path;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ResetDrivetrain;
import frc.robot.commands.intake.ContinousIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.Constants;
import frc.robot.commands.shooter.*;

public class AutoGroups {

    static Drivetrain m_drivetrain;
    static Intake m_intake;
    static Shooter m_shooter;

    public AutoGroups(Drivetrain drive, Intake intake, Shooter shoot) {
        m_drivetrain = drive;
        m_intake = intake;
        m_shooter = shoot;
    }

    public final static Command getRamseteJSON(String name) {
        Trajectory traj = new Trajectory();

        try {
            Path trajpath = Filesystem.getDeployDirectory().toPath().resolve("pathplanner/generatedJSON/" + name + ".wpilib.json");
            traj = TrajectoryUtil.fromPathweaverJson(trajpath);
        } catch (IOException e) {
            DriverStation.reportError("Unable to open traj", e.getStackTrace());
        }
        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Auto.ksVolts,
                        Constants.Auto.kvVoltSecondsPerMeter,
                        Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                m_drivetrain::tankDriveVolts,
                m_drivetrain);

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 2, 1.67);

        Trajectory traj = new Trajectory();

        traj = path;

        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Auto.ksVolts,
                        Constants.Auto.kvVoltSecondsPerMeter,
                        Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                m_drivetrain::tankDriveVolts,
                m_drivetrain);

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name, boolean reset, boolean reversed) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, 2, 1.67, reversed);

        Trajectory traj = new Trajectory();

        traj = path;

        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Auto.ksVolts,
                        Constants.Auto.kvVoltSecondsPerMeter,
                        Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                m_drivetrain::tankDriveVolts,
                m_drivetrain);

        if (true) m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name, double velo, double accel) {
        PathPlannerTrajectory path = PathPlanner.loadPath(name, velo, accel);

        Trajectory traj = new Trajectory();

        traj = path;

        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Auto.ksVolts,
                        Constants.Auto.kvVoltSecondsPerMeter,
                        Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                m_drivetrain::tankDriveVolts,
                m_drivetrain);

        m_drivetrain.resetOdometry(traj.getInitialPose());

        return ramseteCommand;
    }

    public final static Command getRamsete(String name, String translate) {
        Trajectory traj = PathPlanner.loadPath(name, 2, 1.67);
        Trajectory translation = PathPlanner.loadPath(name, 2, 1.67);

        if(translate != "") { 
            traj = traj.relativeTo(translation.getInitialPose());
        }

        m_drivetrain.setBrake();

        RamseteCommand ramseteCommand = new RamseteCommand(
                traj,
                m_drivetrain::getPose,
                new RamseteController(Constants.Auto.kRamseteB, Constants.Auto.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Auto.ksVolts,
                        Constants.Auto.kvVoltSecondsPerMeter,
                        Constants.Auto.kaVoltSecondsSquaredPerMeter),
                Constants.Auto.kDriveKinematics,
                m_drivetrain::getWheelSpeeds,
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                new PIDController(Constants.Auto.kPDriveVel, 0, 0),
                m_drivetrain::tankDriveVolts,
                m_drivetrain);

        // m_drivetrain.resetOdometry(traj.getInitialPose());

        return new SequentialCommandGroup(
            new ResetDrivetrain(m_drivetrain, traj),
            ramseteCommand
        );
    }

    public final static Command intake(String name) {
        return new ParallelRaceGroup(
                getRamsete(name),
                new ContinousIntake(m_intake));
    }

    public final static Command intake(String name, String translation) {
        return new ParallelRaceGroup(
                getRamsete(name, translation),
                new ContinousIntake(m_intake));
    }

    public final static Command intake(String name, boolean reset) {
        return new ParallelRaceGroup(
                getRamsete(name, reset, false),
                new ContinousIntake(m_intake));
    }

    public final static Command shoot() {
        return new SequentialCommandGroup(
                new ParallelRaceGroup(
                        new AutoShoot(m_shooter, m_intake),
                        new WaitCommand(4)));
    }

    public final Command getAutoCommand() {
        return new SequentialCommandGroup(
                intake("Blue2Intake"),
                getRamsete("Blue2Intake"),
                shoot());
    }

    public final Command tarmacShoot() {
        return shoot();
    }
}
