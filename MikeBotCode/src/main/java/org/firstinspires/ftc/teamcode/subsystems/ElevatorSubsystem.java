package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.math.filter.Debouncer;

/**
 * This subsystem represents the elevator on our robots. We have a single motor and a single switch.
 * <p>
 * The motor moves us up and down, the switch tells us when we're all the way down so we can know
 * how far we can safely move.
 */
@Config
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx elevatorMotor;

    public static int UPPER_LIMIT_TICKS = 1600;
    public static int MAX_VELOCITY_TICKS_PER_SEC = 2000;
    public static double RESET_POWER = 0.10;
    public static double RESET_STALL_TIME_SEC = 0.050; // 50ms

    final Debouncer stalledDebouncer = new Debouncer(RESET_STALL_TIME_SEC, Debouncer.DebounceType.kRising);

    int targetTicks = 0;

    public ElevatorSubsystem(HardwareMap hardwareMap) {
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevator");
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPositionTolerance(10);
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // RUN_TO_POSITION uses both PIDs. Both need to be tuned!!
        //   'Velocity' internally to try to hit a speed target
        //   'Position' to hit the position.
        // elevatorMotor.setVelocityPIDFCoefficients(1, 0.001, 0.1, 1.0);
        // elevatorMotor.setPositionPIDFCoefficients(1.0);
    }

    /**
     * Start the motor moving down gently. We'll look for it to stall to find the bottom.
     */
    public void downSlowForReset() {
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevatorMotor.setPower(-RESET_POWER);
        stalledDebouncer.calculate(false); // Reset the debouncer
    }

    /**
     * True if the elevator is stalled, indicating we're at the bottom during a reset.
     */
    public boolean isStalled() {
        telemetry.addData("Elevator velocity", elevatorMotor.getVelocity());
        return stalledDebouncer.calculate(Math.abs(elevatorMotor.getVelocity()) < 1);
    }

    /**
     * We're stalled going down, so we know we're at the bottom. Reset the system to zero.
     */
    public void completeReset() {
        elevatorMotor.setPower(0);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        targetTicks = 0;
        elevatorMotor.setTargetPosition(targetTicks);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Current location as a percentage of the max height
     */
    public double getCurrentLocation() {
        return elevatorMotor.getCurrentPosition() / (float) UPPER_LIMIT_TICKS;
    }

    /**
     * Target location as a percentage of the max height
     */
    public double getTargetLocation() {
        return targetTicks / (float) UPPER_LIMIT_TICKS;
    }

    /**
     * Ask the elevator to go to a percentage of its maximum height.
     */
    public void setTargetLocation(double percent) {
        if (percent > 1.0) {
            percent = 1.0;
        } else if (percent < 0.0) {
            percent = 0.0;
        }

        targetTicks = (int) (percent * UPPER_LIMIT_TICKS);
    }

    /**
     * True if the elevator is trying to reach a new target location.
     */
    public boolean isBusy() {
        return elevatorMotor.isBusy();
    }

    /**
     * Ask the elevator to go to the position we've already set.
     */
    public void goToPosition() {
        elevatorMotor.setTargetPosition(targetTicks);
        elevatorMotor.setVelocity(MAX_VELOCITY_TICKS_PER_SEC);
    }

    /**
     * This is called all the time while the opmode is running.
     */
    @Override
    public void periodic() {
        telemetry.addData("Elevator current ticks", elevatorMotor.getCurrentPosition());
        telemetry.addData("Elevator target ticks", targetTicks);
    }
}


