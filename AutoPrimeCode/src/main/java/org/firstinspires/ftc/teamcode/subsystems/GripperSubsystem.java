package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class GripperSubsystem extends SubsystemBase {
    final Servo gripperServo;

    public double OPEN_POSITITON = 0.4;
    public double CLOSE_POSITION = 0.6;

    public GripperSubsystem(HardwareMap hardwareMap) {
        gripperServo = hardwareMap.get(Servo.class, "gripper");

    }

    public void open() {
        gripperServo.setPosition(OPEN_POSITITON);
    }

    public void close() {
        gripperServo.setPosition(CLOSE_POSITION);
    }


    public void periodic() {
        telemetry.addData("Gripper position", gripperServo.getPosition());

    }
}
