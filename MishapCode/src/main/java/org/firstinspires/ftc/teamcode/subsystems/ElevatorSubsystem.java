package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx elevatorMotor;

    final int SLOW_SPEED = 500;
    final int SUPER_SPEED = 10000;
    final int AUTO_SPEED = 250;

    public ElevatorSubsystem(HardwareMap hardwareMap) {
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "Elevator");
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public int getEncoderValue() {
        return elevatorMotor.getCurrentPosition();
    }

    public void stop() {
        elevatorMotor.setVelocity(0);
    }

    public void spinForwardSlow() {
        if (Alliance.isBlue()) {
            elevatorMotor.setVelocity(-SLOW_SPEED);
        } else {
            elevatorMotor.setVelocity(SLOW_SPEED);
        }
    }

    public void spinBackwardsSlow() {
        if (Alliance.isBlue()) {
            elevatorMotor.setVelocity(SLOW_SPEED);
        } else {
            elevatorMotor.setVelocity(-SLOW_SPEED);
        }
    }

}
