package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

/**
 * A very simple subsystem that has a single wheel that you can turn.
 */
public class WheelSubsystem extends SubsystemBase {
    final DcMotorEx wheelieMotor;

    final int SLOW_SPEED = 500;
    final int SUPER_SPEED = 10000;
    final int AUTO_SPEED = 250;

    /**
     * Find your hardware and initialize it. You'll usually lookup motors, sensors, and such in the
     * hardware map and remember them for later in member variables. This is also where you
     * initialize things like motor directions, modes, etc.
     */
    public WheelSubsystem(HardwareMap hardwareMap) {
        wheelieMotor = hardwareMap.get(DcMotorEx.class, "wheelie");
        wheelieMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelieMotor.setDirection(DcMotor.Direction.FORWARD);
        wheelieMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelieMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Just set the motor velocity to zero when we're asked to stop.
     */
    public void stop() {
        wheelieMotor.setVelocity(0);
    }

    /**
     * We spin the motor different directions because we're on different sides of the field depending
     * on which alliance we're playing.
     */
    public void spinForwardSlow() {
        if (Alliance.isBlue()) {
            wheelieMotor.setVelocity(-SLOW_SPEED);
        } else {
            wheelieMotor.setVelocity(SLOW_SPEED);
        }
    }

    public void spinBackwardsSlow() {
        if (Alliance.isBlue()) {
            wheelieMotor.setVelocity(SLOW_SPEED);
        } else {
            wheelieMotor.setVelocity(-SLOW_SPEED);
        }
    }

}
