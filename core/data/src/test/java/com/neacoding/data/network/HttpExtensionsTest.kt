package com.neacoding.data.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.neacoding.domain.util.DataError
import com.neacoding.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HttpExtensionsTest {

    @Serializable
    private data class Payload(val name: String)

    private fun clientReturning(status: HttpStatusCode, body: String = ""): HttpClient {
        val engine = MockEngine {
            if (body.isEmpty()) {
                respondError(status)
            } else {
                respond(
                    content = body,
                    status = status,
                    headers = headersOf("Content-Type", ContentType.Application.Json.toString()),
                )
            }
        }
        return HttpClient(engine) {
            expectSuccess = false
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
    }

    @Test
    fun `a 2xx response is deserialized into a success`() = runTest {
        val client = clientReturning(HttpStatusCode.OK, """{"name":"Nidhal"}""")

        val result = client.get<Payload>("/profile")

        assertThat(result).isEqualTo(Result.Success(Payload("Nidhal")))
    }

    @Test
    fun `a malformed 2xx body maps to SERIALIZATION`() = runTest {
        val client = clientReturning(HttpStatusCode.OK, """{"unexpected":1}""")

        val result = client.get<Payload>("/profile")

        assertThat(result).isEqualTo(Result.Error(DataError.Network.SERIALIZATION))
    }

    @ParameterizedTest
    @CsvSource(
        "400, BAD_REQUEST",
        "401, UNAUTHORIZED",
        "403, FORBIDDEN",
        "404, NOT_FOUND",
        "408, REQUEST_TIMEOUT",
        "409, CONFLICT",
        "413, PAYLOAD_TOO_LARGE",
        "429, TOO_MANY_REQUESTS",
        "500, SERVER_ERROR",
        "502, SERVER_ERROR",
        "503, SERVICE_UNAVAILABLE",
        "302, UNKNOWN",
    )
    fun `http status codes map to the expected network error`(code: Int, expected: String) = runTest {
        val client = clientReturning(HttpStatusCode.fromValue(code))

        val result = client.get<Payload>("/profile")

        assertThat(result).isEqualTo(Result.Error(DataError.Network.valueOf(expected)))
    }

    @Test
    fun `constructRoute leaves an absolute url untouched`() {
        val absolute = BuildConfigBaseUrl + "/rooms"

        assertThat(constructRoute(absolute)).isEqualTo(absolute)
    }

    @Test
    fun `constructRoute prefixes the base url whether or not the route is slash-prefixed`() {
        assertThat(constructRoute("/rooms")).isEqualTo("$BuildConfigBaseUrl/rooms")
        assertThat(constructRoute("rooms")).isEqualTo("$BuildConfigBaseUrl/rooms")
    }

    private companion object {
        val BuildConfigBaseUrl: String = com.neacoding.data.BuildConfig.BASE_URL
    }
}
