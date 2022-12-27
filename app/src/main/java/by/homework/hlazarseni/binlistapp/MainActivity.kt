package by.homework.hlazarseni.binlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.homework.hlazarseni.binlistapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentRequest: Call<BankCardDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            editTextNumber
                .textChanges()
                .debounce(500)
                .map { getData(it.toString()) }
                .launchIn(lifecycleScope)
        }
    }

    private suspend fun getData(numberCard: String) = withContext(Dispatchers.IO) {
        with(binding) {
            currentRequest = BinApiService
                .binApi
                .getInfo(numberCard)
                .apply {
                    enqueue(object : Callback<BankCardDTO> {
                        override fun onResponse(
                            call: Call<BankCardDTO>,
                            response: Response<BankCardDTO>
                        ) {
                            if (response.isSuccessful) {
                                val dataCard = response.body() ?: return
                                scheme.text = dataCard.scheme ?: "?"
                                type.text = dataCard.type ?: "?"
                                brand.text = dataCard.brand ?: "?"
                                prepaid.text = dataCard.prepaid.toString() ?: "?"
                                length.text = dataCard.number?.length ?: "?"
                                luhn.text = dataCard.number?.luhn.toString() ?: "?"
                                country.text = dataCard.country?.name ?: "?"
                                flag.text = dataCard.country?.emoji ?: "?"
                                bankName.text = dataCard.bank?.name ?: "?"
                                url.text = dataCard.bank?.url ?: "?"
                                phone.text = dataCard.bank?.phone ?: "?"

                                val latitude = dataCard.country?.latitude
                                val longitude = dataCard.country?.longitude
                                location.text = getString(R.string.location, latitude, longitude)

                            } else {
                                handleException(HttpException(response))
                            }
                        }

                        override fun onFailure(call: Call<BankCardDTO>, t: Throwable) {
                            if (!call.isCanceled) {
                                handleException(t)
                            }
                        }
                    })
                }
        }
    }

    private fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(this, e.message ?: "unknown error", Toast.LENGTH_SHORT).show()
    }
}