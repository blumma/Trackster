package at.sw2017.calculator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.intent.IntentCallback;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import at.sw2017.calculator.Calculator;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.calculator", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<Calculator> mActivityRule = new
            ActivityTestRule<>(Calculator.class);

    @Test
    public void testButtons() throws Exception {


        for (int i = 1; i <= 9; i++) {
            onView(withText(Integer.toString(i))).perform(click());
        }
        onView(withText("+")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("*")).perform(click());
        onView(withText("/")).perform(click());
        onView(withText("=")).perform(click());
        onView(withText("C")).perform(click());
    }

    @Test
    public void testInputField(){
        for(int i = 9 ; i >= 0; i--){
            onView(withText(Integer.toString(i))).perform(click());
        }
        onView(withText("9876543210")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearButton(){
        onView(withText("3")).perform(click());
        onView(withText("C")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("0")));
    }

    @Test
    public void testResultMult(){
        onView(withText("3")).perform(click());
        onView(withText("*")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("=")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("6")));
    }

    @Test
    public void testResultSub(){
        onView(withText("3")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("=")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("5")));
    }

    @Test
    public void testResultAdd(){
        onView(withText("3")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("2")).perform(click());
        onView(withText("=")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("1")));
    }

    @Test
    public void testResultDiv(){
        onView(withText("6")).perform(click());
        onView(withText("/")).perform(click());
        onView(withText("3")).perform(click());
        onView(withText("=")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("2")));
    }

    @Test
    public void testResultDefault(){
        onView(withText("6")).perform(click());
        onView(withText("=")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("6")));
    }

    @Test
    public void privateConstructorTest() throws Exception {
        Constructor<Calculations> c = Calculations.class.getDeclaredConstructor();
        c.setAccessible(true);
        Calculations u = c.newInstance();
    }

    @Test
    public void enumTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {


        Calculator.State.valueOf(Calculator.State.ADD.toString());
        Calculator.State.valueOf(Calculator.State.SUB.toString());
        Calculator.State.valueOf(Calculator.State.MUL.toString());
        Calculator.State.valueOf(Calculator.State.DIV.toString());
        Calculator.State.valueOf(Calculator.State.INIT.toString());
        Calculator.State.valueOf(Calculator.State.NUM.toString());

       /* Class<?> c = Class.forName("Calculator");
        Object t = c.newInstance();
        Method m = Calculator.class.getMethod("calculateResult", null);
        m.setAccessible(true);
        Object o = m.invoke(t ,null);*/
    }

}
