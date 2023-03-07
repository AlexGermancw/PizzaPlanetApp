package ec.edu.espe.pizzaplanetapp.ui.customizer

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ec.edu.espe.pizzaplanetapp.databinding.FragmentCustomizeBillBinding
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import ec.edu.espe.pizzaplanetapp.model.Bill
import ec.edu.espe.pizzaplanetapp.model.Ingredient
import ec.edu.espe.pizzaplanetapp.model.Size


class CustomizeBillFragment : Fragment() {

    private var _binding: FragmentCustomizeBillBinding? = null
    private val binding get() = _binding!!

    private val args: CustomizeBillFragmentArgs by navArgs()
    private lateinit var size: Size
    private var ingredientList = mutableListOf<Ingredient>()

    private lateinit var bill: Bill
    var total: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizeBillBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
        //getSize()
        getArgs()
        setupTable()
    }

    private fun initClick(){
        binding.btnSave.setOnClickListener {
            getBill()
            if(::bill.isInitialized){
                saveBill()
            }else{
                Toast.makeText(requireContext(), "Error guardar Factura", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveBill(){
        FirebaseHelper
            .getDataBase()
            .child("bill")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(bill.id)
            .setValue(bill)
            .addOnCompleteListener{ bill ->
                if(bill.isSuccessful){
                    Toast.makeText(requireContext(), "Guardado exitosamente", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_LONG).show()
            }
    }

    private fun getArgs() {
        args.size.let {
            if (it != null) {
                size = it
            }
        }

        args.ingredients.let {
            if(it != null){
                ingredientList = it.toMutableList()
            }
        }
    }

    private fun validate(): Boolean{
        var name = binding.txtName.text.toString()
        var identification = binding.txtIdentification.text.toString()

        return name.isNotEmpty() && identification.isNotEmpty()
    }

    private fun getBill(){
        if(validate()){
            bill = Bill()
            bill.identification = binding.txtIdentification.text.toString().trim()
            bill.name = binding.txtName.text.toString().trim()
            bill.total =total
            bill.size = size

            for (i in 0 until ingredientList.size) {
                val item = ingredientList[i]
                bill.ingredients.add(item)
            }

        }else{
            Toast.makeText(requireContext(), "Ingresar Nombre e/o Identificacion", Toast.LENGTH_LONG).show()
        }
    }

    private  fun setupTable(){

        val tableRow = TableRow(requireContext())
        val textViewC1 = TextView(requireContext())
        val textViewC2 = TextView(requireContext())
        val emptyView = View(requireContext())

        val separation = 300

        textViewC1.text = "Descripción"
        textViewC1.setTextColor(Color.BLACK)
        textViewC1.setTypeface(null, Typeface.BOLD)
        tableRow.addView(textViewC1)

        emptyView.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
        tableRow.addView(emptyView)

        textViewC2.text = "V. Unitario"
        textViewC2.setTextColor(Color.BLACK)
        textViewC2.setTypeface(null, Typeface.BOLD)
        tableRow.addView(textViewC2)

        binding.tblBill.addView(tableRow)


        for (i in 0 until 1) {

            val tblRow = TableRow(requireContext())
            val textView0 = TextView(requireContext())
            textView0.text = ""
            textView0.gravity = Gravity.CENTER
            tblRow.addView(textView0)

            val emptyView1 = View(requireContext())
            emptyView1.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
            tblRow.addView(emptyView1)

            val textView1 = TextView(requireContext())
            textView1.text = ""
            textView1.gravity = Gravity.CENTER
            tblRow.addView(textView1)

            binding.tblBill.addView(tblRow)
        }

        for (i in 0 until 1) {

            val tblRow = TableRow(requireContext())
            val textView0 = TextView(requireContext())
            textView0.text = size.name
            textView0.gravity = Gravity.CENTER
            tblRow.addView(textView0)

            val emptyView1 = View(requireContext())
            emptyView1.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
            tblRow.addView(emptyView1)

            val textView1 = TextView(requireContext())
            textView1.text = String.format("%.2f", size.unitValue)
            total += size.unitValue
            textView1.gravity = Gravity.CENTER
            tblRow.addView(textView1)

            binding.tblBill.addView(tblRow)
        }

        for (i in 0 until ingredientList.size) {
            val item = ingredientList[i]

            val tblRow = TableRow(requireContext())
            val textView0 = TextView(requireContext())
            textView0.text = item.name
            textView0.gravity = Gravity.CENTER
            tblRow.addView(textView0)

            val emptyView1 = View(requireContext())
            emptyView1.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
            tblRow.addView(emptyView1)

            val textView1 = TextView(requireContext())
            textView1.text = String.format("%.2f", item.unitValue)
            total += item.unitValue
            textView1.gravity = Gravity.CENTER
            tblRow.addView(textView1)

            binding.tblBill.addView(tblRow)
        }

        for (i in 0 until 2) {

            val tblRow = TableRow(requireContext())
            val textView0 = TextView(requireContext())
            textView0.text = ""
            textView0.gravity = Gravity.CENTER
            tblRow.addView(textView0)

            val emptyView1 = View(requireContext())
            emptyView1.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
            tblRow.addView(emptyView1)

            val textView1 = TextView(requireContext())
            textView1.text = ""
            textView1.gravity = Gravity.CENTER
            tblRow.addView(textView1)

            binding.tblBill.addView(tblRow)
        }

        for (i in 0 until 1) {

            val tblRow = TableRow(requireContext())
            val textView0 = TextView(requireContext())
            textView0.text = "TOTAL"
            textView0.gravity = Gravity.CENTER
            textView0.setTextColor(Color.BLACK)
            textView0.setTypeface(null, Typeface.BOLD)
            tblRow.addView(textView0)

            val emptyView1 = View(requireContext())
            emptyView1.layoutParams = TableRow.LayoutParams(separation, TableRow.LayoutParams.MATCH_PARENT)
            tblRow.addView(emptyView1)

            val textView1 = TextView(requireContext())
            textView1.text = String.format("%.2f", total)
            textView1.gravity = Gravity.CENTER
            textView1.setTextColor(Color.BLACK)
            textView1.setTypeface(null, Typeface.BOLD)
            tblRow.addView(textView1)

            binding.tblBill.addView(tblRow)
        }


    }



}