package worker;

public enum OrganizationType {
    COMMERCIAL("Комерческая"),
    PRIVATE_LIMITED_COMPANY("Приватная"),
    OPEN_JOINT_STOCK_COMPANY("Открытая компания");

    private final String type;

    OrganizationType(String type){
        this.type = type;
    }

    public String organizationTypeToString(){
        return type;
    }
}

