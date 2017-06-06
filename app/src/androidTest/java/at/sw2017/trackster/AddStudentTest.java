package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddStudentTest {

    @Rule
    public ActivityTestRule<StudentListActivity> studentListActivityTestRule =
            new ActivityTestRule<>(StudentListActivity.class, true, true);


    @Test
    public void testAddStudent() throws Exception
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView( withId(R.id.button_add_student)). perform ( click());
        onView( withId(R.id.add_student)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.editForename)).perform(replaceText("Martin"));
        onView(withId(R.id.editSurename)).perform(replaceText("Sonneborn"));
       /* onView(withId(R.id.spinnerClass)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onView(withId(R.id.spinnerGrade)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());*/
        onView(withId(R.id.checkBox)).perform(click());
        onView(withId(R.id.gebDateText)).perform(replaceText("01-02-2003"));

        onView(withId(R.id.button_ok)).perform(click());

        Thread.sleep(5000);
    }
}
