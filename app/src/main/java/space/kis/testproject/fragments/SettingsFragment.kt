package space.kis.testproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import space.kis.data.model.NightMode
import space.kis.testproject.databinding.FragmentSettingsBinding
import space.kis.testproject.manager.SharedPrefersManager

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager: SharedPrefersManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            appBar.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right, top = insets.top)
                insets
            }

            darkModeSwitch.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right)
                insets
            }

            when (prefsManager.nightMode) {
                NightMode.LIGHT -> darkModeSwitch.isChecked = false
                NightMode.DARK -> darkModeSwitch.isChecked = true
            }

            darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    prefsManager.nightMode = NightMode.DARK
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    prefsManager.nightMode = NightMode.LIGHT
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

fun View.applyInsetsWithAppBar(block: (View, Insets) -> Insets) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        block(v, insets)
        windowInsets
    }
}