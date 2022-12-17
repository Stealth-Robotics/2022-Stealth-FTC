package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Reset the elevator all the way down. Uses the limit switch to know when the hardware is down.
 */
public class ResetElevatorCommand extends CommandBase {
    final ElevatorSubsystem elevator;

    public ResetElevatorCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.downSlowForReset();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.completeReset();
    }

    @Override
    public boolean isFinished() {
        return elevator.isStalled();
    }
}