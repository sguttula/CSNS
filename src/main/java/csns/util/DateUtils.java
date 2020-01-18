package csns.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static int getYear( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        return calendar.get( Calendar.YEAR );
    }

}
