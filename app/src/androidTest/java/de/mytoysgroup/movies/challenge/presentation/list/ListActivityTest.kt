package de.mytoysgroup.movies.challenge.presentation.list

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AutoCompleteTextView
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.atPosition
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
@LargeTest
class ListActivityTest {

    @get:Rule
    val mActivityRule = ActivityTestRule<ListActivity>(ListActivity::class.java)

    @Test
    fun listGoesOverTheFold() {
        onView(withId(R.id.action_search))
                .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText("Avengers"),
                        pressImeActionButton())

        sleep(1500)

        onView(withId(R.id.searchResultLayout))
                .check(matches(atPosition(0, hasDescendant(withText("The Avengers")))))
    }
}