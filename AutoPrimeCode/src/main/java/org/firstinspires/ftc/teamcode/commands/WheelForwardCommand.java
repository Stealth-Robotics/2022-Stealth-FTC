package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;

public class WheelForwardCommand extends CommandBase {
    final WheelSubsystem wheel;

    public WheelForwardCommand(WheelSubsystem wheel) {
        this.wheel = wheel;
        addRequirements(wheel);
    }

    @Override
    public void initialize() {
        wheel.spinForwardSlow();
    }

    @Override
    public void end(boolean interrupted) {
        wheel.stop();
    }
}
