package space.kis.testproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.kis.domain.model.LceState
import space.kis.testproject.adapter.CountryListAdapter
import space.kis.testproject.databinding.FragmentDatabaseBinding
import space.kis.testproject.recyclerViewExt.SwipeToDeleteCallback
import space.kis.testproject.recyclerViewExt.addSpaceDecoration
import space.kis.testproject.viewmodels.DatabaseViewModel

class DatabaseFragment : Fragment() {

    private var _binding: FragmentDatabaseBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<DatabaseViewModel>()

    private val adapter by lazy {
        CountryListAdapter(
            itemClick = {
                findNavController().navigate(
                    directions = DatabaseFragmentDirections.actionListFromDatabaseFragmentToDetailsFragment(
                        it.name, it.capital, it.area.toString(), it.population.toString(), it.flag
                    )
                )
            },
            longItemClick = {
                Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDatabaseBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.lceDatabaseFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { lce ->
                    binding.progressCircular.isVisible = lce == LceState.Loading
                    when (lce) {
                        is LceState.Content -> {
                            lce.country.collect {
                                adapter.submitList(it)
                            }
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                view,
                                lce.throwable.message ?: "State_Error",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                        LceState.Loading -> {
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val countryToDel = adapter.currentList[viewHolder.bindingAdapterPosition]
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.onCountrySwiped(countryToDel)
                    }
                }
            }
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addSpaceDecoration(8)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}