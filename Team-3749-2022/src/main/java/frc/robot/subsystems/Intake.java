package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {
    public CANSparkMax m_intakeMotor = new CANSparkMax(Constants.Intake.intakeMotor, MotorType.kBrushless);

    private final Compressor m_comp = new Compressor(0, PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid m_rightPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.Intake.kSolenoidForwardChannel[0],
            Constants.Intake.kSolenoidReverseChannel[0]);
    private final DoubleSolenoid m_leftPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.Intake.kSolenoidForwardChannel[1],
            Constants.Intake.kSolenoidReverseChannel[1]);

    public CANSparkMax m_shintakeFront = new CANSparkMax(Constants.Shintake.shintakeFront, MotorType.kBrushless);
    public CANSparkMax m_shintakeBack = new CANSparkMax(Constants.Shintake.shintakeBack, MotorType.kBrushless);
    public RelativeEncoder m_frontEncoder = m_shintakeFront.getEncoder();
    public RelativeEncoder m_backEncoder = m_shintakeBack.getEncoder();

    private final PIDController m_pidControllerHigh = new PIDController(Constants.Shintake.kP, Constants.Shintake.kI, Constants.Shintake.kD);
    private final PIDController m_pidControllerLow = new PIDController(Constants.Shintake.kPlow, Constants.Shintake.kI, Constants.Shintake.kD);

    public Intake() {
        m_shintakeBack.setInverted(true);
        m_shintakeFront.setInverted(false);

        m_intakeMotor.setIdleMode(IdleMode.kBrake);
        m_shintakeFront.setIdleMode(IdleMode.kBrake);
        m_shintakeBack.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("SHINTAKE FRONT RPM", m_frontEncoder.getVelocity());
        SmartDashboard.putNumber("SHINTAKE BACK RPM", m_backEncoder.getVelocity());
        startCompressor();
    }

    public void setIntake() {
        m_intakeMotor.set(Constants.Intake.kIntakeSpeed);
    }
    
    public void setIntakeReverse() {
        m_intakeMotor.set(-Constants.Intake.kIntakeSpeed);
    }

    public void stopMotors() {
        m_intakeMotor.set(0);
    }

    public void stopIntake() {
        m_intakeMotor.stopMotor();
    }

    public void holdShintake() {
        m_shintakeFront.set(Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
    }

    public void setShintake() {
        m_shintakeFront.set(Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(Constants.Shintake.kShintakeSpeed);
    }

    public void setShintakePID() {
        PIDController frontPIDController = m_frontEncoder.getVelocity() > Constants.Shintake.targetRPM - 50 ? m_pidControllerLow : m_pidControllerHigh;
        double frontVel = frontPIDController.calculate(m_frontEncoder.getVelocity(), Constants.Shintake.targetRPM);
        m_shintakeFront.set(frontVel);

        PIDController backPIDController = m_backEncoder.getVelocity() > Constants.Shintake.targetRPM - 50 ? m_pidControllerLow : m_pidControllerHigh;
        double backVel = backPIDController.calculate(m_backEncoder.getVelocity(), Constants.Shintake.targetRPM);
        m_shintakeBack.set(backVel);
        /*
            double frontVel = m_pidControllerHigh.calculate(m_frontEncoder.getVelocity(), Constants.Shintake.targetRPM);
            double backVel = m_pidControllerHigh.calculate(m_backEncoder.getVelocity(), Constants.Shintake.targetRPM);
            m_shintakeFront.set(frontVel);
            m_shintakeBack.set(backVel);
        */
    }

    public void setShintakeReverse() {
        m_shintakeFront.set(-Constants.Shintake.kShintakeSpeed);
        m_shintakeBack.set(-Constants.Shintake.kShintakeSpeed);
    }

    public void stopShintake() {
        m_shintakeFront.stopMotor();
        m_shintakeBack.stopMotor();
    }

    public void startCompressor() {
        m_comp.enableDigital();
    }

    public void stopCompressor() {
        m_comp.disable();
    }

    public void setShintakeVoltage(double volts) {
        m_shintakeFront.setVoltage(volts);
        m_shintakeBack.setVoltage(volts);
        
    }

    public void intakeFwd() {
        m_rightPiston.set(DoubleSolenoid.Value.kForward);
        m_leftPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void intakeRev() {
        m_rightPiston.set(DoubleSolenoid.Value.kReverse);
        m_leftPiston.set(DoubleSolenoid.Value.kReverse);
    }
}
