package com.dawist_o.client.util

import java.io.File
import java.io.Serializable

class Message : Serializable {
    var client: Client
    var msg: String = ""
    var file: File? = null

    fun containsFile(): Boolean = file != null
    override fun toString(): String {
        return "Message(client=$client, msg='$msg', file=$file)"
    }

    constructor(client: Client, msg: String) {
        this.client = client
        this.msg = msg
    }

    constructor(client: Client, file: File) {
        this.client = client
        this.file = file
    }
}