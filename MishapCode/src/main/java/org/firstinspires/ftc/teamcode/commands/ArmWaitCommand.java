package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class ArmWaitCommand extends CommandBase {
    final ArmSubsystem arm;
    final Telemetry telemetry;

    public ArmWaitCommand(ArmSubsystem arm, Telemetry telemetry) {
        this.arm = arm;
        this.telemetry = telemetry;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        telemetry.addData("Arm", "Desired position %d", arm.getTargetLocation());
        telemetry.addData("Arm", "Current position %d", arm.getCurrentLocation());
        telemetry.update();
    }

    @Override
    public boolean isFinished() {
        return !arm.isBusy();
    }
}
