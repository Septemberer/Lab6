package worker;

public enum Status {
    FIRED("Fired"),
    PROBATION("Probation"),
    HIRED("Hired"),
    REGULAR("Regular"),
    RECOMMENDED_FOR_PROMOTION("Recommended");

    private final String sta;

    Status(String sta){
        this.sta = sta;
    }
}
