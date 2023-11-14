package christmas.service;

import christmas.domain.Events;
import christmas.domain.Orders;
import christmas.view.InputView;

public class ChristmasService {
    public Events askVisitDate() {
        return InputView.inputVisitDate();
    }

    public Orders askOrders() {
        return InputView.inputOrders();
    }

    public void matchEvents(Orders orders, Events events) {
        int totalOrderAmount = orders.getTotalOrderAmount();
        int dDayEventDiscount = dDayEvent(events);
        int weekEventDiscount = weekEvent(orders, events);
        int specialEventDiscount = specialEvent(events);
        boolean supplementEventPossibility = supplementEvent(totalOrderAmount);
        System.out.println();
    }

    private int dDayEvent(Events events) {
        int discountCount = events.getVisitDate() - Events.DDayEventStartDate;
        return Events.DDayEventStartDiscount + (discountCount * Events.DDayEventIncreaseDiscount);
    }

    private int weekEvent(Orders orders, Events events) {
        String discountMenuType = getWeekEventMenuType(events);
        return orders.getOrders().entrySet().stream()
                .filter(order -> order.getKey().getMenuType().equals(discountMenuType))
                .mapToInt(order -> Events.WeekEventDiscount * order.getValue())
                .sum();
    }

    private String getWeekEventMenuType(Events events) {
        if (Events.WeekendEventDates.contains(events.getVisitDate())) {
            return Events.WeekendEventMenuType;
        }
        return Events.WeekEventMenuType;
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
