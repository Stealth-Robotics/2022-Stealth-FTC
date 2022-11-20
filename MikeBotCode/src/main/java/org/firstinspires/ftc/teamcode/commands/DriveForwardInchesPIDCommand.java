package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class DriveForwardInchesPIDCommand extends CommandBase {
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

    int endTicks; // How far are we going?

    public static double kp = 0.001;
    public static double ki = 0.1;
    public static double kd = 0;
    public static double kf = 0;

    PIDFController pid = new PIDFController(kp, ki, kd, kf);

    public DriveForwardInchesPIDCommand(SimpleMecanumDriveSubsystem drive, double forward) {
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
    }

    @Override
    public void execute() {
        // Very simple PID to get us to the destination
        pid.setPIDF(kp, ki, kd, kf);
        double power = pid.calculate(drive.getTicks());
        power = Math.max(-0.5, Math.min(power, 0.5));
        drive.drive(power, 0, 0);

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
