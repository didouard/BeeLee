package com.fernandocejas.android10.sample.test.mockito.espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.MainActivity;
import com.parse.ParseUser;

import org.hamcrest.Matcher;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {
    public static String uuid = null;
    public static String username;
    public static String password;
    public static String pseudo;

    public MainActivityTests() {
        super(MainActivity.class);
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) user.logOut();

        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString().substring(0, 8);
            this.username = this.uuid + "@meuuh.com";
            this.password = this.uuid;
            this.pseudo = "test_" + this.uuid;
        }
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testCreateLogin() throws InterruptedException {

    }

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                Matcher<View> viewMatcher;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        viewMatcher = ViewMatchers.isRoot();
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(100);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
}
