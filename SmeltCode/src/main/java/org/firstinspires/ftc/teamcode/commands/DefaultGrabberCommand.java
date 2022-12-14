package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Command to move grabber with joystick
 */
public class DefaultGrabberCommand extends CommandBase {
    final GrabberSubsystem grabber;
    DoubleSupplier leftY, leftX, heading, liftPos;

    public DefaultGrabberCommand(GrabberSubsystem grabber, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier heading, DoubleSupplier liftPos) {
        this.grabber = grabber;
        this.leftX = leftX;
        this.leftY = leftY;
        this.heading = heading;
        this.liftPos = liftPos;
        addRequirements(grabber);
    }

    @Override
    public void execute(){
        //if bot is facing normally, or if trying to score rotate as expected
        if((Math.toDegrees(heading.getAsDouble()) > -90 && Math.toDegrees(heading.getAsDouble()) < 90) || liftPos.getAsDouble() > 1000){
            grabber.setPos(grabber.getPos() + .01 * leftX.getAsDouble());
            telemetry.addData("normal rotate: ", "true");
        }
        //"field centric" grabber movement when grabber is at bottom and bot is facing toward driver
        else{
            grabber.setPos(grabber.getPos() - .01 * leftX.getAsDouble());
            telemetry.addData("normal rotate: ", "false");
        }

        grabber.setLiftPos(grabber.getLiftPos() - .01 * leftY.getAsDouble());


        telemetry.addData("rotate pos", grabber.getPos());
        telemetry.addData("lift pos", grabber.getLiftPos());
    }


}
