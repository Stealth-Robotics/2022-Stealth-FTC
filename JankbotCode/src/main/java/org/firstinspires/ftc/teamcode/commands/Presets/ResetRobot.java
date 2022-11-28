package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class ResetRobot extends SequentialCommandGroup {

    public ResetRobot(ExtenderSubsystem extender, GrabberSubsystem grabber) {

        addCommands(
                new ParallelCommandGroup(

                        new InstantCommand(() -> grabber.setArmPosition(grabber.ARM_DOWN_POSITION_LOWEST)),
                        new InstantCommand(() -> grabber.setRotationServo(grabber.ROTATOR_UP_POSITION)),
                        new InstantCommand(() -> grabber.toggleOpen())
                )//,
        //  new ResetElevator(extender),

        );

        addRequirements(extender, grabber);
    }
}