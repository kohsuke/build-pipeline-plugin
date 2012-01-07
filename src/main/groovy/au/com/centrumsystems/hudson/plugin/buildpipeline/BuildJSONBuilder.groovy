package au.com.centrumsystems.hudson.plugin.buildpipeline

import groovy.json.JsonBuilder

class BuildJSONBuilder {

    BuildForm buildForm

    BuildJSONBuilder(BuildForm buildForm) {
        this.buildForm = buildForm
    }

    String asJSON() {
        def builder = new JsonBuilder()
        def root = builder {
            title(buildForm.projectName)
            status(buildForm.status)
            buildNumber(buildForm.buildNumber)
            startDate(buildForm.startDate)
            startTime(buildForm.startTime)
            duration(buildForm.duration)
            buildUrl(buildForm.url)
            complete(buildForm.status != 'BUILDING')
            progress(buildForm.buildProgress)
            progressLeft(100 - buildForm.buildProgress)
            id(buildForm.hashCode())
        }
        return builder.toString()
    }
}
