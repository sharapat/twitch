package uz.arview.test.ui.rate

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.arview.test.R
import uz.arview.test.core.onClick
import uz.arview.test.databinding.FragmentRateBinding

class RateFragment : Fragment(R.layout.fragment_rate) {
    
    private lateinit var binding: FragmentRateBinding
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRateBinding.bind(view)
        binding.sendFeedback.onClick {
            Toast.makeText(requireContext(), "Feedback sent, thank you!", Toast.LENGTH_SHORT).show()
        }
    }
}