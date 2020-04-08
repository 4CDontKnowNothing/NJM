package org.ht.util;  
  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;  
  
/*** 
 * Date tool
 */  
public class DateAndTimeUtil {  
    /*** 
     * Date month minus one month
     *  
     * @param datetime 
     */  
    public static String dateFormat(String datetime) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        Date date = null;  
        try {  
            date = sdf.parse(datetime);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        Calendar cl = Calendar.getInstance();  
        cl.setTime(date);  
        cl.add(Calendar.MONTH, -1);  
        date = cl.getTime();  
        return sdf.format(date);  
    }  
  
    public static String dateFormat(Date date) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        return sdf.format(date);  
    }  
  
    /**** 
     * Pass in a specific date and return a specific date minus one month
     *  
     * @param date 
     * 
     * @throws ParseException 
     */  
    public static String subMonth(String date) throws ParseException {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dt = sdf.parse(date);  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(dt);  
  
        rightNow.add(Calendar.MONTH, -1);  
        Date dt1 = rightNow.getTime();  
        String reStr = sdf.format(dt1);  
  
        return reStr;  
    }  
  
    /**** 
     * Get the last day of the month
     *  
     * @param sDate 
     * 
     * @return 30 
     */  
    private static String getMonthMaxDay(String sDate) {  
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();  
        Date date = null;  
        try {  
            date = sdf_full.parse(sDate + "-01");  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        cal.setTime(date);  
        int last = cal.getActualMaximum(Calendar.DATE);  
        return String.valueOf(last);  
    }  
  
    // Determine if it is the end of the month  
    public static boolean isMonthEnd(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        if (cal.get(Calendar.DATE) == cal  
                .getActualMaximum(Calendar.DAY_OF_MONTH))  
            return true;  
        else  
            return false;  
    }  
  
    /*** 
     * Date minus one day, plus one day 
     *  
     * @param option 
     *            Incoming type pro: date minus one day, next: date plus one day
     * @param _date 
     *           e.g. 2020-2-14 
     * @return minus one day：2020-2-14 or (plus one day：2020-2-14) 
     */  
    public static String checkOption(String option, String _date) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cl = Calendar.getInstance();  
        Date date = null;  
  
        try {  
            date = (Date) sdf.parse(_date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        cl.setTime(date);  
        if ("pre".equals(option)) {  
            // minus one day 
            cl.add(Calendar.DAY_OF_MONTH, -1);  
  
        } else if ("next".equals(option)) {  
            // plus one day  
            cl.add(Calendar.DAY_OF_YEAR, 1);  
        } else {  
            // do nothing  
        }  
        date = cl.getTime();  
        return sdf.format(date);  
    }  
  
    /*** 
     * Determine whether the date is the current month, 
     * is the current month returns the minimum date of the current month 
     * 		and the current maximum date of the current month, 
     * and the maximum day and minimum day of the month on the incoming date
     *  
     * Not the current month returns the maximum and minimum days 
     * 		of the incoming month and the maximum and minimum days of the previous month 
     *  
     * @param date 
     *            date e.g. 2020-2-14  
     * @return String[] :Start date, end date, last month start date, last month end date 
     * @throws ParseException 
     */  
    public static String[] getNow_Pre_Date(String date) throws ParseException {  
  
        String[] str_date = new String[4];  
        Date now = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");  
        String stMonth = sdf.format(now);  
        String stdate = "";// start date  
        String endate = "";// end date  
        String preDate_start = "";// Last month start date 
        String preDate_end = "";// Last month end date
  
        // current month 
        if (date.equals(stMonth)) {  
            stdate = stMonth + "-01"; // 2020-2-13 
            endate = sdf_full.format(now);// 2020-2-14   
            preDate_start = subMonth(stdate);// 2020-1-01  
            preDate_end = subMonth(endate);// 2020-1-14  
        } else {  
            // not current month 
            String monthMaxDay = getMonthMaxDay(date);  
            stdate = date + "-01";// 2020-2-13 
            endate = date + "-" + monthMaxDay;// 2020-2-28  
            preDate_start = subMonth(stdate);// 2020-1-01  
            preDate_end = subMonth(endate);// 2020-1-31  
        }  
        str_date[0] = stdate;  
        str_date[1] = endate;  
        str_date[2] = preDate_start;  
        str_date[3] = preDate_end;  
  
        return str_date;  
    }  
  
    public static void main(String[] args) throws ParseException {  
        /* 
         * String a =DateAndTimeUtil.dateFormat(new Date()); 
         * System.out.println(a); String b = 
         * DateAndTimeUtil.subMonth("2014-03-31"); System.out.println(b); 
         * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); Date 
         * dt=sdf.parse("2014-03-31"); 
         * System.out.println(DateAndTimeUtil.isMonthEnd(dt)); 
         */  
        String str = null;  
        // str = DateAndTimeUtil.checkOption("next", "2014-11-30");  
        // str = getMonthMaxDay("2014-11-24");  
        // str = dateFormat("2014-11");  
        str = getNow_Pre_Date("2014-10")[0];  
        System.out.println(str);  
    }  
}  