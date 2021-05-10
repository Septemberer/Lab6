import worker.Position;
import worker.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class server {

    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server = new ServerSocket(3345)){

            Socket client = server.accept();

            Log.getLogger().info("Connection accepted.");

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            Log.getLogger().info("ObjectOutputStream  created");

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Log.getLogger().info("ObjectInputStream created");
            while(!client.isClosed()){
                DataList data = (DataList) in.readObject();
                ListManager lm = new ListManager(data);
                while (true) {
                    Log.getLogger().info("Server reading from channel");
                    Command command = (Command) in.readObject();
                    if (command.getCommand().equalsIgnoreCase("exit")){
                        lm.saveList();
                        lm.exit();
                        out.writeUTF("exit");
                        out.flush();
                        break;
                    }
                    else if (command.getCommand().equalsIgnoreCase("help")){
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF(lm.helpList());
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("info")){
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF(lm.infoList());
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("show")){
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF(lm.showList());
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("add")){
                        out.writeUTF("element");
                        out.flush();
                        try {
                            Worker worker = (Worker) in.readObject();
                            lm.addWorker(worker);
                            out.writeUTF("next");
                            out.flush();
                        }
                        catch (NullPointerException e) {
                            out.writeUTF("next");
                            out.flush();
                        }

                    }
                    else if (command.getCommand().equalsIgnoreCase("update")){
                        out.writeUTF("element");
                        out.flush();
                        Worker worker = (Worker) in.readObject();
                        lm.updateId(worker, Long.parseLong(command.getArgument()));
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("remove_by_id")){
                        out.writeUTF("no_element");
                        out.flush();
                        lm.removeById(Long.parseLong(command.getArgument()));
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("clear")){
                        out.writeUTF("no_element");
                        out.flush();
                        lm.clearList();
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("remove_last")){
                        out.writeUTF("no_element");
                        out.flush();
                        lm.removeLast();
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("remove_greater")){
                        out.writeUTF("element");
                        out.flush();
                        Worker worker = (Worker) in.readObject();
                        lm.removeGreater(worker);
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("remove_lower")){
                        out.writeUTF("element");
                        out.flush();
                        Worker worker = (Worker) in.readObject();
                        lm.removeLower(worker);
                        out.writeUTF("next");
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("min_by_status")){
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF(lm.minByStatus());
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("filter_by_start_date")){
                        out.writeUTF("no_element");
                        out.flush();

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        out.writeUTF(lm.filterByStartDate(command.getArgument()));
                        out.flush();
                    }
                    else if (command.getCommand().equalsIgnoreCase("filter_greater_than_position")){
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF(lm.filterGreaterThanPosition(Position.valueOf(command.getArgument().toUpperCase())));
                        out.flush();
                    } else {
                        out.writeUTF("no_element");
                        out.flush();
                        out.writeUTF("Wrong COMMAND");
                        out.flush();
                    }
                }
            }
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            in.close();
            out.close();
            client.close();
            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.getLogger().error("EXCEPTION:", e);
        }
    }
}