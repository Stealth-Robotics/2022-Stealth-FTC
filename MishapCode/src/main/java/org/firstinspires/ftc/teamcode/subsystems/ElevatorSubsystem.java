package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This subsystem represents the elevator on our robots. We have a single motor and a single switch.
 * <p>
 * The motor moves us up and down, the switch tells us when we're all the way down so we can know
 * how far we can safely move.
 */
@Config
public class ElevatorSubsystem extends SubsystemBase {
    final DcMotorEx elevatorMotor;
    final DigitalChannel lowerLimitSwitch;

    public static int UPPER_LIMIT_TICKS = 4200;
    public static int MAX_VELOCITY_TICKS_PER_SEC = 2000;
    public static double RESET_VELOCITY_TICKS_PER_SEC = 500;
    public static int RESET_TICKS = -6000;
    public static double LITTLE_UP = 0.04;
    public static double LITTLE_DOWN = -0.04;
    public static double RESET_STALL_TIME_SEC = 0.050; // 50ms
    public static double RESET_POWER = 0.10;

    final Debouncer stalledDebouncer = new Debouncer(RESET_STALL_TIME_SEC, Debouncer.DebounceType.kRising);

    int targetTicks = 0;

    public ElevatorSubsystem(HardwareMap hardwareMap) {
        elevatorMotor = hardwareMap.get(DcMotorEx.class, "elevator");
        elevatorMotor.setDirection(DcMotor.Direction.REVERSE);
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

        lowerLimitSwitch = hardwareMap.get(DigitalChannel.class, "limitSwitch");
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
     * Start the motor moving down gently. We'll look for the limit switch to get hit and stop it then.
     */
    public void startLimitSwitchReset() {
        elevatorMotor.setTargetPosition(RESET_TICKS);
        elevatorMotor.setVelocity(RESET_VELOCITY_TICKS_PER_SEC);
    }

    /**
     * the limit switch has been hit, so we know we're at the bottom. Reset the system to zero.
     */
    public void limitSwitchReset() {
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        targetTicks = 0;
        elevatorMotor.setTargetPosition(targetTicks);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * True when the limit switch is activated, telling us we're at the bottom.
     * <p>
     * Note: the actual switch appears false when open, and true when closed.
     */
    public boolean isAtLimitSwitch() {
        return lowerLimitSwitch.getState();
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
    public boolean isElevatorBusy() {
        return elevatorMotor.isBusy();
    }

    /**
     * Ask the elevator to go to the position we've already set.
     */
    public void goToPosition() {
        elevatorMotor.setTargetPosition(targetTicks);
        elevatorMotor.setVelocity(MAX_VELOCITY_TICKS_PER_SEC);
    }

    public double upALittle(){
        return(getTargetLocation() + LITTLE_UP);
    }

    public double downALittle(){
        return(getTargetLocation() + LITTLE_DOWN);
    }
    /**
     * This is called all the time while the opmode is running.
     */
    @Override
    public void periodic() {
        telemetry.addData("Elevator current ticks", elevatorMotor.getCurrentPosition());
        telemetry.addData("Elevator target ticks", targetTicks);
        telemetry.addData("Elevator limit switch", isAtLimitSwitch() ? 1 : 0);
    }
}