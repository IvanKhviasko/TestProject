package space.kis.testproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.kis.domain.model.LceState
import space.kis.testproject.R
import space.kis.testproject.adapter.CountryListAdapter
import space.kis.testproject.databinding.FragmentRetrofitBinding
import space.kis.testproject.viewmodels.RetrofitViewModel

class RetrofitFragment : Fragment() {

    private var _binding: FragmentRetrofitBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<RetrofitViewModel>()

    private val adapter by lazy {
        CountryListAdapter(itemClick = {
            findNavController().navigate(
                RetrofitFragmentDirections.actionListFromRetrofitFragmentToDetailsFragment(
                    it.name, it.capital, it.area.toString(), it.population.toString(), it.flag
                )
            )
        }, longItemClick = {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.addCountryToDb(
                    it.id,
                    it.name,
                    it.capital,
                    it.area,
                    it.population,
                    it.flag
                )
            }
            Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRetrofitBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.lceFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { lce ->
                    binding.progressCircular.isVisible = lce == LceState.Loading
                    when (lce) {
                        is LceState.Content -> {
                            adapter.submitList(lce.country)
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

            toolbar.menu.findItem(R.id.action_search)
                .let { it.actionView as SearchView }
                .setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel._searchQueryFlow.tryEmit(newText)
                        return true
                    }
                })

            viewModel.searchQueryFlow
                .onEach {
                    val list = viewModel.filterCountryList(it)
                    adapter.submitList(list)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}