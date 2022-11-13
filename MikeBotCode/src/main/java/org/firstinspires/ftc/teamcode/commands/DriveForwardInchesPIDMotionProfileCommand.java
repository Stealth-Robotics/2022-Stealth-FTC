package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

public class DriveForwardInchesPIDMotionProfileCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double forward;

    public static double MOTOR_TICS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double DISTANCE_PER_REVOLUTION_MM = WHEEL_DIAMETER_MM * Math.PI;
    public static double TICS_PER_MM = MOTOR_TICS_PER_REVOLUTION / DISTANCE_PER_REVOLUTION_MM;
    public static double TICS_PER_INCHES = TICS_PER_MM * 25.4;

    int startTicks;
    int driveTicks;
    long startTime;
    double mpPosition;
    PIDFController pid = new PIDFController(0.001, 0.1, 0, 0);

    public DriveForwardInchesPIDMotionProfileCommand(SimpleMecanumDriveSubsystem drive, double forward) {
        this.drive = drive;
        this.forward = forward;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        startTicks = drive.getTicks();
        driveTicks = (int) (forward * TICS_PER_INCHES);
        pid.setTolerance(10);
        pid.reset();
        startTime = System.nanoTime();
    }

    @Override
    public void execute() {
        // Use the motion profile to change the setpoint for the PID and let it chase it.
        // Good results with reasonable accel/decel.
        double dt = (System.nanoTime() - startTime) * 1E-9;
        mpPosition = motion_profile(1000, 1500, driveTicks, dt);
        pid.setSetPoint(mpPosition + startTicks);
        double power = pid.calculate(drive.getTicks());
        power = Math.max(-1.0, Math.min(power, 1.0));
        drive.drive(power, 0, 0);

        telemetry.addData("dt", dt);
        telemetry.addData("end ticks", startTicks + driveTicks);
        telemetry.addData("mpPosition", mpPosition);
        telemetry.addData("power", power);
    }

    // Algorithm from CTRL ALT FTC, Motion Profiling section.
    //
    // Minimal change at this point, just translated their func to Java.
    // Retrieved on 2022/11/12 8:56pm from https://www.ctrlaltftc.com/advanced/motion-profiling
    double motion_profile(double max_acceleration, double max_velocity, double distance, double current_dt) {
        //  Return the current reference position based on the given motion profile times, maximum acceleration, velocity, and current time.

        // calculate the time it takes to accelerate to max velocity
        double acceleration_dt = max_velocity / max_acceleration;

        // If we can't accelerate to max velocity in the given distance, we'll accelerate as much as possible
        double halfway_distance = distance / 2;
        double acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;

        if (acceleration_distance > halfway_distance)
            acceleration_dt = Math.sqrt(halfway_distance / (0.5 * max_acceleration));

        acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;

        // recalculate max velocity based on the time we have to accelerate and decelerate
        max_velocity = max_acceleration * acceleration_dt;

        // we decelerate at the same rate as we accelerate
        double deacceleration_dt = acceleration_dt;

        // calculate the time that we're at max velocity
        double cruise_distance = distance - 2 * acceleration_distance;
        double cruise_dt = cruise_distance / max_velocity;
        double deacceleration_time = acceleration_dt + cruise_dt;

        // check if we're still in the motion profile
        double entire_dt = acceleration_dt + cruise_dt + deacceleration_dt;
        if (current_dt > entire_dt)
            return distance;

        // if we're accelerating
        if (current_dt < acceleration_dt)
            // use the kinematic equation for acceleration
            return 0.5 * max_acceleration * current_dt * current_dt;

            // if we're cruising
        else if (current_dt < deacceleration_time) {
            acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;
            double cruise_current_dt = current_dt - acceleration_dt;

            // use the kinematic equation for constant velocity
            return acceleration_distance + max_velocity * cruise_current_dt;
        }

        // if we're decelerating
        else {
            acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;
            cruise_distance = max_velocity * cruise_dt;
            deacceleration_time = current_dt - deacceleration_time;

            // use the kinematic equations to calculate the instantaneous desired position
            return acceleration_distance + cruise_distance + max_velocity * deacceleration_time - 0.5 * max_acceleration * deacceleration_time * deacceleration_time;
        }
    }

    @Override
    public boolean isFinished() {
        return pid.atSetPoint() && mpPosition >= driveTicks;
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
