package ru.paulmy.tractorfield;

public enum TractorColor {
    GREY("textures/tractor_gray.png"),
    ORANGE("textures/tractor_orange.png"),
    GREEN("textures/tractor_green.png"),
    BLUE("textures/tractor_blue.png"),
    YELLOW("textures/tractor_yellow.png"),
    RED("textures/tractor_red.png");
    private String title;

    TractorColor(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
