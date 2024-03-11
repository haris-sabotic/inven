package com.ets.inven.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ets.inven.R
import com.ets.inven.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()

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
            findNavController().navigate(R.id.action_login_to_jobs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}