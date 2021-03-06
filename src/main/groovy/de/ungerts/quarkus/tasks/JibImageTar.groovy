package de.ungerts.quarkus.tasks

import de.ungerts.quarkus.config.QuarkusJibExtension
import de.ungerts.quarkus.util.JibUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class JibImageTar extends DefaultTask {

    @Input
    QuarkusJibExtension quarkusJibExtension

    @TaskAction
    void buildImage() {
        JibUtils.buildToImageTar(quarkusJibExtension, project)
    }

}
