package org.firstinspires.ftc.teamcode.opmodes.auto;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.SampleAutoPathing;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.ftc.NextFTCOpMode;

//Test 1: push the robot forward manually,
// watch telemetry to check x increase when robot moves physically forward
//Test 2: test auto move from the start position (0,0,0) to (12,0,0) when
// press gamepad1.a (A - X).
//Test 3: test auto move from the current position of the robot back
// to the starting point when press gamepad2.b (B - O).
//watch the robot move physically forward/backward

@Autonomous(name="Pedro Simple Drive Test ",group="Test")
public class AutoPP extends OpMode {

    private Follower follower;
    //Test 2
    private final Pose testTwoStartPose= new Pose(0,0,0);
    private final Pose testTwoEndPose = new Pose(12,0,0);

    private Pose currentPos = testTwoEndPose;
    private PathChain testForwardPath;
    private PathChain testBackwardPath;
    //private boolean pathStarted = false;

    private boolean lastA = false;
    private boolean lastB = false;
    public void buildForwardPath(){
        //put in coordinates for starting pos to ending pos
        testForwardPath= follower.pathBuilder()
                .addPath(new BezierLine(testTwoStartPose,testTwoEndPose))
                .setLinearHeadingInterpolation(testTwoStartPose.getHeading(),
                        testTwoEndPose.getHeading())
                .build();
        //testBackwardPath = follower.pathBuilder()
          //      .addPath(new BezierLine(currentPos,testTwoStartPose))
            //    .setConstantHeadingInterpolation(0)
              //  .build();
    }

    public void buildBackwardPath(){
        currentPos = follower.getPose();
        testBackwardPath = follower.pathBuilder()
                .addPath(new BezierLine(currentPos,testTwoStartPose))
                .setLinearHeadingInterpolation(currentPos.getHeading(),
                        testTwoStartPose.getHeading())
                .build();
    }

    @Override
    public void init(){

        follower= Constants.createFollower(hardwareMap);
        follower.setPose(testTwoStartPose);
        //buildPaths();
        buildForwardPath();
        telemetry.addLine("Pedro diagnostic ready");
        telemetry.addLine("Test 1: Push robot forward manually");
        telemetry.addLine("X should increase");
        telemetry.addLine("Test 2: Place robot at start and press A");
        telemetry.addLine("Robot should drive forward about 12 inches");
        telemetry.addLine("Test 3: Robot move backward when press B");
        telemetry.update();
    }

    @Override
    public void start(){
        /* reset pose when START is pressed
        * robot should physically by positioned at the test starting point
         */
        follower.setPose(testTwoStartPose);
        lastA= false;
        lastB = false;
       // pathState = pathState;
    }

    @Override
    public void loop(){
        /*Always update Pedro
         pinpoint localization, follower calculations, and motor control
         */
        follower.update();

        /* Test 2: Press gamepad A once to start the simple Path

         */
        boolean aPressed = gamepad1.a && !lastA;
        boolean bPressed = gamepad2.b && !lastB;

        if (aPressed){
            follower.followPath(testForwardPath,true);
        }

        if (bPressed){
            buildBackwardPath();
            follower.followPath(testBackwardPath,true);
        }
        lastA = gamepad1.a;
        lastB = gamepad2.b;

        currentPos = follower.getPose();
        telemetry.addLine("Pedro localization test");
        telemetry.addData("x",currentPos.getX());
        telemetry.addData("Y",currentPos.getY());
        telemetry.addData("Heading degrees",Math.toDegrees(currentPos.getHeading()));
        telemetry.addData("Follower busy",follower.isBusy());
        telemetry.addData("A button",gamepad1.a);
        telemetry.addData("B button",gamepad1.b);
        telemetry.addLine("");
        telemetry.addLine("Manual localization test");
        telemetry.addLine("Push robot FORWARD -> x should increase");
        telemetry.addLine("Push robot LEFT -> Y should increase");
        telemetry.addLine(" ");
        telemetry.addLine("Press A to run the simple path");
        telemetry.addLine("from (0,0) to (12,0)");
        telemetry.addLine("Press B");
        telemetry.addLine("Robot move from (12,0) to (0,0)");

        //if (pathStarted && !follower.isBusy()){
        //    telemetry.addLine("Path finished");
        //}
        telemetry.update();
    }

}
