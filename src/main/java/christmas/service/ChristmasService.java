package christmas.service;

import christmas.domain.EventCalendar;
import christmas.domain.Menu;
import christmas.domain.Orders;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class ChristmasService {
    public EventCalendar askVisitDate() {
        return InputView.inputVisitDate();
    }

    public Orders askOrders() {
        return InputView.inputOrders();
    }

    public void matchEvents(Orders orders, EventCalendar eventCalendar) {
        int totalOrderAmount = orders.getTotalOrderAmount();
        int dDayEventDiscount = dDayEvent(eventCalendar);
        int weekEventDiscount = weekEvent(orders, eventCalendar);
        int specialEventDiscount = specialEvent(eventCalendar);
        boolean supplementEventPossibility = supplementEvent(totalOrderAmount);
        System.out.println();
    }

    private int dDayEvent(EventCalendar eventCalendar) {
        int discountCount = eventCalendar.getVisitDate() - EventCalendar.DDayEventStartDate;
        return EventCalendar.DDayEventStartDiscount + (discountCount * EventCalendar.DDayEventIncreaseDiscount);
    }

    private int weekEvent(Orders orders, EventCalendar eventCalendar) {
        String discountMenuType = getWeekEventMenuType(eventCalendar);
        return orders.getOrders().entrySet().stream()
                .filter(order -> order.getKey().getMenuType().equals(discountMenuType))
                .mapToInt(order -> EventCalendar.WeekEventDiscount * order.getValue())
                .sum();
    }

    private String getWeekEventMenuType(EventCalendar eventCalendar) {
        if (EventCalendar.WeekendEventDates.contains(eventCalendar.getVisitDate())) {
            return EventCalendar.WeekendEventMenuType;
        }
        return EventCalendar.WeekEventMenuType;
    }

    private int specialEvent(EventCalendar eventCalendar) {
        int discountAmount = 0;
        if (EventCalendar.SpecialEventDates.contains(eventCalendar.getVisitDate())) {
            discountAmount = EventCalendar.SpecialEventDiscount;
        }
        return discountAmount;
    }

    private boolean supplementEvent(int totalOrderAmount) {
        if (totalOrderAmount >= EventCalendar.SupplementEventMoney) {
            return true;
        }
        return false;
    }
}
