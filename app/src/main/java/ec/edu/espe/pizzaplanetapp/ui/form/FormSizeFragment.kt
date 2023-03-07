package ec.edu.espe.pizzaplanetapp.ui.form

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentFormSizeBinding
import ec.edu.espe.pizzaplanetapp.helper.BaseFragment
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import ec.edu.espe.pizzaplanetapp.model.Size


class FormSizeFragment : BaseFragment() {

    private val args: FormSizeFragmentArgs by navArgs()

    private var _binding: FragmentFormSizeBinding? = null
    private val binding get() = _binding!!

    private lateinit var size: Size
    private var newSize: Boolean = true
    private var stateSize: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormSizeBinding.inflate(inflater,container,false)
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

                if(newSize) size = Size()
                size.name = name
                size.description = description
                size.unitValue = unitValue.toDouble()
                size.state = state.toString()

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
        args.size.let {
            if (it != null) {
                size = it
                configTask()
            }
        }
    }

    private fun configTask(){
        newSize = false
        stateSize = size.state
        binding.txtToolbar.text = R.string.edit_size.toString()

        binding.txtName.setText(size.name)
        binding.txtUnitValue.setText(size.unitValue.toString())
        binding.txtDescription.setText(size.description)
    }

    private fun saveIngredient(){
        FirebaseHelper
            .getDataBase()
            .child("size")
            .child(size.id)
            .setValue(size)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    if(newSize){// add task
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