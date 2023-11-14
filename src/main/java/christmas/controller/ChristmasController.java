package christmas.controller;

import christmas.domain.EventCalendar;
import christmas.domain.Orders;
import christmas.service.ChristmasService;

public class ChristmasController {
    private final ChristmasService christmasService;

    public ChristmasController(ChristmasService christmasService) { this.christmasService = christmasService; }

    public void start() {
        EventCalendar eventCalendar = christmasService.askVisitDate();
        Orders orders = christmasService.askOrders();
        christmasService.matchEvents(orders, eventCalendar);
    }

    /*
    안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.
    12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)
    3
    주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)
    티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1
    12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!

    <주문 메뉴>
    티본스테이크 1개
    바비큐립 1개
    초코케이크 2개
    제로콜라 1개

    <할인 전 총주문 금액>
    142,000원

    <증정 메뉴>
    샴페인 1개

    <혜택 내역>
    크리스마스 디데이 할인: -1,200원
    평일 할인: -4,046원
    특별 할인: -1,000원
    증정 이벤트: -25,000원

    <총혜택 금액>
    -31,246원

    <할인 후 예상 결제 금액>
    135,754원

    <12월 이벤트 배지>
    산타
     */
}
