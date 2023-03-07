package ec.edu.espe.pizzaplanetapp.ui.form

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentFormIngredientBinding
import ec.edu.espe.pizzaplanetapp.helper.BaseFragment
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import ec.edu.espe.pizzaplanetapp.model.Ingredient


class FormIngredientFragment : BaseFragment() {

    private val args: FormIngredientFragmentArgs by navArgs()

    private var _binding: FragmentFormIngredientBinding? = null
    private val binding get() = _binding!!

    private lateinit var ingredient: Ingredient
    private var newIngredient: Boolean = true
    private var stateIngredient: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormIngredientBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        getArgs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private  fun initListeners(){
        binding.btnSave.setOnClickListener { validateData() }
    }

    private fun validateData(){
        val name = binding.txtName.text.toString().trim()
        val unitValue = binding.txtUnitValue.text.toString().trim()
        val description = binding.txtDescription.text.toString().trim()
        var state: String? = null

        binding.spnState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {
                state = adapterView.getItemAtPosition(position) as String
                Log.d("Spinner", "Valor seleccionado: $state")
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                state = null
            }
        }

        if(name.isNotEmpty()){
            if (unitValue.isNotEmpty()){
                hideKeyboard()
                binding.progressBar.isVisible = true

                if(newIngredient) ingredient = Ingredient()
                ingredient.name = name
                ingredient.description = description
                ingredient.unitValue = unitValue.toDouble()
                ingredient.state = state.toString()

                saveIngredient()
            }else{
                Toast.makeText(requireContext(), "Ingresar un Valor", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(requireContext(), "Ingresar un Nombre", Toast.LENGTH_LONG).show()
        }
    }

    private fun getArgs() {
        args.ingredient.let {
            if (it != null) {
                ingredient = it
                configTask()
            }
        }
    }

    private fun configTask(){
        newIngredient = false
        stateIngredient = ingredient.state
        binding.txtToolbar.text = R.string.edit_ingredient.toString()

        binding.txtName.setText(ingredient.name)
        binding.txtUnitValue.setText(ingredient.unitValue.toString())
        binding.txtDescription.setText(ingredient.description)

    }

    private fun saveIngredient(){
        FirebaseHelper
            .getDataBase()
            .child("ingredient")
            .child(ingredient.id)
            .setValue(ingredient)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    if(newIngredient){// add task
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Guardado exitosamente", Toast.LENGTH_LONG).show()
                    }
                    else{ // Edit task
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), "Actualizado exitosamente", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_LONG).show()
            }
    }

}