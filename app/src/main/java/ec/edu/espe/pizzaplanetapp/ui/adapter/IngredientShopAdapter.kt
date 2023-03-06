package ec.edu.espe.pizzaplanetapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.pizzaplanetapp.databinding.ItemIngredientAdapterBinding
import ec.edu.espe.pizzaplanetapp.model.Ingredient

class IngredientShopAdapter (
    private val context: Context,
    private val ingredientList: List<Ingredient>,
) : RecyclerView.Adapter<IngredientShopAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemIngredientAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {

        viewHolder.binding.lblId.text = ingredientList.get(position).id
        //viewHolder.binding.iconImageView.setImageResource() //imagen
        viewHolder.binding.lblName.text = ingredientList.get(position).description
        viewHolder.binding.lblUnitValue.text = String.format("%.2f", ingredientList.get(position).unitValue)
        viewHolder.binding.item.setOnClickListener{
            var select = viewHolder.binding.lblSelect.text
            if(select == "1"){
                viewHolder.binding.btnRdItem.visibility = View.GONE
                viewHolder.binding.btnRdItemBN.visibility = View.VISIBLE
                viewHolder.binding.lblSelect.text = "0"
            }else{
                viewHolder.binding.btnRdItem.visibility= View.VISIBLE
                viewHolder.binding.btnRdItemBN.visibility = View.GONE
                viewHolder.binding.lblSelect.text = "1"
            }
        }
    }

    fun getItem(i: Int): Ingredient {
        return ingredientList[i]
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    inner class MyViewHolder(val binding: ItemIngredientAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

}