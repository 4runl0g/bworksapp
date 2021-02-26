Hybrid framework using TestNG
--

This Automation Framework is a combination of Hybrid, Data driven & Keyword driven using json file as dataprovider.

## src/main/java packages:
1. DataProvider
2. DriverFactory
3. PageObjects
4. Utilities

### DataProvider module:
This module covers setting up TestData
1. DataFactory used for every single test case
2. TestData for TestNG suite in JSON format

### DriverFactory module:
1. WebDriverManager
2. WebDriver / Remote Driver (Selenium Grid)
3. ChromeDriver

### PageObjects module:
Application wise web page xpaths / locators using page identifier using POM

### Utilities module:
1. JSON util to read json files 
