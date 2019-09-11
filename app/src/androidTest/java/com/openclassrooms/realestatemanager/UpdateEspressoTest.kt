package com.openclassrooms.realestatemanager


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UpdateEspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun updateEspressoTest() {
        val linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.activity_property_list_container),
                                0),
                        0),
                        isDisplayed()))
        linearLayout.perform(click())

        val actionMenuItemView = onView(
                allOf(withId(R.id.update_button), withContentDescription("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val editText = onView(
                allOf(withId(R.id.form_path_edit_text), withText("311 Edinboro Rd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.form_path_layout),
                                        0),
                                0),
                        isDisplayed()))
        editText.check(matches(withText("311 Edinboro Rd")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
