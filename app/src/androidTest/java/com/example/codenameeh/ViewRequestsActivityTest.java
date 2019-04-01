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
import org.junit.Assert;
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
 * Tests requesting and denying books, signing in with a user already created.
 */
@RunWith(AndroidJUnit4.class)
public class ViewRequestsActivityTest{
    String username = "testbqi1";
    String password = "password";
    User myUser;
    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    /**
     * Test requesting a book in SearchBooks, then checking if it can be found in ViewRequestsActivity
     */
    @Test
    public void testRequesting(){
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
            //Search a book to request
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Search Books"))))).perform(click());
            onData(anything()).inAdapterView(withId(R.id.search_books)).atPosition(0).perform(click());
            onView(withId(R.id.requestBookButton)).perform(click());

            //Go to view requests activity
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Requests"))))).perform(click());
            onData(anything()).inAdapterView(withId(R.id.requestedPendingListView)).atPosition(0).perform(click());

            //See if book requested matches with what we requested
            Book testBook = myUser.RequestedBooks().get(0);
            onView(withId(R.id.book_owner_view)).check(matches(withText("Owner: "+testBook.getOwner())));
            onView(withId(R.id.book_title_view)).check(matches(withText("Title: "+testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText("Author: "+testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText("ISBN: "+testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText("Description: "+testBook.getDescription())));
            onView(withId(R.id.requestBookButton)).check(matches(withText("Cancel Request")));
            onView(withId(R.id.requestBookButton)).check(matches(isDisplayed()));
            onView(withId(R.id.deleteBookButton)).check(matches(not(isDisplayed())));
            onView(withId(R.id.editBookButton)).check(matches(not(isDisplayed())));
            // Unrequest the book, book should no longer be displayed
            onView(withId(R.id.requestBookButton)).perform(click());
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Requests"))))).perform(click());


            //Check if the book we cancelled is not displayed at all
            onView(withId(R.id.requestedPendingListView))
                    .check(matches(not(hasDescendant(withText(containsString(testBook.getTitle()))))));

            //Logout for further tests
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Logout"))))).perform(click());

        }
    }
}
