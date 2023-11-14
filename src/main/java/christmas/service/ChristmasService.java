package christmas.service;

import christmas.domain.EventCalendar;
import christmas.domain.Order;
import christmas.view.InputView;

public class ChristmasService {
    public EventCalendar askVisitDate() {
        return InputView.inputVisitDate();
    }

    public Order askOrder() {
        return InputView.inputOrder();
    }
}
