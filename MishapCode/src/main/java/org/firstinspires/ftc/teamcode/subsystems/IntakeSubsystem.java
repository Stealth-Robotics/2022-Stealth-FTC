package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    final DcMotorEx intakeDrive;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeDrive = hardwareMap.get(DcMotorEx.class, "intake");
        intakeDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeDrive.setDirection(DcMotor.Direction.FORWARD);
        intakeDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        intakeDrive.setPower(0);
    }

    public void in() {
        intakeDrive.setPower(-1.0);
    }

    public void out() {
        intakeDrive.setPower(1.0);
        intakeDrive.setVelocity(400);
    }
}
