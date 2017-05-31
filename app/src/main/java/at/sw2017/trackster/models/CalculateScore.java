package at.sw2017.trackster.models;

/**
 * Created by Helmut on 31.05.2017.
 */

public class CalculateScore {


    double run_60m;
    double jump;
    double schlagball;
    double kugelstossen;
    String gender;

    double points_60m = 0;
    double points_jump = 0;
    double points_schlagball = 0;
    double points_kugelstossen = 0;


    public CalculateScore(double run_60m, double jump, double schlagball, double kugelstossen, String gender){
        this.run_60m = run_60m;
        this.jump = jump;
        this.schlagball = schlagball;
        this.kugelstossen = kugelstossen;
        this.gender = gender;
    }

    public double calculateOverallScore(){
        if(gender == "m")
        {
            if(run_60m > 0)
            {
                points_60m = round(runs(17.686955, 1397, 2.1, run_60m ),2);
            }
            if(jump > 0)
            {
                points_jump = round(jumps_throws(180.85908, 190, 1, jump ),2);
            }
            if(kugelstossen > 0)
            {
                points_kugelstossen = round(jumps_throws(82.491673, 178, 0.9, kugelstossen ),2);
            }
            if(schlagball > 0)
            {
                schlagball = round(jumps_throws(18, 800, 0.9, schlagball ),2);
            }
        }
        else
        {
            if(run_60m > 0)
            {
                points_60m = round(runs(19.742424, 1417, 2.1, run_60m ),2);
            }
            if(jump > 0)
            {
                points_jump = round(jumps_throws(220.628792, 190, 1, jump ),2);
            }
            if(kugelstossen > 0)
            {
                points_kugelstossen = round(jumps_throws(83.435373, 130, 0.9, kugelstossen ),2);
            }
            if(schlagball > 0)
            {
                schlagball = round(jumps_throws(22, 500, 0.9, schlagball ),2);
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
