package frc.robot;

import frc.robot.commands.*;
import frc.robot.utilities.AutoGroups;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import frc.robot.utilities.POV;
import frc.robot.utilities.Xbox;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

    private final Drivetrain m_drivetrain = new Drivetrain();

    private final Shooter m_shooter = new Shooter();

    private final Intake m_intake = new Intake();

    // private final Elevator m_elevator = new Elevator();

    Xbox Pilot;
    Xbox Operator;
    POV PiPOV;
    POV OpPOV;

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        Pilot = new Xbox(0);
        Operator = new Xbox(1);

        PiPOV = new POV(new GenericHID(0));
        OpPOV = new POV(    new GenericHID(1));

        // Pilot.y().whenPressed(new Extend(m_elevator)).whenReleased(new StopClimb(m_elevator));
        // Pilot.b().whenPressed(new Lift(m_elevator)).whenReleased(new StopClimb(m_elevator));
        Pilot.a().toggleWhenPressed(new InstantCommand(m_drivetrain::setCoast));
        Pilot.x().toggleWhenPressed(new InstantCommand(m_drivetrain::setBrake));

        m_drivetrain.setDefaultCommand(
                new ArcadeDrive(m_drivetrain, Pilot::getLeftY, Pilot::getRightX));
        m_shooter.setDefaultCommand(
                new Shoot(m_shooter, m_intake, Pilot, Operator, OpPOV, PiPOV));
        // m_intake.setDefaultCommand(
        //     new Input(m_intake, Pilot::getLeftTrigger, Pilot::getRightTrigger));
    }


    public Command getAutonomousCommand() {
        AutoGroups autoGroup = new AutoGroups(m_drivetrain, m_intake, m_shooter);

        m_drivetrain.setBrake();
        return autoGroup.getTwoLA();
    }

    public Command setCoast() {
        return new InstantCommand(m_drivetrain::setCoast, m_drivetrain);
    }

    public Command runChecks() {
        return new Checks(m_drivetrain, m_intake, m_shooter);
    }
    
}
 