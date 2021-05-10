package worker;

import java.io.Serializable;

/**
 * Class with the "Organization" field for the Worker object
 */

public class Organization implements Serializable {
    private static final long serialVersionUID = 5L;
    private int employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null

    public Organization(OrganizationType organizationType, int employeesCount){
        this.employeesCount = employeesCount;
        this.type = organizationType;
    }

    public OrganizationType getOrganizationType(){
        return this.type;
    }

    public int getEmployeesCount() {
        return this.employeesCount;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "employeesCount=" + employeesCount +
                ", type=" + type +
                '}';
    }
}
