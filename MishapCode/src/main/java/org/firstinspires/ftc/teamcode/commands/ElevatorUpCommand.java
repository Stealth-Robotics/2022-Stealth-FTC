package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Spin a wheel slowly at first, then much faster until we've gone a certain distance.
 */
public class ElevatorUpCommand extends CommandBase {
    final ElevatorSubsystem elevator;

    // Positions where the wheel starts spinning quickly, and stops spinning
    public int FASTER_SPEED_TICKS = 400;
    public int STOPPING_POINT_TICKS = 50; // TODO: we need to figure this out!

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
        elevator.spinForwardSlow();
    }

    /**
     * Called continuously while the command is running. Once it's gone far enough, start spinning
     * it much faster.
     */
    @Override
    public void execute() {
//        if (Math.abs(elevator.getEncoderValue()) >= FASTER_SPEED_TICKS) {
//            elevator.spinForwardFast();
//        }
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
