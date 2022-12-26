package by.homework.hlazarseni.binlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.homework.hlazarseni.binlistapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            BinApiService
                .binApi
                .getInfo("45717360")
                .apply {
                    enqueue(object : Callback<BankCardDTO> {
                        override fun onResponse(
                            call: Call<BankCardDTO>,
                            response: Response<BankCardDTO>
                        ) {
                            if (response.isSuccessful) {
                                val dataCard = response.body() ?: return
                                scheme.text = dataCard.scheme
                                type.text = dataCard.type
                                brand.text = dataCard.brand
                                prepaid.text = dataCard.prepaid.toString()
                                length.text = dataCard.number?.length
                                luhn.text = dataCard.number?.luhn.toString()
                                country.text = dataCard.country?.name
                                flag.text = dataCard.country?.emoji
                                bankName.text = dataCard.bank?.name
                                url.text = dataCard.bank?.url
                                phone.text = dataCard.bank?.phone
                                location.text =
                                    dataCard.country?.latitude + " " + dataCard.country?.longitude
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