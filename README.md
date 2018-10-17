# WebDriverAgent Java Client
WebDriverAgent Java Client for tvOS automation

## Environment

* A Mac computer with macOS 10.12 or higher is required.
* Xcode 9 or higher is required.
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

Install [ios-deploy](https://github.com/phonegap/ios-deploy) for app installation.

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
    <version>1.0.0-beta-1</version>
</dependency>
```
Download [WebDriverAgent](https://github.com/shvul/WebDriverAgent) with tvOS support:
```
git clone https://github.com/shvul/WebDriverAgent.git
```
Move to WebDriverAgent folder
```
cd ./WebDriverAgent
```
Switch to application-commands branch to use application install/activate/launch commands:
```
git checkout -b application-commands origin/application-commands
```

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
