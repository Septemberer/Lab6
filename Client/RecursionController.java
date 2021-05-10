import java.util.ArrayList;

public class RecursionController {
    private final ArrayList<String> files = new ArrayList<String>();

    RecursionController(){}

    /**
     * Method for checking whether calling scripts causes recursion
     * @param nameScript Script environment variable
     * @return
     */

    public boolean checkRecursion(String nameScript){
        for (String file : this.files) {
            if (file.equals(nameScript)) {
                return true;
            }
        }
        this.files.add(nameScript);
        return false;
    }

    public void clearListFiles(){
        this.files.clear();
    }
}
