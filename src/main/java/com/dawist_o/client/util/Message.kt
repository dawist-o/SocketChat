package com.dawist_o.client.util

import java.io.Serializable

data class Message(val client: Client, val msg:String): Serializable {
}