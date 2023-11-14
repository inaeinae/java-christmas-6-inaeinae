package christmas.domain;

import java.util.*;
import java.util.regex.Pattern;

public class EventCalendar {
    public static final int EventYear = 2023;
    public static final int EventMonth = 12;
    public static final int EventStartDate = 1;
    public static final int EventEndDate = 31;
    public static final int DDayEventStartDate = 1;
    public static final int DDayEventEndDate = 25;
    public static final int DDayEventStartDiscount = 1000;
    public static final int DDayEventIncreaseDiscount = 100;
    public static final List<Integer> SpecialEventDates = new ArrayList<>(List.of(3, 10, 17, 24, 25, 31));
    public static final int SpecialEventDiscount = 1000;
    public static final List<Integer> WeekendEventDates = getWeekendDates();
    public static final String WeekEventMenuType = "DESSERT";
    public static final String WeekendEventMenuType = "MAIN";
    public static final int WeekEventDiscount = 2023;
    public static final int SupplementEventMoney = 120000;
    private static final String VISIT_DATE_VALIDATION_ERROR_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private int visitDate;

    private EventCalendar(final String visitDate) {
        validationNull(visitDate);
        validationNumber(visitDate);
        validationDate(visitDate);
        this.visitDate = Integer.parseInt(visitDate);
    }

    public static EventCalendar of(final String visitDate) {
        return new EventCalendar(visitDate);
    }

    public static void validationNull(String input) {
        if (Objects.isNull(input) || input.length() == 0) {
            throw new IllegalArgumentException(VISIT_DATE_VALIDATION_ERROR_MESSAGE);
        }
    }

    public static void validationNumber(String input) {
        if (!Pattern.matches("^[0-9]*$", input)) {
            throw new IllegalArgumentException(VISIT_DATE_VALIDATION_ERROR_MESSAGE);
        }
    }

    public static void validationDate(String input) {
        if (!(Integer.parseInt(input) >= EventStartDate && Integer.parseInt(input) <= EventEndDate)) {
            throw new IllegalArgumentException(VISIT_DATE_VALIDATION_ERROR_MESSAGE);
        }
    }

    private static List<Integer> getWeekendDates() {
        List<Integer> weekendDates = new ArrayList<>();
        Calendar startCalendar = new GregorianCalendar(EventYear, EventMonth-1, EventStartDate);
        Calendar endCalendar = new GregorianCalendar(EventYear, EventMonth-1, EventEndDate);
        while(startCalendar.before(endCalendar)) {
            int weekNumber = startCalendar.get(Calendar.DAY_OF_WEEK);
            if(weekNumber == Calendar.FRIDAY || weekNumber == Calendar.SATURDAY) {
                weekendDates.add(startCalendar.get(Calendar.DATE));
            }
            startCalendar.add(Calendar.DATE, 1);
        }
        return weekendDates;
    }

    public int getVisitDate() {
        return this.visitDate;
    }
}
