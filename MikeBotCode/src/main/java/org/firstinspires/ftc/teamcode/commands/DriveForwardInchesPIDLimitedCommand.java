package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class DriveForwardInchesPIDLimitedCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double forward;

    // From https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder/
    // Encoder Resolution           537.7 PPR at the Output Shaft
    // Encoder Resolution Formula   ((((1+(46/17))) * (1+(46/11))) * 28)
    public static double MOTOR_TICS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double DISTANCE_PER_REVOLUTION_MM = WHEEL_DIAMETER_MM * Math.PI;
    public static double TICS_PER_MM = MOTOR_TICS_PER_REVOLUTION / DISTANCE_PER_REVOLUTION_MM;
    public static double TICS_PER_INCHES = TICS_PER_MM * 25.4;

    public static double maxSpeed = 0.5;
    public static double accelTimeSec = 1.0;

    int endTicks; // How far are we going?
    long startTime;

    public static double kp = 0.001;
    public static double ki = 0.1;
    public static double kd = 0;
    public static double kf = 0;

    PIDFController pid = new PIDFController(kp, ki, kd, kf);

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
        pid.reset();
        startTime = System.nanoTime();
    }

    @Override
    public void execute() {
        pid.setPIDF(kp, ki, kd, kf);
        double power = pid.calculate(drive.getTicks());

        // Super-simple acceleration limiter. Nothing for decel, the pid tuning handles that.
        double dt = (System.nanoTime() - startTime) * 1E-9;
        double speed = maxSpeed;
        if (dt < accelTimeSec) {
            speed = maxSpeed * (dt / accelTimeSec);
        }

        telemetry.addData("endTicks", endTicks);
        telemetry.addData("power", power);
        telemetry.addData("dt", dt);

        power = Math.max(-speed, Math.min(power, speed));
        drive.drive(power, 0, 0);
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
