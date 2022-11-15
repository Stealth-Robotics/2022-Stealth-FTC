package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(12, 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-30, -64, Math.toRadians(90)))
                                .lineToSplineHeading(new Pose2d(-34, -43, Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(-38, -12, Math.toRadians(180)))
                                .forward(18)
                                .lineToSplineHeading(new Pose2d(-32, -12, Math.toRadians(160)))
                                .lineToSplineHeading(new Pose2d(-56, -12, Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(-32, -12, Math.toRadians(160)))
                                .lineToSplineHeading(new Pose2d(-56, -12, Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(-32, -12, Math.toRadians(160)))
                                .lineToSplineHeading(new Pose2d(-56, -12, Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(-32, -12, Math.toRadians(160)))
                                .lineToSplineHeading(new Pose2d(-56, -12, Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(-32, -12, Math.toRadians(160)))
                                .lineToSplineHeading(new Pose2d(-56, -12, Math.toRadians(180)))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
