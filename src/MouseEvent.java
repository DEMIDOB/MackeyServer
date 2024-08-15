public class MouseEvent extends Event {
    public static final int CODE_CONST = 10000;

    public int dx, dy;
    public MouseButton button;

    public MouseEvent(String[] args) {
        super(EventType.MOUSE_MOVE_EVENT, 0);
//        Main.dlog(args);

        if (args[1].equals("m")) {
            // moveasdasdasddeweqweeew
            dx = Integer.parseInt(args[2]);
            dy = Integer.parseInt(args[3]);
            update(EventType.MOUSE_MOVE_EVENT, 0);
        } else {
            // button
            update(EventType.MOUSE_BUTTON_EVENT, args[1].charAt(0));
            switch (args[2].charAt(0)) {
                case 'r':
                    Main.dlog("Right!");
                    button = MouseButton.RIGHT;
                    break;
                default:
                    Main.dlog("LEFT!");
                    button = MouseButton.LEFT;
                    break;
            }
        }
    }

    public boolean isMouseButtonDown() {
        return code == 'd';
    }
}
