package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.Alliance;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx elevatorMotor;
    final DigitalChannel LowerLimit;
    final Telemetry telemetry;

    final int SLOW_SPEED = 500;
    final int SUPER_SPEED = 10000;
    final int AUTO_SPEED = 250;

    public ElevatorSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevator");
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LowerLimit = hardwareMap.get(DigitalChannel.class, "LimitSwitch");
        this.telemetry = telemetry;
    }

    public int getEncoderValue() {
        return elevatorMotor.getCurrentPosition();
    }

    public void stop() {
        elevatorMotor.setPower(0);
    }

    public void upFast() {
        elevatorMotor.setPower(1);
    }

    public void upSlow() {
        elevatorMotor.setPower(.25);
    }

    public void downFast() {
        elevatorMotor.setPower(-1);
    }

    public void downSlow() {
        elevatorMotor.setPower(-.25);
    }

    @Override
    public void periodic() {
        telemetry.addData("ourlimitswitch", "%s", LowerLimit.getState());
    }

}


