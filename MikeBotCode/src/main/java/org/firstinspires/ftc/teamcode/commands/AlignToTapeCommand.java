package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

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

    public AlignToTapeCommand(SimpleMecanumDriveSubsystem drive, GroundSensorSubsystem groundSensor, Direction direction) {
        this.drive = drive;
        this.groundSensor = groundSensor;
        this.direction = direction;
        addRequirements(drive, groundSensor);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double speed = ALIGNMENT_SPEED;
        if (direction == Direction.LEFT) speed *= -1;
        telemetry.addData("Alignment speed", speed);
        drive.drive(0, speed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return groundSensor.centerColorDetected();
    }
}
