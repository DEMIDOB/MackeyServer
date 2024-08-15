import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MackeyServer {
    private String ip, listenAddr;
    private int port;

    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean shouldListen = true;

    private Robot robot;

    public MackeyServer(String ip, int port) throws IOException, AWTException {
        this.ip = ip;
        this.port = port;
        this.listenAddr = addrFromIpAndPort(ip, port);

        this.initializeRobot();
    }

    private void initializeRobot() throws AWTException {
        this.robot = new Robot();
    }

    public void listen() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket clientSocket = null;
        shouldListen = true;

        Main.dlog("Waiting for connection...");
        clientSocket = serverSocket.accept();
        Main.dlog("Accepted connection from " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (shouldListen) {
            String line = in.readLine();
            if (line == null) {
                break;
            }

            Event event = Event.parse(line);
            try {
                handleEvent(event);
            } catch (IllegalArgumentException exc) {
                Main.dlog(exc.getMessage(), "" + event.code);
            }

            out.println("thx :)");
        }

        Main.dlog("Closing connection...");
        tryClose();
        if (clientSocket != null)
            clientSocket.close();
    }

    public void tryClose() {
        shouldListen = false;

        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {}

        try {
            out.close();
        } catch (NullPointerException ignored) {}

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {}
    }

    private void handleEvent(Event event) throws IllegalArgumentException {
        switch (event.type) {
            case KEYBOARD_EVENT:
                KeyboardEvent ke = (KeyboardEvent) event;
                Main.dlog(ke.code + " is down: " + ke.isKeyDown);

                if (ke.isKeyDown) {
                    robot.keyPress(ke.keyCode());
                } else {
                    robot.keyRelease(ke.keyCode());
                }

                break;
            case MOUSE_BUTTON_EVENT:
            {
                MouseEvent me = (MouseEvent) event;
                switch (me.button) {
                    case LEFT:
                        if (me.isMouseButtonDown())
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        else
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        break;
                    case RIGHT:
                        if (me.isMouseButtonDown())
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        else
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        break;
                }
            }
                break;
            case MOUSE_MOVE_EVENT:
            {
                MouseEvent me = (MouseEvent) event;
                Point currentLoc = MouseInfo.getPointerInfo().getLocation();
                robot.mouseMove(currentLoc.x + me.dx, currentLoc.y + me.dy);
            }
                break;
            default:
                break;
        }
    }

    private static String addrFromIpAndPort(String ip, int port) {
        return ip + ":" + port;
    }
}
