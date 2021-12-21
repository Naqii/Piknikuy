package com.example.piknikuy.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.adapter.HotelAdapter
import com.example.piknikuy.databinding.FragmentHotelBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.view.hotel.DetailHotel
import com.example.piknikuy.viewModel.HotelViewModel


class HotelFragment : Fragment() {
    private lateinit var hotelViewModel: HotelViewModel
    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hotelViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[HotelViewModel::class.java]

        hotelAdapter = HotelAdapter()
        hotelAdapter.setOnItemClickCallback(object : HotelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ModelHotel) {
                val moveIntent = Intent(requireContext(), DetailHotel::class.java)
                moveIntent.putExtra(DetailHotel.EXTRA_HOTEL, data.id)
                startActivity(moveIntent)
            }
        })

        binding.listHotel.adapter = hotelAdapter
        binding.listHotel.layoutManager = LinearLayoutManager(requireContext())
        hotelViewModel.setListHotel()
        hotelViewModel.listHotel.observe(requireActivity(), {  hotelItem ->
            if (hotelItem!= null){
                hotelAdapter.listHotel = hotelItem
                progressBarDisplay(false)
            }
        })
        super.onViewCreated(view, savedInstanceState)
        progressBarDisplay(true)
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.listHotel.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.listHotel.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}