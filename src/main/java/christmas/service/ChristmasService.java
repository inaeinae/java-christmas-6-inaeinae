package christmas.service;

import christmas.domain.EventCalendar;
import christmas.view.InputView;

public class ChristmasService {
    public EventCalendar askVisitDate() {
        return InputView.inputVisitDate();
    }
}
