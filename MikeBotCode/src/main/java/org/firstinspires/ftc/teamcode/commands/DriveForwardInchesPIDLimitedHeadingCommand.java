package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

@Config
public class DriveForwardInchesPIDLimitedHeadingCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double forward;

    // From https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder/
    // Encoder Resolution           537.7 PPR at the Output Shaft
    // Encoder Resolution Formula   ((((1+(46/17))) * (1+(46/11))) * 28)
    public static double MOTOR_TICS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double DISTANCE_PER_REVOLUTION_MM = WHEEL_DIAMETER_MM * Math.PI;
    public static double TICS_PER_MM = MOTOR_TICS_PER_REVOLUTION / DISTANCE_PER_REVOLUTION_MM;
    public static double TICS_PER_INCHES = TICS_PER_MM * 25.4;

    public static double maxSpeed = 0.5;
    public static double accelTimeSec = 1.0;

    int endTicks; // How far are we going?
    long startTime;
    double startHeading; // Hold the heading we start with

    // Tunes on shop mats, 20221115
    public static double DISTANCE_KP = 0.001;
    public static double DISTANCE_KI = 0.1;
    public static double DISTANCE_KD = 0.00015;
    public static double DISTANCE_KF = 0;
    PIDFController distancePID = new PIDFController(DISTANCE_KP, DISTANCE_KI, DISTANCE_KD, DISTANCE_KF);

    public static double HEADING_KP = 1.0;
    public static double HEADING_KI = 0.2;
    public static double HEADING_KD = 0.1;
    public static double HEADING_KF = 0;
    PIDFController headingPID = new PIDFController(HEADING_KP, HEADING_KI, HEADING_KD, HEADING_KF);

    public DriveForwardInchesPIDLimitedHeadingCommand(SimpleMecanumDriveSubsystem drive, double forward) {
        this.drive = drive;
        this.forward = forward;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        endTicks = drive.getTicks() + (int) (forward * TICS_PER_INCHES);
        distancePID.setSetPoint(endTicks);
        distancePID.setTolerance(10);
        distancePID.reset();

        startTime = System.nanoTime();

        startHeading = drive.getHeading();
        headingPID.reset();
    }

    @Override
    public void execute() {
        distancePID.setPIDF(DISTANCE_KP, DISTANCE_KI, DISTANCE_KD, DISTANCE_KF);
        double power = distancePID.calculate(drive.getTicks());

        // Super-simple acceleration limiter. Nothing for decel, the pid tuning handles that.
        double dt = (System.nanoTime() - startTime) * 1E-9;
        double speedLimit = maxSpeed;
        if (dt < accelTimeSec) {
            speedLimit = maxSpeed * (dt / accelTimeSec);
        }

        double currentHeading = drive.getHeading();
        double delta = AngleUnit.normalizeRadians(startHeading - currentHeading);
        headingPID.setPIDF(HEADING_KP, HEADING_KI, HEADING_KD, HEADING_KF);
        double headingCorrection = headingPID.calculate(-delta);

        telemetry.addData("endTicks", endTicks);
        telemetry.addData("power", power);
        telemetry.addData("dt", dt);
        telemetry.addData("heading delta", delta);
        telemetry.addData("heading correction", headingCorrection);

        power = Math.max(-speedLimit, Math.min(power, speedLimit));

        double headingSpeedLimit = 1.0;
        headingCorrection = Math.max(-headingSpeedLimit, Math.min(headingCorrection, headingSpeedLimit));

        drive.drive(power, 0, headingCorrection);
    }

    @Override
    public boolean isFinished() {
        return distancePID.atSetPoint();
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
