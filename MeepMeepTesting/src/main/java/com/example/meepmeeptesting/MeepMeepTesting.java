package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, Math.toRadians(180), Math.toRadians(180), 9)
                .setDimensions(11.755, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 62, Math.toRadians(270)))
                                .lineToSplineHeading(new Pose2d(-36, 27.5, 0))
                                .lineToSplineHeading(new Pose2d(-32, 31, Math.toRadians(303)))
                                .forward(8)
                                .lineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(270)))
                                .lineToSplineHeading(new Pose2d(-36, 12, Math.toRadians(180)))
                                .forward(25)
                                .lineToSplineHeading(new Pose2d(-36, 11, Math.toRadians(45)))
                                .forward(15)
                                .back(15)
                                .lineToSplineHeading(new Pose2d(-36, 12, Math.toRadians(180)))
                                .forward(25)
                                .lineToSplineHeading(new Pose2d(-36, 11, Math.toRadians(45)))
                                .forward(15)
                                .back(15)
                                .lineToSplineHeading(new Pose2d(-36, 12, Math.toRadians(180)))
                                .forward(25)
                                .lineToSplineHeading(new Pose2d(-36, 11, Math.toRadians(45)))
                                .forward(15)
                                .lineToSplineHeading(new Pose2d(-35.5, 11.5, Math.toRadians(90)))
                                .forward(23)
                                .strafeRight(20)
                                .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}