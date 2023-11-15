package christmas.view;

import christmas.domain.*;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.StringJoiner;

public class OutputView {
    public static void outputEventResult(Orders orders, EventResult eventResult) {
        System.out.println("12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");
        outputOrders(orders);
        outputTotalOrderAmount(orders);
        outputSupplementEventResult(eventResult.getSupplementEventFlag());
        outputEventResult(eventResult);
        outputTotalDiscountAmount(eventResult);
        outputTotalPayAmount(orders.getTotalOrderAmount() - eventResult.getOnlyDiscountEventTotalAmount());
        outputEventBadge(eventResult.getEventBadge());
    }

    private static void outputOrders(Orders orders) {
        System.out.println(orders.toString());
    }

    private static void outputTotalOrderAmount(Orders orders) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<할인 전 총주문 금액>");
        joiner.add(String.format("%s원", new DecimalFormat("###,###").format(orders.getTotalOrderAmount())));
        System.out.println(joiner.toString());
    }

    private static void outputSupplementEventResult(boolean supplementEventFlag) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<증정 메뉴>");
        if(!supplementEventFlag) {
            joiner.add("없음");
            System.out.println(joiner.toString());
            return;
        }
        joiner.add(String.format("%s %d개", Events.SupplementEventMenu.name(), Events.SupplementEventMenuCount));
        System.out.println(joiner.toString());
    }

    private static void outputEventResult(EventResult eventResult) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<혜택 내역>");
        if(eventResult.getDiscountTotalAmount() == 0) {
            joiner.add("없음");
            System.out.println(joiner.toString());
            return;
        }
        eventResult.getDiscountEventResults().entrySet().stream()
                .filter(discountEventResult -> discountEventResult.getValue() > 0)
                .map(discountEventResult -> String.format("%s: -%s원", EventType.valueOf(discountEventResult.getKey()).getEventTypeName(), new DecimalFormat("###,###").format(discountEventResult.getValue())))
                .forEach(joiner::add);
        if(eventResult.getSupplementEventFlag()) {
            int supplementPrice = Events.SupplementEventMenu.getPrice() * Events.SupplementEventMenuCount;
            joiner.add(String.format("%s: -%s원", EventType.valueOf("SupplementEvent").getEventTypeName(), new DecimalFormat("###,###").format(supplementPrice)));
        }
        System.out.println(joiner.toString());
    }

    private static void outputTotalDiscountAmount(EventResult eventResult) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<총혜택 금액>");
        joiner.add(String.format("%s원", new DecimalFormat("###,###").format(eventResult.getDiscountTotalAmount() * -1)));
        System.out.println(joiner.toString());
    }

    private static void outputTotalPayAmount(int totalPayAmount) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<할인 후 예상 결제 금액>");
        joiner.add(String.format("%s원", new DecimalFormat("###,###").format(totalPayAmount)));
        System.out.println(joiner.toString());
    }

    private static void outputEventBadge(EventBadge eventBadge) {
        StringJoiner joiner = new StringJoiner("\n", "", "\n");
        joiner.add("<12월 이벤트 배지>");
        if(Objects.isNull(eventBadge)) {
            joiner.add("없음");
            System.out.println(joiner.toString());
            return;
        }
        joiner.add(eventBadge.getEventBadgeName());
        System.out.println(joiner.toString());
    }
}
