package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem extends SubsystemBase {
    final DcMotorEx armDrive;
    final DigitalChannel armLimitSwitch;
    final Telemetry telemetry;

    public final int DRIVING_HEIGHT = 200;
    int targetLocation = DRIVING_HEIGHT;

    // Arm minimum and maximum location, used to stop the arm from moving where it cannot move
    final int MIN_LOCATION = 0;
    final int MAX_LOCATION = 1700;

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        armDrive = hardwareMap.get(DcMotorEx.class, "arm");
        armDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armDrive.setTargetPositionTolerance(10);
        armDrive.setTargetPosition(0);
        armDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armDrive.setDirection(DcMotor.Direction.REVERSE);
        armDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //                                    P     I   D    F
        armDrive.setVelocityPIDFCoefficients(13.0, 0.002, 0.1, 2.0);
        // armDrive.setPositionPIDFCoefficients(50.0);

        armLimitSwitch = hardwareMap.get(DigitalChannel.class, "armLimitSwitch");
    }

    public void startLimitSwitchReset() {
        armDrive.setTargetPosition(-7760);
        armDrive.setPower(0.6);
    }

    public void armLimitSwitchReset() {
        armDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        targetLocation = MIN_LOCATION;
        armDrive.setTargetPosition(0);
        armDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armDrive.setPower(0.0);
    }

    public int getCurrentLocation() {
        return armDrive.getCurrentPosition();
    }

    public int getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(int armLocation) {
        if (armLocation > MAX_LOCATION) {
            armLocation = MAX_LOCATION;
        } else if (armLocation < MIN_LOCATION) {
            armLocation = MIN_LOCATION;
        }
        this.targetLocation = armLocation;
    }

    public boolean isAtLimitSwitch() {
        return armLimitSwitch.getState();
    }

    public boolean isBusy() {
        return armDrive.isBusy();
    }

    @Override
    public void periodic() {
        // Sets the arm to the correct position
        armDrive.setTargetPosition(targetLocation);
        armDrive.setVelocity(1500);

        telemetry.addData("Arm", "Location %d", targetLocation);
        telemetry.addData("Arm", "Current position %d", armDrive.getCurrentPosition());
        telemetry.addData("Arm Limit Switch", "%s", armLimitSwitch.getState());
    }
}
