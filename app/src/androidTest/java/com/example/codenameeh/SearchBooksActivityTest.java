package com.example.codenameeh;

import com.example.codenameeh.activities.BaseActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Goal of this test is to test the functionality of searching for a book.
 * @author Daniel Shim
 */
@RunWith(AndroidJUnit4.class)
public class SearchBooksActivityTest {
    @Rule public ActivityTestRule<BaseActivity> activityRule =
            new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void test() {
        onView(withId(R.id.nav_view)).perform(click());
        onView(withId(R.id.nav_search_books)).perform(click());

        onView(withId(R.id.search_book_items)).perform(click());
        onView(withId(R.id.search_book_items)).perform(typeText("Harry"));
        onView(withId(R.id.search_books)).perform(click());
        onView(withId(R.id.book_title_view)).check(matches(withText("Harry Potter and the Sorcerer's Stone")));
    }
}
