package com.ets.inven.ui.individual.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ets.inven.api.requests.EditUserRequest
import com.ets.inven.databinding.FragmentProfileIndividualBinding
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

class ProfileIndividualFragment : Fragment() {
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
            binding.profileIndividualSwiperefresh.isRefreshing = true
        }
    }

    private val pickCV = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val inputStream = requireContext().contentResolver.openInputStream(uri)!!

            CoroutineScope(Dispatchers.IO).launch {
                val cv = MultipartBody.Part
                    .createFormData(
                        "cv",
                        "cv",
                        inputStream.readBytes()
                            .toRequestBody(
                                "*/*".toMediaTypeOrNull(),
                            )
                    )

                userViewModel.editCV(GlobalData.getToken()!!, cv) {
                    Toast.makeText(requireContext(), "CV ažuriran.", Toast.LENGTH_SHORT).show()
                }
            }
            binding.profileIndividualSwiperefresh.isRefreshing = true
        }
    }

    private var _binding: FragmentProfileIndividualBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileIndividualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileIndividualTextLogOut.setOnClickListener {
            GlobalData.saveToken(requireContext(), null)

            // restart
            val ctx: Context = requireActivity().applicationContext
            val pm = ctx.packageManager
            val intent = pm.getLaunchIntentForPackage(ctx.packageName)
            val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
            ctx.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.profileIndividualSwiperefresh.isRefreshing = false

            binding.profileIndividualImageEditName.visibility = View.VISIBLE

            binding.profileIndividualTextName.text = userData.name
            binding.profileIndividualTextPhone.text = userData.phone ?: "--- --- ---"
            binding.profileIndividualTextEmail.text = userData.email
            binding.profileIndividualTextAbout.text = userData.about
            setPhoto(requireContext(), userData.photo, binding.profileIndividualCircleimage)
        }

        userViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.profileIndividualSwiperefresh.isRefreshing = false

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.profileIndividualSwiperefresh.setOnRefreshListener {
            userViewModel.loadUserData(GlobalData.getToken()!!)
        }

        binding.profileIndividualCircleimage.setOnClickListener {
            pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.profileIndividualLayoutCv.setOnClickListener {
            pickCV.launch("*/*")
        }

        binding.profileIndividualLayoutPhone.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni broj telefona", EditorInfo.TYPE_CLASS_PHONE) { phone ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  null, phone, null))
                binding.profileIndividualSwiperefresh.isRefreshing = true
            }
        }

        binding.profileIndividualLayoutEmail.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni email", EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) { email ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  email, null, null))
                binding.profileIndividualSwiperefresh.isRefreshing = true
            }
        }

        binding.profileIndividualTextAbout.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni tekst o sebi", EditorInfo.TYPE_CLASS_TEXT, 3) { about ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(null,  null, null, about))
                binding.profileIndividualSwiperefresh.isRefreshing = true
            }
        }

        binding.profileIndividualImageEditName.setOnClickListener {
            showTextInputDialog(requireContext(), "Promijeni ime", EditorInfo.TYPE_CLASS_TEXT) { name ->
                userViewModel.edit(GlobalData.getToken()!!, EditUserRequest(name,  null, null, null))
                binding.profileIndividualSwiperefresh.isRefreshing = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}