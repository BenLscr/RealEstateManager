package com.openclassrooms.realestatemanager


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UpdateFormPathEspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun tryToUpdateThePath_EspressoTest() {
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

        val textInputEditText = onView(
                allOf(withId(R.id.form_path_edit_text), withText("311 Edinboro Rd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.form_path_layout),
                                        0),
                                0)))
        textInputEditText.perform(scrollTo(), replaceText("fake path"))

        val textInputEditText2 = onView(
                allOf(withId(R.id.form_path_edit_text), withText("fake path"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.form_path_layout),
                                        0),
                                0),
                        isDisplayed()))
        textInputEditText2.perform(closeSoftKeyboard())

        val materialButton = onView(
                allOf(withId(R.id.form_add_button), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.LinearLayout")),
                                        20),
                                1)))
        materialButton.perform(scrollTo(), click())

        val textView = onView(
                allOf(withId(R.id.property_detail_path), withText("fake path"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                                        1),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("fake path")))
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
