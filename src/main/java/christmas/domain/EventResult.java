package christmas.domain;

import java.util.Map;

public class EventResult {
    private Map<String, Integer> discountEventResults;
    private boolean supplementEventFlag;
    private EventBadge eventBadge;

    private EventResult(final Map<String, Integer> discountEventResults, final boolean supplementEventFlag) {
        this.discountEventResults = discountEventResults;
        this.supplementEventFlag = supplementEventFlag;
        this.eventBadge = giveEventBadge();
    }

    public static EventResult of(final Map<String, Integer> discountEventResults, final boolean supplementEventFlag) {
        return new EventResult(discountEventResults, supplementEventFlag);
    }

    private EventBadge giveEventBadge() {
        return EventBadge.valueOf(getDiscountTotalAmount());
    }

    public int getDiscountTotalAmount() {
        int discountTotalAmount = getOnlyDiscountEventTotalAmount();
        if(supplementEventFlag) {
            discountTotalAmount += Events.SupplementEventMenu.getPrice() * Events.SupplementEventMenuCount;
        }
        return discountTotalAmount;
    }

    public int getOnlyDiscountEventTotalAmount() {
        return this.discountEventResults.entrySet().stream()
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<String, Integer> getDiscountEventResults() {
        return this.discountEventResults;
    }

    public boolean getSupplementEventFlag() {
        return this.supplementEventFlag;
    }

    public EventBadge getEventBadge() {
        return this.eventBadge;
    }
}
