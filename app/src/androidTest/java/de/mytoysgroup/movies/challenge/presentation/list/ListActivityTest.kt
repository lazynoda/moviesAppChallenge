package de.mytoysgroup.movies.challenge.presentation.list

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.matcher.UriMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.AutoCompleteTextView
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.atPosition
import de.mytoysgroup.movies.challenge.presentation.detail.DetailActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
@SmallTest
class ListActivityTest {

    @get:Rule
    val mActivityRule = ActivityTestRule<ListActivity>(ListActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule<DetailActivity>(DetailActivity::class.java)

    // Is frustrating that Unit test allows backtiles and spaces in function names, not in Instrumentation test...
    @Test
    fun WHEN_search_with_text_Avengers_THEN_first_movie_has_The_Avengers_as_title() {
        onView(withId(R.id.action_search))
                .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText("Avengers"),
                        pressImeActionButton())

        sleep(1500)

        onView(withId(R.id.searchResultLayout))
                .check(matches(atPosition(0, hasDescendant(allOf(
                        withId(R.id.titleLabel),
                        withText("The Avengers")
                )))))

        onView(withId(R.id.searchResultLayout))
                .check(matches(atPosition(2, hasDescendant(allOf(
                        withId(R.id.titleLabel),
                        withText("Avengers: Infinity War")
                )))))
    }

    @Test
    fun WHEN_search_with_text_Avengers_AND_click_on_Avengers_Infinity_War_movie_THEN_detail_screen_is_launched_with_url() {
        onView(withId(R.id.action_search))
                .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText("Avengers"),
                        pressImeActionButton())

        sleep(1500)

        onView(withId(R.id.searchResultLayout))
                .perform(actionOnItemAtPosition<ListAdapter.ViewHolder>(2, click()))

        intended(allOf(
                hasAction(equalTo(Intent.ACTION_VIEW)),
                hasData(allOf(
                        hasScheme(equalTo(mActivityRule.activity.getString(R.string.deep_linking_scheme))),
                        hasHost(equalTo(mActivityRule.activity.getString(R.string.deep_linking_host))),
                        hasPath(equalTo("/tt4154756"))
                )),
                hasPackage(mActivityRule.activity.packageName)
        ))
    }
}