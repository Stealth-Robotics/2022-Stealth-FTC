package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

import java.util.function.BooleanSupplier;

public class DefaultArmCommand extends CommandBase {
    final ArmSubsystem arm;
    final BooleanSupplier armHalfSpeed, armDown, armUp;

    // Base speed of arm movement
    final int armIncrement = 10; // TODO: tune or new arm motor speed!!

    public DefaultArmCommand(ArmSubsystem arm, BooleanSupplier armHalfSpeed, BooleanSupplier armDown, BooleanSupplier armUp) {
        this.arm = arm;
        this.armHalfSpeed = armHalfSpeed;
        this.armDown = armDown;
        this.armUp = armUp;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        int armLocationDelta = armIncrement;
        if (armHalfSpeed.getAsBoolean()) {
            armLocationDelta /= 2;
        }
        if (armDown.getAsBoolean()) {
            arm.setTargetLocation(arm.getTargetLocation() - armLocationDelta);
        } else if (armUp.getAsBoolean()) {
            arm.setTargetLocation(arm.getTargetLocation() + armLocationDelta);
        }

        if (armDown.getAsBoolean() && arm.isAtLimitSwitch()) {
            arm.armLimitSwitchReset();
        }
    }
}
