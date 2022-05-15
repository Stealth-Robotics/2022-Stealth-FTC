package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

public class QuackSpinnerSubsystem extends SubsystemBase {
    final DcMotorEx duckDrive;

    final int SLOW_SPEED = 500;
    final int SUPER_SPEED = 10000;
    final int AUTO_SPEED = 250;

    public QuackSpinnerSubsystem(HardwareMap hardwareMap) {
        duckDrive = hardwareMap.get(DcMotorEx.class, "Quack wheel");
        duckDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duckDrive.setDirection(DcMotor.Direction.FORWARD);
        duckDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duckDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetEncoder() {
        duckDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int getEncoderValue() {
        return duckDrive.getCurrentPosition();
    }

    public void stop() {
        duckDrive.setVelocity(0);
    }

    public void spinForwardSlow() {
        if (Alliance.isBlue()) {
            duckDrive.setVelocity(-SLOW_SPEED);
        } else {
            duckDrive.setVelocity(SLOW_SPEED);
        }
    }

    public void spinBackwardsSlow() {
        if (Alliance.isBlue()) {
            duckDrive.setVelocity(SLOW_SPEED);
        } else {
            duckDrive.setVelocity(-SLOW_SPEED);
        }
    }

    public void spinForwardSuper() {
        if (Alliance.isBlue()) {
            duckDrive.setVelocity(-SUPER_SPEED);
        } else {
            duckDrive.setVelocity(SUPER_SPEED);
        }
    }

    public void spinBackwardsSuper() {
        if (Alliance.isBlue()) {
            duckDrive.setVelocity(SUPER_SPEED);
        } else {
            duckDrive.setVelocity(-SUPER_SPEED);
        }
    }

    public void spinForwardAuto() {
        if (Alliance.isBlue()) {
            duckDrive.setVelocity(-AUTO_SPEED);
        } else {
            duckDrive.setVelocity(AUTO_SPEED);
        }
    }


}
