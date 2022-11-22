package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
@Config
public class ArmMotorSubsystem extends SubsystemBase {
    final DcMotorEx armMotorA;
    final DcMotorEx armMotorB;

    public static double SPEED = 10000;
    //final int SUPER_SPEED = 10000;
    //final int AUTO_SPEED = 250;

    public ArmMotorSubsystem(HardwareMap hardwareMap) {
        armMotorA = hardwareMap.get(DcMotorEx.class, "armMotorA");
        armMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorA.setDirection(DcMotor.Direction.FORWARD);
        armMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //i hate my life
        armMotorB = hardwareMap.get(DcMotorEx.class, "frontEncoder");
        armMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorB.setDirection(DcMotor.Direction.FORWARD);
        armMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void resetEncoder() {
        armMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int getEncoderValueA() {
        return armMotorA.getCurrentPosition();
    }

    public int getEncoderValueB() {
        return armMotorB.getCurrentPosition();
    }

    public void stop() {
        armMotorA.setVelocity(0);
        armMotorB.setVelocity(0);
    }

    public void spinForward() {
        armMotorA.setVelocity(SPEED);
        armMotorB.setVelocity(SPEED);
    }

    public void spinBackwards() {
        armMotorA.setVelocity(-SPEED);
        armMotorB.setVelocity(-SPEED);
    }

}
