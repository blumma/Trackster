package at.sw2017.trackster;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
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
public class MenuActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = getTargetContext();
        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MenuActivity> menuActivityTestRule =
            new ActivityTestRule<>(MenuActivity.class, true, true);

    @Test
    public void testMenuButton()
    {

        onView( withId(R.id.button_1000m)). perform ( click());

        //Check if not logged in:
        onView(withText(R.string.not_logged_in)).inRoot(withDecorView(not(is(menuActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        //login:
        onView(withId(R.id.edit_text_username)).perform(typeText("pa"));
        onView(withId(R.id.edit_text_password)).perform(typeText("pat"));
        Espresso.closeSoftKeyboard();
        onView( withId(R.id.login_button)). perform ( click());
        onView( withId(R.id.button_1000m)). perform ( click());

        //TODO check if ViewPager visible

        Espresso.pressBack();
        onView( withId(R.id.button_input)). perform ( click());
        onView(withText("Wähle Schüler")).check(matches(isDisplayed()));

    }

}
