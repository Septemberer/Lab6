package worker;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Класс объектов Worker
 */

public class Worker implements Comparable<Worker>, Serializable {
    private static final long serialVersionUID = 1L;

    private static Long nextId = 1L;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int salary; //Значение поля должно быть больше 0
    private Date startDate; //Поле не может быть null
    private Position position; //Поле не может быть null
    private Status status; //Поле может быть null
    private Organization organization; //Поле может быть null

    /**
     * Конструктор Worker для ввода пользователем (без ID и даты создания)
     * @param name
     * @param x
     * @param y
     * @param salary
     * @param startDate
     * @param position
     * @param status
     * @param organizationType
     * @param employeesCount
     */

    public Worker(String name, float x, Integer y, int salary, Date startDate, Position position, Status status, OrganizationType organizationType, int employeesCount){
        setId();
        setName(name);
        setCoordinates(x, y);
        setCreationDate();
        setSalary(salary);
        setOrganization(organizationType, employeesCount);
        if (startDate == null) {throw new NullPointerException("Поле может быть null");}
        this.startDate = startDate;
        if (position == null) {throw new NullPointerException("Поле может быть null");}
        this.position = position;
        this.status = status;
    }

    public Worker(String name, Coordinates coordinates, int salary, Date startDate, Position position, Status status, Organization organization){
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }

    /**
     * КОнструктор Worker для чтения из начального файла(с ID и Датой создания)
     * @param id
     * @param name
     * @param coordinates
     * @param localDate
     * @param salary
     * @param startDate
     * @param position
     * @param status
     * @param organization
     */

    public Worker(Long id, String name, Coordinates coordinates, LocalDate localDate, int salary, Date startDate, Position position, Status status, Organization organization){
        this.id = id;
        setName(name);
        this.coordinates = coordinates;
        this.creationDate = localDate;
        setSalary(salary);
        this.organization = organization;
        if (startDate == null) {throw new NullPointerException("Поле может быть null");}
        this.startDate = startDate;
        if (position == null) {throw new NullPointerException("Поле может быть null");}
        this.position = position;
        this.status = status;
    }

    private void setId(){
        this.id = nextId + 546564334L;
        nextId += 5443L;
    }

    public Long getId(){
        return this.id;
    }

    private void setName(String name){
        if (name == null) {throw new NullPointerException("Нельзя null");}
        if (name.isEmpty()) {throw new NullPointerException("Нельзя пустую строчку");}
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getSalary() {
        return salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Organization getOrganization() {
        return organization;
    }

    private void setCoordinates(float x, Integer y){
        this.coordinates = new Coordinates(x, y);
    }

    private void setCreationDate(){
        this.creationDate = LocalDate.now();
    }

    private void setOrganization(OrganizationType organizationType, int employeesCount){
        if (employeesCount <= 0){throw new NullPointerException("Значение должно быть больше 0");}
        this.organization = new Organization(organizationType, employeesCount);
    }

    public LocalDate getCreationDate(){
        return this.creationDate;
    }

    private void setSalary(int salary){
        if (salary <= 0) {throw new NullPointerException("Значение должно быть больше 0");}
        this.salary = salary;
    }

    public Worker getWorkerGetter(){
        return new Worker(getName(), getCoordinates(), getSalary(), getStartDate(), getPosition(), getStatus(), getOrganization());
    }

    @Override
    public int compareTo(Worker worker){
        int result = Integer.compare(salary, worker.salary);
        if (result != 0){
            return result;
        }
        return Long.compare(id, worker.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return getSalary() == worker.getSalary() &&
                Objects.equals(getId(), worker.getId()) &&
                Objects.equals(getName(), worker.getName()) &&
                Objects.equals(getCoordinates(), worker.getCoordinates()) &&
                Objects.equals(getCreationDate(), worker.getCreationDate()) &&
                Objects.equals(getStartDate(), worker.getStartDate()) &&
                getPosition() == worker.getPosition() &&
                getStatus() == worker.getStatus() &&
                Objects.equals(getOrganization(), worker.getOrganization());
    }
}

