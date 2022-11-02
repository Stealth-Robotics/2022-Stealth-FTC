package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This subsystem represents the gripper on our robots. We have a single servo.
 */
@Config
public class GrabberSubsystem extends SubsystemBase {
    final Servo gripperServo;
    final Servo rotationServo;
    final Servo armServo;

    boolean open = true;

    public static double GRIPPER_CLOSED_POSITION = 0;
    public static double GRIPPER_OPEN_POSITION = 0.3;


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

    public void rotaterToPosition(double position) {
        rotationServo.setPosition(position);
    }
    public void armToPosition(double position) {
        armServo.setPosition(position);
    }
    /**
     * This is called all the time while the opmode is running.
     */
    @Override
    public void periodic() {
        telemetry.addData("Gripper position", gripperServo.getPosition());
    }
}