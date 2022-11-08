package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

public class BlueRightTrajectories {
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (-35, 62, Math.toRadians(270)))
            .lineToSplineHeading(new Pose2d(-36, 33, 0))
            .build();
    public static Trajectory forward2 = TrajectoryBuilder.buildTrajectory(forward1.end())
            .lineToSplineHeading(new Pose2d(-29.5, 33, Math.toRadians(303)))
            .build();
    public static Trajectory forward3 = TrajectoryBuilder.buildTrajectory(forward2.end())
            .forward(2.75)
            .build();
    public static Trajectory back1 = TrajectoryBuilder.buildTrajectory(forward3.end())
            .lineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(270)))
            .build();
    public static Trajectory park1 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-11, 32, Math.toRadians(270)))
            .build();
    public static Trajectory park2 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-36, 36.5, Math.toRadians(270)))
            .build();
    public static Trajectory park3 = TrajectoryBuilder.buildTrajectory(back1.end())
            .lineToSplineHeading(new Pose2d(-65, 25, Math.toRadians(270)))
            .build();

    public static Trajectory back2 = TrajectoryBuilder.buildTrajectory(forward3.end())
            .lineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(270)))
            .build();
    public static Trajectory cone1 = TrajectoryBuilder.buildTrajectory(back2.end())
            .lineToSplineHeading(new Pose2d(-36, 26.5, Math.toRadians(180)))
            .build();
    public static Trajectory cone2 = TrajectoryBuilder.buildTrajectory(cone1.end())
            .strafeLeft(17.5)
            .build();
    public static Trajectory cone3 = TrajectoryBuilder.buildTrajectory(cone2.end())
            .forward(20.5)
            .build();
    public static  Trajectory cone4 = TrajectoryBuilder.buildTrajectory(cone3.end())
            .back(21.25)
            .build();
    public static Trajectory cone5 = TrajectoryBuilder.buildTrajectory(cone4.end())
            .lineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(215)))
            .build();

}
