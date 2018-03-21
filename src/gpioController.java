import java.io.IOException;

/**
 * Created by Trist on 21-3-2018.
 */
public class gpioController {

    public void initGpio() {
        try {
            Runtime.getRuntime().exec("gpio -g mode 18 out");
            Runtime.getRuntime().exec("gpio -g mode 23 out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void gpioControl(int pin, int engage) {
        try {
            Runtime.getRuntime().exec("gpio -g write " + pin + " " + engage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}