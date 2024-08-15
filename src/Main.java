import java.awt.*;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static boolean IS_DEBUG = true;
    public static int SERVER_PORT = 9230;

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        // TODO: get my local ip and output the whole address
        // TODO: ?accept only local network connections?

        MackeyServer server = new MackeyServer("0.0.0.0", SERVER_PORT);
        while (true) {
            try {
                server.listen();
            } catch (Exception e) {
                dlog("Exception was thrown!");
                System.err.println(e.getMessage());
                throw e;
            }

            server.tryClose();
        }
    }

    public static void dlog(String... args) {
        // TODO: ?consider logging to a file when in release mode?
        if (!IS_DEBUG) {
            return;
        }

        System.out.print("[DEBUG] ");

        for (String arg : args) {
            System.out.print(arg);
        }

        System.out.println();
    }
}