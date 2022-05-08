package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

public class DuckWheel extends SubsystemBase {
    private final DcMotorEx duckMotor;

    // The fast and slow speeds for automated spinning
    private final int AUTO_SLOW_SPEED = 1000;
    private final int AUTO_FAST_SPEED = 10000;

    public DuckWheel(HardwareMap hardwareMap) {
        duckMotor = hardwareMap.get(DcMotorEx.class, "duckMotor");
        duckMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duckMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        duckMotor.setDirection(Alliance.isRed() ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
    }

    public void spinForward() {
        duckMotor.setVelocity(AUTO_SLOW_SPEED);
    }

    public void spinBackwards() {
        duckMotor.setVelocity(-AUTO_SLOW_SPEED);
    }

    public void stop() {
        duckMotor.setVelocity(0);
    }

    public void reset() {
        duckMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runSlowArc() {
        duckMotor.setVelocity(AUTO_SLOW_SPEED);
    }

    public void runFastArc() {
        duckMotor.setVelocity(AUTO_FAST_SPEED);
    }

    public int getCurrentPosition() {
        return duckMotor.getCurrentPosition();
    }

}
