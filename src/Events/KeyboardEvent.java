package Events;

public class KeyboardEvent extends Event {
    public boolean isKeyDown;

    public KeyboardEvent(String[] args) {
        super(EventType.KEYBOARD_EVENT, Integer.parseInt(args[2]));

        isKeyDown = false;

        if (args.length >= 3) {
            isKeyDown = args[1].equals("d");
        }
    }

    public int keyCode() {
        return code;
    }
}
