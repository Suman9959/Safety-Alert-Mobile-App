package com.kroger.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kroger.classapp.ui.adapter.EmergencyHistoryAdapter
import com.kroger.classapp.databinding.FragmentHistoryBinding
import com.kroger.classapp.viewmodel.EmergencyHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmergencyHistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: EmergencyHistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emergencyEvents = historyViewModel.getAllHistory()
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EmergencyHistoryAdapter(emergencyEvents)
        }
    }
}