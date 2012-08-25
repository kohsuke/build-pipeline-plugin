package au.com.centrumsystems.hudson.plugin.buildpipeline

import au.com.centrumsystems.hudson.plugin.buildpipeline.PipelineBuild;
import groovy.json.JsonBuilder
import hudson.model.Cause;
import hudson.model.Cause.UserIdCause;
import hudson.model.Item;

class BuildJSONBuilder {

	static String asJSON(PipelineBuild pipelineBuild, Integer formId, Integer projectId, List<Integer> buildDependencyIds, boolean firstRow) {
		def builder = new JsonBuilder()
		def buildStatus = pipelineBuild.currentBuildResult
		def root = builder {
			id(formId)
			build {
				dependencyIds(buildDependencyIds)
				displayName(pipelineBuild.currentBuild?.displayName)
				duration(pipelineBuild.buildDuration)
				extId(pipelineBuild.currentBuild?.externalizableId)
				hasPermission(pipelineBuild.project?.hasPermission(Item.BUILD));
				hasUpstreamBuild(null != pipelineBuild.upstreamBuild)
				isBuilding(buildStatus == 'BUILDING')
				isComplete(buildStatus != 'BUILDING' && buildStatus != 'PENDING' && buildStatus != 'MANUAL')
				isFirstRow(firstRow)
				isPending(buildStatus == 'PENDING')
				isReadyToBeManuallyBuilt(pipelineBuild.isReadyToBeManuallyBuilt())
				isManualTrigger(pipelineBuild.isManualTrigger())
				isRerunable(buildStatus != 'PENDING' && buildStatus != 'BUILDING' && !pipelineBuild.isReadyToBeManuallyBuilt())
				number(pipelineBuild.currentBuild?.number)
				progress(pipelineBuild.buildProgress)
				progressLeft(100 - pipelineBuild.buildProgress)
				startDate(pipelineBuild.formattedStartDate)
				startTime(pipelineBuild.formattedStartTime)
				status(buildStatus)
				url(pipelineBuild.buildResultURL ? pipelineBuild.buildResultURL : pipelineBuild.projectURL)
				userId(pipelineBuild.currentBuild?.getCause(Cause.UserIdCause.class)?.getUserId())
				estimatedRemainingTime(pipelineBuild.currentBuild?.executor?.estimatedRemainingTime)
			}
			project {
				disabled(pipelineBuild.projectDisabled)
				name(pipelineBuild.project.name)
				url(pipelineBuild.projectURL)
				health(pipelineBuild.projectHealth)
				id(projectId)
			}
			upstream {
				projectName(pipelineBuild.upstreamPipelineBuild?.project?.name)
				buildNumber(pipelineBuild.upstreamBuild?.number)
			}
		}
		return builder.toString()
	}
}