package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;

public class ElevatorBackwardManualCommand extends CommandBase {
    final ArmMotorSubsystem armMotors;

    public ElevatorBackwardManualCommand(ArmMotorSubsystem wheel) {
        this.armMotors = wheel;
        addRequirements(wheel);
    }

    @Override
    public void initialize() {
        //armMotors.resetEncoder();
    }

    @Override
    public void execute() {
        armMotors.spinBackwards();
    }

    @Override
    public void end(boolean interrupted) {
        armMotors.stop();
    }


    @Override
    public boolean isFinished() {
        //return Math.abs(wheels.getEncoderValueA()) >= STOPPING_POINT_TICKS;
        return false;
    }
}
