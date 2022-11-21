package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Subsystem to control the elevator
 */
@Config
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx liftMotor;
    public static int MAX_VELOCITY = 4000;

    public static int RESET_VELOCITY = 3000;
    public static int RESET_TICKS = 0;

    int target = 0;

    public ElevatorSubsystem(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotorEx.class, "lift");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftMotor.setTargetPosition(0);
        liftMotor.setTargetPositionTolerance(10);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // elevatorMotor.setVelocityPIDFCoefficients(1, 0.001, 0.1, 1.0);
        // elevatorMotor.setPositionPIDFCoefficients(1.0);
    }

    public void initReset() {
        liftMotor.setTargetPosition(RESET_TICKS);
        liftMotor.setVelocity(RESET_VELOCITY);
    }

    /**
     * can be used to reset where "zero" is on elevator
     */
    public void limitSwitchReset() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        target = 0;
        liftMotor.setTargetPosition(target);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * should be default command, will run to target, use setTarget to set target to run to
     */
    public void runToPosition() {
        liftMotor.setTargetPosition(target);
        if (target == 0) {
            liftMotor.setVelocity(RESET_VELOCITY);
        } else {
            liftMotor.setVelocity(MAX_VELOCITY);
        }
    }

    public void setTarget(int ticks) {
        target = ticks;
    }

    public int getPos() {
        return liftMotor.getCurrentPosition();
    }
    public boolean isBusy(){
        return liftMotor.isBusy();
    }

    @Override
    public void periodic() {
        telemetry.addData("pos", getPos());
        telemetry.addData("target", target);
    }

}
