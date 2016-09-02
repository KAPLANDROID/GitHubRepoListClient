package com.kaplandroid.githubrepolistclient.util;

import android.content.Context;

import com.kaplandroid.githubrepolistclient.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */
public class DateUtil {

    /**
     * Convert a millisecond duration to a string format
     *
     * @return A string of the form "Y Hours Z Minutes A Seconds" or if more than 24 hours returns past date string
     */
    public static String getUpdateTimeText(Context ctx, Date d1, Date d2) {

        long millis = d2.getTime() - d1.getTime();

        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(ctx.getString(R.string.updated));
        sb.append(" ");
        if (days == 0) {
            if (hours != 0) {
                sb.append(hours);
                sb.append(" ");
                sb.append(ctx.getString(R.string.hours));
                sb.append(" ");
            }
            if (minutes != 0) {
                sb.append(minutes);
                sb.append(" ");
                sb.append(ctx.getString(R.string.minutes));
                sb.append(" ");
            }
            sb.append(ctx.getString(R.string.ago));
        } else if (days > 0 && days < 8) {
            sb.append(days);
            sb.append(" ");
            sb.append(ctx.getString(R.string.days));
            sb.append(" ");
            sb.append(ctx.getString(R.string.ago));
        } else {
            sb.append(ctx.getString(R.string.on));
            sb.append(" ");
            sb.append(getFormattedDateTime(d1));
        }

        return (sb.toString());
    }

    /**
     * Returns Formatted Time using default format "dd-MM-yyyy HH:mm"
     *
     * @param date - Date instance
     * @return date as formatted string
     */
    public static String getFormattedDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(date);
    }


}
