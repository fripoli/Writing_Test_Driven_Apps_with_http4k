package _08

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

interface SentenceAnalyserContract {
    val app: HttpHandler

    @Test
    fun `can count words`() {
        assertThat(app(Request(POST, "/count").body("the lazy lazy cat")), hasStatus(OK).and(hasBody("4")))
    }

    @Test
    fun `keeps track of total of calls`() {
        assertThat(app(Request(GET, "/calls")), hasStatus(OK).and(hasBody("0")))
        app(Request(POST, "/count").body("the lazy lazy cat"))
        assertThat(app(Request(GET, "/calls")), hasStatus(OK).and(hasBody("1")))
    }
}