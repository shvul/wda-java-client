package com.github.shvul.wda.client;

import java.util.ArrayList;
import java.util.List;

public class XCodeBuilder {

    private static String COMMAND_NAME = "xcodebuild";
    private static String RUN_TYPE = "test";
    private static String TV_OS_SCHEME = "WebDriverAgentRunner_tvOS";
    private String wdaPath;
    private String platform;
    private String deviceName;
    private String deviceId;
    private String osVersion;

    public Process build() {
        return CommandLineExecutor.createProcess(getCommand());
    }

    public XCodeBuilder setWdaPath(String wdaPath) {
        this.wdaPath = wdaPath;
        return this;
    }

    public XCodeBuilder setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public XCodeBuilder setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public XCodeBuilder setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    private XCodeBuilder setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    private String[] getCommand() {
        if (wdaPath == null) {
            throw new WebDriverAgentException("Unable to build WDA. Please, specify path to WebDriverAgent.xcodeproj");
        }
        List<String> command = new ArrayList<>();
        command.add(COMMAND_NAME);
        command.add(RUN_TYPE);
        command.add(CLProperty.PROJECT.getValue());
        command.add(wdaPath);
        command.add(CLProperty.SCHEME.getValue());
        command.add(TV_OS_SCHEME);
        command.add(CLProperty.DESTINATION.getValue());
        command.add(new DestinationBuilder().build());
        return command.toArray(new String[command.size()]);
    }

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

    private final class DestinationBuilder {
        private static final String PLATFORM_KEY = "platform";
        private static final String NAME_KEY = "name";
        private static final String ID_KEY = "id";
        private static final String OS_KEY = "OS";
        private static final String PLATFORM_SIMULATOR = "tvOS Simulator";
        private static final String PLATFORM_REAL_DEVICE = "tvOS";
        private static final String SEPARATOR = ",";
        private static final String GENERIC_ERR = "Unable to build wda destination.";

        String build() {
            StringBuilder builder = new StringBuilder();
            if (platform == null) {
                throw new WebDriverAgentException(GENERIC_ERR + " Please, specify device platform.");
            }
            builder.append(generateKeyValuePair(PLATFORM_KEY, platform));
            builder.append(SEPARATOR);
            switch (platform) {
            case PLATFORM_REAL_DEVICE:
                appendDeviceInfo(builder);
                break;
            case PLATFORM_SIMULATOR:
                appendDeviceInfo(builder);
                if (osVersion != null) {
                    builder.append(SEPARATOR);
                    builder.append(generateKeyValuePair(OS_KEY, osVersion));
                }
                break;
            default:
                throw new WebDriverAgentException(GENERIC_ERR +
                        " Incorrect platform. Supported platforms: tvOS, tvOS Simulator.");
            }
            return builder.toString();
        }

        private void appendDeviceInfo(StringBuilder builder) {
            if (deviceId != null) {
                builder.append(generateKeyValuePair(ID_KEY, deviceId));
            } else if (deviceName != null) {
                builder.append(generateKeyValuePair(NAME_KEY, deviceName));
            } else {
                throw new WebDriverAgentException(GENERIC_ERR + " Please, specify device id or name.");
            }
        }

        private String generateKeyValuePair(String key, String value) {
            return String.format("%s=%s", key, value);
        }
    }
}
