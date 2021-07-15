package com.motorolasolutions.psx.capture.ui.actions;

import android.content.Context;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anyOf;

public class ClickActions {

    private static final int timeToWaitInMillis = 5000;

    /***
     * Wait for the view to be clickable before attempting the click.
     * Will fail if the id of the view to be clicked can not be matched
     * to a defined resource name.
     */
    public static ViewAction patientClick() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anyOf(isAssignableFrom(View.class));
            }

            @Override
            public String getDescription() {
                return "wait for view to be clickable";
            }

            @Override
            public void perform(UiController uiController, View view) {
                UiDevice device = UiDevice.getInstance(getInstrumentation());
                Context context = getInstrumentation().getTargetContext();
                String identifier = context.getResources().getResourceName(view.getId());
                UiObject2 element = device.findObject(By.res(identifier));
                element.wait(Until.clickable(true), timeToWaitInMillis);

                new GeneralClickAction(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER,
                        InputDevice.SOURCE_UNKNOWN, MotionEvent.BUTTON_PRIMARY).perform(
                        uiController, view);
            }
        };
    }
}
