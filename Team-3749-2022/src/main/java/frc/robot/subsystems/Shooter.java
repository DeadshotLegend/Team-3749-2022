package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Constants;
import frc.robot.utilities.Constants.Auto;

public class Shooter extends SubsystemBase {
    private WPI_TalonFX m_leftShooterMotor = new WPI_TalonFX(Constants.Shooter.leftShooterMotor);
    private WPI_TalonFX m_rightShooterMotor = new WPI_TalonFX(Constants.Shooter.rightShooterMotor);

    private CANSparkMax m_turretMotor = new CANSparkMax(Constants.Shooter.turretMotor, MotorType.kBrushless);
    private RelativeEncoder m_turretEncoder = m_turretMotor.getEncoder();

    private MotorControllerGroup m_shooterMotors = new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor);

    private PIDController m_pidController = new PIDController(Constants.Shooter.kP, Constants.Shooter.kI,
            Constants.Shooter.kD);

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    public Shooter() {
        m_leftShooterMotor.setInverted(true);
        m_turretMotor.setInverted(true);
        m_turretEncoder.setPositionConversionFactor(Constants.Shooter.gearRatio);
        m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
        m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);

        m_leftShooterMotor.setSensorPhase(false);
    }

    public void setRPM(double target) {
        m_shooterMotors.setVoltage(m_pidController.calculate(m_leftShooterMotor.getSelectedSensorVelocity(), target * 60) * .0019);
        SmartDashboard.putNumber("Voltage", m_leftShooterMotor.getBusVoltage());
    }

    public double getRPM() {
        return m_leftShooterMotor.getSelectedSensorVelocity() * 0.034;
    }

    public void setVelocity(double velocity) {
        setRPM(velocity * 60 / .476);
        SmartDashboard.putNumber("Velocity", m_leftShooterMotor.getSelectedSensorVelocity());
    }

    public void setTargetVelocity(){
        setRPM(targetVelocity() * 60);
    }

    public void rawShoot(double speed) {
        m_shooterMotors.set(speed);
    }

    public void stopMotor() {
        m_shooterMotors.set(0);
    }

    public double getVelocity() {
        return m_leftShooterMotor.getSelectedSensorVelocity();
    }

    public void setTurret(double position){
        if(m_turretEncoder.getPosition() + 0.1 > position || m_turretEncoder.getPosition() - 0.1 < position) {
            m_turretMotor.set((Math.abs(m_turretEncoder.getPosition()) - Math.abs(position))*0.5);
        }
        else{
            m_turretMotor.set(0);
        }
    }

    public void setTurretRaw(double speed) {
        m_turretMotor.set(speed * 0.05);
    }

    public void stopTurret() {
        m_turretMotor.stopMotor();
    }
    
    public void visionAlign(){
        m_turretMotor.set(tx.getDouble(0.0)*0.01);
        System.out.println(tx.getDouble(0.0));
    }

    public void setTurretMotor(double speed){
        if (Math.abs(m_turretEncoder.getPosition()) <= .24){
            m_turretMotor.set(speed);
        }
        else if (m_turretEncoder.getPosition() * speed < 0) { //Checks if speed and encoder position have opposite signs
            m_turretMotor.set(speed);
        }
        else{
            m_turretMotor.set(0);
        }
    }
    
    public double targetVelocity(){
        double hubY = Constants.Shooter.shooterHeight - Constants.Shooter.hubHeight;
        double hubX = getDistance()+0.61;
        double A = Math.toRadians(Constants.Shooter.shooterAngle);
        double velocity = Math.sqrt(((4.9 * hubX * hubX) / (Math.cos(A) * Math.cos(A))) * (1 / (hubY + (Math.tan(A) * hubX))));
        return velocity;
    }
     
    public double getDistance(){    
        double y = Auto.ty.getDouble(0.0);    
        return (Constants.Shooter.hubHeight - Constants.Shooter.shooterHeight)/Math.tan(Math.toRadians(Constants.Shooter.limelightAngle + y));
    }
}