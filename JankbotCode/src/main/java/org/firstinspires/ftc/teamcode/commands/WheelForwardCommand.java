package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class WheelForwardCommand extends CommandBase {
    final ArmMotorSubsystem wheel;

    public WheelForwardCommand(ArmMotorSubsystem wheel) {
        this.wheel = wheel;
        addRequirements(wheel);
    }

    @Override
    public void initialize() {
        wheel.spinForward();
    }

    @Override
    public void end(boolean interrupted) {
        wheel.stop();
    }
}
