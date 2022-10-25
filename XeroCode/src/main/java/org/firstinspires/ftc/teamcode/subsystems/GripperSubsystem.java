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
public class GripperSubsystem extends SubsystemBase {
    final Servo gripperServo;

    public static double CLOSED_POSITION = 0.5;
    public static double OPEN_POSITION = 0.9;

    public GripperSubsystem(HardwareMap hardwareMap) {
        gripperServo = hardwareMap.get(Servo.class, "gripper");
    }

    // TODO: we need methods to open and close the gripper, and then we need to bind them to buttons.

    /**
     * This is called all the time while the opmode is running.
     */
    @Override
    public void periodic() {
        telemetry.addData("Gripper position", gripperServo.getPosition());
    }
}


