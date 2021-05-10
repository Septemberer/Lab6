import java.io.Serializable;
import java.util.ArrayList;
import worker.*;

public class DataList implements Serializable {
    private static final long serialVersionUID = 8888L;
    private ArrayList<Worker> list;

    public DataList (ArrayList<Worker> data){
        this.list = data;
    }

    public ArrayList<Worker> getList(){
        return this.list;
    }

    public void setList(ArrayList<Worker> list){
        this.list = list;
    }
}