package com.example.jira.dataClasses

class AssignTask {
    var id : String = ""
    var module:String? = null
    var tasks:String? = null
    var projectId:String? = null
    var assignTo:String? = null
    var assignId:String? = null
    constructor(){}
    constructor(module: String?, tasks: String?, id: String?,assign:String?,assignId:String?) {
        this.module = module
        this.tasks = tasks
        this.projectId = id
        this.assignTo = assign
        this.assignId = assignId
    }
}