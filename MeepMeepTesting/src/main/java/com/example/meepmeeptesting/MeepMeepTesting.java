package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.*;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 9)
                .setDimensions(11.75, 13)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(37, -65, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(37, -14, 0))
                                .forward(20)
                                .waitSeconds(.5)
                                .lineToLinearHeading(new Pose2d(30,-8, Math.toRadians(115)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(57, -14, 0))
                                .waitSeconds(.5)
                                .lineToLinearHeading(new Pose2d(30,-8, Math.toRadians(115)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(57, -14, 0))
                                .waitSeconds(.5)
                                .lineToLinearHeading(new Pose2d(30,-8, Math.toRadians(115)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(57, -14, 0))
                                .waitSeconds(.5)
                                .lineToLinearHeading(new Pose2d(30,-8, Math.toRadians(115)))
                                .waitSeconds(1)
                                .turn(Math.toRadians(-115))
                                .strafeRight(3)
                                .back(90)
                                .lineToLinearHeading(new Pose2d(-60, -65, Math.toRadians(270)))
                                .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}