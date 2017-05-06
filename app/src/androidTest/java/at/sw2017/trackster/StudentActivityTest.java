package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StudentActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<StudentListActivity> menuActivityTestRule =
            new ActivityTestRule<>(StudentListActivity.class, true, true);

    @Test
    public void testListView()
    {
        onView(withId(R.id.simpleSearchView)).perform(typeText("Jan"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.mobile_list)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.mobile_list)).atPosition(1).perform(click());
        onView( withId(R.id.start)). perform ( click());

    }

}
