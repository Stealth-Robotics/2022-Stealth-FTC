package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx elevatorMotor;
    final DigitalChannel LowerLimit;

    public ElevatorSubsystem(HardwareMap hardwareMap) {
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevator");
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LowerLimit = hardwareMap.get(DigitalChannel.class, "LimitSwitch");
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
        StealthOpMode.telemetry.addData("Our elevaor limit switch", "%s", LowerLimit.getState());
        StealthOpMode.telemetry.addData("Our elevaor limit switch int", "%d", LowerLimit.getState() ? 1 : 0);
    }

}


