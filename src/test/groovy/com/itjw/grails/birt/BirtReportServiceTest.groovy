package com.itjw.grails.birt

import org.grails.io.support.ClassPathResource
import org.grails.io.support.Resource
import spock.lang.Specification

/**
 * @author Paul Wellner Bou <paul@wellnerbou.de>
 */
class BirtReportServiceTest extends Specification {
    def setup() {
    }
    
    def "test report rendering with classpath"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.reportHome = "Reports"
        birtReportService.initEngine("", 100)
        def reportName = "new_report"
        def targetFileName = "./build/new_report_from_classpath.pdf"
        def options = birtReportService.getRenderOption(null, 'pdf')
        def params = [:]
        
        when:
        birtReportService.run(reportName, params, targetFileName)
		def result = birtReportService.render(targetFileName, params, options)
        
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
        birtReportService.reportHome = "Reports"
        
        when:
        InputStream inputStream = birtReportService.getInputStreamForClasspathResource("report")
        
        then:
        inputStream != null
        inputStream.text == "File in default report location\n"
    }
    
    def "test report rendering with path to file on local filesystem"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        birtReportService.reportHome = "src/test/resources/Reports"
        birtReportService.initEngine("", 100)
        def reportName = "new_report"
        def targetFileName = "./build/new_report.pdf"
        def options = birtReportService.getRenderOption(null, 'pdf')
        def params = [:]
        
        when:
        birtReportService.run(reportName, null, params, targetFileName)
		def result = birtReportService.render(targetFileName, params, options)
        
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
        birtReportService.reportHome = "Reports"
        
        when:
        def reportHome = birtReportService.createCompleteReportFilename("reportName")
        
        then:
        reportHome == "Reports/reportName.rptdesign"
    }
    
    def "test detectReportHome default classpath location"() {
        given:
        BirtReportService birtReportService = new BirtReportService(null)
        
        when:
        def reportHome = birtReportService.detectReportHome(null)
        
        then:
        reportHome == "Reports"
    }
    
    def "test get resource from classpath"() {
        given:
        def fn = "report.rptdesign"
        
        when:
        Resource resource = new ClassPathResource("Reports/report.rptdesign");
        InputStream inputStream = resource.inputStream

        then:
        inputStream != null
        inputStream.text == "File in default report location\n"
    }
}
