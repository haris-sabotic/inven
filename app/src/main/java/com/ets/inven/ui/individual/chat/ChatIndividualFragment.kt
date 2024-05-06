package com.ets.inven.ui.individual.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ets.inven.databinding.FragmentChatIndividualBinding
import com.ets.inven.util.setPhoto

class ChatIndividualFragment : Fragment() {
    private val args: ChatIndividualFragmentArgs by navArgs()

    private var _binding: FragmentChatIndividualBinding? = null

    private var messages = mutableListOf<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatIndividualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val company = args.companyData

        binding.chatIndividualHeaderTextName.text = company.name
        setPhoto(requireContext(), company.photo, binding.chatIndividualHeaderImage)

        binding.chatIndividualRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.chatIndividualRecyclerView.adapter = ChatIndividualRecyclerViewAdapter(requireContext(), messages)

        binding.chatIndividualImageSend.setOnClickListener {
            sendMessage()
        }

        binding.chatIndividualEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage()

                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun sendMessage() {
        val message = binding.chatIndividualEditText.text.toString()
        if (message.trim().isEmpty()) {
            return
        }

        messages.add(message)
        binding.chatIndividualRecyclerView.adapter?.notifyItemInserted(messages.size)

        binding.chatIndividualEditText.text.clear()

        binding.chatIndividualRecyclerView.scrollToPosition(messages.size-1)
    }
}