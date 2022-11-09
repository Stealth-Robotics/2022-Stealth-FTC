package org.firstinspires.ftc.teamcode.trajectories;

import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_ANG_ACCEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.TRACK_WIDTH;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BlueRightTrajectories {
    //goes to pole and scores first cone
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (-35, 62, Math.toRadians(270)))
            .splineTo(new Vector2d(-36.7, 45), Math.toRadians(270))
            .splineTo(new Vector2d(-26, 30), Math.toRadians(310))
            .build();

    //drives to cone stack
    public static Trajectory back1 = TrajectorySequenceBuilder.buildTrajectorySequence(forward1.end())
            .back(4.5)
            .splineTo(new Vector2d(-35.3, 12), Math.toRadians(0))
            .forward(25)
            .build();
    //drives to pole to socre second cone
    public static Trajectory back2 = TrajectoryBuilder.buildTrajectory(back1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(235)), 0)
            .build();
    //gets second cone from stack
    public static Trajectory cone1 = TrajectoryBuilder.buildTrajectory(back2.end())
            .splineToSplineHeading(new Pose2d(-58, 12,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static Trajectory cone2 = TrajectoryBuilder.buildTrajectory(cone1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(235)), Math.toRadians(45))
            .build();



}
