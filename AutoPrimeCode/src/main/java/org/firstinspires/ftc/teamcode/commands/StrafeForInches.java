package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class StrafeForInches extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double distance;
    int end_ticks;
    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    public StrafeForInches(SimpleMecanumDriveSubsystem drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        end_ticks = drive.getTicks() + (int) (distance * TICKS_PER_IN);
        double drive_power = -0.5;
        if (distance < 0) {
            drive_power *= -1;
        }
        drive.drive(0, drive_power, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        if (distance > 0) {
            if (drive.getTicks() > end_ticks) {
                return true;
            }
        } else {
            if (drive.getTicks() < end_ticks) {
                return true;
            }
        }
        return false;
    }
}
