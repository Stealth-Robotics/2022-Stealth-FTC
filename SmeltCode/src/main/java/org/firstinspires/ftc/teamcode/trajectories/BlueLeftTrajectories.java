package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class BlueLeftTrajectories {
    //goes to pole and scores first cone
    public static Trajectory blueforward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (42.55, 62.425, Math.toRadians(270)))
            .splineTo(new Vector2d(36.7, 45), Math.toRadians(290))
            .splineTo(new Vector2d(32.5, 31), Math.toRadians(295))
            .build();
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (42.5, 62.425, Math.toRadians(270)))
            .splineTo(new Vector2d(36.7, 45), Math.toRadians(255))
            .splineTo(new Vector2d(32.5, 31), Math.toRadians(230))
            .build();

    //drives to cone stack to pick up first stack cone
    public static TrajectorySequence back1 = TrajectorySequenceBuilder.buildTrajectorySequence(forward1.end())
            .back(4.5)
            .splineTo(new Vector2d(35.3, 10.75), Math.toRadians(183))
            .forward(24.75)
            .build();
    //drives to pole to score first stack cone
    public static TrajectorySequence back2 = TrajectorySequenceBuilder.buildTrajectorySequence(back1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(35, 14, Math.toRadians(320)), Math.toRadians(180))
            .build();
    //gets second cone from stack
    public static TrajectorySequence getCone1 = TrajectorySequenceBuilder.buildTrajectorySequence(back2.end())
            .forward(3)
            .splineToSplineHeading(new Pose2d(60.75, 10.5,  Math.toRadians(0  )), Math.toRadians(0  ))
            .build();
    public static TrajectorySequence scoreCone1 = TrajectorySequenceBuilder.buildTrajectorySequence(getCone1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(35, 14, Math.toRadians(320)), Math.toRadians(180))
            .build();
    public static TrajectorySequence getCone2 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone1.end())
            .splineToSplineHeading(new Pose2d(60.75, 10.5,  Math.toRadians(0  )), Math.toRadians(0  ))
            .build();
    public static TrajectorySequence scoreCone2 = TrajectorySequenceBuilder.buildTrajectorySequence(getCone2.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(35, 14, Math.toRadians(320)), Math.toRadians(180))
            .build();
    public static TrajectorySequence park1 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(35, 11, Math.toRadians(180 )))
            .back(22)
            .build();
    public static TrajectorySequence park2 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(37, 12, Math.toRadians(180 )))
            .build();
    public static TrajectorySequence park3 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(35, 11, Math.toRadians(270 )))
            .lineToSplineHeading(new Pose2d(61.25, 11, Math.toRadians(180 )))
            .build();



}
