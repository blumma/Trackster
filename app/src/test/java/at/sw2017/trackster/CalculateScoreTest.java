package at.sw2017.trackster;

import org.junit.Test;

import at.sw2017.trackster.models.CalculateScore;
import at.sw2017.trackster.models.Student;

import static org.junit.Assert.assertEquals;

/**
 * Created by heinz on 05.06.17.
 */

public class CalculateScoreTest {

    @Test
    public void calc_isCorrect() throws Exception {
        Student s = new Student();
        s.setPerformance60mRun(10.1);
        s.setPerformance1000mRun(60.1);
        s.setPerformanceLongJump(10);
        s.setPerformanceShotPut(2);
        s.setPerformanceLongThrow(5);
        s.setGeschlecht("m");
        s.setGeburtsdatum("2002-04-05");
        CalculateScore score = new CalculateScore(s.getPerformance60mRun(), s.getPerformanceLongJump(), s.getPerformanceLongThrow(),
                s.getPerformanceShotPut(), s.getGeschlecht(), s.getGeburtsdatum());
        System.out.println(score.calculateOverallScore());
        assertEquals(1789.35, score.calculateOverallScore(), 0.001);

        s.setPerformance60mRun(10.1);
        s.setPerformance1000mRun(60.1);
        s.setPerformanceLongJump(10);
        s.setPerformanceShotPut(2);
        s.setPerformanceLongThrow(5);
        s.setGeschlecht("w");
        s.setGeburtsdatum("20008-04-05");
        score = new CalculateScore(s.getPerformance60mRun(), s.getPerformanceLongJump(), s.getPerformanceLongThrow(),
                s.getPerformanceShotPut(), s.getGeschlecht(), s.getGeburtsdatum());
        System.out.println(score.calculateOverallScore());
        assertEquals(2163.4, score.calculateOverallScore(), 0.001);

        s.setPerformance60mRun(10.1);
        s.setPerformance1000mRun(60.1);
        s.setPerformanceLongJump(10);
        s.setPerformanceShotPut(2);
        s.setPerformanceLongThrow(5);
        s.setGeschlecht("w");
        s.setGeburtsdatum("2002-04-05");

        score = new CalculateScore(s.getPerformance60mRun(), s.getPerformanceLongJump(), s.getPerformanceLongThrow(),
                s.getPerformanceShotPut(), s.getGeschlecht(), s.getGeburtsdatum());
        System.out.println(score.calculateOverallScore());
        assertEquals(2223.93, score.calculateOverallScore(), 0.001);
    }

}
