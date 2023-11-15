package christmas;

import christmas.domain.Events;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventsTest {
    @Test
    void getWeekendDates_2023년_12월의_주말_리턴() {
        assertThat(Events.getWeekendDates()).contains(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    }

    @Test
    void isWeekend_2023년_12월_기준_방문날짜의_주말여부_리턴() {
        Events events = Events.of("25");
        assertThat(events.isWeekend()).isFalse();
    }

    @Test
    void getWeekEventMenuType_평일_주말방문_할인_이벤트_메뉴타입_확인() {
        Events weekEvents = Events.of("25");
        assertThat(weekEvents.getWeekEventMenuType()).isEqualTo(Events.WeekEventMenuType);
        Events weekendEvents = Events.of("1");
        assertThat(weekendEvents.getWeekEventMenuType()).isEqualTo(Events.WeekendEventMenuType);
    }
}
