package com.prmto

import com.prmto.models.ApiResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class SearchHeroesEndpointTests {

    @Test
    fun `access search heroes endpoint, query hero name, assert single hero result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sas").apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size

                assertEquals(
                    expected = 1,
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `access search heroes endpoint, query hero name, assert multiple hero result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sa").apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size

                assertEquals(
                    expected = 3,
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `access search heroes endpoint, query an empty txt, assert empty list as a result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=").apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes

                assertEquals(
                    expected = emptyList(),
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `access search heroes endpoint, query non existing hero, assert empty list as a result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=unknown").apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes

                assertEquals(
                    expected = emptyList(),
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `access non existing endpoint,  assert not found `() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/unknown").apply {

                assertEquals(HttpStatusCode.NotFound, response.status())


                assertEquals(
                    expected = "Page not found",
                    actual = response.content
                )
            }
        }
    }

}