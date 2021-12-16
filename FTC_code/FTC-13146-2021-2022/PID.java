package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

@TeleOp(name = "PID", group = "Concept")
public class PID extends OpMode {

  public void arcadeDrive(double speed, double turn) {
    double leftPower =  speed - turn;
    double rightPower = speed + turn;

    leftFront.setPower(Range.clip(leftPower, -1.0, 1.0));
    rightFront.setPower(Range.clip(rightPower, -1.0, 1.0));
    leftBack.setPower(Range.clip(leftPower, -1.0, 1.0));
    rightBack.setPower(Range.clip(rightPower, -1.0, 1.0));
  }

  int P, I, D = 0;
  double rcw, setpoint, input, error, integral, derivative, prev_error, prev_time = 0;

  public void setSetpoint(int setpoint) {
    this.setpoint = setpoint;
  }

  ElapsedTime timer = new ElapsedTime();

  public void PID(double input, double set_point) {
    error = setpoint - input;
    double time = timer.milliseconds();
    double deltaTime = time - prev_time;

    this.integral += (error * deltaTime);
    derivative = (error - this.prev_error) / deltaTime;
    prev_time = time;
    this.rcw = P * error + I * this.integral + D * derivative;
  }

  public void execute() {
    PID(0, 0);
    arcadeDrive(0, rcw);
  }

  private ElapsedTime runtime = new ElapsedTime();
  private DcMotor leftFront;
  private DcMotor leftBack;
  private DcMotor rightFront;
  private DcMotor rightBack;
  private Servo servo;

  @Override
  public void init() {
    leftFront = hardwareMap.get(DcMotor.class, "leftFront");
    leftFront.setDirection(DcMotor.Direction.FORWARD);
    leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    leftBack.setDirection(DcMotor.Direction.FORWARD);
    rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    rightFront.setDirection(DcMotor.Direction.FORWARD);
    rightBack = hardwareMap.get(DcMotor.class, "rightBack");
    rightBack.setDirection(DcMotor.Direction.FORWARD);
    Servo servo = hardwareMap.get(Servo.class, "servo");
  }

  private boolean pressedLast = false;

  public void loop() {
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;

    boolean pressed = gamepad1.a;
    if (!pressedLast && pressed) {
      if (servo.getPosition() == 1)
        servo.setPosition(0);
    }
    else {
      servo.setPosition(1);
    }
    execute();
  }
}