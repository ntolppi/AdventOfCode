package net.nzti

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://adventofcode.com/2023/day/1/input")
    println("$response.status, $response.bodyAsText()")
    client.close()
}