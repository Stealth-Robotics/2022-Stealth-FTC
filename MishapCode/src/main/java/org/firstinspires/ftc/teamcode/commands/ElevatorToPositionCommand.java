package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * Keep moving the elevator to the target position we set elsewhere.
 *
 * This command never ends, and just keeps asking the elevator subsystem to update its position.
 */
public class ElevatorToPositionCommand extends CommandBase {
    final ElevatorSubsystem elevator;
    final double percent;

    public ElevatorToPositionCommand(ElevatorSubsystem elevator, double percent) {
        this.elevator = elevator;
        this.percent = percent;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setTargetLocation(percent);
        elevator.goToPosition();
    }

    @Override
    public void execute() {
        elevator.goToPosition();
    }

    @Override
    public boolean isFinished() {
        return !elevator.isElevatorBusy();
    }
}