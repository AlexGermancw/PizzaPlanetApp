package ec.edu.espe.pizzaplanetapp.ui.customizer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentCustomizeIngredientBinding
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import ec.edu.espe.pizzaplanetapp.model.Ingredient
import ec.edu.espe.pizzaplanetapp.model.Size
import ec.edu.espe.pizzaplanetapp.ui.adapter.IngredientShopAdapter


class CustomizeIngredientFragment : Fragment(), IngredientShopAdapter.OnItemClickListener {

    private var _binding: FragmentCustomizeIngredientBinding? = null
    private val binding get() = _binding!!

    private lateinit var ingredientAdapter: IngredientShopAdapter
    private val args: CustomizeIngredientFragmentArgs by navArgs()
    private lateinit var size: Size

    private val ingredientList = mutableListOf<Ingredient>()
    private val ingredientSelectList = mutableListOf<Ingredient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizeIngredientBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
        getIngredient()
        getArgs()
    }

    private fun initClick(){
        binding.btnNext.setOnClickListener {
            selectIngredient()
            if(ingredientSelectList.isNotEmpty()){
                val action = CustomizeIngredientFragmentDirections
                    .actionCustomizeIngredientFragmentToCustomizeBillFragment(size,ingredientList.toTypedArray())
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(), "Seleccione uno o mas ingredientes", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getIngredient(){
        FirebaseHelper
            .getDataBase()
            .child("ingredient")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        ingredientList.clear()
                        for (snap in snapshot.children) {
                            val ingredient = snap.getValue(Ingredient::class.java) as Ingredient
                            if(ingredient.state != "inactivo") {
                                ingredientList.add(ingredient)
                            }
                        }
                        ingredientList.sortBy { it.name }
                        initAdapter()
                    }
                    else{
                        binding.txtLoading.text = "No se encontró ningun tamaño"
                    }
                    taskEmpty()
                    binding.progressBar.isVisible = false
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error en la Lista de Tamaño", Toast.LENGTH_LONG).show()

                }
            })
    }

    private fun initAdapter(){
        binding.rvIngredient.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIngredient.setHasFixedSize(true)
        ingredientAdapter = IngredientShopAdapter(requireContext(), ingredientList)
        ingredientAdapter.setOnItemClickListener(this)
        binding.rvIngredient.adapter = ingredientAdapter
        binding.rvIngredient.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    private fun taskEmpty(){
        binding.txtLoading.text = if(ingredientList.isEmpty()){
            getText(R.string.no_size)
        }else{
            ""
        }
    }

    private fun selectIngredient(){
        val recyclerView = binding.rvIngredient
        val adapter = recyclerView.adapter as IngredientShopAdapter
        val count = adapter.itemCount
        for (i in 0 until count) {
            val holder = recyclerView.findViewHolderForAdapterPosition(i) as IngredientShopAdapter.MyViewHolder
            val editText = holder.binding.lblSelect.text.toString()
            if (editText.equals("1")){
                ingredientSelectList.add(adapter.getItem(i))
            }
        }

    }

    override fun onItemClick(valueSize: Double) {
        binding.txtTotal.text = "$ " + String.format("%.2f", valueSize+size.unitValue)
    }

    private fun getArgs() {
        args.size.let {
            if (it != null) {
                size = it
                binding.txtTotal.text = "$ " + String.format("%.2f", size.unitValue)
            }
        }
    }
}