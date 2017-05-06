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
    private Date geburtsdatum;
    private double performance60m;
    private double performance1000m;
    private double performanceShotPut;
    private double performanceLongThrow;
    private double performanceLongJump;
    private double sumPoints;

    public Student() {
    }

    public Student(int id, String kennzahl, String klasse, String nachname, String vorname, String geschlecht, Date geburtsdatum) {
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

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public double getPerformance60m() {
        return performance60m;
    }

    public void setPerformance60m(double performance60m) {
        this.performance60m = performance60m;
    }

    public double getPerformance1000m() {
        return performance1000m;
    }

    public void setPerformance1000m(double performance1000m) {
        this.performance1000m = performance1000m;
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

    public double getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(double sumPoints) {
        this.sumPoints = sumPoints;
    }
}
