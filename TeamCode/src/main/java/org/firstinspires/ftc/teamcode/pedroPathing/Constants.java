package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants();

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    //added based on subsystem classes

    public static MecanumConstants driveConstants=
            new MecanumConstants().maxPower(1.0)
                    .rightFrontMotorName("rightFront")
                    .rightRearMotorName("rightBack")
                    .leftFrontMotorName("leftFront")
                    .leftRearMotorName("leftBack")
                    .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
                    .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
                    .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
                    .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE);

            //added based on subsystem classes
    public static PinpointConstants localizerConstants=
                    new PinpointConstants().hardwareMapName("odom")
                            .distanceUnit(DistanceUnit.MM)
                            .forwardPodY(70)
                            .strafePodX(100)//these two should be verified by Pedro's tuning
                            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
                            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
                            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);//change it if wrong


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)//added
                .pinpointLocalizer(localizerConstants)//added
                .pathConstraints(pathConstraints)
                .build();
    }
}