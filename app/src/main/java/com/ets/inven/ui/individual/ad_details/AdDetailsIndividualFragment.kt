package com.ets.inven.ui.individual.ad_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ets.inven.databinding.FragmentAdDetailsIndividualBinding

class AdDetailsIndividualFragment : Fragment() {
    private val viewModel: AdDetailsIndividualViewModel by viewModels()

    private val args: AdDetailsIndividualFragmentArgs by navArgs()

    private var _binding: FragmentAdDetailsIndividualBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdDetailsIndividualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAd(args.adId)
        binding.adDetailsIndividualSwipeRefresh.isRefreshing = true

        binding.adDetailsIndividualSwipeRefresh.setOnRefreshListener {
            viewModel.loadAd(args.adId)
        }

        viewModel.ad.observe(viewLifecycleOwner) { ad ->
            binding.adDetailsIndividualSwipeRefresh.isRefreshing = false

            binding.adDetailsIndividualTextTitle.text = ad.name
            binding.adDetailsIndividualTextAbout.text = ad.description
            binding.adDetailsIndividualInfoTextFreePositions.text = ad.freePositions.toString()
            binding.adDetailsIndividualInfoTextCompany.text = ad.company.name

            Glide.with(requireContext())
                .load(ad.photo)
                .centerCrop()
                .into(binding.adDetailsIndividualImage)

            binding.adDetailsIndividualInfoTextCompany.setOnClickListener {
                val action =
                    AdDetailsIndividualFragmentDirections.actionAdDetailsIndividualToCompanyDetailsIndividual(
                        ad.company
                    )

                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}