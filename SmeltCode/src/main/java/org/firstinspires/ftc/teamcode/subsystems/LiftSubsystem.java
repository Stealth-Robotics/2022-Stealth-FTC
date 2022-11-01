package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
@Config
public class LiftSubsystem extends SubsystemBase {
    final DcMotorEx liftMotor;

    public static int SLOW_SPEED = 100;


    public LiftSubsystem(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotorEx.class, "lift");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        liftMotor.setVelocity(0);
    }

    public void spinForwardSlow() {
        liftMotor.setVelocity(SLOW_SPEED);
    }
    public void spinBackwardSlow(){
        liftMotor.setVelocity(-SLOW_SPEED);
    }



}
