package ec.edu.espe.pizzaplanetapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    //private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Handler(Looper.getMainLooper()).postDelayed(this::checkAuth,3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun checkAuth(){
        auth = Firebase.auth
        if(auth.currentUser == null) {
            findNavController().navigate(R.id.action_splashFragment_to_authentication)
        }
        else{
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }*/

}