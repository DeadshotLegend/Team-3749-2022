package frc.robot.commands.shooter;

import frc.robot.subsystems.*;
import frc.robot.utilities.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

    private Shooter m_shooter;
    private Intake m_intake;
    private BooleanSupplier m_rightTrigger;
    private BooleanSupplier m_leftTrigger;

    private JoystickButton align;
    private DoubleSupplier m_joystick;

    public Shoot(Shooter shooter, Intake intake, BooleanSupplier rightTrig, BooleanSupplier leftTrig, JoystickButton alignBtn, DoubleSupplier joystick) {
        m_shooter = shooter;
        m_intake = intake;
        m_rightTrigger = rightTrig;
        m_leftTrigger = leftTrig;
        align = alignBtn;
        m_joystick = joystick;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        m_shooter.stopMotor();
        m_shooter.stopTurret();
    }

    @Override
    public void execute() {
        double turretControl = Constants.round(m_joystick.getAsDouble());
        if (Math.abs(turretControl) >= .1) { 
            m_shooter.setTurretMotor(turretControl*Constants.Shooter.turretSpeed);
            SmartDashboard.putNumber("turret pos: ", m_shooter.getTurretPosition());
        } else if (align.get()) {
             m_shooter.visionAlign();
        }
        else {m_shooter.stopTurret();}  

        SmartDashboard.putNumber("turret pos", m_shooter.getTurretPosition());
        SmartDashboard.putNumber("Shooter RPM", m_shooter.getRPM());
        SmartDashboard.putBoolean("align btn t/f", align.get());
        SmartDashboard.putBoolean("lower shoot t/f", m_leftTrigger.getAsBoolean());

        
        // System.out.println(m_shooter.getTurretPosition());
        if (m_rightTrigger.getAsBoolean()) {
            // m_shooter.visionAlign();
            
            m_shooter.setRPM(Constants.Shooter.shooterRPM);
        } else if (m_leftTrigger.getAsBoolean())
            m_shooter.setRPM(Constants.Shooter.lowerRPM);
        else m_shooter.stopMotor();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
