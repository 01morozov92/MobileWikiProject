package lib.uiMobile;

public enum Direction {
    вправо("Right"),
    влево("Left"),
    вверх("Up"),
    вниз("Down");

    private final String direcrion;

    Direction(String direcrion) {
        this.direcrion = direcrion;
    }

    public String Direction() {
        return direcrion;
    }
}
