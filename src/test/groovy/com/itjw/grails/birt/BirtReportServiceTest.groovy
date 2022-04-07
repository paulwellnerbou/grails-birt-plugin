package com.itjw.grails.birt

import grails.config.Config
import grails.testing.spring.AutowiredTest
import org.grails.config.PropertySourcesConfig
import org.grails.io.support.ClassPathResource
import org.grails.io.support.Resource
import spock.lang.Specification

/**
 * @author Paul Wellner Bou <paul@wellnerbou.de>
 */
class BirtReportServiceTest extends Specification implements AutowiredTest {

    def "assure test context is up"() {
        when:
        def ctx = applicationContext

        then:
        ctx != null
    }

    def "test report rendering with classpath"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.applicationContext = applicationContext
        birtReportService.reportHome = "classpath:Reports"
        birtReportService.initEngine("", new PropertySourcesConfig())
        def reportName = "new_report"
        def tmpFileName = File.createTempFile("tmp_report", ".birt", new File("./build")).getAbsolutePath()
        def targetFileName = "./build/new_report_from_classpath.pdf"
        def options = birtReportService.getRenderOption(null, 'pdf')
        def params = [:]

        when:
        birtReportService.run(reportName, params, tmpFileName)
        def result = birtReportService.render(tmpFileName, params, options)

        then:
        result != null

        when:
        new File(targetFileName).newOutputStream() << result.toByteArray()

        then:
        new File(targetFileName).exists()
    }

    def "test get input stream from classpath file"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.applicationContext = applicationContext
        birtReportService.reportHome = "classpath:Reports"

        when:
        InputStream inputStream = birtReportService.getInputStreamForResource("report")

        then:
        inputStream != null
        inputStream.text == "File in default report location"
    }

    def "test report rendering with path to file on local filesystem"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.applicationContext = applicationContext
        birtReportService.reportHome = "file:src/test/resources/Reports"
        birtReportService.initEngine("", new PropertySourcesConfig())
        def reportName = "new_report"
        def tmpFileName = File.createTempFile("tmp_report", ".birt", new File("./build")).getAbsolutePath()
        def targetFileName = "./build/new_report.pdf"
        def options = birtReportService.getRenderOption(null, 'pdf')
        def params = [:]

        when:
        birtReportService.run(reportName, params, tmpFileName)
        def result = birtReportService.render(tmpFileName, params, options)

        then:
        result != null

        when:
        new File(targetFileName).newOutputStream() << result.toByteArray()

        then:
        new File(targetFileName).exists()
    }

    def "test createCompleteReportFilename"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.applicationContext = applicationContext
        birtReportService.reportHome = "Reports"

        when:
        def reportHome = birtReportService.createCompleteReportFilename("reportName")

        then:
        reportHome == "Reports" + File.separator + "reportName.rptdesign"
    }

    def "test detectReportHome default location no nullpointer"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)

        when:
        def reportHome = birtReportService.detectReportHome(null)

        then:
        reportHome == "classpath:Reports"
    }

    def "test detectReportHome default location no nullpointer without birt config"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        def config = new PropertySourcesConfig()

        when:
        def reportHome = birtReportService.detectReportHome(config)

        then:
        reportHome == "classpath:Reports"
    }

    def "test detectReportHome default location no nullpointer with birt config"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        Config config = new PropertySourcesConfig()
        config.birt.whatever = "string"

        when:
        def reportHome = birtReportService.detectReportHome(config)

        then:
        reportHome == "classpath:Reports"
    }

    def "test detectReportHome with location config and with birt config"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        Config config = new PropertySourcesConfig()
        config.birt.reportHome = "reportHome"

        when:
        def reportHome = birtReportService.detectReportHome(config)

        then:
        reportHome == "reportHome"
    }

    def "test get resource from classpath"() {
        when:
        Resource resource = new ClassPathResource("Reports/report.rptdesign");
        InputStream inputStream = resource.inputStream

        then:
        inputStream != null
        inputStream.text == "File in default report location"
    }

    def "test get classpath resource input stream with spring's applicationContext"() {
        given:
        def fn = "classpath:Reports/report.rptdesign"

        when:
        InputStream inputStream = applicationContext.getResource(fn).inputStream

        then:
        inputStream != null
        inputStream.text == "File in default report location"
    }

    def "test get file input stream with spring's applicationContext"() {
        given:
        def fn = "file:src/test/resources/Reports/report.rptdesign"

        when:
        InputStream inputStream = applicationContext.getResource(fn).inputStream

        then:
        inputStream != null
        inputStream.text == "File in default report location"
    }
}
