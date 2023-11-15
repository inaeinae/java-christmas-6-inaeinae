package christmas.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    STAR("별", 5000),
    TREE("트리", 10000),
    SANTA("산타", 20000);

    private String BadgeName;
    private int totalDiscountAmountPoint;

    EventBadge(String BadgeName, int totalDiscountAmountPoint) {
        this.totalDiscountAmountPoint = totalDiscountAmountPoint;
    }

    public static EventBadge valueOf(int totalDiscountAmount) {
        return Arrays.stream(values())
                .filter(eventBadge -> totalDiscountAmount >= eventBadge.getTotalDiscountAmountPoint())
                .sorted(Comparator.comparing(EventBadge::getTotalDiscountAmountPoint).reversed())
                .findFirst()
                .orElse(null);
    }

    public int getTotalDiscountAmountPoint() {
        return this.totalDiscountAmountPoint;
    }
}
