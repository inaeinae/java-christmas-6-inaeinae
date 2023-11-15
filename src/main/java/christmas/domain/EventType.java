package christmas.domain;

public enum EventType {
    DDayEvent("크리스마스 디데이 할인"),
    WeekEvent("평일 할인"),
    WeekendEvent("주말 할인"),
    SpecialEvent("특별 할인"),
    SupplementEvent("증정 이벤트");

    private String eventTypeName;

    EventType(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getEventTypeName() { return this.eventTypeName; }
}
