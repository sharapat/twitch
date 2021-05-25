package uz.arview.test.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.arview.test.R
import uz.arview.test.core.ResourceState
import uz.arview.test.core.onClick
import uz.arview.test.core.visibility
import uz.arview.test.databinding.FragmentTopGameBinding

class TopGameListFragment : Fragment(R.layout.fragment_top_game) {

    private val adapter = TopGameAdapter()
    private val viewModel: TopGameViewModel by viewModel()
    private lateinit var binding: FragmentTopGameBinding
    private lateinit var navController: NavController
    private var isLoading = false
    private val limit = 10
    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData(limit, offset)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopGameBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.apply {
            topGames.adapter = adapter
            val layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            topGames.layoutManager = layoutManager
            topGames.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                            loadData(limit, offset)
                        }
                    }
                }
            })
            viewModel.topGameListLocal.observe(viewLifecycleOwner, {
                when (it.status) {
                    ResourceState.LOADING -> progressBar.visibility(true)
                    ResourceState.SUCCESS -> {
                        progressBar.visibility(false)
                        adapter.games = it.data!!.toMutableList()
                    }
                    ResourceState.ERROR -> {
                        progressBar.visibility(false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModel.topGameListNetwork.observe(viewLifecycleOwner, {
                when (it.status) {
                    ResourceState.LOADING -> progressBar.visibility(true)
                    ResourceState.SUCCESS -> {
                        viewModel.insertToDatabase(it.data!!)
                        progressBar.visibility(false)
                        adapter.addGames(it.data)
                        offset += 2
                    }
                    ResourceState.ERROR -> {
                        viewModel.getTopGamesFromLocal()
                    }
                }
            })
            rateUs.onClick {
                navController.navigate(R.id.action_topGameListFragment_to_rateFragment)
            }
        }
    }

    private fun loadData(limit: Int, offset: Int) {
        viewModel.getTopGamesFromNetwork(limit, offset)
    }
}