import java.io.IOException;

/**
 * Created by Trist on 21-3-2018.
 */
public class gpioController {

    //Runs pi command to initialize gpio pins
    public void initGpio() {
        try {
            Runtime.getRuntime().exec("gpio -g mode 18 out");
            Runtime.getRuntime().exec("gpio -g mode 23 out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Turns all used pins off
    public void gpioReset()
    {
        try {
            Runtime.getRuntime().exec("gpio -g write 18 0");
            Runtime.getRuntime().exec("gpio -g write 23 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Allows us to turn any specific pin on or off
    public void gpioControl(int pin, int engage) {
        try {
            Runtime.getRuntime().exec("gpio -g write " + pin + " " + engage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}