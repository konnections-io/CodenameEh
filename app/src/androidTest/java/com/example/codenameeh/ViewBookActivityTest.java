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
import com.example.codenameeh.classes.User;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Test Build currently fails, with error "Cause: method ID not in [0, 0xffff]: 65536"
 * I don't see a way to fix this, so I hope that I am using the ID's properly (if not, my intent should
 * at least be clear.
 *
 * This is a class to test various actions about the ViewBookActivity
 * @author Ryan Jensen
 */
@RunWith(AndroidJUnit4.class)
public class ViewBookActivityTest {
    String username = "test";
    String password = "test123";
    User myUser;
    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    /**
     * Do the test
     */
    @Test
    public void Test(){
        FirebaseAuth.getInstance().signOut();
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.sign_in)).perform(click());
        try {
            // Delay to get data from the server.
            sleep(10000);
        } catch(Exception e){
        }
        myUser = CurrentUser.getInstance();
        if(myUser != null) {
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("My Books"))))).perform(click());
            Book testBook = myUser.BooksOwned().get(0);
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
            // Check that this is as expected
            onView(withId(R.id.book_owner_view)).check(matches(not(isDisplayed())));
            onView(withId(R.id.book_title_view)).check(matches(withText("Title: "+testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText("Author: "+testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText("ISBN: "+testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText("Description: "+testBook.getDescription())));
            onView(withId(R.id.editBookButton)).check(matches(isDisplayed()));
            onView(withId(R.id.requestBookButton)).check(matches(not(isDisplayed())));
            onView(withId(R.id.deleteBookButton)).check(matches(isDisplayed()));
            onView(withId(R.id.book_photo)).check(matches(not(isDisplayed())));
            // Maybe an edit, to add a photo. I don't know how to check if the photo is the correct photo though
            // Can check if the photo is visible
            Espresso.pressBack(); // Assuming no crash
            // become not the owner for this purpose
            testBook.setOwner(myUser.getUsername()+"5");
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
            onView(withId(R.id.book_owner_view)).check(matches(isDisplayed()));
            onView(withId(R.id.book_owner_view)).check(matches(withText("Owner: "+myUser.getUsername()+"5")));
            onView(withId(R.id.book_title_view)).check(matches(withText("Title: "+testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText("Author: "+testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText("ISBN: "+testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText("Description: "+testBook.getDescription())));
            onView(withId(R.id.editBookButton)).check(matches(not(isDisplayed())));
            onView(withId(R.id.requestBookButton)).check(matches(isDisplayed()));
            onView(withId(R.id.requestBookButton)).check(matches(withText("Request")));
            onView(withId(R.id.deleteBookButton)).check(matches(not(isDisplayed())));
            onView(withId(R.id.book_photo)).check(matches(not(isDisplayed())));
            // Request Button works
            onView(withId(R.id.requestBookButton)).perform(click());
            onView(withId(R.id.requestBookButton)).check(matches(withText("Cancel Request")));
            onView(withId(R.id.requestBookButton)).perform(click());
            onView(withId(R.id.requestBookButton)).check(matches(withText("Request")));

            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Logout"))))).perform(click());

        }
    }
}
