

import worker.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static boolean exitCode = false;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3345);
             Scanner sc = new Scanner(new InputStreamReader(System.in));
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            String PATH = System.getenv("LAB6_IN");
            Parser parser = new Parser();
            ArrayList<Worker> list = parser.readFromSCV(PATH);
            DataList data1 = new DataList(list);
            oos.writeObject(data1);
            ArrayList<Long> startIDs = new ArrayList<Long>();
            for (Worker worker : list) {
                startIDs.add(worker.getId());
            }
            RecursionController rc = new RecursionController();
            while (!exitCode) {
                System.out.println("Введите команду:");
                Command command = new Command(sc.nextLine());
                if (command.getCommand().equals("execute_script")) {
                    String fileName = command.getArgument();
                    System.out.println(fileName);
                    if (rc.checkRecursion(fileName)) {
                        continue;
                    }
                    Scanner scScript;
                    try {
                        scScript = new Scanner(new File(fileName));
                    } catch (FileNotFoundException e) {
                        System.out.println("Не могу исполнить этот скрипт");
                        continue;
                    }
                    while (scScript.hasNext()) {
                        Command commandScript = new Command(scScript.nextLine());
                        oos.writeObject(commandScript);
                        oos.flush();
                        String answer = ois.readUTF();
                        if (answer.equals("element")){
                            String[] lines = new String[9];
                            Validator vr = new Validator();
                            lines[0] = scScript.nextLine();
                            lines[1] = scScript.nextLine();
                            lines[2] = scScript.nextLine();
                            lines[3] = scScript.nextLine();
                            lines[4] = scScript.nextLine();
                            lines[5] = scScript.nextLine();
                            lines[6] = scScript.nextLine();
                            lines[7] = scScript.nextLine();
                            lines[8] = scScript.nextLine();
                            if (!vr.isName(lines[0]) || !vr.isFloat(lines[1]) || !vr.isIntCoord(lines[2]) ||
                                    !vr.isInt(lines[3]) || !vr.isDate(lines[4]) || !vr.isPosition(lines[5]) ||
                                    !vr.isStatus(lines[6]) || !vr.isOrganizationType(lines[7])|| !vr.isInt(lines[8])){
                                System.out.println("File broken");
                                oos.writeObject(null);
                            } else {
                                oos.writeObject(parser.workerFromLines(lines));
                            }
                            oos.flush();
                        }else if (answer.equals("exit")){
                            sc.close();
                            oos.close();
                            ois.close();
                            break;
                        }
                        String answer1 = ois.readUTF();
                        if (!answer1.equals("next")){
                            System.out.println(answer1);
                        }
                    }
                    rc.clearListFiles();
                    scScript.close();
                }
                oos.writeObject(command);
                oos.flush();
                String answer = ois.readUTF();
                if (answer.equals("element")){
                    String[] lines = new String[9];
                    Validator vr = new Validator();
                    System.out.println("Введите String name:");
                    lines[0] = sc.nextLine();
                    while (!vr.isName(lines[0])){
                        System.out.println("Неверно");
                        System.out.println("Введите String name:");
                        lines[0] = sc.nextLine();
                    }
                    System.out.println("Введите float x:");
                    lines[1] = sc.nextLine();
                    while (!vr.isFloat(lines[1])){
                        System.out.println("Неверно");
                        System.out.println("Введите float x:");
                        lines[1] = sc.nextLine();
                    }
                    System.out.println("Введите Integer y:");
                    lines[2] = sc.nextLine();
                    while (!vr.isIntCoord(lines[2])){
                        System.out.println("Неверно");
                        System.out.println("Введите Integer y:");
                        lines[2] = sc.nextLine();
                    }
                    System.out.println("Введите int salary:");
                    lines[3] = sc.nextLine();
                    while (!vr.isInt(lines[3])){
                        System.out.println("Неверно");
                        System.out.println("Введите int salary:");
                        lines[3] = sc.nextLine();
                    }
                    System.out.println("Введите Date StartDate(YYYY-MM-DD):");
                    lines[4] = sc.nextLine();
                    while (!vr.isDate(lines[4])){
                        System.out.println("Неверно");
                        System.out.println("Введите Date StartDate:");
                        lines[4] = sc.nextLine();
                    }
                    System.out.println("Введите Position (HEAD_OF_DIVISION, COOK, MANAGER_OF_CLEANING):");
                    lines[5] = sc.nextLine();
                    while (!vr.isPosition(lines[5])){
                        System.out.println("Неверно");
                        System.out.println("Введите Position position:");
                        lines[5] = sc.nextLine();
                    }
                    System.out.println("Введите Status (FIRED,PROBATION, HIRED, REGULAR, RECOMMENDED_FOR_PROMOTION):");
                    lines[6] = sc.nextLine();
                    while (!vr.isStatus(lines[6])){
                        System.out.println("Неверно");
                        System.out.println("Введите Status status:");
                        lines[6] = sc.nextLine();
                    }
                    System.out.println("Введите OrganizationType (COMMERCIAL, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY)");
                    lines[7] = sc.nextLine();
                    while (!vr.isOrganizationType(lines[7])){
                        System.out.println("Неверно");
                        System.out.println("Введите OrganizationType organizationType");
                        lines[7] = sc.nextLine();
                    }
                    System.out.println("Введите int employeesCount:");
                    lines[8] = sc.nextLine();
                    while (!vr.isInt(lines[8])){
                        System.out.println("Неверно");
                        System.out.println("Введите int employeesCount:");
                        lines[8] = sc.nextLine();
                    }
                    oos.writeObject(parser.workerFromLines(lines));
                    oos.flush();
                }else if (answer.equals("exit")){
                    sc.close();
                    oos.close();
                    ois.close();
                    break;
                }
                String answer1 = ois.readUTF();
                if (!answer1.equals("next")){
                    System.out.println(answer1);
                }
            }
        } catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }
}
