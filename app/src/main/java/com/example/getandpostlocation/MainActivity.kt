package com.example.getandpostlocation

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etname = findViewById<View>(R.id.ETname) as EditText
        val etloc = findViewById<View>(R.id.ETloc) as EditText
        val etsearch = findViewById<View>(R.id.ETsearch) as EditText
        val savebtn = findViewById<View>(R.id.btsave) as Button
        val showbtn = findViewById<View>(R.id.btshow) as Button

        savebtn.setOnClickListener {

            var user = UserDetails.Datum(Random.nextInt(0,100), etname.text.toString(),etloc.text.toString())

            addUserdata(user, onResult = {
                etname.setText("")
                etloc.setText("")
            })

        }
        showbtn.setOnClickListener {
            doGetListResources(etsearch.text.toString())
            etsearch.text.clear()
        }
        }

    private fun doGetListResources(name:String){
        val responseText = findViewById<TextView>(R.id.textView)
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        val call: Call<List<UserDetails.Datum>> = apiInterface!!.doGetListResources()

        call?.enqueue(object : Callback<List<UserDetails.Datum>> {
            override fun onResponse(
                call: Call<List<UserDetails.Datum>>,
                response: Response<List<UserDetails.Datum>>
            ) {
                Log.d("TAG", response.code().toString() + "")
                var displayResponse = ""
                val resource: List<UserDetails.Datum>? = response.body()
                val datumList = resource
                progressDialog.dismiss()

                for (datum in datumList!!) {
                    if(datum.name == name.capitalize()){
                        displayResponse = datum.location!!
                    }
                }
                responseText.text = displayResponse
            }

            override fun onFailure(call: Call<List<UserDetails.Datum>>, t: Throwable?) {
                call.cancel()
                progressDialog.dismiss()
            }
        })

    }
    private fun addUserdata(user: UserDetails.Datum, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<UserDetails.Datum> {
                override fun onResponse(
                    call: Call<UserDetails.Datum>,
                    response: Response<UserDetails.Datum>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<UserDetails.Datum>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
}