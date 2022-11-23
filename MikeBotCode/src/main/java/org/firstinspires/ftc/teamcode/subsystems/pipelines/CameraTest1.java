package org.firstinspires.ftc.teamcode.subsystems.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class CameraTest1 extends OpenCvPipeline {

    Mat bgr = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        // Is the input RGB or BGR, different on-bot vs. sim?
        // Zero out the first channel. If we're getting RGB then pure-red should render black in the output.
        Mat rgb = input;
        List<Mat> channels = new ArrayList<Mat>();
        Core.split(rgb, channels);
        channels.get(0).setTo(Scalar.all(0));
//        channels.get(1).setTo(Scalar.all(0));
//        channels.get(2).setTo(Scalar.all(0));
        Core.merge(channels, rgb);

        return rgb;
    }

    // Returns an enum being the current position where the robot will park
    public ParkingPosition getPosition() {
        return ParkingPosition.LEFT;
    }
}
