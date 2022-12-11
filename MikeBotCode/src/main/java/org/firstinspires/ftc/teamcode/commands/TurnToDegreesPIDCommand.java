package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class TurnToDegreesPIDCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double toRadians;

    public static double kp = 1;
    public static double ki = 0.2;
    public static double kd = 0.1;
    public static double kf = 0;

    PIDFController pid = new PIDFController(kp, ki, kd, kf);

    public TurnToDegreesPIDCommand(SimpleMecanumDriveSubsystem drive, double degrees) {
        this.drive = drive;
        toRadians = Math.toRadians(degrees);
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        double currentRadians = drive.getHeading();
        double delta = AngleUnit.normalizeRadians(toRadians - currentRadians);

        pid.setPIDF(kp, ki, kd, kf);
        double power = pid.calculate(-delta);

        telemetry.addData("toRadians", toRadians);
        telemetry.addData("currentRadians", currentRadians);
        telemetry.addData("delta", delta);
        telemetry.addData("power", power);

        double maxSpeed = 0.8;
        power = Math.max(-maxSpeed, Math.min(power, maxSpeed));
        drive.drive(0, 0, power);
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
