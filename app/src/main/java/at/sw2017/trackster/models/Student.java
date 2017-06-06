package at.sw2017.trackster.models;

import java.util.Date;

/**
 * Created by mblum on 06.05.2017.
 */

public class Student {

    private int id;
    private String kennzahl;
    private String klasse;
    private String nachname;
    private String vorname;
    private String geschlecht;
    private String geburtsdatum;
    private double performance60mRun;
    private double performance1000mRun;
    private double performanceShotPut;
    private double performanceLongThrow;
    private double performanceLongJump;
    private double sumPoints;

    public Student() {
    }

    public Student(int id) {
        this.id = id;
    }

    public Student(int id, String kennzahl, String klasse, String nachname, String vorname, String geschlecht, String geburtsdatum) {
        this.id = id;
        this.kennzahl = kennzahl;
        this.klasse = klasse;
        this.nachname = nachname;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.geburtsdatum = geburtsdatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKennzahl() {
        return kennzahl;
    }

    public void setKennzahl(String kennzahl) {
        this.kennzahl = kennzahl;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public double getPerformanceShotPut() {
        return performanceShotPut;
    }

    public void setPerformanceShotPut(double performanceShotPut) {
        this.performanceShotPut = performanceShotPut;
    }

    public double getPerformanceLongThrow() {
        return performanceLongThrow;
    }

    public void setPerformanceLongThrow(double performanceLongThrow) {
        this.performanceLongThrow = performanceLongThrow;
    }

    public double getPerformanceLongJump() {
        return performanceLongJump;
    }

    public void setPerformanceLongJump(double performanceLongJump) {
        this.performanceLongJump = performanceLongJump;
    }

    public double getPerformance60mRun() {
        return performance60mRun;
    }

    public void setPerformance60mRun(double performance60mRun) {
        this.performance60mRun = performance60mRun;
    }

    public double getPerformance1000mRun() {
        return performance1000mRun;
    }

    public void setPerformance1000mRun(double performance1000mRun) {
        this.performance1000mRun = performance1000mRun;
    }

    public double getPerformanceSumPoints() {return sumPoints;}

    public void setPerformanceSumPoints(double performanceSumPoints) {
        this.sumPoints = performanceSumPoints;
    }

    public double getOverallScore() {
        CalculateScore score = new CalculateScore(this.getPerformance60mRun(), this.getPerformanceLongJump(), this.getPerformanceLongThrow(),
                this.getPerformanceShotPut(), this.getGeschlecht(), this.getGeburtsdatum());

        return score.calculateOverallScore();
    }


}
