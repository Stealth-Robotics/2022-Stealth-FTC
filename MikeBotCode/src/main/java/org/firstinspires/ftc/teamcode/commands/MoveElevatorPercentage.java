package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class MoveElevatorPercentage extends CommandBase {
    final ElevatorSubsystem elevator;
    double distance;

    public MoveElevatorPercentage(ElevatorSubsystem elevator, double distance) {
        this.elevator = elevator;
        this.distance = distance;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setTargetLocation(distance);
        elevator.goToPosition();
    }

    @Override
    public boolean isFinished() {
        return !elevator.isBusy();
    }
}

