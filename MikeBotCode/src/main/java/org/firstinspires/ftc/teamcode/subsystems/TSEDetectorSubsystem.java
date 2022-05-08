/*
 * Copyright (c) 2020 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// This was taken from the EasyOpenCV SkystoneDeterminationExample
// https://github.com/OpenFTC/EasyOpenCV/blob/526c42cefb13f2a36fe8bae350ef9c80f656dc3a/examples/src/main/java/org/firstinspires/ftc/teamcode/SkystoneDeterminationExample.java
//
// I've not made too many changes from the original sample, since it's easy to read and refer to.
//
// There are minimal changes to the example code to change the region sizes and the color detected.
// It's also been re-packaged into something that can be used from our auto opmodes easily.

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

// This detects which of three regions of the camera's view has the most red in it, indicating our
// Team Shipping Element is present in that region.

public class TSEDetectorSubsystem {
    private final HardwareMap hardwareMap;
    private OpenCvCamera webcam;
    private TSEDeterminationPipeline pipeline;

    private static final int CAMERA_WIDTH = 320;
    private static final int CAMERA_HEIGHT = 240;

    public static final String CAMERA_1_NAME = "Webcam 1";
    public static final String CAMERA_2_NAME = "Webcam 2";

    public enum TSEPosition {LEFT, CENTER, RIGHT}

    public TSEDetectorSubsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void setupCamera(String cameraName) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, cameraName), cameraMonitorViewId);
        pipeline = new TSEDeterminationPipeline();
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }

    public TSEPosition getPosition() {
        return pipeline.getAnalysis();
    }

    public static class TSEDeterminationPipeline extends OpenCvPipeline {
        /*
         * Colors used to add squares to the image so we can see the regions we're working with
         */
        static final Scalar BLUE = new Scalar(0, 0, 255);
        static final Scalar GREEN = new Scalar(0, 255, 0);

        /*
         * The core values which define the location and size of the sample regions.
         * Right now we're splitting the view into thirds, top-to-bottom.
         */
        static final int REGION_WIDTH = CAMERA_WIDTH / 3;
        static final int REGION_HEIGHT = CAMERA_HEIGHT;
        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(0, 0);
        static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(REGION_WIDTH, 0);
        static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(REGION_WIDTH * 2, 0);

        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region2_pointA = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x,
                REGION2_TOPLEFT_ANCHOR_POINT.y);
        Point region2_pointB = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region3_pointA = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x,
                REGION3_TOPLEFT_ANCHOR_POINT.y);
        Point region3_pointB = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        /*
         * Working variables
         */
        Mat region1_Cr, region2_Cr, region3_Cr;
        Mat YCrCb = new Mat();
        Mat Cr = new Mat();
        int avg1, avg2, avg3;

        // Volatile since accessed by OpMode thread w/o synchronization.
        // Note: we make the default to be the highest position, that way if detection fails at
        // least we'll score in the most valuable position.
        private volatile TSEPosition position = TSEPosition.RIGHT;

        /*
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Cr channel to the 'Cr' variable
         */
        void inputToCr(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cr, 1);
        }

        @Override
        public void init(Mat firstFrame) {
            /*
             * We need to call this in order to make sure the 'Cr'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */
            inputToCr(firstFrame);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
            region1_Cr = Cr.submat(new Rect(region1_pointA, region1_pointB));
            region2_Cr = Cr.submat(new Rect(region2_pointA, region2_pointB));
            region3_Cr = Cr.submat(new Rect(region3_pointA, region3_pointB));
        }

        @Override
        public Mat processFrame(Mat input) {
            /*
             * Overview of what we're doing:
             *
             * We first convert to YCrCb color space, from RGB color space.
             * Why do we do this? Well, in the RGB color space, chroma and
             * luma are intertwined. In YCrCb, chroma and luma are separated.
             * YCrCb is a 3-channel color space, just like RGB. YCrCb's 3 channels
             * are Y, the luma channel (which essentially just a B&W image), the
             * Cr channel, which records the difference from red, and the Cb channel,
             * which records the difference from blue. Because chroma and luma are
             * not related in YCrCb, vision code written to look for certain values
             * in the Cr/Cb channels will not be severely affected by differing
             * light intensity, since that difference would most likely just be
             * reflected in the Y channel.
             *
             * After we've converted to YCrCb, we extract just the 2nd channel, the
             * Cr channel. We do this because our TSE is bright red and contrast
             * STRONGLY on the Cr channel against everything else.
             *
             * We then take the average pixel value of 3 different regions on that Cr
             * channel, one positioned over each barcode mark on thefield. The brightest of the
             * 3 regions is where we assume the TSE to be.
             *
             * We also draw blue rectangles on the screen showing where the sample regions
             * are, as well as drawing a green rectangle around the sample region
             * we believe is on top of the TSE.
             *
             * In order for this whole process to work correctly, each sample region
             * should be positioned in the center of each of each barcode.
             */

            /*
             * Get the Cr channel of the input frame after conversion to YCrCb
             */
            inputToCr(input);

            /*
             * Compute the average pixel value of each submat region. We're
             * taking the average of a single channel buffer, so the value
             * we need is at index 0. We could have also taken the average
             * pixel value of the 3-channel image, and referenced the value
             * at index 2 here.
             */
            avg1 = (int) Core.mean(region1_Cr).val[0];
            avg2 = (int) Core.mean(region2_Cr).val[0];
            avg3 = (int) Core.mean(region3_Cr).val[0];

            /*
             * Draw a rectangle showing sample region 1 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 2 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region2_pointA, // First point which defines the rectangle
                    region2_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines

            /*
             * Draw a rectangle showing sample region 3 on the screen.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region3_pointA, // First point which defines the rectangle
                    region3_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    2); // Thickness of the rectangle lines


            /*
             * Find the max of the 3 averages
             */
            int maxOneTwo = Math.max(avg1, avg2);
            int max = Math.max(maxOneTwo, avg3);

            /*
             * Now that we found the max, we actually need to go and
             * figure out which sample region that value was from
             */
            if (max == avg1) // Was it from region 1?
            {
                position = TSEPosition.LEFT; // Record our analysis

                /*
                 * Draw a green rectangle around the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region1_pointA, // First point which defines the rectangle
                        region1_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        3); // Thickness of the rectangle lines
            } else if (max == avg2) // Was it from region 2?
            {
                position = TSEPosition.CENTER; // Record our analysis

                /*
                 * Draw a green rectangle around the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region2_pointA, // First point which defines the rectangle
                        region2_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        3); // Thickness of the rectangle lines
            } else if (max == avg3) // Was it from region 3?
            {
                position = TSEPosition.RIGHT; // Record our analysis

                /*
                 * Draw a green rectangle around the chosen region.
                 * Simply a visual aid. Serves no functional purpose.
                 */
                Imgproc.rectangle(
                        input, // Buffer to draw on
                        region3_pointA, // First point which defines the rectangle
                        region3_pointB, // Second point which defines the rectangle
                        GREEN, // The color the rectangle is drawn in
                        3); // Thickness of the rectangle lines
            }

            /*
             * Render the 'input' buffer to the viewport. But note this is not
             * simply rendering the raw camera feed, because we called functions
             * to add some annotations to this buffer earlier up.
             */
            return input;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public TSEPosition getAnalysis() {
            return position;
        }
    }
}