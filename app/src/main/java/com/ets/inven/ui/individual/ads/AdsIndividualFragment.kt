package com.ets.inven.ui.individual.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.inven.databinding.FragmentAdsIndividualBinding
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.util.GlobalData

class AdsIndividualFragment : Fragment() {
    private val viewModel: AdsIndividualViewModel by activityViewModels()

    private var jobChipSelected = true
    private var volunteeringChipSelected = true

    private var ads = arrayListOf<AdPreviewModel>()

    private var _binding: FragmentAdsIndividualBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdsIndividualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAds()

        binding.adsIndividualSwiperefresh.setOnRefreshListener {
            loadAds()
        }

        binding.adsIndividualFiltersChipJobs.setOnCheckedChangeListener { _, isChecked ->
            jobChipSelected = isChecked
            loadAds()
        }
        binding.adsIndividualFiltersChipVolunteering.setOnCheckedChangeListener { _, isChecked ->
            volunteeringChipSelected = isChecked
            loadAds()
        }

        viewModel.ads.observe(viewLifecycleOwner) {
            binding.adsIndividualSwiperefresh.isRefreshing = false

            ads.clear()
            ads.addAll(it)

            binding.adsIndividualRecyclerview.adapter?.notifyDataSetChanged()
        }

        binding.adsIndividualRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.adsIndividualRecyclerview.adapter = AdsIndividualRecyclerViewAdapter(requireContext(), ads) { ad ->
            val action = AdsIndividualFragmentDirections.actionAdsIndividualToAdDetailsIndividual(ad.id)

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadAds() {
        val selectedTypes = arrayListOf<String>()
        if (jobChipSelected) {
            selectedTypes.add("job")
        }
        if (volunteeringChipSelected) {
            selectedTypes.add("volunteering")
        }

        viewModel.loadAds(selectedTypes.joinToString(","))
        binding.adsIndividualSwiperefresh.isRefreshing = true
    }
}