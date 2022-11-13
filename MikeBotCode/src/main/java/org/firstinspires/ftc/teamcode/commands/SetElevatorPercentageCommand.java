package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;


public class SetElevatorPercentageCommand extends CommandBase {
    final ElevatorSubsystem elevator;
    double percentage;

    public SetElevatorPercentageCommand(ElevatorSubsystem elevator, double percentage) {
        this.elevator = elevator;
        this.percentage = percentage;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setTargetLocation(percentage);
        elevator.goToPosition();
    }

    @Override
    public boolean isFinished() {
        return !elevator.isBusy();
    }
}

