package com.example.piknikuy.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.adapter.WisataAdapter
import com.example.piknikuy.databinding.FragmentWisataBinding
import com.example.piknikuy.model.ModelWisata
import com.example.piknikuy.view.wisata.DetailWisata
import com.example.piknikuy.viewModel.WisataViewModel


class WisataFragment : Fragment() {
    private lateinit var wisataViewModel: WisataViewModel
    private var _binding: FragmentWisataBinding? = null
    private val binding get() = _binding!!
    private lateinit var wisataAdapter: WisataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWisataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wisataViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[WisataViewModel::class.java]

        wisataAdapter = WisataAdapter()
        wisataAdapter.setOnItemClickCallback(object : WisataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ModelWisata) {
                val moveIntent = Intent(requireContext(), DetailWisata::class.java)
                moveIntent.putExtra(DetailWisata.EXTRA_WISATA, data.id)
                startActivity(moveIntent)
            }
        })

        binding.listWisata.adapter = wisataAdapter
        binding.listWisata.layoutManager = LinearLayoutManager(requireContext())
        wisataViewModel.setListWisata()
        wisataViewModel.listWisata.observe(requireActivity(), { wisataItem ->
            if (wisataItem!= null){
                wisataAdapter.listWisata = wisataItem
                progressBarDisplay(false)
            }
        })
        super.onViewCreated(view, savedInstanceState)
        progressBarDisplay(true)
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.listWisata.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.listWisata.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}