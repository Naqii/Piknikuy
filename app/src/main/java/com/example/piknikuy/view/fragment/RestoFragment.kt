package com.example.piknikuy.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.adapter.RestoAdapter
import com.example.piknikuy.databinding.FragmentRestoBinding
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.view.resto.DetailResto
import com.example.piknikuy.viewModel.RestoViewModel


class RestoFragment : Fragment() {
    private lateinit var restoViewModel: RestoViewModel
    private var _binding: FragmentRestoBinding? = null
    private val binding get() = _binding!!
    private lateinit var restoAdapter: RestoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        restoViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[RestoViewModel::class.java]

        restoAdapter = RestoAdapter()
        restoAdapter.setOnItemClickCallback(object : RestoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ModelResto) {
                val moveIntent = Intent(requireContext(), DetailResto::class.java)
                moveIntent.putExtra(DetailResto.EXTRA_RESTO, data.id)
                startActivity(moveIntent)
            }
        })

        binding.listResto.adapter = restoAdapter
        binding.listResto.layoutManager = LinearLayoutManager(requireContext())
        restoViewModel.setListResto()
        restoViewModel.listResto.observe(requireActivity(), { restoItem ->
            if (restoItem!= null){
                restoAdapter.listResto = restoItem
                progressBarDisplay(false)
            }
        })
        super.onViewCreated(view, savedInstanceState)
        progressBarDisplay(true)
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.listResto.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.listResto.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}