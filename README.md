# WebDriverAgent Java Client
WebDriverAgent Java Client for tvOS automation

## Environment

* A Mac computer with macOS 10.12 or higher is required.
* Xcode 9 or higher is required.
* [WebDriverAgent](https://github.com/shvul/WebDriverAgent) installed on the Mac

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

Install [ios-deploy](https://github.com/phonegap/ios-deploy) for app installation

```
npm install -g ios-deploy
```
## Installation

## Usage

Create driver capabilities:
```
DriverCapabilities capabilities = new DriverCapabilities();
capabilities.setDeviceId("E67584CD-1530-4EBA-BFC7-2442AD9D2786");
capabilities.setWdaPath("WebDriverAgent/WebDriverAgent.xcodeproj");
capabilities.setPlatfrom("tvOS Simulator");
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
