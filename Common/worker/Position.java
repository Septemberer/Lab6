package worker;

public enum Position {
    HEAD_OF_DIVISION("Начальник отдела"),
    COOK("Повар"),
    MANAGER_OF_CLEANING("Уборщик");

    private final String pos;

    Position(String pos){
        this.pos = pos;
    }

    public String positionToString() {
        return pos;
    }
}
