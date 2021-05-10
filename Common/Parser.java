import worker.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Parser implements Serializable {
    private String fileName;

    public Parser(String fileName){
        this.fileName = fileName;
    }

    public Parser(){
        this.fileName = " -_- ";
    }

    /**
     * Writing to a csv file
     * @param aList
     * @throws FileNotFoundException
     */

    public void writeInSCV(ArrayList aList) throws FileNotFoundException {
        System.out.println(fileName);
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        try {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            String preText = "id,name,\"x,y\",yyyy-MM-dd,salary,yyyy-MM-dd,position,status,\"organizationType,employeesCount\"\n";
            byte[] preBuffer = preText.getBytes();
            bos.write(preBuffer, 0, preBuffer.length);
            for (Object o : aList) {
                Worker element = (Worker) o;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String text = element.getId() + "L,"
                        + element.getName() + ","
                        + element.getCoordinates().toString() + ","
                        + element.getCreationDate() + ","
                        + element.getSalary() + ","
                        + df.format(element.getStartDate()) + ","
                        + element.getPosition() + ","
                        + element.getStatus() + ",\""
                        + element.getOrganization().getOrganizationType() + ","
                        + element.getOrganization().getEmployeesCount() + "\"\n";
                byte[] buffer = text.getBytes();
                bos.write(buffer, 0, buffer.length);
            }
            bos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Reading from a csv file
     * @param file
     * @return
     */

    public ArrayList<Worker> readFromSCV(String file) {
        Scanner sc = null;
        try {
            System.out.println(file);
            sc = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
        ArrayList<Worker> readList = new ArrayList();
        sc.next();
        // id, name, coordinates, creationDate, salary, startDate, position, status, organization
        String element;
        while (sc.hasNext()){
            element = sc.next();
            Worker worker = null;
            try {
                worker = workerFromString(element);
            } catch (ParseException e) {
                System.out.println("Ошибка парсинга файла csv");
                System.exit(0);
            }
            readList.add(worker);

        }
        sc.close();
        return readList;
    }

    /**
     * Method for getting a Worker object from a single line(csv lines)
     * @param element
     * @return
     * @throws ParseException
     */

    public Worker workerFromString(String element) throws ParseException {
        try {
            String[] parts = element.split(",");
            float coord1 = Float.parseFloat(parts[2].substring(2));
            Integer coord2 = Integer.parseInt(parts[3].substring(0, parts[3].length() - 2), 10);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            OrganizationType type = OrganizationType.valueOf(parts[9].substring(2).toUpperCase());
            int employeesCount = Integer.parseInt(parts[10].substring(0, parts[10].length() - 3), 10);
            return new Worker(Long.parseLong(parts[0].substring(1, parts[0].length() - 1), 10),
                    parts[1],
                    new Coordinates(coord1, coord2),
                    java.time.LocalDate.parse(parts[4], formatter1),
                    Integer.parseInt(parts[5], 10),
                    formatter.parse(parts[6]),
                    Position.valueOf(parts[7].toUpperCase()),
                    Status.valueOf(parts[8].toUpperCase()),
                    new Organization(type, employeesCount));
        } catch (IllegalArgumentException e){
            System.out.println("Неверные аргументы");
            System.exit(0);
            return null;
        }
    }

    /**
     * Method for getting a Worker object from a rowset
     * @param parts
     * @return
     */

    public Worker workerFromLines(String[] parts) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(parts[4]);
            return new Worker(parts[0],
                    Float.parseFloat(parts[1]),
                    Integer.parseInt(parts[2], 10),
                    Integer.parseInt(parts[3], 10),
                    date,
                    Position.valueOf(parts[5].toUpperCase()),
                    Status.valueOf(parts[6].toUpperCase()),
                    OrganizationType.valueOf(parts[7].toUpperCase()),
                    Integer.parseInt(parts[8], 10));
        } catch (ParseException | IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(0);
            return null;
        }
    }
}
