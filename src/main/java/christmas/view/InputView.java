package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.EventCalendar;
import christmas.domain.Order;
import org.mockito.internal.matchers.Or;

import java.util.Objects;
import java.util.regex.Pattern;

public class InputView {
    public static EventCalendar inputVisitDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        while(true) {
            try {
                return EventCalendar.of(Console.readLine());
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Order inputOrder() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        while(true) {
            try {
                return Order.of(Console.readLine().replaceAll(" ", ""));
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
