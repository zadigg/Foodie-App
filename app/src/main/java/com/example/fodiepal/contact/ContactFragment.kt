package com.example.fodiepal.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fodiepal.R

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        val btnPhoneCall: Button = view.findViewById(R.id.btnPhoneCall)
        val btnSendEmail: Button = view.findViewById(R.id.btnSendEmail)

        btnPhoneCall.setOnClickListener {
            makePhoneCall()
        }

        btnSendEmail.setOnClickListener {
            sendEmail()
        }

        return view
    }

    private fun makePhoneCall() {
        val phoneNumber = "+16412339191"
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun sendEmail() {
        val email = "abel@abel.com"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        startActivity(intent)
    }
}
