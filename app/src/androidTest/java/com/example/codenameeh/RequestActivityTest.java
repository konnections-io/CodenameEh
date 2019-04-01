package com.example.codenameeh;

import android.content.Intent;

import com.example.codenameeh.activities.RequestActivity;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Dan Sune
 *
 * Test currently fails with message:
 *
 * 'java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String
 * com.example.codenameeh.classes.User.getUsername()' on a null object reference'
 *
 * I believe this is an issue with initiating a User for the intent to be passed.
 * Goal of this test is to verify that all text and buttons on screen are as they should be.
 *
 */
@RunWith(AndroidJUnit4.class)
public class RequestActivityTest {
    ArrayList<String> testWords = new ArrayList<String>();
    User user = CurrentUser.getInstance();//new User("Name", "1234567890", "email@email.com", "username");
    Book book = new Book("Title", "Author", "ISBN", "Description", "Owner", testWords);

    @Rule
    public ActivityTestRule<RequestActivity> activityRule =
            new ActivityTestRule<RequestActivity>(RequestActivity.class, true, false);

    @Test
    public void Test(){
        Intent intent = new Intent();
        intent.putExtra("Book", book);
        intent.putExtra("UUID", book.getUuid());
        intent.putExtra("Other Username", user.getUsername());

        activityRule.launchActivity(intent);

        //check text on screen
        onView(withId(R.id.textView2)).check(matches(withText(user.getUsername())));
        onView(withId(R.id.textView)).check(matches(withText("This user wants to borrow your book!")));

        //check buttons
        onView(withId(R.id.accept)).check(matches(withText("ACCEPT")));
        onView(withId(R.id.decline)).check(matches(withText("DECLINE")));

        //unsure of how to test what happens when the buttons are clicked, the activity closes and-
        //-passes information back to the NotificationActivity
    }
}