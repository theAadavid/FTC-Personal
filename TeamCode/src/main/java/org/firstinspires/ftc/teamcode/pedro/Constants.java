package org.firstinspires.ftc.teamcode.pedro;
//import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.pedropathing.paths.PathConstraints;
//import com.pedropathing.ftc.FollowerBuilder;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.algorithm.Foresight;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.controllers.Controller;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    //no need for mass input
    //constants.java is split between foresight: the algorithm,our drivetrain config and localization/odometry config
    //once autotuner is done it'll give us copy and paste values here and we'll be good to go




    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("left_front");
        c.frontRightName.set("right_front");
        c.backLeftName.set("left_back");
        c.backRightName.set("right_back");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    //public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 67);
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-2.5457436268723854);
        c.yPodOffset.set(-2.243913665531189);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    // ---------------------------------------------------------------------------------------
    // FORESIGHT (path-following algorithm) CONFIG
    // !!! PLACEHOLDER VALUES !!!  These are NOT tuned. Run the "Pedro Tuning" tuners on the Driver
    // Station (Mecanum -> Pinpoint -> Foresight) and paste the printed foresightConfig block over
    // this one, then set FORESIGHT_TUNED = true. AutoPilot will refuse to drive until it is true.
    // ---------------------------------------------------------------------------------------
    public static final boolean FORESIGHT_TUNED = false;

    public static ForesightConfig foresightConfig = new ForesightConfig(c -> {
        Controller primaryTranslationalForward = Controller.proportional(0.1);
        Controller secondaryTranslationalForward = Controller.proportional(0.1);
        Controller primaryTranslationalLateral = Controller.proportional(0.1);
        Controller secondaryTranslationalLateral = Controller.proportional(0.1);

        c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
        c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

        c.coast.set(Controller.proportionalFeedforward(0.01));
        c.brake.set(Controller.proportionalFeedforward(0.01));

        c.headingFeedback.set(Controller.proportional(1.0));
        c.headingBrakeCoefficients.set(Vector2D.cartesian(0.1, 0.01));

        c.linearBrakeCoefficients.set(Matrix.diag(0.1, 0.1));
        c.quadraticBrakeCoefficients.set(Matrix.diag(0.01, 0.01));

        c.maxAchievableForwardVelocity.set(50.0);
        c.maxAchievableStrafeVelocity.set(40.0);
        c.naturalForwardDeceleration.set(30.0);
        c.naturalStrafeDeceleration.set(30.0);
    });

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig));
    }
}
