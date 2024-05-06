package com.ets.inven.ui.individual.company_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ets.inven.databinding.FragmentCompanyDetailsIndividualBinding
import com.ets.inven.util.setPhoto


class CompanyDetailsIndividualFragment : Fragment() {
    private val args: CompanyDetailsIndividualFragmentArgs by navArgs()

    private var _binding: FragmentCompanyDetailsIndividualBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDetailsIndividualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val company = args.companyData

        binding.companyDetailsIndividualTextTitle.text = company.name
        binding.companyDetailsIndividualTextAbout.text = company.about

        setPhoto(requireContext(), company.photo, binding.companyDetailsIndividualImage)

        binding.companyDetailsIndividualLayoutChat.setOnClickListener {
            val action = CompanyDetailsIndividualFragmentDirections.actionCompanyDetailsIndividualToChatIndividual(company)

            findNavController().navigate(action)
        }

        binding.companyDetailsIndividualLayoutEmail.setOnClickListener {
            val subject = ""
            val body = ""

            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:${company.email}?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body))
            intent.setData(data)
            startActivity(intent)
        }

        binding.companyDetailsIndividualLayoutPhone.setOnClickListener {
            if (company.phone != null) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:${company.phone}"))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Ova kompanija nije postavila broj telefona.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}