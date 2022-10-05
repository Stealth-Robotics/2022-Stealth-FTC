package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;


public class ElevatorUpCommand extends CommandBase {
    final ElevatorSubsystem elevator;

    // Positions where the wheel starts spinning quickly, and stops spinning

    public int STOPPING_POINT_TICKS = 1575;
    public int FASTER_SPEED_TICKS = 100;
    public int SLOWER_SPEED_TICKS = STOPPING_POINT_TICKS - 500;

    /**
     * Remember our wheel subsystem for later.
     */
    public ElevatorUpCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    /**
     * Zero the wheel's encoder position so we can tell how far it has turned later, and then
     * start the will spinning slowly.
     */
    @Override
    public void initialize() {
        elevator.upSlow();
    }

    @Override
    public void execute() {
        if (elevator.getEncoderValue() >= SLOWER_SPEED_TICKS) {
            elevator.upSlow();
        } else if (elevator.getEncoderValue() >= FASTER_SPEED_TICKS) {
            elevator.upFast();
        }
    }

    /**
     * Just stop the wheel when we're done.
     */
    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }

    /**
     * Decide to stop when the wheel has turned passed the stopping point.
     */
    @Override
    public boolean isFinished() {
        return Math.abs(elevator.getEncoderValue()) >= STOPPING_POINT_TICKS;
    }
}
