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
            .splineTo(new Vector2d(-36.7, 45), Math.toRadians(270))
            .splineTo(new Vector2d(-27.5, 30), Math.toRadians(305))
            .build();

    //drives to cone stack
    public static TrajectorySequence back1 = TrajectorySequenceBuilder.buildTrajectorySequence(forward1.end())
            .back(4.5)
            .splineTo(new Vector2d(-35.3, 12), Math.toRadians(0))
            .forward(22)
            .build();
    //drives to pole to socre second cone
    public static TrajectorySequence back2 = TrajectorySequenceBuilder.buildTrajectorySequence(back1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(235)), 0)
            .build();
    //gets second cone from stack
    public static TrajectorySequence cone1 = TrajectorySequenceBuilder.buildTrajectorySequence(back2.end())
            .splineToSplineHeading(new Pose2d(-58, 12,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence cone2 = TrajectorySequenceBuilder.buildTrajectorySequence(cone1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-32, 18, Math.toRadians(225)), Math.toRadians(45))
            .build();



}
