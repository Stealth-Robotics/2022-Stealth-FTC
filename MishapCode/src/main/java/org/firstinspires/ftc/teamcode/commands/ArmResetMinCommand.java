package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class ArmResetMinCommand extends CommandBase {
    final ArmSubsystem arm;

    public ArmResetMinCommand(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.startLimitSwitchReset();
    }

    @Override
    public void end(boolean interrupted) {
        arm.armLimitSwitchReset();
    }

    @Override
    public boolean isFinished() {
        return arm.isAtLimitSwitch();
    }
}
