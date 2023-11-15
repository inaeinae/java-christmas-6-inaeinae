package christmas.service;

import christmas.domain.EventResult;
import christmas.domain.Events;
import christmas.domain.Orders;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.*;

public class ChristmasService {
    public Events askVisitDate() {
        return InputView.inputVisitDate();
    }

    public Orders askOrders() {
        return InputView.inputOrders();
    }

    public void matchEvents(Orders orders, Events events) {
        Map<String, Integer> discountEventResults = new HashMap<>();
        String weekEventType = "WeekEvent";
        if(events.isWeekend()) {
            weekEventType = "WeekendEvent";
        }
        discountEventResults.put(weekEventType, weekEvent(orders, events));
        discountEventResults.put("DDayEvent", dDayEvent(events));
        discountEventResults.put("SpecialEvent", specialEvent(events));
        EventResult eventResult = EventResult.of(discountEventResults, supplementEvent(orders.getTotalOrderAmount()));
        OutputView.outputEventResult(orders, eventResult);
    }

    private int dDayEvent(Events events) {
        if(events.getVisitDate() > Events.DDayEventEndDate) {
            return 0;
        }
        int discountCount = events.getVisitDate() - Events.DDayEventStartDate;
        return Events.DDayEventStartDiscount + (discountCount * Events.DDayEventIncreaseDiscount);
    }

    private int weekEvent(Orders orders, Events events) {
        return orders.getOrders().entrySet().stream()
                .filter(order -> order.getKey().getMenuType().equals(events.getWeekEventMenuType()))
                .mapToInt(order -> Events.WeekEventDiscount * order.getValue())
                .sum();
    }

    private int specialEvent(Events events) {
        int discountAmount = 0;
        if (Events.SpecialEventDates.contains(events.getVisitDate())) {
            discountAmount = Events.SpecialEventDiscount;
        }
        return discountAmount;
    }

    private boolean supplementEvent(int totalOrderAmount) {
        if (totalOrderAmount >= Events.SupplementEventMoney) {
            return true;
        }
        return false;
    }
}
