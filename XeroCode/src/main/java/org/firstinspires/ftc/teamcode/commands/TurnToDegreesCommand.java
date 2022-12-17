package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

public class TurnToDegreesCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double speed;
    double degrees;
    double endAngle;
    boolean positiveDirection;

    public TurnToDegreesCommand(SimpleMecanumDriveSubsystem drive, double degrees) {
        this.drive = drive;
        this.degrees = degrees;
        this.speed = 0.25;
        addRequirements(drive);
    }

    public TurnToDegreesCommand(SimpleMecanumDriveSubsystem drive, double degrees, double speed) {
        this.drive = drive;
        this.degrees = degrees;
        this.speed = speed;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        endAngle = Math.toRadians(degrees);
        positiveDirection = drive.getHeading() < endAngle;
        double drive_power = speed;
        if (!positiveDirection) {
            drive_power *= -1;
        }
        drive.drive(0, 0, drive_power);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        telemetry.addData("End Angle", endAngle);
        if (positiveDirection) {
            if (drive.getHeading() >= endAngle) {
                return true;
            }
        } else {
            if (drive.getHeading() <= endAngle) {
                return true;
            }
        }
        return false;
    }
}
