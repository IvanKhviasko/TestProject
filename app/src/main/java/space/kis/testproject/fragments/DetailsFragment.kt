package space.kis.testproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import space.kis.testproject.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Destroyed" }

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())

        with(binding) {
            val stringBuilder = StringBuilder()
            stringBuilder.append(args.name)
            stringBuilder.append("\n")
            stringBuilder.append("capital: ")
            stringBuilder.append(args.capital)
            stringBuilder.append("\n")
            stringBuilder.append("area: ")
            stringBuilder.append(args.area)
            stringBuilder.append(" km2")
            stringBuilder.append("\n")
            stringBuilder.append("population: ")
            stringBuilder.append(args.population)
            infoDetails.text = stringBuilder
            imageDetails.load(args.flag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}