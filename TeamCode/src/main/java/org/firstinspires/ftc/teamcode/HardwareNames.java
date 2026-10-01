package org.firstinspires.ftc.teamcode;

/**
 * Every hardware name in one place. These must match the Robot Configuration on the Driver Hub
 * EXACTLY (spelling and underscores). If you rename something in the config, change it here only.
 */
public final class HardwareNames {
    private HardwareNames() {}

    // Drive motors
    public static final String FRONT_LEFT  = "left_front";
    public static final String BACK_LEFT   = "left_back";
    public static final String FRONT_RIGHT = "right_front";
    public static final String BACK_RIGHT  = "right_back";

    // Sensors
    public static final String IMU      = "imu";      // Control Hub built-in IMU
    public static final String PINPOINT = "pinpoint"; // goBILDA Pinpoint odometry (if installed)

    // TODO: add intake / shooter names here when the robot has them
}
