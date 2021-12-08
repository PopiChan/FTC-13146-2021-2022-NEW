public class PID_test_3 {

    double I = 0;
    double prev_error = 0;
    ElapsedTime timer = new ElapsedTime();
    double prev_time = 0;

    public double PID(double kp, double ki, double kd, double set_point, double input) {

        double time = timer.milliseconds(); // current time between timer construction and this one
        double P = set_point - input; // error
        I = I + P;
        double D = (P - prev_error) / (time - prev_time);
        prev_error = P;
        prev_time = time;

        return P * kp + I * ki + D * kd;
    }
}
