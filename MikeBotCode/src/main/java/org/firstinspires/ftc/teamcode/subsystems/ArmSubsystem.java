package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public class ArmSubsystem extends SubsystemBase {
    final DcMotorEx armMotor;
    final DigitalChannel armLimitSwitch;
    final DigitalChannel armLimitSwitch2;

    public static final int MIN_POSITION = 0;
    public static final int MAX_POSITION = 2000;

    public ArmSubsystem(HardwareMap hardwareMap) {
        armLimitSwitch = hardwareMap.get(DigitalChannel.class, "armLimitSwitch");
        armLimitSwitch2 = hardwareMap.get(DigitalChannel.class, "armLimitSwitch2");

        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPositionTolerance(10);
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setVelocityPIDFCoefficients(5.0, 0.01, 0.0, 7.0);
        // armDrive.setPositionPIDFCoefficients(50.0);

        armMotor.setCurrentAlert(2000, CurrentUnit.MILLIAMPS);
    }

    @Override
    public void periodic() {
        StealthOpMode.telemetry.addData("Arm:Position", armMotor.getCurrentPosition());
        StealthOpMode.telemetry.addData("Arm:Limit switch", armLimitSwitch.getState());
        StealthOpMode.telemetry.addData("Arm:Limit switch 2", armLimitSwitch2.getState());

        StealthOpMode.telemetry.addData("Arm:isOverCurrent", armMotor.isOverCurrent());
        StealthOpMode.telemetry.addData("Arm:getCurrent(mA)", armMotor.getCurrent(CurrentUnit.MILLIAMPS));
        StealthOpMode.telemetry.addData("Arm:getCurrentAlert(mA)", armMotor.getCurrentAlert(CurrentUnit.MILLIAMPS));
    }

    public void setPosition(int position, double power) {
        armMotor.setTargetPosition(position);
        armMotor.setPower(power);
    }

    public void setPosition(int position) {
        setPosition(position, 1.0);
    }

    public int getCurrentPosition() {
        return armMotor.getCurrentPosition();
    }

    public void resetLowerPosition() {
        armMotor.setPower(0.0);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isArmAtLowerLimit() {
        return !armLimitSwitch.getState();
    }

    public boolean isOverCurrent() {
        return armMotor.isOverCurrent();
    }
}
