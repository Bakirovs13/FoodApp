package com.example.foodapp.presentation.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FragmentFavoriteBinding
import com.example.foodapp.presentation.activities.main.MainActivity
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {
     lateinit var binding:FragmentFavoriteBinding
     private val favoriteAdapter by lazy { FavoriteAdapter() }

     private val viewModel by lazy{
         (activity as MainActivity).viewModel
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRV()
        observeFavoritesLiveData()

        val itemTouchHelper= object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentMeal = favoriteAdapter.differ.currentList[position]
                viewModel.deleteMeal(currentMeal)
               Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                   "Undo",
                   View.OnClickListener {
                      viewModel.insertMeal(currentMeal)
                   }
               ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoritesRv)
    }

    private fun prepareRV() {
        binding.favoritesRv.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        }
    }

    private fun observeFavoritesLiveData() {
        viewModel.observeFavoritesLiveData().observe(requireActivity()){favoriteMeals->
            favoriteAdapter.differ.submitList(favoriteMeals)
        }
    }


}