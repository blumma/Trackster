package at.sw2017.trackster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.edit_text_username)).perform(typeText("pa"));
        onView(withId(R.id.edit_text_password)).perform(typeText("pat"));
        Espresso.closeSoftKeyboard();
        onView( withId(R.id.login_button)). perform ( click());
        onView( withId(R.id.button_1000m)). perform ( click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(isCompletelyDisplayed()));

    }

    @Test
    public void testSwipe()
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
            onView(withId(R.id.button_1000m)).perform(click());
        }
        catch (NoMatchingViewException e){
            //Already logged in
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(hasDescendant(withText(R.string.ranking_title))));

        onView(withId(R.id.viewpager))
                .perform(swipeLeft());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(hasDescendant(withText(R.string.sport_60m))));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(hasDescendant(withText(R.string.sport_1000m))));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(hasDescendant(withText(R.string.sport_jump))));
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.viewpager)).check(matches(hasDescendant(withText(R.string.sport_rounders))));

    }

}
