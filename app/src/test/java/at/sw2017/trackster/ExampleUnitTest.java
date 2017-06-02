package at.sw2017.trackster;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import at.sw2017.trackster.models.TimeConvert;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void timeConvert() throws Exception{
        String time = "1:23:456";
        double millisec = TimeConvert.timeToMillisec(time);
        assertEquals(456+23*1000+1*60*1000, millisec, 10);
    }

    @Test
    public void timeConvert2() throws Exception{
        String time = "1:23:456";
        String millisec = TimeConvert.millisecToTime(456+23*1000+1*60*1000);
        assertEquals(time, millisec);
    }

}