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
 * A very simple subsystem that has a single wheel that you can turn.
 */
@Config
public class GrabberSubsystem extends SubsystemBase {
    final Servo grabberServo;
    final Servo liftServo;
    final Servo rotateServo;

    public static double OPEN_POSITION = 0.5;
    public static double CLOSED_POSITION = 0.75;
    public static double UP_POSITION = .1;
    public static double DOWN_POSITION = .5;
    public static double LEFT_POSITION = .7;
    public static double RIGHT_POSITION = 0;
    boolean open = true;
    final int CENTER_POSITION = 0;

    public GrabberSubsystem(HardwareMap hardwareMap) {
        grabberServo = hardwareMap.get(Servo.class, "grabberServo");
        liftServo = hardwareMap.get(Servo.class, "liftServo");
        rotateServo = hardwareMap.get(Servo.class, "rotateServo");

    }
    public void toggleOpen(){
        open = !open;
        if(open){
            grabberServo.setPosition(OPEN_POSITION);
        }
        else{
            grabberServo.setPosition(CLOSED_POSITION);
        }
    }

    public void up(){
        liftServo.setPosition(UP_POSITION);
    }
    public void down(){
        liftServo.setPosition(DOWN_POSITION);
    }
    public void left(){
        rotateServo.setPosition(LEFT_POSITION);
    }
    public void right(){
        rotateServo.setPosition(RIGHT_POSITION);
    }





}
