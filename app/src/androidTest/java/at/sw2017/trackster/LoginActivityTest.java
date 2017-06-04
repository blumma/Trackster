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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.trackster", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<LoginActivity> menuActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Test
    public void testLoginSuccess() throws Exception {

        MockWebServer server = new MockWebServer();

        String message = "{\"id\":1,\"firstName\":\"Test\",\"lastName\":\"Test\",\"email\":\"test@test.com\",\"createdAt\":\"2017-05-01\"}";

        // Schedule some responses.
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(message));

        // Start the server.
        server.start();

        HttpUrl baseUrl = server.url("/tracksterMockServer/");
        ApiClient.BASE_URL = baseUrl.toString();

        String first_name = "test@test.com";
        String password = "test123";

        onView(withId(R.id.edit_text_username)).perform(typeText(first_name), closeSoftKeyboard());

        onView(withId(R.id.edit_text_password)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());

        RecordedRequest request = server.takeRequest();
        assertEquals("/tracksterMockServer/api/login", request.getPath());

        onView(withText("Successful login!")).
                inRoot(withDecorView(not(is(menuActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        server.shutdown();
    }

    @Test
    public void testLoginFail() throws Exception {

        MockWebServer server = new MockWebServer();

        String message = "{\"message\":\"Login Failed.\"}";

        // Schedule some responses.
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(message));

        // Start the server.
        server.start();

        HttpUrl baseUrl = server.url("/tracksterMockServer/");
        ApiClient.BASE_URL = baseUrl.toString();

        String first_name = "test@test.com";
        String password = "test123";

        onView(withId(R.id.edit_text_username)).perform(typeText(first_name), closeSoftKeyboard());

        onView(withId(R.id.edit_text_password)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());

        RecordedRequest request = server.takeRequest();
        assertEquals("/tracksterMockServer/api/login", request.getPath());

        onView(withText("Login failed!")).
                inRoot(withDecorView(not(is(menuActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        server.shutdown();
    }

}
