package com.ets.inven.ui.company.ads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.inven.R
import com.ets.inven.databinding.FragmentAdsCompanyBinding
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.ui.individual.ads.AdsIndividualFragmentDirections
import com.ets.inven.ui.individual.ads.AdsIndividualRecyclerViewAdapter
import com.ets.inven.util.GlobalData

class AdsCompanyFragment : Fragment() {
    private val viewModel: AdsCompanyViewModel by activityViewModels()

    private var jobChipSelected = true
    private var volunteeringChipSelected = true

    private var ads = arrayListOf<AdPreviewModel>()

    private var _binding: FragmentAdsCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdsCompanyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAds()

        binding.adsCompanySwiperefresh.setOnRefreshListener {
            loadAds()
        }

        binding.adsCompanyFiltersChipJobs.setOnCheckedChangeListener { _, isChecked ->
            jobChipSelected = isChecked
            loadAds()
        }
        binding.adsCompanyFiltersChipVolunteering.setOnCheckedChangeListener { _, isChecked ->
            volunteeringChipSelected = isChecked
            loadAds()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.adsCompanySwiperefresh.isRefreshing = false

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.ads.observe(viewLifecycleOwner) {
            binding.adsCompanySwiperefresh.isRefreshing = false

            ads.clear()
            ads.addAll(it)

            binding.adsCompanyRecyclerview.adapter?.notifyDataSetChanged()
        }

        binding.adsCompanyRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.adsCompanyRecyclerview.adapter = AdsIndividualRecyclerViewAdapter(requireContext(), ads) { ad ->
            val action = AdsCompanyFragmentDirections.actionAdsCompanyToAdApplicationsCompany(ad)

            findNavController().navigate(action)
        }

        binding.adsCompanyFab.setOnClickListener {
            findNavController().navigate(R.id.action_ads_company_to_new_ad)
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

        Log.d("SELECTED_TYPES", selectedTypes.joinToString(","))
        viewModel.loadAds(GlobalData.getToken()!!, selectedTypes.joinToString(","))
        binding.adsCompanySwiperefresh.isRefreshing = true
    }
}