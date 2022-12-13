package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;


public class AlignToTapeCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final ColorSensorSubsystem colorSensors;

    public enum Direction {LEFT, RIGHT}

    final Direction directionHint;

    boolean done = false;
    boolean lookingForAnyTape = false;
    double findTapeSpeed = 0.25;


    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, ColorSensorSubsystem colorSensors, Direction directionHint) {
        this.drive = drive;
        this.colorSensors = colorSensors;
        this.directionHint = directionHint;
        addRequirements(drive);
    }

    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, ColorSensorSubsystem colorSensors) {
        this(drive, colorSensors, Direction.RIGHT);
    }

    int leftFalseCount = 0;
    int rightFalseCount = 0;

    @Override
    public void initialize() {
        done = false;
//        leftFalseCount = 0;
//        rightFalseCount = 0;
        boolean lt = colorSensors.doesLeftSensorSeeTape();
        boolean rt = colorSensors.doesRightSensorSeeTape();

        if (!lt && !rt) {
            lookingForAnyTape = true;
            if (directionHint == Direction.RIGHT) {
                drive.drive(0, -findTapeSpeed, 0); // Strafe right slowly
            } else {
                drive.drive(0, findTapeSpeed, 0); // Strafe left slowly
            }
        }
    }

    @Override
    public void execute() {
        boolean lt = colorSensors.doesLeftSensorSeeTape();
        boolean rt = colorSensors.doesRightSensorSeeTape();

        StealthOpMode.telemetry.addData("Left see tape", lt);
        StealthOpMode.telemetry.addData("Right see tape", rt);

//        if (lt) {
//            leftFalseCount = 0;
//        } else {
//            leftFalseCount++;
//        }
//
//        if (rt) {
//            rightFalseCount = 0;
//        } else {
//            rightFalseCount++;
//        }


        // I think "left" is positive, and "right" is negative.
        if (rt) {
            drive.drive(0, findTapeSpeed, 0); // Strafe left slowly
            lookingForAnyTape = false;
        } else if (lt) {
            drive.drive(0, -findTapeSpeed, 0); // Strafe right slowly
            lookingForAnyTape = false;
        } else if (lookingForAnyTape) {
            // do nothing
        } else {
            done = true;
        }

//        if (leftFalseCount > 10) {
//            done = true;
//        }
//
//        if (rightFalseCount > 10) {
//            done = true;
//        }
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
