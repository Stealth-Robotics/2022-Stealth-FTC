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
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (-40.5, 62, Math.toRadians(270)))
            .splineTo(new Vector2d(-36.7, 45), Math.toRadians(290))
            .splineTo(new Vector2d(-29, 31.5), Math.toRadians(305))
            .build();

    //drives to cone stack to pick up first stack cone
    public static TrajectorySequence back1 = TrajectorySequenceBuilder.buildTrajectorySequence(forward1.end())
            .back(4.5)
            .splineTo(new Vector2d(-35.3, 10), Math.toRadians(0))
            .forward(24)
            .build();
    //drives to pole to socre first stack cone
    public static TrajectorySequence back2 = TrajectorySequenceBuilder.buildTrajectorySequence(back1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-31.75, 16, Math.toRadians(245)), 0)
            .build();
    //gets second cone from stack
    public static TrajectorySequence getcone1 = TrajectorySequenceBuilder.buildTrajectorySequence(back2.end())
            .splineToSplineHeading(new Pose2d(-59.8, 9.75,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence scorecone1 = TrajectorySequenceBuilder.buildTrajectorySequence(getcone1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-32, 16.7, Math.toRadians(230)), Math.toRadians(45))
            .build();
    public static TrajectorySequence getcone2 = TrajectorySequenceBuilder.buildTrajectorySequence(scorecone1.end())
            .splineToSplineHeading(new Pose2d(-59.8, 9.5,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence scorecone2 = TrajectorySequenceBuilder.buildTrajectorySequence(getcone2.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-32.75, 16.7, Math.toRadians(230)), Math.toRadians(45))
            .build();
    public static TrajectorySequence getcone3 = TrajectorySequenceBuilder.buildTrajectorySequence(scorecone2.end())
            .splineToSplineHeading(new Pose2d(-59.8, 9.25,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence scorecone3 = TrajectorySequenceBuilder.buildTrajectorySequence(getcone3.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-32, 16.7, Math.toRadians(230)), Math.toRadians(45))
            .build();



}
