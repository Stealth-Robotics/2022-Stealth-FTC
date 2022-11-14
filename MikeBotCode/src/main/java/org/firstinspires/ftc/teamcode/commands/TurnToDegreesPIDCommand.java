package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

public class TurnToDegreesPIDCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double degrees;
    double toRadians;

    PIDFController pid = new PIDFController(1, 0.2, 0, 0);

    public TurnToDegreesPIDCommand(SimpleMecanumDriveSubsystem drive, double degrees) {
        this.drive = drive;
        this.degrees = degrees;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        pid.reset();
        toRadians = Math.toRadians(degrees);
    }

    @Override
    public void execute() {
        double currentRadians = drive.getHeading();
        double delta = AngleUnit.normalizeRadians(toRadians - currentRadians);

        double power = pid.calculate(-delta);

        double maxSpeed = 0.5;
        power = Math.max(-maxSpeed, Math.min(power, maxSpeed));
        drive.drive(0, 0, power);

        telemetry.addData("toRadians", toRadians);
        telemetry.addData("currentRadians", currentRadians);
        telemetry.addData("delta", delta);
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
