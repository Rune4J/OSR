package ethos.rune4j.entity.event;

public class HolidayEvent {

    public int getEventTime() {
        return eventTime;
    }

    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventConfigId() {
        return eventConfigId;
    }

    public void setEventConfigId(int eventConfigId) {
        this.eventConfigId = eventConfigId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "HolidayEvent{" +
                "eventTime=" + eventTime +
                "eventId=" + eventId +
                ", eventConfigId=" + eventConfigId +
                ", active=" + active +
                '}';
    }

    private int eventTime;
    private int eventId;
    private int eventConfigId;
    private boolean active;
}
