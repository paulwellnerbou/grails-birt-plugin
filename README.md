# grails-birt-plugin

Plugin for Grails 3.2+ offering BIRT functionality from legacy Grails 2 BIRT Plugin (http://grails.org/plugin/birt-report)

--- 

This plugin takes mainly [eyck's](https://github.com/eyck) code from his [BIRT Plugin for Grails 2](https://github.com/eyck/grails-birt-report),
restructured so that it works with Grails 3 and works with Reports from classpath resources, not only from files.

As I am neither an expert for grails nor for BIRT, there may be way better methods to set up this Grails 3 plugin or
to initialize the BIRT engine. If so, please contact me, open an issue or create a pull request.

--- 

# Usage

## Setup

1. Include the dependency in your `build.gradle` of your Grails 3 project:
	```
	compile 'org.grails.plugins:birt-report:4.3.0.4-SNAPSHOT'
	```
	As the artifact is not published in any public repositories, you will have to build it before (see below under *Test, build & install*).
2. Add `BirtReportService` to your beans in your `resources.groovy`:
	```
	birtReportService(BirtReportService, grailsApplication)
	```
3. Use `BirtReportService` in your Controllers/Services, it will be automatically injected:
	```
	class MyController {

		BirtReportService birtReportService
		...
	}
	```

## Configuration

The location of the reports can be configures in your `application.groovy` with

```
birt {
    reportHome = "Reports"
    imageUrl = "images/report"
}
```

## Creating a report

Have a look at the unit test class `BirtReportServiceTest`, where a sample report is created.

### Create a PDF file

```groovy
def params = [:]
def reportName = "new_report" // .rptdesign is added automatically
def targetFileName = "/path/to/target/file.pdf"
def options = birtReportService.getRenderOption(null, 'pdf')
birtReportService.run(reportName, params, targetFileName) // params is a key-value structure containing params your report needs
def result = birtReportService.render(targetFileName, params, options)
new File(targetFileName).newOutputStream() << result.toByteArray()
```

### Use it in your servlet context to deliver a PDF as response

```groovy
def params = [:]
def reportName = "new_report" // .rptdesign is added automatically
def targetFileName = "output.pdf"
def options = birtReportService.getRenderOption(request, 'pdf')
birtReportService.run(reportName, params, fileName)
def result = birtReportService.render(targetFileName, params, options)
response.setHeader("Content-disposition", "attachment; filename=" + targetFileName)
response.contentType = 'application/pdf'
response.outputStream << result.toByteArray()
```

# Test, build & install

	./gradlew check install

The `check` task will build and run the tests. The `install` tasks will package the plugin and publish it to your
local Maven repository.

# Release

*TODO*
