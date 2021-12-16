public class PIDController {
    double kP, kI, kD = 0.3;
    double integral = 0;
    double lastError = 0;
    double lastTime = 0;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
    public double calculate (double input, double setpoint, double time) {
        double error = setpoint - input;
        integral += error * (time - lastTime);
        double derivative = (error - lastError) / (time - lastTime);
        double output = kP * error + kI * integral + kD * derivative;
        lastError = error;
        lastTime = time;
        return output;
    }

    // PIDController pid = new PIDController(0.1, 0.1, 0.1);
    // pid.calculate(0, 0);
}
