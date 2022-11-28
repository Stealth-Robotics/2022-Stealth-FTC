package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;


public class ResetElevator extends CommandBase {
    final ExtenderSubsystem extender;

    public ResetElevator(ExtenderSubsystem extender) {
        this.extender = extender;
        addRequirements(extender);
    }

    @Override
    public void initialize() {
        extender.setElevatorDownSlowly();
    }

    @Override
    public void end(boolean interrupted) {
        extender.completeReset();
    }

    @Override
    public boolean isFinished() {
        telemetry.addData("Reset Command RUNNING",0);
        return extender.checkVelocity();
    }
}