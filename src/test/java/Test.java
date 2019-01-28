import java.util.Calendar;
import java.util.Date;

public class Test {


    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,34);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        System.out.println(calendar.getTime().getTime());
       long now =  new Date().getTime();
        System.out.println(now);
       Date d = new Date(calendar.getTime().getTime() - new Date().getTime());

        System.out.println(d.getTime());
    }
}
