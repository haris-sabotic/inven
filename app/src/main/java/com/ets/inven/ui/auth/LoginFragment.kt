package com.ets.inven.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ets.inven.MainActivity
import com.ets.inven.R
import com.ets.inven.databinding.FragmentLoginBinding
import com.ets.inven.ui.UserViewModel
import com.ets.inven.util.GlobalData

class LoginFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTextRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register_one)
        }

        binding.loginFormButtonRegister.setOnClickListener {
            binding.loginProgressBar.visibility = View.VISIBLE

            val email = binding.loginFormTextinputEmail.editText!!.text.toString()
            val password = binding.loginFormTextinputPassword.editText!!.text.toString()
            userViewModel.login(email, password)
        }

        userViewModel.token.observe(viewLifecycleOwner) {
            GlobalData.saveToken(requireContext(), it)
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.loginFormTextinputEmail.error = null
            binding.loginFormTextinputPassword.error = null
            binding.loginProgressBar.visibility = View.GONE

            if (userData.type == "individual") {
                // individual
                (requireActivity() as MainActivity).setIndividualMenu()
                findNavController().navigate(R.id.action_login_to_ads_individual)
            } else {
                // company
                (requireActivity() as MainActivity).setCompanyMenu()
                findNavController().navigate(R.id.action_login_to_ads_company)
            }
        }

        userViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.loginFormTextinputEmail.error = null
            binding.loginFormTextinputPassword.error = null
            binding.loginProgressBar.visibility = View.GONE

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        userViewModel.loginErrors.observe(viewLifecycleOwner) {
            binding.loginFormTextinputEmail.error = null
            binding.loginFormTextinputPassword.error = null
            binding.loginProgressBar.visibility = View.GONE

            if (it.email != null && it.email.size > 0) {
                binding.loginFormTextinputEmail.error = it.email[0]
            }
            if (it.password != null && it.password.size > 0) {
                binding.loginFormTextinputPassword.error = it.password[0]
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}