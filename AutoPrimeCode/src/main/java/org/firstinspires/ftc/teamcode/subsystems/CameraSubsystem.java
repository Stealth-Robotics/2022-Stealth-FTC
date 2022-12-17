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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

// This was taken from the EasyOpenCV SkystoneDeterminationExample
// https://github.com/OpenFTC/EasyOpenCV/blob/526c42cefb13f2a36fe8bae350ef9c80f656dc3a/examples/src/main/java/org/firstinspires/ftc/teamcode/SkystoneDeterminationExample.java
//
// I've not made too many changes from the original sample, since it's easy to read and refer to.
//
// There are minimal changes to the example code to change the region sizes and the color detected.
// It's also been re-packaged into something that can be used from our auto opmodes easily.

// The CameraSubsystem sets up the webcam so we can process each frame and decide what to do.
// Within the subsystem is a "pipeline", which actually does the work on each frame from the camera.

public class CameraSubsystem extends SubsystemBase {
    private final OpenCvCamera webcam;
    private final SleeveDetection pipeline;

    private static final int CAMERA_WIDTH = 320;
    private static final int CAMERA_HEIGHT = 240;

    public CameraSubsystem(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new SleeveDetection();
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
//        FtcDashboard.getInstance().startCameraStream(webcam,25);
    }

    public SleeveDetection.ParkingPosition getPosition() {
        return pipeline.getPosition();
    }

    public void setLeftSide() {
        SleeveDetection.SLEEVE_TOPLEFT_ANCHOR_POINT = SleeveDetection.SLEEVE_LEFT_ANCHOR_POINT;
    }

    public void setRightSide() {
        SleeveDetection.SLEEVE_TOPLEFT_ANCHOR_POINT = SleeveDetection.SLEEVE_RIGHT_ANCHOR_POINT;
    }

    @Override
    public void periodic() {
        telemetry.addData("Camera fps", webcam.getFps());
        telemetry.addData("Parking position", getPosition());
    }
}