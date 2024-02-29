package frc.robot.commands.driving;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Dampener;

public class TeleopDrive extends Command {
    private final Drivetrain drivetrain;

    private final XboxController controller;
    // private final XboxController turnController;

    private final int X_AXIS;
    private final int Y_AXIS;

    private Dampener xDampener;
    private Dampener yDampener;

    public TeleopDrive(Drivetrain drivetrain, XboxController controller, int fwdRevAxis, int leftRightAxis) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        // this.turnController = turnController;

        this.X_AXIS = leftRightAxis;
        this.Y_AXIS = fwdRevAxis;

        this.xDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 6);
        this.yDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 4);

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double y = yDampener.dampen(controller.getRawAxis(Y_AXIS));
        double x = xDampener.dampen(turnController.getRawAxis(X_AXIS));

        drivetrain.arcadeDrive(y * 0.85, x * 0.35);
    }
}