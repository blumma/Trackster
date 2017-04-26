package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SportsTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<SportActivity> menuActivityTestRule =
            new ActivityTestRule<>(SportActivity.class, true, true);

    @Test
    public void testSportsButtons()
    {
        onView( withId(R.id.button_throw)). perform ( click());
        onView( withId(R.id.button_weitsprung)). perform ( click());
        onView( withId(R.id.button_1000m)). perform ( click());
        onView( withId(R.id.button_60m)). perform ( click());
    }

}
