package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Keep moving the elevator to the target position we set elsewhere.
 *
 * This command never ends, and just keeps asking the elevator subsystem to update its position.
 */
public class DefaultElevatorCommand extends CommandBase {
    final ElevatorSubsystem elevator;

    public DefaultElevatorCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.goToPosition();
    }
}