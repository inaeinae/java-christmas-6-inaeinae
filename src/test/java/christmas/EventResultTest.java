package christmas;

import christmas.domain.EventBadge;
import christmas.domain.EventResult;
import christmas.domain.Events;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventResultTest {
    private EventResult noSupplmentEventResult;
    private EventResult supplementEventResult;
    @BeforeEach
    void initEventResult() {
        Map<String, Integer> discountEventResults = new HashMap<>();
        discountEventResults.put("WeekEvent", Events.WeekEventDiscount*4);
        discountEventResults.put("DDayEvent", 2300);
        discountEventResults.put("SpecialEvent", 1000);
        noSupplmentEventResult = EventResult.of(discountEventResults, false);
        supplementEventResult = EventResult.of(discountEventResults, true);
    }

    @Test
    void giveEventBadge_총할인_혜택_금액에_따라_배지_부여() {
        assertThat(noSupplmentEventResult.giveEventBadge()).isEqualTo(EventBadge.TREE);
        assertThat(supplementEventResult.giveEventBadge()).isEqualTo(EventBadge.SANTA);
    }

    @Test
    void getOnlyDiscountEventTotalAmount_금액_할인_이벤트_합계_금액() {
        assertThat(noSupplmentEventResult.getOnlyDiscountEventTotalAmount()).isEqualTo(11392);
        assertThat(supplementEventResult.getOnlyDiscountEventTotalAmount()).isEqualTo(11392);
    }

    @Test
    void getDiscountTotalAmount_모든_이벤트_합계_금액() {
        assertThat(noSupplmentEventResult.getDiscountTotalAmount()).isEqualTo(11392);
        assertThat(supplementEventResult.getDiscountTotalAmount()).isEqualTo(11392 + Events.SupplementEventMenu.getPrice());
    }
}
