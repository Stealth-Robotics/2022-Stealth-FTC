package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


@Config
public class GrabberSubsystem extends SubsystemBase {
    final Servo gripperServo;
    final Servo rotationServo;
    final Servo armServo;

    boolean open = true;
    boolean isUp = false;

    public double GRIPPER_CLOSED_POSITION = 0.4;
    public double GRIPPER_OPEN_POSITION = 0.7;

    public double ARM_UP_POSITION = 0.85;
    public double ARM_DOWN_POSITION = 0.3;

    public double ROTATOR_UP_POSITION = 0.4;
    public double ROTATOR_DOWN_POSITION = 0.7;

    public GrabberSubsystem(HardwareMap hardwareMap) {
        gripperServo = hardwareMap.get(Servo.class, "gripper");
        rotationServo = hardwareMap.get(Servo.class, "rotation");
        armServo = hardwareMap.get(Servo.class, "arm");
    }

    public void toggleOpen(){
        open = !open;
        if(open){
            gripperServo.setPosition(GRIPPER_OPEN_POSITION);
        }
        else{
            gripperServo.setPosition(GRIPPER_CLOSED_POSITION);
        }
    }

    public void toggleArm(){
        isUp = !isUp;
        if(isUp){
            armServo.setPosition(ARM_DOWN_POSITION);
            rotationServo.setPosition(ROTATOR_DOWN_POSITION);
        }
        else{
            armServo.setPosition(ARM_UP_POSITION);
            rotationServo.setPosition(ROTATOR_UP_POSITION);
        }
    }

    public void toggleArmDownCone(){
        isUp = !isUp;
        if(isUp){
            armServo.setPosition(ARM_DOWN_POSITION);
            //rotationServo.setPosition(ROTATOR_DOWN_POSITION);
        }
        else{
            armServo.setPosition(ARM_UP_POSITION);
            //rotationServo.setPosition(ROTATOR_UP_POSITION);
        }
    }
}