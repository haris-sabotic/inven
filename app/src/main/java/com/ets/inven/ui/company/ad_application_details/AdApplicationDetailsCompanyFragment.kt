package com.ets.inven.ui.company.ad_application_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ets.inven.databinding.FragmentAdApplicationDetailsCompanyBinding
import com.ets.inven.util.GlobalData
import com.ets.inven.util.setPhoto

class AdApplicationDetailsCompanyFragment : Fragment() {
    private val args: AdApplicationDetailsCompanyFragmentArgs by navArgs()

    private var _binding: FragmentAdApplicationDetailsCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdApplicationDetailsCompanyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = args.application

        binding.adApplicationDetailsCompanyTextTitle.text = application.name
        binding.adApplicationDetailsCompanyTextAbout.text = application.about

        setPhoto(requireContext(), application.photo, binding.adApplicationDetailsCompanyImage)

        binding.adApplicationDetailsCompanyLayoutEmail.setOnClickListener {
            val subject = ""
            val body = ""

            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:${application.email}?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body))
            intent.setData(data)
            startActivity(intent)
        }

        binding.adApplicationDetailsCompanyLayoutPhone.setOnClickListener {
            if (application.phone != null) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:${application.phone}"))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Ovaj korisnik nije postavio broj telefona.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.adApplicationDetailsCompanyLayoutCv.setOnClickListener {
            if (application.cv != null) {
                val url = GlobalData.STORAGE_PREFIX + application.cv
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            } else {
                Toast.makeText(requireContext(), "Ovaj korisnik nije postavio CV.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}