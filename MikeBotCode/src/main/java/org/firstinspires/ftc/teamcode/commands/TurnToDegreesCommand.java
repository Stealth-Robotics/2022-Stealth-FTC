package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class TurnToDegreesCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double degrees;
    double end_angle;
    boolean positive_direction;

    public TurnToDegreesCommand(SimpleMecanumDriveSubsystem drive, double degrees) {
        this.drive = drive;
        this.degrees = degrees;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        end_angle = Math.toRadians(degrees);
        positive_direction = drive.getHeading() < end_angle;
        double drive_power = 0.5;
        if (!positive_direction) {
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
        telemetry.addData("End Angle", end_angle);

        if (positive_direction) {
            if (drive.getHeading() > end_angle) {
                return true;
            }
        } else {
            if (drive.getHeading() < end_angle) {
                return true;
            }
        }
        return false;
    }
}
