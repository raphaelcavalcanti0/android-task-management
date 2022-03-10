package com.rcfin.taskmanagement.models

class Task {

    var id: Int = 0;
    var title: String = "";
    var describe: String = "";
    var date: String = "";
    var time: String = "";

    constructor(title: String, describe: String, date: String, time: String) {
        this.title = title
        this.describe = describe
        this.date = date
        this.time = time
    }

    constructor() {

    }
}