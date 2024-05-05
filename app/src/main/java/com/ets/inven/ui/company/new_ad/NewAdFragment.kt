package com.ets.inven.ui.company.new_ad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ets.inven.databinding.FragmentNewAdBinding
import com.ets.inven.util.GlobalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class NewAdFragment : Fragment() {
    private val viewModel: NewAdViewModel by viewModels()

    private var pickedPhoto: MultipartBody.Part? = null

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

                pickedPhoto = photo
            }

            Glide.with(requireContext())
                .load(uri)
                .centerCrop()
                .into(binding.newAddImage)
        }
    }


    private var _binding: FragmentNewAdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewAdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.newAdProgressBar.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.done.observe(viewLifecycleOwner) { done ->
            if (done) {
                binding.newAdProgressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Oglas kreiran", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        }

        binding.newAddImageParent.setOnClickListener {
            pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.newAddButtonSubmit.setOnClickListener {
            val type = if (binding.newAddRadioTypeJob.isChecked) "job" else "volunteering"
            val name = binding.newAddTextinputName.editText!!.text.toString()
            val description = binding.newAddTextinputDescription.editText!!.text.toString()
            val maxAvailablePositions = binding.newAddSlider.value.toInt()
            val photo = pickedPhoto

            viewModel.createAd(GlobalData.getToken()!!, type, name, description, maxAvailablePositions, photo)
            binding.newAdProgressBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}