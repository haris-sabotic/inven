package com.ets.inven.ui.ads_individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.inven.databinding.FragmentAdsIndividualBinding
import com.ets.inven.util.GlobalData

class AdsIndividualFragment : Fragment() {
    private val viewModel: AdsIndividualViewModel by activityViewModels()

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

        binding.adsIndividualRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.adsIndividualRecyclerview.adapter = AdsIndividualRecyclerViewAdapter(requireContext(), GlobalData.PLACEHOLDER_ADS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}