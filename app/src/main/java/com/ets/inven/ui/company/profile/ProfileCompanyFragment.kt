package com.ets.inven.ui.company.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ets.inven.api.requests.EditUserRequest
import com.ets.inven.databinding.FragmentProfileCompanyBinding
import com.ets.inven.ui.UserViewModel
import com.ets.inven.util.GlobalData
import com.ets.inven.util.setPhoto
import com.ets.inven.util.showTextInputDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileCompanyFragment : Fragment() {
    val userViewModel: UserViewModel by activityViewModels()

    private val pickPhoto = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val inputStream = requireContext().contentResolver.openInputStream(uri)!!

            CoroutineScope(Dispatchers.IO).launch {
                val photo = MultipartBody.Part
                    .createFormData(
                        "photo",
                        "photo",
                        inputStream.readBytes()
                            .toRequestBody(
                                "image/*".toMediaTypeOrNull(),
                            )
                    )

                userViewModel.editPhoto(GlobalData.getToken()!!, photo)
            }
            binding.profileCompanySwiperefresh.isRefreshing = true
        }
    }


    private var _binding: FragmentProfileCompanyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileCompanyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.profileCompanySwiperefresh.isRefreshing = false

            binding.profileCompanyImageEditName.visibility = View.VISIBLE

            binding.profileCompanyTextName.text = userData.name
            binding.profileCompanyTextPhone.text = userData.phone ?: "--- --- ---"
            binding.profileCompanyTextEmail.text = userData.email
            binding.profileCompanyTextAbout.text = userData.about
            setPhoto(requireContext(), userData.photo, binding.profileCompanyImage)
        }

        binding.profileCompanySwiperefresh.setOnRefreshListener {
            userViewModel.loadUserData(GlobalData.getToken()!!)
        }

        binding.profileCompanyImageParent.setOnClickListener {
            pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.profileCompanyLayoutPhone.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni broj telefona", EditorInfo.TYPE_CLASS_PHONE) { phone ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  null, phone, null))
                binding.profileCompanySwiperefresh.isRefreshing = true
            }
        }

        binding.profileCompanyLayoutEmail.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni email", EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) { email ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  email, null, null))
                binding.profileCompanySwiperefresh.isRefreshing = true
            }
        }

        binding.profileCompanyTextAbout.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni tekst o sebi", EditorInfo.TYPE_CLASS_TEXT, 3) { about ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  null, null, about))
                binding.profileCompanySwiperefresh.isRefreshing = true
            }
        }

        binding.profileCompanyImageEditName.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni ime", EditorInfo.TYPE_CLASS_TEXT) { name ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(name,  null, null, null))
                binding.profileCompanySwiperefresh.isRefreshing = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}