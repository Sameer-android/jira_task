package com.example.jira.dataClasses
class User {
    var name:String? = null
    var code:String? = null
    var role:String? = null
    var id:String? = null
    constructor(){}
    constructor(name: String?, code: String?,role : String?, id: String?) {
        this.name = name
        this.code = code
        this.role = role
        this.id = id
    }
}
