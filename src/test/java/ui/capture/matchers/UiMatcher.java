package com.motorolasolutions.psx.capture.ui.matchers;

import android.view.View;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Custom matchers to help with locating elements in the UI
 */
public class UiMatcher {

    public static Matcher<View> first(final Matcher<View> matcher) {
        return new BaseMatcher<View>() {
            boolean isFirst = true;

            @Override
            public void describeTo(Description description) {
                description.appendText("the first element matching: ");
                matcher.describeTo(description);
            }

            @Override
            public boolean matches(Object element) {
                if (isFirst && matcher.matches(element)) {
                    isFirst = false;
                    return true;
                }
                return false;
            }
        };
    }

    public static Matcher<View> atIndex(final Matcher<View> matcher, final int index) {
        return new BaseMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("at index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matches(Object view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}
