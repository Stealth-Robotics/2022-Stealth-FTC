package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class DriveForwardInchesPID extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double forward;
    final double speed;

    public static double p = 0.0007;
    public static double i = 0.08;
    public static double d = 0.0001;

    public static double MOTOR_TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double DISTANCE_PER_REVOLUTION_MM = WHEEL_DIAMETER_MM * Math.PI;
    public static double TICKS_PER_MM = MOTOR_TICKS_PER_REVOLUTION / DISTANCE_PER_REVOLUTION_MM;
    public static double TICKS_PER_INCH = TICKS_PER_MM * 25.4;

    int endTicks;
    long startTime;
    int slowTime = 1; //measured in seconds

    PIDFController pid = new PIDFController(p, i, d, 0);

    public DriveForwardInchesPID(SimpleMecanumDriveSubsystem drive, double forward, double speed) {
        this.drive = drive;
        this.forward = forward;
        this.speed = speed;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        endTicks = drive.getTicks() + (int) (forward * TICKS_PER_INCH);
        pid.setSetPoint(endTicks);
        pid.setTolerance(10, 10);
        startTime = System.nanoTime();
    }

    @Override
    public void execute() {
        pid.setPIDF(p, i, d, 0);
        long now = System.nanoTime();
        double dt = (now - startTime) * 1E-9;
        double power;
        power = pid.calculate(drive.getTicks());
        if (dt < slowTime)
        {
            double speedLimit = dt / slowTime;
            power = Math.max(-speedLimit, Math.min(power, speedLimit));
        }
        else
        {
            power = Math.max(-0.5, Math.min(power, 0.5));
        }
        drive.drive(power, 0, 0, 1);
        telemetry.addData("endTicks", endTicks);
        telemetry.addData("power", power);
    }

    @Override
    public boolean isFinished() {
        return pid.atSetPoint();
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
