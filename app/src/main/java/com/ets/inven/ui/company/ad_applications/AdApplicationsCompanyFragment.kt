package com.ets.inven.ui.company.ad_applications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.inven.databinding.FragmentAdApplicationsCompanyBinding
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.models.UserModel
import com.ets.inven.ui.company.ads.AdsCompanyFragmentDirections
import com.ets.inven.ui.individual.ads.AdsIndividualRecyclerViewAdapter
import com.ets.inven.util.GlobalData

class AdApplicationsCompanyFragment : Fragment() {
    private val args: AdApplicationsCompanyFragmentArgs by navArgs()
    private val viewModel: AdApplicationsCompanyViewModel by viewModels()

    private var _binding: FragmentAdApplicationsCompanyBinding? = null

    private var applications = arrayListOf<UserModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdApplicationsCompanyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adApplicationsCompanyTextTitle.text = args.ad.name

        viewModel.loadApplications(GlobalData.getToken()!!, args.ad.id)
        binding.adApplicationsCompanySwipeRefresh.isRefreshing = true

        binding.adApplicationsCompanySwipeRefresh.setOnRefreshListener {
            viewModel.loadApplications(GlobalData.getToken()!!, args.ad.id)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.adApplicationsCompanySwipeRefresh.isRefreshing = false

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.applications.observe(viewLifecycleOwner) {
            binding.adApplicationsCompanySwipeRefresh.isRefreshing = false

            applications.clear()
            applications.addAll(it)

            binding.adApplicationsCompanyRecyclerview.adapter?.notifyDataSetChanged()
        }

        binding.adApplicationsCompanyRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.adApplicationsCompanyRecyclerview.adapter = AdApplicationsCompanyRecyclerViewAdapter(requireContext(), applications) { application ->
            val action = AdApplicationsCompanyFragmentDirections.actionAdApplicationsCompanyToAdApplicationDetailsCompany(application)

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}