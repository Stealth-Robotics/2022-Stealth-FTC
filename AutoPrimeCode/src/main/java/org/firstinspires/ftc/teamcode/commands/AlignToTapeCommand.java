package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class AlignToTapeCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final ColorSensorSubsystem colorSensors;
    double distance;
    int end_ticks;
    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    boolean done = false;

    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, ColorSensorSubsystem colorSensors, double distance) {
        this.drive = drive;
        this.colorSensors = colorSensors;
        this.distance = distance;
        addRequirements(drive);
    }

    int leftFalseCount = 0;

    @Override
    public void initialize() {
        done = false;
        leftFalseCount = 0;
    }

    @Override
    public void execute() {
        boolean lt = colorSensors.doesLeftSensorSeeTape();
        boolean rt = colorSensors.doesRightSensorSeeTape();

        if (lt) {
            leftFalseCount = 0;
        } else {
            leftFalseCount++;
        }

        double findTapeSpeed = 0.25;

        // I think "left" is positive, and "right" is negative.
        if (rt) {
            drive.drive(0, findTapeSpeed, 0); // Strafe left slowly
        } else if (lt) {
            drive.drive(0, -findTapeSpeed, 0); // Strafe right slowly
        }

        if (leftFalseCount > 4) {
            done = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
