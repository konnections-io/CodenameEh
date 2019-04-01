package com.example.codenameeh;

import android.content.Intent;
import android.support.design.internal.NavigationMenuItemView;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import com.example.codenameeh.activities.LoginActivity;
import com.example.codenameeh.activities.RequestActivity;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author Dan Sune, Brian Qi
 *
 * Testing on the RequestActivity. Currently does not test what happens when the buttons are clicked.
 * The activity closes and passes information back to NotificationActivity.
 */
@RunWith(AndroidJUnit4.class)
public class RequestActivityTest {
    String username = "testbqi3";
    String password = "password";
    User myUser;
    ArrayList<String> testKeywords = new ArrayList<String>();
    //User user = CurrentUser.getInstance();//new User("Name", "1234567890", "email@email.com", "username");

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void Test() {
        /**
         * taken from GeolocationActivity written by Brian Qi
         *
         * starts from login and goes to RequestActivity for testing
         */
        FirebaseAuth.getInstance().signOut();
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.sign_in)).perform(click());
        try {
            // Delay to get data from the server.
            sleep(7000);
        } catch (Exception e) {
        }
        myUser = CurrentUser.getInstance();
        if (myUser != null) {
            //Go to notifications activity
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class), withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Notifications"))))).perform(click());
        }
        testKeywords.add("keyword");
        Book book = new Book("DummyBook","DummyAuthor","123123","DummyDescription","DummyOwner",testKeywords);
        book.setUuid("e80788db-4f1d-4665-9cfe-6911910d44f4");
        Notification borrowRequest = new Notification("DummyUser",book);
        FirebaseFirestore.getInstance().collection("users").document(username).update("notifications",FieldValue.arrayUnion(borrowRequest));
        try {
            sleep(3000);
        } catch(Exception e){
        }
        onData(anything()).inAdapterView(withId(R.id.requestBookList)).atPosition(0).perform(click());

        String expected = "DummyUser would like to borrow\nTextbook";

        //check text on screen
        onView(withId(R.id.textView2)).check(matches(withText(expected)));
        onView(withId(R.id.textView)).check(matches(withText("This user wants to borrow your book!")));

        //check buttons
        onView(withId(R.id.accept)).check(matches(withText("ACCEPT")));
        onView(withId(R.id.decline)).check(matches(withText("DECLINE")));

    }
}