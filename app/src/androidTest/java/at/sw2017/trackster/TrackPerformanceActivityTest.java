package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.sw2017.trackster.api.ApiClient;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by mblum on 07.05.2017.
 */

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TrackPerformanceActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<TrackPerformanceActivity> trackPerformanceActivityTestRule =
            new ActivityTestRule<>(TrackPerformanceActivity.class, true, true);

    @Test
    public void testGetStudentById() throws Exception
    {
        MockWebServer server = new MockWebServer();

        String message = "{\"id\":1,\"kennzahl\":10047099955719,\"klasse\":\"1e\",\"nachname\":\"Ashborne\",\"vorname\":\"Andra\",\"geschlecht\":\"m\",\"geburtsdatum\":\"2006-04-05\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0}";

        // Schedule some responses.
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(message));

        // Start the server.
        server.start();

        HttpUrl baseUrl = server.url("/tracksterMockServer/");
        ApiClient.BASE_URL = baseUrl.toString();

        // @mblum TODO: how to check if the request was made during activity initialisation?

        RecordedRequest request = server.takeRequest();
        assertEquals("/api/student", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals("application/json", request.getHeader("Content-Type"));

        onView(withText("Successfully loaded student data!")).
                inRoot(withDecorView(not(is(trackPerformanceActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        server.shutdown();

    }

}

