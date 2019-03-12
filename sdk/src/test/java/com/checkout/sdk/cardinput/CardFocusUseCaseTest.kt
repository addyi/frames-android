package com.checkout.sdk.cardinput

import com.checkout.sdk.store.InMemoryStore
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardFocusUseCaseTest {

    @Mock
    private lateinit var store: InMemoryStore

    @Test
    fun `given valid card without focus then card focus result is error`() {
        given(store.cardNumber).willReturn("1234567812345678")

        val showError = CardFocusUseCase(false, store).execute()

        assertEquals(showError, true)
    }

    @Test
    fun `given valid card field has focus then card focus result is no error`() {
        val showError = CardFocusUseCase(true, store).execute()

        assertEquals(showError, false)
    }

    @Test
    fun `given invalid card without focus then card focus result is error`() {
        given(store.cardNumber).willReturn("11")

        val showError = CardFocusUseCase(false, store).execute()

        assertEquals(showError, true)
    }

}
