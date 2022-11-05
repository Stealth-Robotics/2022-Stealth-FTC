package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Reset the elevator all the way down. Uses the limit switch to know when the hardware is down.
 */
public class ElevatorToPosition extends CommandBase {
    final ElevatorSubsystem elevator;
    final double position;

    public ElevatorToPosition(ElevatorSubsystem elevator, double position) {
        this.elevator = elevator;
        this.position = position;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setTargetLocation(position);
        elevator.goToPosition();
    }


    @Override
    public boolean isFinished() {
        return !elevator.isBusy();
    }
}
