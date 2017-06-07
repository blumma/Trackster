package at.sw2017.trackster.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Helmut on 31.05.2017.
 */

public class CalculateScore {


    double run_60m;
    double jump;
    double schlagball;
    double kugelstossen;
    String gender;
    String geburtsdatum;

    double points_60m = 0;
    double points_jump = 0;
    double points_schlagball = 0;
    double points_kugelstossen = 0;


    public CalculateScore(double run_60m, double jump, double schlagball, double kugelstossen, String gender, String geburtsdatum){
        this.run_60m = run_60m;
        this.jump = jump;
        this.schlagball = schlagball;
        this.kugelstossen = kugelstossen;
        this.gender = gender;
        this.geburtsdatum = geburtsdatum;
    }

    public double calculateOverallScore(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String datum = dateFormat.format(date);
        String[] split_date = datum.split("/");
        String year = split_date[0];
        int actual_year = Integer.parseInt(year);
        String[] date_split_student = geburtsdatum.split("-");
        int student_year = Integer.parseInt(date_split_student[0]);

        //System.out.println(split_date[0]);
        //System.out.println(date_split_student[0]);

        boolean young = false;
        if(actual_year - student_year < 15)
        {
            young = true;
            //System.out.println("is young");
        }

        if(gender.equals("m"))
        {
            if(run_60m > 0)
            {
                points_60m = round(runs(17.686955, 1397, 2.1, run_60m ),2);
            }
            if(jump > 0)
            {
                points_jump = round(jumps_throws(180.85908, 190, 1, jump ),2);
            }


            if(kugelstossen > 0 && young == false)
            {
                points_kugelstossen = round(jumps_throws(82.491673, 178, 0.9, kugelstossen ),2);
                //System.out.println("kugel");

            }
            if(schlagball > 0 && young == true)
            {
                points_schlagball = round(jumps_throws(18, 800, 0.9, schlagball ),2);
                //System.out.println("schlag");
            }
        }
        else
        {
            //System.out.println("w");
            if(run_60m > 0)
            {
                points_60m = round(runs(19.742424, 1417, 2.1, run_60m ),2);
            }
            if(jump > 0)
            {
                points_jump = round(jumps_throws(220.628792, 190, 1, jump ),2);
            }
            if(kugelstossen > 0 && young == false)
            {
                points_kugelstossen = round(jumps_throws(83.435373, 130, 0.9, kugelstossen ),2);
                //System.out.println("Kugel");

            }
            if(schlagball > 0 && young == true)
            {
                points_schlagball = round(jumps_throws(22, 500, 0.9, schlagball ),2);
                //System.out.println("Schlag");
            }
        }

        return points_60m + points_kugelstossen + points_jump + points_schlagball;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    public double runs(double A, double B, double e, double Leistung)
    {
        return A* Math.pow(((B - Leistung * 100)/100), e);
    }

    public double jumps_throws(double A, double B, double e, double Leistung)
    {
        return A* Math.pow(((Leistung * 100 - B)/100), e);
    }


}
