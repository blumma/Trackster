package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.sw2017.trackster.api.ApiClient;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StudentListActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<StudentListActivity> studentListActivityTestRule =
            new ActivityTestRule<>(StudentListActivity.class, true, true);


    @Test
    public void testStudentList() throws Exception
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            onView(withId(R.id.edit_text_username)).perform(typeText("pa"));
            onView(withId(R.id.edit_text_password)).perform(typeText("pat"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.login_button)).perform(click());
        }
        catch (NoMatchingViewException e){
            //Already logged in
        }

        onView( withId(R.id.button_input)). perform ( click());
        onView( withId(R.id.dpClass)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1a"))).perform(click());
        onView(withId(R.id.dpClass)).check(matches(withSpinnerText(containsString("1a"))));


    }

    @Test
    public void testStudentFilter() throws Exception {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            onView(withId(R.id.edit_text_username)).perform(typeText("pa"));
            onView(withId(R.id.edit_text_password)).perform(typeText("pat"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.login_button)).perform(click());
        } catch (NoMatchingViewException e) {
            //Already logged in
        }

        onView(withId(R.id.button_input)).perform(click());
        onView(withId(R.id.simpleSearchView)).perform(typeText("Myriam"));

        onData(anything()).inAdapterView(withId(R.id.student_list)).atPosition(0).perform(click());
        onView(withId(R.id.txt_vorname)).check(matches(withText("Myriam")));
    }


    public void testStudentListView() throws Exception
    {

        MockWebServer server = new MockWebServer();

        String message = "[{\"id\":1,\"kennzahl\":10047099955719,\"klasse\":\"1e\",\"nachname\":\"Ashborne\",\"vorname\":\"Andra\",\"geschlecht\":\"m\",\"geburtsdatum\":\"2006-04-05\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0},"
            + "{\"id\":2,\"kennzahl\":10067084861159,\"klasse\":\"1e\",\"nachname\":\"Terbrug\",\"vorname\":\"Haven\",\"geschlecht\":\"w\",\"geburtsdatum\":\"2006-04-03\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0},"
            + "{\"id\":3,\"kennzahl\":10263997594987,\"klasse\":\"1e\",\"nachname\":\"McGillacoell\",\"vorname\":\"Gayler\",\"geschlecht\":\"w\",\"geburtsdatum\":\"2006-03-31\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0},"
            + "{\"id\":4,\"kennzahl\":10320700009471,\"klasse\":\"1e\",\"nachname\":\"Alltimes\",\"vorname\":\"Nikos\",\"geschlecht\":\"w\",\"geburtsdatum\":\"2006-03-27\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0},"
            + "{\"id\":5,\"kennzahl\":10451681124298,\"klasse\":\"1e\",\"nachname\":\"Allison\",\"vorname\":\"Gerry\",\"geschlecht\":\"m\",\"geburtsdatum\":\"2006-03-24\",\"performance60mRun\":99,\"performance1000mRun\":3599,\"performanceShotPut\":0,\"performanceLongThrow\":0,\"performanceLongJump\":0,\"sumPoints\":0}]";

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
        assertEquals("/api/students", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals("application/json", request.getHeader("Content-Type"));

        onView(withText("Successfully loaded students!")).
                inRoot(withDecorView(not(is(studentListActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        server.shutdown();

    }

}
