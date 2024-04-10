package com.ets.inven.ui.company_details_individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ets.inven.databinding.FragmentCompanyDetailsIndividualBinding

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
        binding.companyDetailsIndividualTextAbout.text = company.description

        Glide.with(requireContext())
            .load(company.photo)
            .centerInside()
            .into(binding.companyDetailsIndividualImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}