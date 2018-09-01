package de.mytoysgroup.movies.challenge.presentation.detail

import android.content.Intent
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import de.mytoysgroup.movies.challenge.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
@SmallTest
class DetailActivityTest {

    @get:Rule
    val mActivityRule = object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            val uri = Uri.Builder()
                    .scheme(targetContext.getString(R.string.deep_linking_scheme))
                    .authority(targetContext.getString(R.string.deep_linking_host))
                    .path("tt4154756") // Avengers: Infinity War
                    .build()

            return Intent(Intent.ACTION_VIEW, uri).apply {
                `package` = targetContext.packageName
            }
        }
    }

    @Test
    fun WHEN_movie_load_completes_THEN_toolbar_has_title_Avengers_Infinity_War() {
        sleep(1500)

        onView(withId(R.id.toolbar))
                .check(matches(hasDescendant(withText("Avengers: Infinity War"))))

        onView(withId(R.id.titleLabel))
                .check(matches(withText("Avengers: Infinity War")))

        onView(withId(R.id.descriptionLabel))
                .check(matches(withText("The Avengers and their allies must be willing to sacrifice all in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.")))
    }
}