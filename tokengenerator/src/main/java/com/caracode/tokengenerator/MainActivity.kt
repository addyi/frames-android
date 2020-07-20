package com.caracode.tokengenerator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.checkout.sdk.CheckoutClient
import com.checkout.sdk.core.TokenResult
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.utils.Environment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenCallback = createTokenCallback()
        val checkoutClient = CheckoutClient.create(this, KEY, Environment.SANDBOX, tokenCallback)
        generate_token_button.setOnClickListener {
            progress_bar.visibility = VISIBLE
            checkoutClient.requestToken(createCardTokenizationRequest())
        }
    }

    private fun createTokenCallback(): CheckoutClient.TokenCallback {
        return object : CheckoutClient.TokenCallback {
            override fun onTokenResult(tokenResult: TokenResult) {
                when (tokenResult) {
                    is TokenResult.TokenResultSuccess -> setSuccessText(tokenResult)
                    is TokenResult.TokenResultTokenizationFail -> setTokenizationFail(tokenResult)
                    is TokenResult.TokenResultNetworkError -> setNetworkFail(tokenResult)
                }
                progress_bar.visibility = GONE
            }
        }
    }

    fun createCardTokenizationRequest(): CardTokenizationRequest {
        return CardTokenizationRequest(
            "4242424242424242",
            "Jim Stynes",
            "06",
            "2020",
            "100",
            null
        )
    }

    private fun setSuccessText(tokenSuccess: TokenResult.TokenResultSuccess) {
        token_text_view.text =
            getString(R.string.token_success_format, tokenSuccess.response.token())
    }

    private fun setTokenizationFail(tokenizationFail: TokenResult.TokenResultTokenizationFail) {
        token_text_view.text =
            getString(R.string.token_failed_tokenization_format, tokenizationFail.error.errorCode())
    }

    private fun setNetworkFail(networkFail: TokenResult.TokenResultNetworkError) {
        token_text_view.text =
            getString(R.string.token_failed_tokenization_format, networkFail.exception)
    }

    companion object {
        const val KEY = "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73"
    }
}
