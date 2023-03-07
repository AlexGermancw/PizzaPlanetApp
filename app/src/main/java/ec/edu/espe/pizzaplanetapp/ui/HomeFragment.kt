package ec.edu.espe.pizzaplanetapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentHomeBinding
import ec.edu.espe.pizzaplanetapp.databinding.FragmentLoginBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClick(){
        binding.btnIngredient.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormIngredientFragment(null)
            findNavController().navigate(action)
        }

        binding.btnSize.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormSizeFragment(null)
            findNavController().navigate(action)
        }

        binding.btnPersonalizar.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToCustomizeSizeFragment()
            findNavController().navigate(action)
        }
    }

    /*private fun loguotApp(){
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_authentication)
    }*/

}