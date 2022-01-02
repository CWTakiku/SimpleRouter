package com.stl.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class SimpleRouterPlugin implements Plugin<Project> {

    /**
     * 当插件apply时调用
     *
     * @param project 项目
     */
    void apply(Project project) {
        project.getExtensions().add("pathExtension", SimpleRouterPathExtension.class)
        project.getTasks().create("createSimpleRouterMembersFile", SimpleRouterCreateMembers.class)
        Task task = project.tasks.getByName('preBuild')
        task.dependsOn('createSimpleRouterMembersFile')
        System.out.println("-----------------SimpleRouter Plugin begin---------------")
    }
}
