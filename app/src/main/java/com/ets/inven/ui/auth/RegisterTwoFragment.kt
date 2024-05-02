package com.ets.inven.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ets.inven.MainActivity
import com.ets.inven.R
import com.ets.inven.databinding.FragmentRegisterTwoBinding
import com.ets.inven.ui.UserViewModel
import com.ets.inven.util.GlobalData

class RegisterTwoFragment : Fragment() {
    private val args: RegisterTwoFragmentArgs by navArgs()
    private val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentRegisterTwoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterTwoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerTwoFormButtonRegister.setOnClickListener {
            binding.registerProgressBar.visibility = View.VISIBLE

            val type = args.type
            val name = binding.registerTwoFormTextinputName.editText!!.text.toString()
            val email = binding.registerTwoFormTextinputEmail.editText!!.text.toString()
            val password = binding.registerTwoFormTextinputPassword.editText!!.text.toString()
            userViewModel.register(type, name, email, password)
        }

        userViewModel.token.observe(viewLifecycleOwner) {
            GlobalData.saveToken(requireContext(), it)
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.registerTwoFormTextinputName.error = null
            binding.registerTwoFormTextinputEmail.error = null
            binding.registerTwoFormTextinputPassword.error = null
            binding.registerProgressBar.visibility = View.GONE

            if (userData.type == "individual") {
                // individual
                (requireActivity() as MainActivity).setIndividualMenu()
                findNavController().navigate(R.id.action_register_two_to_ads_individual)
            } else {
                // company
                (requireActivity() as MainActivity).setCompanyMenu()
                findNavController().navigate(R.id.action_register_two_to_ads_company)
            }
        }

        userViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.registerTwoFormTextinputName.error = null
            binding.registerTwoFormTextinputEmail.error = null
            binding.registerTwoFormTextinputPassword.error = null
            binding.registerProgressBar.visibility = View.GONE

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        userViewModel.registerErrors.observe(viewLifecycleOwner) {
            binding.registerTwoFormTextinputName.error = null
            binding.registerTwoFormTextinputEmail.error = null
            binding.registerTwoFormTextinputPassword.error = null
            binding.registerProgressBar.visibility = View.GONE

            if (it.name != null && it.name.size > 0) {
                binding.registerTwoFormTextinputName.error = it.name[0]
            }
            if (it.email != null && it.email.size > 0) {
                binding.registerTwoFormTextinputEmail.error = it.email[0]
            }
            if (it.password != null && it.password.size > 0) {
                binding.registerTwoFormTextinputPassword.error = it.password[0]
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}