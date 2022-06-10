package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;

/**
 * Spin a wheel slowly at first, then much faster until we've gone a certain distance.
 */
public class TwoSpeedWheelCommand extends CommandBase {
    final WheelSubsystem wheel;

    // Positions where the wheel starts spinning quickly, and stops spinning
    public int FASTER_SPEED_TICKS = 400;
    public int STOPPING_POINT_TICKS = 2000;

    /**
     * Remember our wheel subsystem for later.
     */
    public TwoSpeedWheelCommand(WheelSubsystem wheel) {
        this.wheel = wheel;
        addRequirements(wheel);
    }

    /**
     * Zero the wheel's encoder position so we can tell how far it has turned later, and then
     * start the will spinning slowly.
     */
    @Override
    public void initialize() {
        wheel.resetEncoder();
        wheel.spinForwardSlow();
    }

    /**
     * Called continuously while the command is running. Once it's gone far enough, start spinning
     * it much faster.
     */
    @Override
    public void execute() {
        if (Math.abs(wheel.getEncoderValue()) >= FASTER_SPEED_TICKS) {
            wheel.spinForwardFast();
        }
    }

    /**
     * Just stop the wheel when we're done.
     */
    @Override
    public void end(boolean interrupted) {
        wheel.stop();
    }

    /**
     * Decide to stop when the wheel has turned passed the stopping point.
     */
    @Override
    public boolean isFinished() {
        return Math.abs(wheel.getEncoderValue()) >= STOPPING_POINT_TICKS;
    }
}
