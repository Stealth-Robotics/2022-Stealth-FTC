package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GroundSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;


@Config
public class AlignToTapeCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final GroundSensorSubsystem groundSensor;
    final Direction direction;

    public enum Direction {LEFT, RIGHT}

    public static double ALIGNMENT_SPEED = 0.20;

    boolean lookingForAnyTape = false;
    boolean done = false;

    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, GroundSensorSubsystem groundSensor, Direction direction) {
        this.drive = drive;
        this.groundSensor = groundSensor;
        this.direction = direction;
        addRequirements(drive, groundSensor);
    }

    @Override
    public void initialize() {
        done = false;

        // If we see no tape to start, then start searching for tape given the given hint direction
        groundSensor.update();
        boolean lt = groundSensor.leftColorDetected();
        boolean rt = groundSensor.rightColorDetected();
        if (!lt && !rt) {
            lookingForAnyTape = true;
            if (direction == Direction.RIGHT) {
                drive.drive(0, ALIGNMENT_SPEED, 0); // Strafe right slowly
            } else {
                drive.drive(0, -ALIGNMENT_SPEED, 0); // Strafe left slowly
            }
        }
    }

    @Override
    public void execute() {
        groundSensor.update();
        boolean lt = groundSensor.leftColorDetected();
        boolean rt = groundSensor.rightColorDetected();

        StealthOpMode.telemetry.addData("Left see tape", lt);
        StealthOpMode.telemetry.addData("Right see tape", rt);

        if (rt) {
            drive.drive(0, ALIGNMENT_SPEED, 0); // Strafe right slowly
            lookingForAnyTape = false;
        } else if (lt) {
            drive.drive(0, -ALIGNMENT_SPEED, 0); // Strafe left slowly
            lookingForAnyTape = false;
        } else {
            done = !lookingForAnyTape;
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
