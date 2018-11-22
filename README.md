# WebDriverAgent Java Client
WebDriverAgent Java Client for tvOS automation

## Environment

* A Mac computer with macOS 10.12 or higher is required.
* Xcode 9.3 or higher is required.
* Command Line Tools for Xcode are required.

## External dependencies

Next libraries and utilities are required for tests startup:
```
brew install libimobiledevice --HEAD

```

There is also a dependency, made necessary by Facebook's [WebDriverAgent](https://github.com/facebook/WebDriverAgent),
for the [Carthage](https://github.com/Carthage/Carthage) dependency manager. If you
do not have Carthage on your system, it can also be installed with
[Homebrew](http://brew.sh/)

```
brew install carthage
```

Install [ios-deploy](https://github.com/phonegap/ios-deploy) for app installation on real devices.

```
npm install -g ios-deploy
```

## Installation

Add [jitpack](https://jitpack.io) repository to download and build github projects as dependencies:
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
Add wda-java-client dependency:
```
<dependency>
    <groupId>com.github.shvul</groupId>
    <artifactId>wda-java-client</artifactId>
    <version>1.0.3</version>
</dependency>
```
Download [WebDriverAgent](https://github.com/shvul/WebDriverAgent) with tvOS support:
```
git clone https://github.com/shvul/WebDriverAgent.git
```
Move to WebDriverAgent folder:
```
cd ./WebDriverAgent
```
Switch to application-commands branch to use application install/activate/launch commands:
```
git checkout -b application-commands origin/application-commands
```
Build carthage dependencies:
```
sh ./Scripts/bootstrap.sh
```

Open xcode project and sign `WebDriverAgentLib_tvOS`, `WebDriverAgentRunner_tvOS` targets under yours provisioning profile.

## Capabilities

These capabilities are available for java client configuration:

|Capability|Description|Values|
|----------|-----------|------|
|`app`|The absolute local path to a `.ipa` file to install.| e.g., `/path/to/my.ipa`|
|`bundleId`|Bundle ID of the app under test. `*`|e.g., `com.facebook.wda.integrationApp`|
|`udid`|Unique device identifier of the connected physical device. Capability is required for app install/remove.|e.g., `a248bf31a45b0153e088d05d66e12dc9316d117d`|
|`deviceIp`|Ip of the physical device. It should be reachable from you network. Capability is required for real devices.|e.g., `192.168.0.1`|
|`deviceName`|The kind of mobile device or emulator to use.|`Apple TV`, `Apple TV 4K`, `Apple TV 4K (at 1080p)`|
|`language`|(Sim-only) Language to set for the simulator.|e.g., `eng`|
|`launchTimeout`| WebDriverAgent startup timeout. Defaults to `60`.|e.g., `120`|
|`locale`|(Sim-only) Locale to set for the simulator.|e.g., `eng_us`|
|`osVersion`|(Sim-only) tvOS OS version.|e.g., `12.0`|
|`platform`|Which tvOS platform to use. `*`|`tvOS`, `tvOS Simulator`|
|`usePrebuiltWDA`|Skips the build phase of running the WDA app. Building is then the responsibility of the user. Defaults to `false`.|e.g., `true`|
|`wdaPath`|Path to WebDriverAgent xcode project. `*`|e.g., `path/to/WebDriverAgent/WebDriverAgent.xcodeproj`|

`*` - required capabilities

## Usage

Create driver capabilities:
```
DriverCapabilities capabilities = new DriverCapabilities();
capabilities.setDeviceId("a248bf31a45b0153e088d05d66e12dc9316d117d");
capabilities.setWdaPath("WebDriverAgent/WebDriverAgent.xcodeproj");
capabilities.setPlatfrom("tvOS");
capabilities.setDeviceIp("192.168.0.1");
capabilities.setBundleId("com.facebook.wda.integrationApp");
```

Create driver:
```
TVDriver driver = DriverFactory.createDriver(capabilities);
```
Perform checks:
```
TVElement category = driver.findElement(TVLocator.xpath("//XCUIElementTypeAny[@name='Category']"));
category.select();
TVElement title = driver.findElement(TVLocator.accessibilityId("Title"));
Assert.assertTrue(title.getText(), "Category");
```
