package org.firstinspires.ftc.teamcode.subsystems.pipelines;



import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class TestPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat frame){
        Mat hls  = new Mat();
        Mat sat_mask = new Mat();
//        Mat lum_mask = new Mat();
        Mat rgb = new Mat();
        Mat hueMask = new Mat();
        Imgproc.cvtColor(frame, hls, Imgproc.COLOR_BGR2HLS);

        List<Mat> channels = new ArrayList<Mat>();
        Core.split(hls, channels);

        Core.inRange(channels.get(2),Scalar.all(112),Scalar.all(255),sat_mask);

        channels.get(1).setTo(Scalar.all(0));
        channels.get(1).setTo(Scalar.all(127), sat_mask);  // lightness
        channels.get(2).setTo(Scalar.all(0));
        channels.get(2).setTo(Scalar.all(255), sat_mask);  // saturation



        Core.merge(channels, hls);
        Imgproc.cvtColor(hls, rgb, Imgproc.COLOR_HLS2BGR);


        Core.inRange(rgb, new Scalar(254, 169, 0), new Scalar(255, 180, 0), hueMask);
        Imgproc.cvtColor(rgb, hls, Imgproc.COLOR_BGR2HLS);
        Core.split(rgb, channels);
        channels.get(1).setTo(Scalar.all(0));
        channels.get(1).setTo(Scalar.all(127), hueMask);  // lightness
        channels.get(2).setTo(Scalar.all(0));
        channels.get(2).setTo(Scalar.all(255), hueMask);  // saturation
        channels.get(0).setTo(Scalar.all(0));
        channels.get(0).setTo(Scalar.all(100), hueMask);  // saturation
        Core.merge(channels, hls);
        Imgproc.cvtColor(hls, rgb, Imgproc.COLOR_HLS2BGR);

        Mat sub = rgb.submat(new Rect(120, 0, 80, 240));
        Scalar sum = Core.sumElems(sub);
        double minColor = Math.min(sum.val[0], Math.min(sum.val[1], sum.val[2]));
        return rgb;
    }

}
