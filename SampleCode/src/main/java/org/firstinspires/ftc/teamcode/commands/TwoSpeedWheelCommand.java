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

    public TwoSpeedWheelCommand(WheelSubsystem wheel) {
        this.wheel = wheel;
        addRequirements(wheel);
    }

    @Override
    public void initialize() {
        wheel.resetEncoder();
        wheel.spinForwardSlow();
    }

    @Override
    public void execute() {
        if (Math.abs(wheel.getEncoderValue()) >= FASTER_SPEED_TICKS) {
            wheel.spinForwardFast();
        }
    }

    @Override
    public void end(boolean interrupted) {
        wheel.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(wheel.getEncoderValue()) >= STOPPING_POINT_TICKS;
    }
}
