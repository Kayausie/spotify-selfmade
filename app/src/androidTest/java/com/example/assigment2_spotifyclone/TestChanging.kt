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
class TestChanging {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testChanging() {
        onView( allOf(withId(R.id.next), withText("NEXT"))).perform(click())
        onView(allOf(withId(R.id.productView))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.price))).check(matches(withText("25$/m")))
    }
    fun testAddtoCart() {
        onView(allOf(withId(R.id.addtocart))).perform(click())

        onView(allOf(withId(R.id.cartitems))).check(matches(withText("1")))
    }

}
