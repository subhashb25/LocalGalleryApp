package org.example.apptest1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform