package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;

/**
 * Spin a wheel slowly at first, then much faster until we've gone a certain distance.
 */
public class ArmExtenderCommand extends CommandBase {
    final ArmMotorSubsystem wheels;

    // Positions where the wheel starts spinning quickly, and stops spinning
    public int FASTER_SPEED_TICKS = 400;
    public int STOPPING_POINT_TICKS = 2000;

    /**
     * Remember our wheel subsystem for later.
     */
    public ArmExtenderCommand(ArmMotorSubsystem wheel) {
        this.wheels = wheel;
        addRequirements(wheel);
    }

    /**
     * Zero the wheel's encoder position so we can tell how far it has turned later, and then
     * start the will spinning slowly.
     */
    @Override
    public void initialize() {
        wheels.resetEncoder();

    }

    /**
     * Called continuously while the command is running. Once it's gone far enough, start spinning
     * it much faster.
     */
    @Override
    public void execute() {
        wheels.spinForward();
    }

    /**
     * Just stop the wheel when we're done.
     */
    @Override
    public void end(boolean interrupted) {
        wheels.stop();
    }

    /**
     * Decide to stop when the wheel has turned passed the stopping point.
     */
    @Override
    public boolean isFinished() {
        return Math.abs(wheels.getEncoderValueA()) >= STOPPING_POINT_TICKS;
    }
}
