package com.motorolasolutions.psx.capture.ui.matchers;

import com.motorolasolutions.psx.capture.activity.capture.adapter.IncidentSelectionModel;

import org.hamcrest.FeatureMatcher;

import static org.hamcrest.CoreMatchers.equalTo;

public class ObjectMatcher {

    /**
     * Locate an incident in the incident drop down list by matching on its displayed text
     *
     * @param name text to be matched against in the list
     * @return the matching incident
     */
    public static FeatureMatcher<IncidentSelectionModel, String> withIncidentName(
            final String name) {
        return new FeatureMatcher<IncidentSelectionModel, String>(equalTo(name),
                "with incident name", "incident name") {
            @Override
            protected String featureValueOf(IncidentSelectionModel incident) {
                return incident.label;
            }
        };
    }
}
