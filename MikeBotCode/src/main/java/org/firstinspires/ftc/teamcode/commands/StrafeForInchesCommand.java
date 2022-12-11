package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class StrafeForInchesCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double distance;
    int endTicks;

    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    /**
     * Strafe approx. a number of inches.
     * @param drive the drive subsystem
     * @param distance positive is right, negative is left
     */
    public StrafeForInchesCommand(SimpleMecanumDriveSubsystem drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        // nb: we're going in "reverse" when strafing
        endTicks = drive.getTicks() - (int) (distance * TICKS_PER_IN);
        double drive_power = 0.25;
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
        telemetry.addData("endTicks", endTicks);

        if (distance > 0) {
            if (drive.getTicks() < endTicks) {
                return true;
            }
        } else {
            if (drive.getTicks() > endTicks) {
                return true;
            }
        }
        return false;
    }
}
