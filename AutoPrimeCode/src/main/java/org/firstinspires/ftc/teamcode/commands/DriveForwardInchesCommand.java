package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class DriveForwardInchesCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double distance;
    int end_ticks;
    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    //Variables for Pid Controller
    public static double pid_kp = 0.001;
    public static double pid_ki = 0.1;
    public static double pid_kd;
    public static double pid_kf;

    int endTicks; // How far are we going?
    long startTime;

    PIDFController pid = new PIDFController(pid_kp, pid_ki, pid_kd, pid_kf);

    public DriveForwardInchesCommand(SimpleMecanumDriveSubsystem drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        endTicks = drive.getTicks() - (int) (distance * TICKS_PER_IN);
        pid.setSetPoint(endTicks);
        pid.setTolerance(10);
        startTime = System.nanoTime();
    }

    @Override
    public void execute() {
        long now = System.nanoTime();
        double dt = (now - startTime) * 1E-9;
        pid.setPIDF(pid_kp, pid_ki, pid_kd, pid_kf);
        // Very simple PID to get us to the destination
        double power = pid.calculate(drive.getTicks()) * -1.0;
        double maxSpeed = 0.5;
        if (dt < 0.5) {
            maxSpeed = maxSpeed * dt * 2;
        }

        power = Math.max(-maxSpeed, Math.min(power, maxSpeed));
        drive.drive(power, 0, 0);

        telemetry.addData("endTicks", endTicks);
        telemetry.addData("currentTicks", drive.getTicks());
        telemetry.addData("power", power);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return pid.atSetPoint();
    }
}
