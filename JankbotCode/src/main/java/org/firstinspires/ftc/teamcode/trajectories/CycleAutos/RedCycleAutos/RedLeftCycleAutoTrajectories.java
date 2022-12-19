package org.firstinspires.ftc.teamcode.trajectories.CycleAutos.RedCycleAutos;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectories.TrajectorySequenceBuilder;
/// THIS IS THE WORKING ONE
public class RedLeftCycleAutoTrajectories {
    public static Pose2d startingPose = new Pose2d(-27, -64, Math.toRadians(90));
    //Spline to line up to push signal cone and strafe right to score the preload
    public static TrajectorySequence trajectory1 = TrajectorySequenceBuilder.buildTrajectorySequence(startingPose)
            .lineToSplineHeading(new Pose2d(-33, -39, Math.toRadians(180)))
            .strafeRight(20)
            .build();
    // score cone on mid goal here

    public static TrajectorySequence trajfx1 = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory1.end())
            .lineToSplineHeading(new Pose2d(-29,-14, Math.toRadians(180)))
            . build();
    //spline and forward to go to location to pick up the cones
    public static TrajectorySequence trajectory2 = TrajectorySequenceBuilder.buildTrajectorySequence(trajfx1.end())
            .lineToSplineHeading(new Pose2d(-50, -10.5, Math.toRadians(180)))
           //  .forward(18)
            .build();
    //pick up the cone here
    //
    //going to the location to score the cone
    public static TrajectorySequence trajectory3 = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory2.end())
            .lineToSplineHeading(new Pose2d(-43, -9, Math.toRadians(209)))
            .build();
    //score the cone
    //
    //come back to pick up antother cone
    public static TrajectorySequence trajectory4 = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory3.end())
            .lineToSplineHeading(new Pose2d(-50, -10.5, Math.toRadians(180)))
            .build();

//different parking sections

    //left
    public static TrajectorySequence slot1Park = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory4.end())
            .lineToSplineHeading(new Pose2d(-60, -15, Math.toRadians(180)))
            .build();
    public static TrajectorySequence forawrdABit = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory3.end())
            .forward(5)
            .build();


    //right
    public static TrajectorySequence slot3Park = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory4.end())
            .lineToSplineHeading(new Pose2d(-10, -15, Math.toRadians(-180)))
            .build();


    public static TrajectorySequence slot2Park = TrajectorySequenceBuilder.buildTrajectorySequence(trajectory4.end())
            .lineToSplineHeading(new Pose2d(-30, -15, Math.toRadians(-180)))
            .build();
    // fixing trajectories

}

