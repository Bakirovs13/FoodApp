package com.example.foodapp.presentation.fragments.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.presentation.activities.main.MainActivity
import com.example.foodapp.presentation.fragments.favorite.FavoriteAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    private val viewModel by lazy {
        (activity as MainActivity).viewModel
    }
    private val searchAdapter by lazy { FavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRV()
        setListeners()
        observeLiveData()


        var searchJob: Job? = null
        binding.searchEt.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMealByName(it.toString())
            }
        }

    }

    private fun observeLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner){
            searchAdapter.differ.submitList(it)
            Log.d("et",it.toString())
        }
    }

    private fun setListeners() {
        binding.arrowSearchImg.setOnClickListener {
            searchMeals()
        }
    }

    private fun searchMeals() {
       val searchQuery = binding.searchEt.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMealByName(searchQuery)
        }

    }

    private fun prepareRV() {
        binding.searchRv.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        }
    }
}