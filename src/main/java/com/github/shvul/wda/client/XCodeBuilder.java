package com.github.shvul.wda.client;

public class XCodeBuilder {

    private enum CLProperty {
        PROJECT("-project"),
        DERIVED_DATA_PATH("-derivedDataPath"),
        DESTINATION("-destination"),
        XCODE_CONFIG("-xcconfig"),
        XCODE_TEST_RUN("-xctestrun"),
        SCHEME("-scheme");

        String value;

        CLProperty(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }
    }
}
