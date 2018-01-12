package birt

import grails.plugins.*

class BirtGrailsPlugin extends Plugin {
    def version = "4.3.0.4-SNAPSHOT"
    def grailsVersion = "3.2.11 > *"
	def author = "Eyck Jentzsch"
	def authorEmail = "eyck@jepemuc.de"
	def title = "Birt Reporting Plugin"
	def description = 'Makes it easy to integrate the Birt reporting engine (runtime component) into your Grails application. Currently BIRT 4.3.0 is used.'
	def documentation = "http://grails.org/plugin/birt-report"

	def license = 'APACHE'
	def issueManagement = [system: 'JIRA', url: 'https://github.com/eyck/grails-birt-report/issues']
	def scm = [url: 'https://github.com/eyck/grails-birt-report']

	def pluginExcludes = [
		'lib/**',
		'web-app/**',
        "grails-app/views/*"
	]
    
    Closure doWithSpring() { {->
            // TODO Implement runtime spring config (optional)
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
