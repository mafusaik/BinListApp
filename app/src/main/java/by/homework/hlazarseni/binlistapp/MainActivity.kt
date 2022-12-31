package by.homework.hlazarseni.binlistapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.homework.hlazarseni.binlistapp.api.BinApiService
import by.homework.hlazarseni.binlistapp.databinding.ActivityMainBinding
import by.homework.hlazarseni.binlistapp.extension.textChanges
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private var currentRequest: Call<BankCardDTO>? = null

    private val viewModel by inject<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            editTextNumber
                .textChanges()
                .debounce(500)
                .mapLatest { getData(it.toString()) }
                .launchIn(lifecycleScope)

            viewModel.databaseFlow
                .map { list ->
                val adapter = ArrayAdapter(this@MainActivity, R.layout.dropdown_item, list)
                editTextNumber.setAdapter(adapter)
                adapter.notifyDataSetChanged()
            }
                .launchIn(lifecycleScope)
        }
    }

    override fun onStop() {
        super.onStop()
        currentRequest?.cancel()
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
                                length.text = dataCard.number?.length ?: "?"
                                country.text = dataCard.country?.name ?: "?"
                                flag.text = dataCard.country?.emoji ?: "?"
                                bankName.text = dataCard.bank?.name ?: "?"
                                url.text = dataCard.bank?.url ?: "?"
                                phone.text = dataCard.bank?.phone ?: "?"

                                when (dataCard.prepaid) {
                                    true -> prepaid.text = getString(R.string.yes)
                                    false -> prepaid.text = getString(R.string.no)
                                    else -> prepaid.text = "?"
                                }

                                when (dataCard.number?.luhn) {
                                    true -> luhn.text = getString(R.string.yes)
                                    false -> luhn.text = getString(R.string.no)
                                    else -> luhn.text = "?"
                                }

                                val latitude = dataCard.country?.latitude
                                val longitude = dataCard.country?.longitude
                                location.text = getString(R.string.location, latitude, longitude)

                                location.setOnClickListener {
                                    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    mapIntent.resolveActivity(packageManager)?.let {
                                        startActivity(mapIntent)
                                    }
                                }

                                if (numberCard.length >= 6) {
                                    viewModel.insertNumberDB(numberCard)
                                }

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

    private fun handleException(e: Throwable) {
        Toast.makeText(this, e.message ?: "unknown error", Toast.LENGTH_SHORT).show()
    }
}