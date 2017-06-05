package at.sw2017.trackster;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
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
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
            new ActivityTestRule<>(TrackPerformanceActivity.class, true, false);


    @Test
    public void testTracking() throws  Exception{



        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, TrackPerformanceActivity.class);
        intent.putExtra("studentId", "1");

        trackPerformanceActivityTestRule.launchActivity(intent);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.edit_text_username)).perform(typeText("pa"));
        onView(withId(R.id.edit_text_password)).perform(typeText("pat"));
        Espresso.closeSoftKeyboard();
        onView( withId(R.id.login_button)). perform ( click());

        trackPerformanceActivityTestRule.launchActivity(intent);
        onView(withId(R.id.txt_vorname)).perform(replaceText("Vorname"));
        onView(withId(R.id.txt_nachname)).perform(replaceText("Nachname"));
        onView(withId(R.id.txt_klasse)).perform(replaceText("1a"));
        onView(withId(R.id.txt_geschlecht)).perform(replaceText("W"));
        onView(withId(R.id.txt_60m)).perform(replaceText("0:0:10"));
        onView(withId(R.id.txt_1000m)).perform(replaceText("0:15:0"));
        onView(withId(R.id.txt_longjump)).perform(replaceText("5"));
        onView(withId(R.id.txt_kugel)).perform(replaceText("10"));
    }

}

