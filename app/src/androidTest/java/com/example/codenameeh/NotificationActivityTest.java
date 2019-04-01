package com.example.codenameeh;

import android.support.design.internal.NavigationMenuItemView;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import androidx.core.widget.ImageViewCompat;
import androidx.test.runner.AndroidJUnit4;

import com.example.codenameeh.activities.LoginActivity;
import com.example.codenameeh.activities.ViewBookActivity;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnitRunner;

import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * @author bqi1
 *
 */
@RunWith(AndroidJUnit4.class)
public class NotificationActivityTest{
    String username = "testbqi3";
    String password = "password";
    User myUser;
    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    /**
     *
     */
    @Test
    public void testNotificationVisibility(){
        FirebaseAuth.getInstance().signOut();
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.sign_in)).perform(click());
        try {
            // Delay to get data from the server.
            sleep(7000);
        } catch(Exception e){
        }
        myUser = CurrentUser.getInstance();
        if(myUser != null) {
            //Go to notifications activity
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Notifications"))))).perform(click());
            //Test if dummy notifications can be displayed when added to Firestore.
            ArrayList<String> testKeywords = new ArrayList<String>();
            testKeywords.add("Dummy keyword");
            Book book = new Book("DummyBook","DummyAuthor","123123","DummyDescription","DummyOwner",testKeywords);
            Notification borrowRequest = new Notification("DummyUser",book);
            Notification acceptedRequest = new Notification("DummyUser","DummyLocation",book,40.0,-70.0);
            //Test borrow requests if they appear when added to Firestore
            FirebaseFirestore.getInstance().collection("users").document(username).update("notifications",FieldValue.arrayUnion(borrowRequest));
            try {
                sleep(3000);
            } catch(Exception e){
            }
            onView(withId(R.id.requestBookList))
                    .check(matches((hasDescendant(withText(containsString(borrowRequest.toString()))))));
            //Test accepted requests if they appear when added to Firestore
            FirebaseFirestore.getInstance().collection("users").document(username).update("notifications",FieldValue.arrayUnion(acceptedRequest));
            try {
                sleep(3000);
            } catch(Exception e){
            }
            onView(withId(R.id.bookAcceptedList))
                    .check(matches((hasDescendant(withText(containsString(acceptedRequest.toString()))))));
            //Test clear button
            onView(withId(R.id.ClearAcceptedNotf)).perform(click());
            onView(withId(R.id.bookAcceptedList))
                    .check(matches(not(hasDescendant(withText(containsString(acceptedRequest.toString()))))));

            //Remove notification for potential repeat tests
            FirebaseFirestore.getInstance().collection("users").document(username).update("notifications",FieldValue.arrayRemove(borrowRequest));
            FirebaseFirestore.getInstance().collection("users").document(username).update("notifications",FieldValue.arrayRemove(acceptedRequest));
            //Logout for further tests
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Logout"))))).perform(click());

        }
    }
}
