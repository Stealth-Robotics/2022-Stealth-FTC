package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;


public class AlignToTapeCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final ColorSensorSubsystem colorSensors;

    boolean done = false;

    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, ColorSensorSubsystem colorSensors) {
        this.drive = drive;
        this.colorSensors = colorSensors;
        addRequirements(drive);
    }

    int leftFalseCount = 0;
    int rightFalseCount = 0;

    @Override
    public void initialize() {
        done = false;
        leftFalseCount = 0;
        rightFalseCount = 0;
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

        double findTapeSpeed = 0.25;

        // I think "left" is positive, and "right" is negative.
        if (rt) {
            drive.drive(0, findTapeSpeed, 0); // Strafe left slowly
        } else if (lt) {
            drive.drive(0, -findTapeSpeed, 0); // Strafe right slowly
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
