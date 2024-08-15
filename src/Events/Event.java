package Events;

public class Event {
    public EventType type;
    public int code;

    public Event(EventType type, int code) {
        this.type = type;
        this.code = code;
    }

    public Event() {
        this.type = EventType.UNKNOWN_EVENT;
        this.code = -1;
    }

    public boolean isValid() {
        return type != EventType.UNKNOWN_EVENT && code >= 0;
    }

    public static Event parse(String message) {
        Event newEvent = new Event();
        String[] args = message.split("\\|");

        if (args.length < 2) {
            return newEvent;
        }

        String typeString = args[0];

        switch (typeString.charAt(0)) {
            case 'k':
                newEvent = new KeyboardEvent(args);
                break;
            case 'm':
                newEvent = new MouseEvent(args);
                break;
            default:
                newEvent = new Event();
        }

        return newEvent;
    }

    protected void update(EventType type, int code) {
        this.type = type;
        this.code = code;
    }
}
