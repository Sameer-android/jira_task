package com.example.jira.dataClasses

interface OnItemClickListener {
    interface OnItemClick {
        fun onClick(
            position: Int, dataString: String
        )
    }
}