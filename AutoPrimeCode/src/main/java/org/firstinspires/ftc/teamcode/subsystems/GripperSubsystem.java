package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class GripperSubsystem extends SubsystemBase {
    final Servo gripperServo;

    public static double OPEN_POSITION = 0.68;
    public static double CLOSE_POSITION = 0.45;
    private boolean open = false;

    public GripperSubsystem(HardwareMap hardwareMap) {
        gripperServo = hardwareMap.get(Servo.class, "gripper");

    }

    public void open() {
        gripperServo.setPosition(OPEN_POSITION);
        open = true;
    }

    public void close() {
        gripperServo.setPosition(CLOSE_POSITION);
        open = false;
    }

    public void toggle() {
        if (open) {
            close();
        }else{
            open();
        }
    }


    public void periodic() {
        telemetry.addData("Gripper position", gripperServo.getPosition());
    }
}
