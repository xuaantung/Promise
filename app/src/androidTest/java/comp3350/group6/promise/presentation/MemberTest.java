package comp3350.group6.promise.presentation;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.group6.promise.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MemberTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /*
        This test creates three accounts and one project to test the member list, and role feature.
        A comment is added to each section to indicate the action made.
     */
    @Test
    public void memberTest2() {

        // Create a new Account: Id = 123, PW = 123
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.registerLink), withText("Don't have an account? Tap to register."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.intro_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.register_button), withText("Create My Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                8),
                        isDisplayed()));
        materialButton.perform(click());

        // Sign-out from 123's account
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        // Create a new Account: Id = 456, PW = 456
        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.registerLink), withText("Don't have an account? Tap to register."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.email_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.password_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.intro_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("hello"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.register_button), withText("Create My Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                8),
                        isDisplayed()));
        materialButton3.perform(click());

        // Sign-out from 456's account
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        // Create a new Account: Id = 789, PW = 789
        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.registerLink), withText("Don't have an account? Tap to register."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.relativeLayout),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.email_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("789"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("789"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.password_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("789"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.intro_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                7),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("wooo"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.register_button), withText("Create My Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                8),
                        isDisplayed()));
        materialButton5.perform(click());

        // Create a new Project
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("Dashboard Add Action"),
                        childAtPosition(
                                allOf(withId(R.id.coordinator_layout),
                                        childAtPosition(
                                                withId(R.id.main_nav_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.project_name_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("789's project"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.project_description_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("My project!"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withText("Create Project"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.appbar)))),
                        isDisplayed()));
        textView.check(matches(withText("Create Project")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.create_project_button), withText("Create Project"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton6.perform(click());

        // Check if text was correct
        ViewInteraction editText = onView(
                allOf(withId(R.id.project_description_input), withText("My project!"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        editText.check(matches(withText("My project!")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.project_name_input), withText("789's project"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        editText2.check(matches(withText("789's project")));

        // Go to project detail page
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.project_container),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.projectRecyclerView),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        // Check if text was correct
        ViewInteraction editText3 = onView(
                allOf(withId(R.id.project_name_input), withText("789's project"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        editText3.check(matches(withText("789's project")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.project_description_input), withText("My project!"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        editText4.check(matches(withText("My project!")));

        // Check members list
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("View Members"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction twoLineListItem = onView(
                allOf(withParent(allOf(withId(R.id.members_list),
                        withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        twoLineListItem.check(matches(isDisplayed()));

        // Invite 123 and 456 to project
        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Invite Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.user_search_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)));
        appCompatEditText18.perform(scrollTo(), replaceText("123"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.sendInviteButton), withText("Invite Users"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Invite Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.user_search_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)));
        appCompatEditText19.perform(scrollTo(), replaceText("456"), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.sendInviteButton), withText("Invite Users"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.goBackProject), withText("Back to project"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                1),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView5.perform(click());

        // Sign-out from 789's account
        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.loginEmailInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText20.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.loginPasswordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText21.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.loginPasswordInput), withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText22.perform(pressImeActionButton());

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                4),
                        isDisplayed()));
        materialButton12.perform(click());

        // Accept project invitation
        ViewInteraction bottomNavigationItemView6 = onView(
                allOf(withId(R.id.notifications), withContentDescription("Notifications"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView6.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.notifRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton13 = onView(
                allOf(withId(R.id.acceptInviteButton), withText("Accept"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                2),
                        isDisplayed()));
        materialButton13.perform(click());

        // Sign-out from 123's account
        ViewInteraction bottomNavigationItemView7 = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView7.perform(click());

        ViewInteraction materialButton14 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton14.perform(click());

        // Sign-in to 456's account
        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.loginEmailInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText23.perform(replaceText("456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.loginPasswordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText24.perform(replaceText("456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.loginPasswordInput), withText("456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText25.perform(pressImeActionButton());

        ViewInteraction materialButton15 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                4),
                        isDisplayed()));
        materialButton15.perform(click());

        // Accept project invitation
        ViewInteraction bottomNavigationItemView8 = onView(
                allOf(withId(R.id.notifications), withContentDescription("Notifications"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView8.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.notifRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton16 = onView(
                allOf(withId(R.id.acceptInviteButton), withText("Accept"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                2),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction bottomNavigationItemView9 = onView(
                allOf(withId(R.id.home), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView9.perform(click());

        // Go to project page
        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.project_container),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.projectRecyclerView),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout2.perform(click());

        // Check members list
        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton4.perform(click());

        ViewInteraction materialTextView7 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("View Members"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView7.perform(click());

        // Check that 123 is a MEMBER
        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text2), withText("MEMBER"),
                        withParent(withParent(withId(R.id.members_list))),
                        isDisplayed()));
        textView2.check(matches(withText("MEMBER")));

        ViewInteraction bottomNavigationItemView10 = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView10.perform(click());

        // Sign-out from 456's account
        ViewInteraction materialButton17 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton17.perform(click());

        // Login to 789's account
        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.loginEmailInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText26.perform(replaceText("789"), closeSoftKeyboard());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.loginPasswordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText27.perform(replaceText("789"), closeSoftKeyboard());

        ViewInteraction materialButton18 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                4),
                        isDisplayed()));
        materialButton18.perform(click());

        // Go to project page
        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.project_container),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.projectRecyclerView),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.perform(click());

        // View members list
        ViewInteraction overflowMenuButton5 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton5.perform(click());

        ViewInteraction materialTextView8 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("View Members"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView8.perform(click());

        // Give 123 an ADMIN role
        DataInteraction twoLineListItem2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.members_list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(1);
        twoLineListItem2.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withId(androidx.appcompat.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.appcompat.R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(allOf(withId(androidx.appcompat.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.appcompat.R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction materialButton19 = onView(
                allOf(withId(android.R.id.button1), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton19.perform(scrollTo(), click());

        // Sign-out from 789's account
        ViewInteraction bottomNavigationItemView11 = onView(
                allOf(withId(R.id.settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView11.perform(click());

        ViewInteraction materialButton20 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign Out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dashboard_nav_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton20.perform(click());

        // Login to 123's account
        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.loginEmailInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText29.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.loginPasswordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText30.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.loginPasswordInput), withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText31.perform(pressImeActionButton());

        ViewInteraction materialButton21 = onView(
                allOf(withId(R.id.signOutButton), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainerView),
                                        0),
                                4),
                        isDisplayed()));
        materialButton21.perform(click());

        // Go to project page
        ViewInteraction linearLayout4 = onView(
                allOf(withId(R.id.project_container),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.projectRecyclerView),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout4.perform(click());

        // View member list
        ViewInteraction overflowMenuButton6 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton6.perform(click());

        ViewInteraction materialTextView9 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("View Members"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView9.perform(click());

        // Check that 123 has an ADMIN role
        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text2), withText("ADMIN"),
                        withParent(withParent(withId(R.id.members_list))),
                        isDisplayed()));
        textView3.check(matches(withText("ADMIN")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
