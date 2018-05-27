package ltd.vblago.rateme.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Opinion {
    public String date;
    public String time;
    public String point;
    public String q1, q2, q3, q4, q5;

    public Opinion() {
        setDate();
        this.q1 = "0";
        this.q2 = "0";
        this.q3 = "0";
        this.q4 = "0";
        this.q5 = "0";
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();

        date = String.format(Locale.US,"%02d.%02d.%d",
                        calendar.get(Calendar.DAY_OF_MONTH),
                        (calendar.get(Calendar.MONTH)+1),
                        calendar.get(Calendar.YEAR));
    }

    public void setTime() {
        Calendar calendar = Calendar.getInstance();

        time = String.format(Locale.US,"%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY),
                (calendar.get(Calendar.MINUTE)));
    }
}
