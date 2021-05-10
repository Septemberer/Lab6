package worker;

import java.io.Serializable;

/**
 * Class with the "Coordinates" field for the Worker object
 */

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 54L;
    private float x;
    private Integer y; //Значение поля должно быть больше -314, Поле не может быть null

    public Coordinates(float x, Integer y){
        this.x = x;
        if (y == null){
            throw new NullPointerException("Нельзя null для y координаты");
        } else if (y <= -314){
            throw new NullPointerException("Значение поля должно быть больше -314");
        } else {
            this.y = y;
        }
    }

    @Override
    public String toString() {
        return "\"" + x + "," + y + "\"";
    }
}
