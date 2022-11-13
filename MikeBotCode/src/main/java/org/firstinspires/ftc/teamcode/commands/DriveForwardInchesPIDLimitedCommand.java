package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

public class DriveForwardInchesPIDLimitedCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double forward;

    public static double MOTOR_TICS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double DISTANCE_PER_REVOLUTION_MM = WHEEL_DIAMETER_MM * Math.PI;
    public static double TICS_PER_MM = MOTOR_TICS_PER_REVOLUTION / DISTANCE_PER_REVOLUTION_MM;
    public static double TICS_PER_INCHES = TICS_PER_MM * 25.4;

    int endTicks; // How far are we going?
    long startTime;
    double lastPower;

    PIDFController pid = new PIDFController(0.001, 0.1, 0, 0);

    public DriveForwardInchesPIDLimitedCommand(SimpleMecanumDriveSubsystem drive, double forward) {
        this.drive = drive;
        this.forward = forward;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        endTicks = drive.getTicks() + (int) (forward * TICS_PER_INCHES);
        pid.setSetPoint(endTicks);
        pid.setTolerance(10);
        startTime = System.nanoTime();
        lastPower = 0;
    }

    @Override
    public void execute() {
        double power = pid.calculate(drive.getTicks());

        // Super-simple acceleration limiter. Nothing for decel, the pid tuning handles that.
        double dt = (System.nanoTime() - startTime) * 1E-9;
        double maxSpeed = 0.5;
        double accelTimeSec = 1.0;
        double speed = maxSpeed;
        if (dt < accelTimeSec) {
            speed = maxSpeed * (accelTimeSec * dt);
        }
        power = Math.max(-speed, Math.min(power, speed));

        drive.drive(power, 0, 0);

        telemetry.addData("endTicks", endTicks);
        telemetry.addData("power", power);
        telemetry.addData("dt", dt);
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
