package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class GripperSubsystem extends SubsystemBase {
    final Servo gripper;

    public double OPEN_POSITION = 0.6;
    public double CLOSED_POSITION = 0.4;

    public GripperSubsystem(HardwareMap hardwareMap) {
        gripper = hardwareMap.get(Servo.class, "gripper");
    }

    //opens the gripper
    public void open() {
        gripper.setPosition(OPEN_POSITION);
    }

    //closes the gripper
    public void close() {
        gripper.setPosition(CLOSED_POSITION);
    }


    @Override
    public void periodic() {
        telemetry.addData("Gripper Position", gripper.getPosition());
    }
}
