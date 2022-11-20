package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class DriveForwardInchesCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    double distance;
    int endTicks;

    // From https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder/
    // Encoder Resolution           537.7 PPR at the Output Shaft
    // Encoder Resolution Formula   ((((1+(46/17))) * (1+(46/11))) * 28)
    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    public DriveForwardInchesCommand(SimpleMecanumDriveSubsystem drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        addRequirements(drive);
    }

    @Override
    public void initialize() {

        endTicks = drive.getTicks() + (int) (distance * TICKS_PER_IN);
        double drive_power = 0.5;
        if (distance < 0) {
            drive_power *= -1;
        }
        drive.drive(drive_power, 0, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        telemetry.addData("endTicks", endTicks);

        if (distance > 0) {
            if (drive.getTicks() > endTicks) {
                return true;
            }
        } else {
            if (drive.getTicks() < endTicks) {
                return true;
            }
        }
        return false;
    }
}
