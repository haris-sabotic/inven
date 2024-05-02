package com.ets.inven.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ets.inven.MainActivity
import com.ets.inven.R
import com.ets.inven.databinding.FragmentSplashBinding
import com.ets.inven.util.GlobalData

class SplashFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalData.loadTokenFromSharedPrefs(requireContext())
        val token = GlobalData.getToken()

        if (token == null) {
            findNavController().navigate(R.id.action_splash_to_login)
        } else {
            userViewModel.loadUserData(token)
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            if (userData.type == "individual") {
                // individual
                (requireActivity() as MainActivity).setIndividualMenu()
                findNavController().navigate(R.id.action_splash_to_ads_individual)
            } else {
                // company
                (requireActivity() as MainActivity).setCompanyMenu()
                findNavController().navigate(R.id.action_splash_to_ads_company)
            }
        }

        userViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}