package com.example.assigment2_spotifyclone


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LongTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun longTest() {
        onView(allOf(withId(R.id.premium), withText("Premium"))).perform(click())

        onView(allOf(withId(R.id.price), withText("70$/m"))).check(matches(withText("70$/m")))

        onView(allOf(withId(R.id.deluxe), withText("Deluxe"))).perform(click())

        onView(allOf(withId(R.id.price), withText("60$/m"))).check(matches(withText("60$/m")))

        onView(allOf(withId(R.id.basic), withText("Basic"))).perform(click())

        onView(allOf(withId(R.id.price), withText("50$/m"))).check(matches(withText("50$/m")))

        onView(allOf(withId(R.id.next), withText("NEXT"))).perform(click())

        onView(allOf(withId(R.id.productView))).check(matches(isDisplayed()))

       onView(allOf(withId(R.id.prodyear), withText("Produced 2015"))).check(matches(withText("Produced 2015")))

         onView(allOf(withId(R.id.listens), withText("19K listens"))).check(matches(withText("19K listens")))

        onView(allOf(withId(R.id.rate))).check(matches(isDisplayed()))

         onView(allOf(withId(R.id.price), withText("25$/m"))).check(matches(withText("25$/m")))

         onView(allOf(withId(R.id.price), withText("25$/m"))).check(matches(withText("25$/m")))
    }


}
