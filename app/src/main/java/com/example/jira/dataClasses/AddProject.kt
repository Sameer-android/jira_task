package com.example.jira.dataClasses

class AddProject {
    var projectName:String? = null
    var projectDes:String? = null
    var id:String? = null
    constructor(){}
    constructor(name: String?, des: String?, id: String?) {
        this.projectName = name
        this.projectDes = des
        this.id = id
    }
}
