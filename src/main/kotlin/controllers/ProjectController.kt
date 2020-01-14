package dev.alpas.fireplace.controllers

import dev.alpas.fireplace.entities.Projects
import dev.alpas.http.HttpCall
import dev.alpas.ozone.create
import dev.alpas.routing.Controller
import me.liuwj.ktorm.entity.findAll

class ProjectController : Controller() {
    fun index(call: HttpCall) {
        val projects = Projects.findAll()
        call.render("project_list", mapOf("projects" to projects))
    }

    fun create(call: HttpCall) {
        call.render("project_new")
    }

    fun store(call: HttpCall) {
        val now = call.nowInCurrentTimezone().toInstant()
        // todo: validate
        val project = Projects.create {
            it.title to call.param("title")
            it.description to call.param("description")
            it.ownerId to call.user.id
            it.createdAt to now
            it.updatedAt to now
        }
        flash("success", "Successfully added project '${project.title}'!")
        call.redirect().toRouteNamed("projects.list")
    }
}