package ec.edu.espe.pizzaplanetapp.ui.customizer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.FragmentCustomizeSizeBinding
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import ec.edu.espe.pizzaplanetapp.model.Size
import ec.edu.espe.pizzaplanetapp.ui.HomeFragmentDirections
import ec.edu.espe.pizzaplanetapp.ui.adapter.SizeShopAdapter


class CustomizeSizeFragment : Fragment(), SizeShopAdapter.OnItemClickListener {

    private var _binding: FragmentCustomizeSizeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sizeAdapter: SizeShopAdapter

    private val sizeList = mutableListOf<Size>()
    private lateinit var sizeSelect: Size

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizeSizeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
        getSize()
    }

    private fun initClick(){
        binding.btnNext.setOnClickListener {
            selectSize()
            if(::sizeSelect.isInitialized){
                val action = CustomizeSizeFragmentDirections
                    .actionCustomizeSizeFragmentToCustomizeIngredientFragment(sizeSelect)
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(), "Seleccione un Tamaño", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getSize(){
        FirebaseHelper
            .getDataBase()
            .child("size")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        sizeList.clear()
                        for (snap in snapshot.children) {
                            val size = snap.getValue(Size::class.java) as Size
                            if(size.state != "inactivo") {
                                sizeList.add(size)
                            }
                        }
                        sizeList.reverse()
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
        binding.rvSize.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSize.setHasFixedSize(true)
        sizeAdapter = SizeShopAdapter(requireContext(), sizeList)
        sizeAdapter.setOnItemClickListener(this)
        binding.rvSize.adapter = sizeAdapter
        binding.rvSize.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    private fun taskEmpty(){
        binding.txtLoading.text = if(sizeList.isEmpty()){
            getText(R.string.no_size)
        }else{
            ""
        }
    }

    private fun selectSize(){

        val recyclerView = binding.rvSize
        val adapter = recyclerView.adapter as SizeShopAdapter
        val count = adapter.itemCount
        val a =""
        for (i in 0 until count) {
            val holder = recyclerView.findViewHolderForAdapterPosition(i) as SizeShopAdapter.MyViewHolder
            val editText = holder.binding.lblSelect.text.toString()
            if (editText.equals("1")){
                sizeSelect = adapter.getItem(i)
                break
            }
        }
    }

    override fun onItemClick(valueSize: Double) {
        binding.txtTotal.text = "$ " + String.format("%.2f", valueSize)
    }

}