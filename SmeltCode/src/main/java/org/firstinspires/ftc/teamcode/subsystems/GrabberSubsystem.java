package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.stealthrobotics.library.Alliance;

/**
 * Subsystem that contains all the servos for the grabber
 */
@Config
public class GrabberSubsystem extends SubsystemBase {
    final Servo grabberServo;
    final Servo liftServo;
    final Servo rotateServo;

    public static double OPEN_POSITION = 0.4;
    public static double CLOSED_POSITION = 0.65;
    public static double UP_POSITION = .29;
    public static double DOWN_POSITION = .64;
    public static double LEFT_POSITION = .9;
    public static double RIGHT_POSITION = 0;
    boolean open = true;
    public GrabberSubsystem(HardwareMap hardwareMap) {
        grabberServo = hardwareMap.get(Servo.class, "grabberServo");
        liftServo = hardwareMap.get(Servo.class, "liftServo");
        rotateServo = hardwareMap.get(Servo.class, "rotateServo");

    }

    public void toggleOpen() {
        open = !open;
        if (open) {
            grabberServo.setPosition(OPEN_POSITION);
        } else {
            grabberServo.setPosition(CLOSED_POSITION);
        }
    }

    public void grabberClose() {
        grabberServo.setPosition(CLOSED_POSITION);
    }

    public void grabberOpen() {
        grabberServo.setPosition(OPEN_POSITION);
    }

    public void up() {
        liftServo.setPosition(UP_POSITION);
    }

    public void down() {
        liftServo.setPosition(DOWN_POSITION);
    }

    public void left() {
        rotateServo.setPosition(LEFT_POSITION);
    }

    public void right() {
        rotateServo.setPosition(RIGHT_POSITION);
    }

    public double getPos() {
        return rotateServo.getPosition();
    }

    public void setPos(double pos) {
        rotateServo.setPosition(pos);
    }

    public double getLiftPos() {
        return liftServo.getPosition();
    }

    public void setLiftPos(double pos) {
        liftServo.setPosition(pos);
    }
    public void setGrabberPos(double pos){
        grabberServo.setPosition(pos);
    }


}
