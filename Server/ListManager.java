import worker.*;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListManager {
    private ArrayList<Worker> aList;

    public ListManager(DataList arrayList) {
        this.aList = arrayList.getList();
    }

    private LocalDate getCreationDate(){
        return LocalDate.now();
    }

    /**
     * clear
     */

    public void clearList(){
        this.aList.clear();
    }

    /**
     * add
     * @param obj
     */

    public void addWorker(Worker obj){
        this.aList.add(obj);
    }

    /**
     * help
     */

    public String helpList(){
        return ("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_last : удалить последний элемент из коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "min_by_status : вывести любой объект из коллекции, значение поля status которого является минимальным\n" +
                "filter_by_start_date startDate : вывести элементы, значение поля startDate которых равно заданному\n" +
                "filter_greater_than_position position : вывести элементы, значение поля position которых больше заданного");
    }

    /**
     * save
     */

    public void saveList() {
        try {
            Parser parser1 = new Parser(System.getenv("LAB6_OUT"));
            parser1.writeInSCV(aList);
        } catch (FileNotFoundException e){
            System.out.println("Не удалось сохранить");
        }
    }

    /**
     * info
     */

    public String infoList(){
        return("Type of collection: Array List\n" +
        "Date of initialization: " + getCreationDate() + "\nSize of initialization: " + aList.size());
    }

    /**
     * show
     */

    public String showList(){
        if (aList.size() == 0){
            return ("Элементы отсутствуют");
        }
        String result = "";
        for (int i = 0; i < aList.size(); i++){
            result += (elementToString(i) + "\n");
        }
        return result;
    }

    /**
     * method responsible for translating an object from a list to a string representation by its number in Array_List
     * @param n
     * @return
     */

    private String elementToString(int n){
        Worker element = aList.get(n);
        DateFormat df = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
        return element.getId() + ","
                + element.getName() + ",\""
                + element.getCoordinates().toString() + "\","
                + element.getCreationDate() + ","
                + element.getSalary() + ","
                + df.format(element.getStartDate()) + ","
                + element.getPosition() + ","
                + element.getStatus() + ",\""
                + element.getOrganization().getOrganizationType() + ","
                + element.getOrganization().getEmployeesCount() + "\"";
    }

    private void setaList(ArrayList<Worker> aList){
        this.aList = aList;
    }

    /**
     * Updating an item by its id
     * @param worker
     * @param id Written on a separate line
     */

    public void updateId(Worker worker, Long id){
        for (int i = 0; i < aList.size(); i++){
            Worker element = aList.get(i);
            if (id.equals(element.getId())){
                aList.set(i, worker);
            }
        }
    }

    /**
     * remove_by_id
     * @param id Written on a separate line
     */

    public void removeById(Long id){
        for (int i = 0; i < aList.size(); i++){
            Worker element = aList.get(i);
            if (id.equals(element.getId())){
                aList.remove(i);
            }
        }
    }

    /**
     * exit Standard exit from the program
     */

    public void exit(){
        System.out.println("That's all");
    }

    /**
     * remove_last
     */

    public void removeLast(){
        aList.remove(aList.size() - 1);
    }

    /**
     * remove_greater
     * @param worker
     */

    public void removeGreater(Worker worker) {
        ArrayList<Worker> test = getWorkerStream()
                .filter(x -> worker.compareTo(x.getWorkerGetter()) > 0)
                .collect(Collectors.toCollection(ArrayList::new));
        setaList(test);
    }

    /**
     * remove_lower
     * @param worker
     */

    public void removeLower(Worker worker) {
        ArrayList<Worker> test = getWorkerStream()
                .filter(x -> worker.compareTo(x.getWorkerGetter()) < 0)
                .collect(Collectors.toCollection(ArrayList::new));
        setaList(test);
    }

    /**
     * min_by_status
     */

    public String minByStatus(){
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getStatus() == Status.FIRED){
                return (elementToString(i));
            }
        }
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getStatus() == Status.PROBATION){
                return (elementToString(i));
            }
        }
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getStatus() == Status.HIRED){
                return (elementToString(i));
            }
        }
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getStatus() == Status.REGULAR){
                return (elementToString(i));
            }
        }
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getStatus() == Status.RECOMMENDED_FOR_PROMOTION){
                return (elementToString(i));
            }
        }
        return ("Отсутствуют элементы имеющие статус");
    }

    /**
     * filter_by_startDate
     * @param start_date
     */

    public String filterByStartDate(String start_date){
        String result = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (formatter.format(element.getStartDate()).equals(start_date)){
                result += elementToString(i) + "\n";
            }
        }
        return result;
    }

    /**
     * filter_greater_than_position
     * @param position
     */

    public String filterGreaterThanPosition(Position position){
        String result = "";
        for (int i = 0; i < aList.size(); i++) {
            Worker element = aList.get(i);
            if (element.getPosition().hashCode() > position.hashCode()){
                result += elementToString(i) + "\n";
            }
        }
        return result;
    }

    public Stream<Worker> getWorkerStream(){
        return aList.stream();
    }
}
