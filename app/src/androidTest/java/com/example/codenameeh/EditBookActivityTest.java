package com.example.codenameeh;

import android.support.design.internal.NavigationMenuItemView;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import com.example.codenameeh.activities.LoginActivity;
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
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
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
 * A test class to test aspects about editing a book
 *
 * This is a class to test various actions about the EditBookActivity
 * @author Ryan Jensen
 */
@RunWith(AndroidJUnit4.class)
public class EditBookActivityTest {
    String username = "Diodone123";
    String password = "Diodone123";
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
            // Delay to get data from the server. Should be a better option.
            sleep(10000);
        } catch(Exception e){
        }
        myUser = CurrentUser.getInstance();
        if(myUser != null) {
            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("My Books"))))).perform(click());
            Book testBook;
            try {
                testBook = myUser.BooksOwned().get(0);
            } catch(Exception e){
                testBook = null;
            }
            // Empty books
            while(testBook!=null) {
                onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
                onView(withId(R.id.deleteBookButton)).perform(click());
                try {
                    testBook = myUser.BooksOwned().get(0);
                } catch (Exception e) {
                    testBook = null;
                }
            }
            onView(withId(R.id.button)).perform(click());

            onView(withId(R.id.editText)).perform(typeText("Title"));
            onView(withId(R.id.editText2)).perform(typeText("Author"));
            onView(withId(R.id.editText3)).perform(typeText("ISBN"));
            onView(withId(R.id.editText4)).perform(typeText("Desc"));
            // Close the typing animation
            Espresso.pressBack();
            onView(withId(R.id.button2)).perform(click());
            try {
                sleep(1000);
            } catch(Exception e){

            }
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
            onView(withId(R.id.editBookButton)).perform(click());
            onView(withId(R.id.book_title_edit)).check(matches(withText("Title")));
            onView(withId(R.id.book_desc_edit)).check(matches(withText("Desc")));
            onView(withId(R.id.book_author_edit)).check(matches(withText("Author")));
            onView(withId(R.id.book_ISBN_edit)).check(matches(withText("ISBN")));
            onView(withId(R.id.book_title_edit)).perform(typeText("Hi"));
            onView(withId(R.id.book_desc_edit)).perform(typeText("Hi"));
            onView(withId(R.id.book_author_edit)).perform(typeText("Hi"));
            onView(withId(R.id.book_ISBN_edit)).perform(typeText("Hi"));
            Espresso.pressBack();
            onView(withId(R.id.save_book_edits_button)).perform(click());
            testBook = new Book("TitleHi", "AuthorHi", "ISBNHi", "DescHi", username,null);
            onView(withId(R.id.book_title_view)).check(matches(withText("Title: "+testBook.getTitle())));
            onView(withId(R.id.book_author_view)).check(matches(withText("Author: "+testBook.getAuthor())));
            onView(withId(R.id.book_ISBN_view)).check(matches(withText("ISBN: "+testBook.getISBN())));
            onView(withId(R.id.book_description)).check(matches(withText("Description: "+testBook.getDescription())));
            Espresso.pressBack();
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).check(matches(withText(testBook.toString())));
            // Remove the book after the test
            onData(anything()).inAdapterView(withId(R.id.BooksOwnedView)).atPosition(0).perform(click());
            onView(withId(R.id.deleteBookButton)).perform(click());



            onView(allOf(instanceOf(AppCompatImageButton.class),
                    withParent(withResourceName("toolbar")))).perform(click());
            onView(allOf(Matchers.<View>instanceOf(NavigationMenuItemView.class),withChild
                    (allOf(Matchers.<View>instanceOf(AppCompatCheckedTextView.class), withText("Logout"))))).perform(click());

        }
    }
}
