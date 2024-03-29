package ec.edu.espe.pizzaplanetapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentSignupBinding
import ec.edu.espe.pizzaplanetapp.helper.BaseFragment

class SignupFragment : BaseFragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initClick(){
        binding.btnSignup.setOnClickListener { validateData() }
    }

    private fun validateData(){
        val email = binding.txtEmail.text.toString().trim()
        val password = binding.txtPassword.text.toString().trim()

        if(email.isNotEmpty()){
            if(password.isNotEmpty()){
                hideKeyboard()
                binding.progressBar.isVisible = true
                registerUser(email, password)
            }
            else{
                Toast.makeText(requireContext(),"Enter password", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(requireContext(),"Enter Email", Toast.LENGTH_LONG).show()
        }
    }

    private fun registerUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    //findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progressBar.isVisible = false
                }
            }
    }
}