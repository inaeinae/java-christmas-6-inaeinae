package christmas;

import christmas.controller.ChristmasController;
import christmas.service.ChristmasService;

public class Application {
    public static void main(String[] args) {
        ChristmasController christmasController = new ChristmasController(new ChristmasService());
        christmasController.start();
    }
}
