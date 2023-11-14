package christmas.domain;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Orders {
    private static final String ORDER_SEPARATOR = ",";
    private static final String ORDER_MENU_COUNT_SEPARATOR = "-";
    private static final String ORDER_VALIDATION_ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String ONLY_DRINK_VALIDATION_ERROR_MESSAGE = "[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요.";
    private static final String TOTAL_ORDER_COUNT_VALIDATION_ERROR_MESSAGE = "[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다. 다시 입력해 주세요.";
    private Map<Menu, Integer> orders;

    private Orders(final String inputOrders) {
        validationNull(inputOrders);
        validationFormat(inputOrders);
        validationOrderMenu(inputOrders);
        validationOderMenuCount(inputOrders);
        this.orders = makeOrders(inputOrders);
    }

    public static Orders of(final String inputOrders) {
        return new Orders(inputOrders);
    }

    public static void validationNull(String input) {
        if (Objects.isNull(input) || input.length() == 0) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationFormat(String inputOrders) {
        int orderSeparatorCount = inputOrders.length() - inputOrders.replaceAll(ORDER_SEPARATOR, "").length();
        int orderMenuCountSeparatorCount = inputOrders.length() - inputOrders.replaceAll(ORDER_MENU_COUNT_SEPARATOR, "").length();
        if (orderSeparatorCount != orderMenuCountSeparatorCount - 1) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationOrderMenu(String inputOrders) {
        List<String> orderMenus = getOrderMenus(inputOrders);
        validationMenuDuplicate(orderMenus);
        validationMenuExist(orderMenus);
        validationOnlyDrink(orderMenus);
    }

    private List<String> getOrderMenus(String inputOrders) {
        return Arrays.stream(inputOrders.split(ORDER_SEPARATOR))
                .map(orders -> orders.split(ORDER_MENU_COUNT_SEPARATOR)[0])
                .collect(Collectors.toList());
    }

    private void validationMenuDuplicate(List<String> orderMenus) {
        if (orderMenus.stream()
                .distinct()
                .count() < orderMenus.size()) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationMenuExist(List<String> orderMenus) {
        if (orderMenus.stream()
                .filter(orderMenu -> Arrays.stream(Menu.values())
                        .noneMatch(menu -> menu.name().equals(orderMenu)))
                .count() > 0) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationOnlyDrink(List<String> orderMenus) {
        if (matchOrderMenus(orderMenus).stream()
                .filter(orderMenuType -> !orderMenuType.getMenuType().equals("DRINK"))
                .count() == 0) {
            throw new IllegalArgumentException(ONLY_DRINK_VALIDATION_ERROR_MESSAGE);
        }
    }

    private List<Menu> matchOrderMenus(List<String> orderMenus) {
        return orderMenus.stream()
                .map(orderMenu -> Arrays.stream(Menu.values())
                        .filter(menu -> menu.name().equals(orderMenu))
                        .findFirst()
                        .get())
                .distinct()
                .collect(Collectors.toList());
    }

    private void validationOderMenuCount(String inputOrders) {
        List<String> orderMenuCounts = getOrderMenuCounts(inputOrders);
        validationNumberAndCount(orderMenuCounts);
        validationTotalCount(orderMenuCounts);
    }

    private List<String> getOrderMenuCounts(String inputOrders) {
        return Arrays.stream(inputOrders.split(ORDER_SEPARATOR))
                .map(orderMenu -> orderMenu.split(ORDER_MENU_COUNT_SEPARATOR)[1])
                .collect(Collectors.toList());
    }

    private void validationNumberAndCount(List<String> orderMenuCounts) {
        if (orderMenuCounts.stream()
                .filter(orderMenuCount -> !Pattern.matches("^[0-9]*$", orderMenuCount) || !(Integer.parseInt(orderMenuCount) > 0))
                .count() > 0) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationTotalCount(List<String> orderMenuCounts) {
        if (orderMenuCounts.stream()
                .mapToInt(Integer::parseInt)
                .sum() > 20) {
            throw new IllegalArgumentException(TOTAL_ORDER_COUNT_VALIDATION_ERROR_MESSAGE);
        }
    }

    private Map<Menu, Integer> makeOrders(String inputOrders) {
        return Arrays.stream(inputOrders.split(ORDER_SEPARATOR))
                .map(ordersMenu -> ordersMenu.split(ORDER_MENU_COUNT_SEPARATOR))
                .collect(Collectors.toMap(orderMenu -> Menu.valueOf(orderMenu[0]), orderMenu -> Integer.parseInt(orderMenu[1])));
    }

    public Map<Menu, Integer> getOrders() {
        return this.orders;
    }

    public int getTotalOrderAmount() {
        return orders.entrySet().stream()
                .mapToInt(order -> order.getKey().getPrice() * order.getValue())
                .sum();
    }

    @Override
    public String toString() {
        StringJoiner lineJoiner = new StringJoiner("\n");
        lineJoiner.add("<주문 메뉴>");
        orders.entrySet().stream()
                .forEach(order -> lineJoiner.add(String.join(" ", order.getKey().name(), order.getValue()+"개")));
        return lineJoiner.toString();
    }
}
