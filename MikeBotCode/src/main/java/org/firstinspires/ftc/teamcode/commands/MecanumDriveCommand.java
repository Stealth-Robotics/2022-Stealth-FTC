package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class MecanumDriveCommand extends CommandBase {

    private final MecanumDrivetrain drive;
    private final DoubleSupplier forward, strafe, rotate;
    private final BooleanSupplier slowdown;

    public MecanumDriveCommand(MecanumDrivetrain drive, DoubleSupplier forward,
                               DoubleSupplier strafe, DoubleSupplier rotate,
                               BooleanSupplier slowdown) {
        this.drive = drive;
        this.strafe = strafe;
        this.forward = forward;
        this.rotate = rotate;
        this.slowdown = slowdown;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        // nb: swap to drive.update() to get the pose telemetry and field overlay.
        drive.updatePoseEstimate();

        boolean goSlow = slowdown.getAsBoolean();
        drive.drive(
                slowItDown(forward.getAsDouble(), goSlow),
                slowItDown(strafe.getAsDouble(), goSlow),
                slowItDown(rotate.getAsDouble(), goSlow));
    }

    private double slowItDown(double v, boolean goSlow) {
        return goSlow ? v * Math.abs(v) : v;
    }
}
