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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RankingActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<ViewPagerActivity> menuActivityTestRule =
            new ActivityTestRule<>(ViewPagerActivity.class, true, true);

    @Test
    public void testMenuButton()
    {
        //bugged
        onView(allOf(withId(R.id.button), isDisplayed())).perform(click());



        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.viewpager)).perform(swipeLeft());

    }

    public void testSwipe()
    {
        onView(allOf(withId(R.id.tv), withText("60Meter"))).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager)).perform(swipeRight());
        onView(withText("1000 Meter")).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager)).perform(swipeRight());
        onView(withText("Weitsprung")).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager)).perform(swipeRight());
        onView(withText("Schlagball")).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withText("Weitsprung")).check(matches(isDisplayed()));

    }

}
