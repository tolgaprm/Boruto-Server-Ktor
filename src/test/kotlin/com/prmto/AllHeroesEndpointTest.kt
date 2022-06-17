package com.prmto


import com.prmto.models.ApiResponse
import com.prmto.repository.HeroRepository
import com.prmto.repository.NEXT_PAGE_KEY
import com.prmto.repository.PREVIOUS_PAGE_KEY
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.*

class AllHeroesEndpointTest {

    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access root endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Welcome to Boruto API", response.content)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = null,
                    nextPage = 2,
                    heroes = heroRepository.page1
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }


    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5
            )
            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                    assertEquals(HttpStatusCode.OK, response.status())

                    val expected = ApiResponse(
                        success = true,
                        message = "ok",
                        prevPage = calculatePage(page)[PREVIOUS_PAGE_KEY],
                        nextPage = calculatePage(page)[NEXT_PAGE_KEY],
                        heroes = heroes[page - 1]
                    )

                    val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                    assertEquals(
                        expected = expected,
                        actual = actual
                    )

                }
            }

        }
    }

    @Test
    fun `access all heroes endpoint, query non existing pages, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=6").apply {

                assertEquals(HttpStatusCode.NotFound, response.status())
                val expected = ApiResponse(
                    success = false,
                    message = "Heroes not Found"
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }

        }
    }

    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=invalid").apply {

                assertEquals(HttpStatusCode.BadRequest, response.status())
                val expected = ApiResponse(
                    success = false,
                    message = "Only Numbers Allowed."
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }

        }
    }


    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..4) {
            prevPage = prevPage?.minus(1)
        }
        if (page == 1) {
            prevPage = null
        }
        if (page == 5) {
            nextPage = null
        }

        return mapOf(
            PREVIOUS_PAGE_KEY to prevPage,
            NEXT_PAGE_KEY to nextPage
        )
    }

}