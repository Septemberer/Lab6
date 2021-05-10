import worker.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Validator {
    public boolean isFloat(String str){
        if (str == null){
            return false;
        }
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isName(String str){
        if (str == null || str.isEmpty()){
            return false;
        }
        return (!Pattern.matches("\\W",str));
    }

    public boolean isIntCoord(String str){
        if (str == null){
            return false;
        }
        try {
            return Integer.parseInt(str) >= -313;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isInt(String str){
        if (str == null){
            return false;
        }
        try {
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDate(String str) {
        if (str == null){
            return false;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(str);
            return true;
        } catch (ParseException e){
            return false;
        }
    }

    public boolean isPosition(String str) {
        if (str == null){
            return false;
        }
        try{
            Position.valueOf(str.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean isStatus(String str) {
        try{
            Status.valueOf(str.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean isOrganizationType(String str) {
        try{
            OrganizationType.valueOf(str.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
