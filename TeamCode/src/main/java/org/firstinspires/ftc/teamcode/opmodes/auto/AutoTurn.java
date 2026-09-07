package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name="Pedro Turn Test", group="Test")
public class AutoTurn extends OpMode {
    private Follower follower;
    private final Pose testStartPos = new Pose(0,0,0);
    private PathChain leftTurnPath;
    private PathChain rightTurnPath;
    private PathChain returnToZeroPath;

    private boolean lastA = false;
    private boolean lastB = false;
    private boolean lastX = false;

    @Override
    public void init(){
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(testStartPos);

        telemetry.addLine("Pedro Turn Test");
        telemetry.addLine(" ");
        telemetry.addLine("A = turn LEFT 90 degrees");
        telemetry.addLine("B = turn RIGHT 90 degrees");
        telemetry.addLine("X = turn heading to 0 degrees");
        telemetry.update();
    }


    private PathChain buildTurnPath(double targetHeading){
        Pose currentPos= follower.getPose();
        Pose targetPose = new Pose(currentPos.getX(),
                currentPos.getY(),targetHeading);
        return follower.pathBuilder().addPath(
                new BezierPoint(currentPos)//stay at one position
        ).setLinearHeadingInterpolation(currentPos.getHeading(),
                targetPose.getHeading())
                .build();
    }

    @Override
    public void start(){
        follower.setPose(testStartPos);
        lastA = false;
        lastB = false;
        lastX = false;
    }

    @Override
    public void loop(){
        follower.update();
        boolean aPressed = gamepad1.a && !lastA;
        boolean bPressed = gamepad1.b && !lastB;
        boolean xPressed = gamepad1.x && !lastX;

        if (aPressed){
            leftTurnPath = buildTurnPath(Math.toRadians(90));
            follower.followPath(leftTurnPath,true);
        }

        if (bPressed){
            rightTurnPath = buildTurnPath(Math.toRadians(-90));
            follower.followPath(rightTurnPath,true);
        }

        if (xPressed){
            returnToZeroPath=buildTurnPath(0);
            follower.followPath(returnToZeroPath,true);
        }

        lastA = gamepad1.a;
        lastB = gamepad1.b;
        lastX = gamepad1.x;

        Pose currentPos = follower.getPose();
        telemetry.addLine("Pedro localization test");
        telemetry.addData("x",currentPos.getX());
        telemetry.addData("Y",currentPos.getY());
        telemetry.addData("Heading degrees",Math.toDegrees(currentPos.getHeading()));
        telemetry.addData("Follower busy",follower.isBusy());
        telemetry.addData("A button = Left +90 deg",gamepad1.a);
        telemetry.addData("B button = right -90 deg",gamepad1.b);
        telemetry.addData("X button = return to 0 deg",gamepad1.x);
        telemetry.addLine("");
        telemetry.update();

    }
}
