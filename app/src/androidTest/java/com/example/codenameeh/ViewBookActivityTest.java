package com.example.codenameeh;

import android.support.test.runner.AndroidJUnit4;

import com.example.codenameeh.activities.ViewBookActivity;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;

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
    public ActivityTestRule<ViewBookActivity> activityRule =
            new ActivityTestRule<ViewBookActivity>(ViewBookActivity.class);

    /**
     * Do the test
     */
    @Test
    public void Test(){

        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.sign_in)).perform(click());
        myUser = CurrentUser.getInstance();
        if(myUser != null) {
            onView(withId(R.id.drawer_layout)).perform(click());
            Book testBook = new Book("TestTitle", "George Washington", "0284e98", "An excellent read.", myUser.getUsername());
            // Manually add to owning, since this is easier and this is not to test adding a book to this, but to View a book
            myUser.getOwning().add(testBook);
            onView(withId(R.id.nav_my_books)).perform(click());
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
            // Check that this is as expected
            onView(withId(R.id.book_owner_view)).check(matches(not(isDisplayed())));
            onView(withId(R.id.book_title_view)).check(matches(withText(testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText(testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText(testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText(testBook.getDescription())));
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
            onView(withId(R.id.book_owner_view)).check(matches(withText(myUser.getUsername()+"5")));
            onView(withId(R.id.book_title_view)).check(matches(withText(testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText(testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText(testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText(testBook.getDescription())));
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
        }
    }
}
