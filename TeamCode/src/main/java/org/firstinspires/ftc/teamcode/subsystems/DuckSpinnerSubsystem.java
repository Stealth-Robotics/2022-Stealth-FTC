package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AllianceColor;

public class DuckSpinnerSubsystem extends SubsystemBase {
    final DcMotorEx duckDrive;

    // We need the alliance we're on so we can spin the wheel different directions. One way on the
    // blue side, the opposite way on the red side.
    final AllianceColor allianceColor;

    final int SLOW_SPEED = 500;
    final int SUPER_SPEED = 10000;
    final int AUTO_SPEED = 250;

    public DuckSpinnerSubsystem(HardwareMap hardwareMap, AllianceColor allianceColor) {
        this.allianceColor = allianceColor;
        duckDrive = hardwareMap.get(DcMotorEx.class, "Quack wheel");
        duckDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duckDrive.setDirection(DcMotor.Direction.FORWARD);
        duckDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duckDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        duckDrive.setVelocity(0);
    }

    public void spinForwardSlow() {
        if (allianceColor == AllianceColor.BLUE) {
            duckDrive.setVelocity(-SLOW_SPEED);
        } else {
            duckDrive.setVelocity(SLOW_SPEED);
        }
    }

    public void spinBackwardsSlow() {
        if (allianceColor == AllianceColor.BLUE) {
            duckDrive.setVelocity(SLOW_SPEED);
        } else {
            duckDrive.setVelocity(-SLOW_SPEED);
        }
    }

}
