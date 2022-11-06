package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

public class BlueRightTrajectories {
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (-35, 62, Math.toRadians(270)))
            .lineToSplineHeading(new Pose2d(-36, 27.5, 0))
            .build();
    public static Trajectory forward2 = TrajectoryBuilder.buildTrajectory(forward1.end())
            .lineToSplineHeading(new Pose2d(-32, 31, Math.toRadians(303)))
            .build();
    public static Trajectory forward3 = TrajectoryBuilder.buildTrajectory(forward2.end())
            .forward(8)
            .build();
    public static Trajectory back1 = TrajectoryBuilder.buildTrajectory(forward3.end())
            .lineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(270)))
            .build();
    public static Trajectory park1 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-12.5, 37, Math.toRadians(270)))
            .build();
    public static Trajectory park2 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-36, 36.5, Math.toRadians(270)))
            .build();
    public static Trajectory park3 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-59, 37, Math.toRadians(270)))
            .build();

}
