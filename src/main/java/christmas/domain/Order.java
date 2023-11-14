package christmas.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Order {
    private static final String ORDER_SEPARATOR = ",";
    private static final String ORDER_MENU_COUNT_SEPARATOR = "-";
    private static final String ORDER_VALIDATION_ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String ONLY_DRINK_VALIDATION_ERROR_MESSAGE = "[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요.";
    private static final String TOTAL_ORDER_COUNT_VALIDATION_ERROR_MESSAGE = "[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다. 다시 입력해 주세요.";
    private Map<Menu, Integer> order;

    private Order(final String inputOrder) {
        validationNull(inputOrder);
        validationFormat(inputOrder);
        validationOrderMenu(inputOrder);
        validationOderMenuCount(inputOrder);
        this.order = makeOrder(inputOrder);
    }

    public static Order of(final String inputOrder) {
        return new Order(inputOrder);
    }

    public static void validationNull(String input) {
        if (Objects.isNull(input) || input.length() == 0) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationFormat(String inputOrder) {
        int orderSeparatorCount = inputOrder.length() - inputOrder.replaceAll(ORDER_SEPARATOR, "").length();
        int orderMenuCountSeparatorCount = inputOrder.length() - inputOrder.replaceAll(ORDER_MENU_COUNT_SEPARATOR, "").length();
        if (orderSeparatorCount != orderMenuCountSeparatorCount - 1) {
            throw new IllegalArgumentException(ORDER_VALIDATION_ERROR_MESSAGE);
        }
    }

    private void validationOrderMenu(String inputOrder) {
        List<String> orderMenus = getOrderMenus(inputOrder);
        validationMenuDuplicate(orderMenus);
        validationMenuExist(orderMenus);
        validationOnlyDrink(orderMenus);
    }

    private List<String> getOrderMenus(String inputOrder) {
        return Arrays.stream(inputOrder.split(ORDER_SEPARATOR))
                .map(order -> order.split(ORDER_MENU_COUNT_SEPARATOR)[0])
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

    private void validationOderMenuCount(String inputOrder) {
        List<String> orderMenuCounts = getOrderMenuCounts(inputOrder);
        validationNumberAndCount(orderMenuCounts);
        validationTotalCount(orderMenuCounts);
    }

    private List<String> getOrderMenuCounts(String inputOrder) {
        return Arrays.stream(inputOrder.split(ORDER_SEPARATOR))
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

    private Map<Menu, Integer> makeOrder(String inputOrder) {
        return Arrays.stream(inputOrder.split(ORDER_SEPARATOR))
                .map(orderMenu -> orderMenu.split(ORDER_MENU_COUNT_SEPARATOR))
                .collect(Collectors.toMap(orderMenu -> Menu.valueOf(orderMenu[0]), orderMenu -> Integer.parseInt(orderMenu[1])));
    }
}
