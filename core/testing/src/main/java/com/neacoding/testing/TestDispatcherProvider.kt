package com.neacoding.testing

import com.neacoding.domain.util.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/** Routes every dispatcher to a single [TestDispatcher] so tests stay deterministic. */
@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : DispatcherProvider {
    override val main: TestDispatcher get() = dispatcher
    override val io: TestDispatcher get() = dispatcher
    override val default: TestDispatcher get() = dispatcher
}
