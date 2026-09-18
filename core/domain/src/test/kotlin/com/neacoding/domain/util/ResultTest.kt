package com.neacoding.domain.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import org.junit.jupiter.api.Test

class ResultTest {

    private enum class TestError : Error { BOOM }

    @Test
    fun `map transforms the payload of a success`() {
        val result: Result<Int, TestError> = Result.Success(21)

        val mapped = result.map { it * 2 }

        assertThat(mapped).isInstanceOf(Result.Success::class)
        assertThat((mapped as Result.Success).data).isEqualTo(42)
    }

    @Test
    fun `map leaves an error untouched and does not invoke the transform`() {
        val result: Result<Int, TestError> = Result.Error(TestError.BOOM)
        var invoked = false

        val mapped = result.map {
            invoked = true
            it * 2
        }

        assertThat(invoked).isEqualTo(false)
        assertThat(mapped).isEqualTo(Result.Error(TestError.BOOM))
    }

    @Test
    fun `onSuccess runs only on success and returns the original result`() {
        var seen: Int? = null
        val result: Result<Int, TestError> = Result.Success(7)

        val returned = result.onSuccess { seen = it }

        assertThat(seen).isEqualTo(7)
        assertThat(returned).isEqualTo(result)
    }

    @Test
    fun `onSuccess does not run on error`() {
        var invoked = false
        val result: Result<Int, TestError> = Result.Error(TestError.BOOM)

        result.onSuccess { invoked = true }

        assertThat(invoked).isEqualTo(false)
    }

    @Test
    fun `onFailure runs only on error and returns the original result`() {
        var seen: TestError? = null
        val result: Result<Int, TestError> = Result.Error(TestError.BOOM)

        val returned = result.onFailure { seen = it }

        assertThat(seen).isEqualTo(TestError.BOOM)
        assertThat(returned).isEqualTo(result)
    }

    @Test
    fun `onFailure does not run on success`() {
        var invoked = false
        val result: Result<Int, TestError> = Result.Success(1)

        result.onFailure { invoked = true }

        assertThat(invoked).isEqualTo(false)
    }

    @Test
    fun `asEmptyResult discards the payload but keeps success`() {
        val result: Result<Int, TestError> = Result.Success(99)

        assertThat(result.asEmptyResult()).isEqualTo(Result.Success(Unit))
    }

    @Test
    fun `asEmptyResult keeps the error`() {
        val result: Result<Int, TestError> = Result.Error(TestError.BOOM)

        assertThat(result.asEmptyResult()).isEqualTo(Result.Error(TestError.BOOM))
    }

    @Test
    fun `getOrElse returns the payload on success and the default on error`() {
        val success: Result<Int, TestError> = Result.Success(5)
        val failure: Result<Int, TestError> = Result.Error(TestError.BOOM)

        assertThat(success.getOrElse(-1)).isEqualTo(5)
        assertThat(failure.getOrElse(-1)).isEqualTo(-1)
    }

    @Test
    fun `getOrNull returns the payload on success and null on error`() {
        val success: Result<Int, TestError> = Result.Success(5)
        val failure: Result<Int, TestError> = Result.Error(TestError.BOOM)

        assertThat(success.getOrNull()).isEqualTo(5)
        assertThat(failure.getOrNull()).isNull()
    }

    @Test
    fun `chaining helpers preserves the original result`() {
        val result: Result<Int, TestError> = Result.Success(3)
        val calls = mutableListOf<String>()

        val returned = result
            .onSuccess { calls += "success" }
            .onFailure { calls += "failure" }
            .map { it + 1 }

        assertThat(calls).isEqualTo(listOf("success"))
        assertThat(returned).isEqualTo(Result.Success(4))
    }
}
