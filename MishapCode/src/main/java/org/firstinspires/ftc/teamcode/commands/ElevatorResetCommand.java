package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

@Config
public class ElevatorResetCommand extends CommandBase {
    final ElevatorSubsystem elevator;

    // Positions where the wheel starts spinning quickly, and stops spinning

    public static int STOPPING_POINT_TICKS = 1575;
    public static int FASTER_SPEED_TICKS = 100;
    public static int SLOWER_SPEED_TICKS = STOPPING_POINT_TICKS - 500;
//
    /**
     * Remember our wheel subsystem for later.
     */
    public ElevatorResetCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    /**
     * Zero the wheel's encoder position so we can tell how far it has turned later, and then
     * start the will spinning slowly.
     */
    @Override
    public void initialize() {
        elevator.downSlowForReset();
    }


    /**
     * Just stop the wheel when we're done.
     */
    @Override
    public void end(boolean interrupted) {
        elevator.completeReset();
    }

    /**
     * Decide to stop when the wheel has turned passed the stopping point.
     */
    @Override
    public boolean isFinished() {
        return elevator.isStalled();
    }
}
