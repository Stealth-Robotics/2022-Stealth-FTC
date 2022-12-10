package org.firstinspires.ftc.teamcode.subsystems;

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
    boolean isUp = true;

    public static double  GRIPPER_CLOSED_POSITION = 0.6;
    public static  double GRIPPER_OPEN_POSITION = 0.5;

    public static double ARM_SCORE_POSITION = 0.75;
    public static double ARM_UP_POSITION = 0.7;
    public static double ARM_DOWN_POSITION = 0.15;

    public static double ARM_DOWN_POSITION_LOWEST = 0.2;
    public static double ARM_DOWN_POSITION_HIGHER = 0.4;


    public static double ROTATOR_SCORE_POSITION = 0.2;
    public static double ROTATOR_UP_POSITION =0.5;
    public static double ROTATOR_DOWN_POSITION = 0.9;


    public static double ROTATOR_DOWN_POSITION_LOWEST = 0.9;
    public static double ROTATOR_DOWN_POSITION_HIGHER = 0.7;


    private enum ArmPosition {
        score,
        up,
        down
    }
    private ArmPosition armPosition = ArmPosition.down;;

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
    public void openGripper(){
        gripperServo.setPosition(GRIPPER_OPEN_POSITION);
        open = true;
    }
    public void closeGripper(){
        open = false;
        gripperServo.setPosition(GRIPPER_CLOSED_POSITION);
    }

    public void armScorePosition(){
        armServo.setPosition(ARM_SCORE_POSITION);
        rotationServo.setPosition(ROTATOR_SCORE_POSITION);
        armPosition = armPosition.score;
    }
    public void armUpPosition(){
        armServo.setPosition(ARM_UP_POSITION);
        rotationServo.setPosition(ROTATOR_UP_POSITION);
        armPosition=armPosition.up;
    }
    public void armPickupPosition(){
        armServo.setPosition(ARM_DOWN_POSITION);
        rotationServo.setPosition(ROTATOR_DOWN_POSITION);
        armPosition= armPosition.down;
    }
    public void armAutoPickupPosition(double autoOffset, double rotOffset){
        armServo.setPosition(ARM_DOWN_POSITION + autoOffset);
        rotationServo.setPosition(ROTATOR_DOWN_POSITION + rotOffset);

        armPosition= armPosition.down;

    }
    public void toggleArm(){
        switch (armPosition) {
            case down:
                armPosition = ArmPosition.up;
                armUpPosition();
                closeGripper();
                break;
            case up:
                armPosition = ArmPosition.score;
                armScorePosition();
                break;
            case score:
                armPosition = ArmPosition.down;
                armPickupPosition();
                closeGripper();
                break;
        }


        /*
        isUp = !isUp;
        if(isUp){
            armServo.setPosition(ARM_DOWN_POSITION);
            rotationServo.setPosition(ROTATOR_DOWN_POSITION);
        }
        else{
            armServo.setPosition(ARM_UP_POSITION);
            rotationServo.setPosition(ROTATOR_UP_POSITION);
        }*/
    }
    public void toggleArmNoScorePos() {
        switch (armPosition) {
            case up:
                armPickupPosition();
                closeGripper();
                break;
            case down:
                armUpPosition();
                closeGripper();
                break;
        }
    }

        public void toggleArmHigher(){
        isUp = !isUp;
        if(isUp){
            armServo.setPosition(ARM_DOWN_POSITION_HIGHER);
            rotationServo.setPosition(ROTATOR_DOWN_POSITION_HIGHER);
        }
        else{
            armServo.setPosition(ARM_UP_POSITION);
            //rotationServo.setPosition(ROTATOR_UP_POSITION);
        }
    }
    public void toggleArmLowest(){
        isUp = !isUp;
        if(isUp){
            armServo.setPosition(ARM_DOWN_POSITION_LOWEST);
            rotationServo.setPosition(ROTATOR_DOWN_POSITION_LOWEST);
        }
        else{
            armServo.setPosition(ARM_UP_POSITION);
            rotationServo.setPosition(ROTATOR_UP_POSITION);
        }
    }

    public void toggleArmDownCone(){
        isUp = !isUp;
        if (isUp) {
            armServo.setPosition(ARM_DOWN_POSITION);
            rotationServo.setPosition(ROTATOR_UP_POSITION);
            armPosition = armPosition.down;
        } else {
            armServo.setPosition(ARM_UP_POSITION);
            rotationServo.setPosition(ROTATOR_UP_POSITION);
            armPosition = armPosition.up;
        }


    }

    public void setArmPositionUp() {
        armServo.setPosition(ARM_UP_POSITION);
        isUp = true;
    }

    public void setArmPositionDown() {
        armServo.setPosition(ARM_DOWN_POSITION);
        isUp = !true;
        armPosition = armPosition.down;
    }

    public void setRotationPositionUp() {
        rotationServo.setPosition(ROTATOR_UP_POSITION);

    }

    public void setRotationPositionDown() {
        rotationServo.setPosition(ROTATOR_DOWN_POSITION);
    }
    public void setRotationPositionScore() {
        rotationServo.setPosition(ROTATOR_SCORE_POSITION);
    }

}