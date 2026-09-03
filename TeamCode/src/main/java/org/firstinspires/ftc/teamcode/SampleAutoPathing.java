package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

//The example are in two steps, the first step shows from start to shoot position.
//step 2 shows at shoot position, go to end pos and also turn .
@TeleOp
public class SampleAutoPathing extends OpMode {

    private Follower follower;
    private Timer pathTimer, opModeTimer;

    //for states of the path
    public enum PathState{
        //START POSItion to end positon
        //drive> movement state
        //shot >>attempt to shot state
        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD,

        //add one state, example step 2
        DRIVE_SHOOTPOS_ENDPOS

    }

    PathState pathState;
    private final Pose startPose=new Pose(20.3862,122.3978,Math.toRadians(138));
    private final Pose shootPose=new Pose(46.415,96.9002,Math.toRadians(138));

    //add one pose, example step 2
    private final Pose endPose=new Pose(63.7676,105.7535,Math.toRadians(90));

    private PathChain driveStartPosShootPos;

    //add one pathchain, example step 2
    private PathChain driveShootPosEndPos;

    public void buildPaths(){
        //put in coordinates for starting pos to ending pos
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPose,shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(),shootPose.getHeading())
                .build();
        //build one more pathchain, example step 2
        driveShootPosEndPos = follower.pathBuilder()
                .addPath(new BezierLine(shootPose,endPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(),endPose.getHeading())
                .build();
    }

    public void statePathUpdate(){
        switch(pathState){
            case DRIVE_STARTPOS_SHOOT_POS:
                follower.followPath(driveStartPosShootPos,true);
                //pathState=PathState.SHOOT_PRELOAD;
                transitionState(PathState.SHOOT_PRELOAD);
                break;
         //   case SHOOT_PRELOAD: //test this case for example step 1
            //     //check is follower done it's path
          //      if (!follower.isBusy()){
           //         telemetry.addLine("Done Path start_to_shot");
           //     }
           //     break;
            case SHOOT_PRELOAD://add for example step 2
                //check is follower done it's path and check that 5 second has elapsed
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds()>5){
                    follower.followPath(driveShootPosEndPos,true);
                    transitionState(PathState.DRIVE_SHOOTPOS_ENDPOS);
                }
                break;
            case DRIVE_SHOOTPOS_ENDPOS://add one case ,example step 2
                if (!follower.isBusy()){
                    telemetry.addLine("Done Path 2 shoot to end");
                }
            default:
                telemetry.addLine("No State");
                break;
        }
    }

    public void transitionState(PathState newState){
        pathState = newState;
        pathTimer.resetTimer();
    }
    @Override
    public void init(){
        pathState = PathState.DRIVE_STARTPOS_SHOOT_POS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        //add in any other init mechanics

        buildPaths();
        follower.setPose(startPose);//where the robot starts

    }

    public void start(){
        opModeTimer.resetTimer();
        transitionState(pathState);
    }
    @Override
    public void loop(){
        //always do this first
        follower.update();
        statePathUpdate();

        telemetry.addData("path state",pathState.toString());
        telemetry.addData("x",follower.getPose().getX());
        telemetry.addData("y",follower.getPose().getY());
        telemetry.addData("heading",follower.getPose().getHeading());
        telemetry.addData("Path time",pathTimer.getElapsedTimeSeconds());
    }
}
