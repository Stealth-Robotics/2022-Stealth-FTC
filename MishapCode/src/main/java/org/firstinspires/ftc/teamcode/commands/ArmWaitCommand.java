package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public class ArmWaitCommand extends CommandBase {
    final ArmSubsystem arm;

    public ArmWaitCommand(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        StealthOpMode.telemetry.addData("Arm", "Desired position %d", arm.getTargetLocation());
        StealthOpMode.telemetry.addData("Arm", "Current position %d", arm.getCurrentLocation());
        StealthOpMode.telemetry.update();
    }

    @Override
    public boolean isFinished() {
        return !arm.isBusy();
    }
}
