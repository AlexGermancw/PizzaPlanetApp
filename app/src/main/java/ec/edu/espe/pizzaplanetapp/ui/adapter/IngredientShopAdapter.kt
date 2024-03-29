package ec.edu.espe.pizzaplanetapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.ItemIngredientAdapterBinding
import ec.edu.espe.pizzaplanetapp.model.Ingredient

class IngredientShopAdapter (
    private val context: Context,
    private val ingredientList: List<Ingredient>,
) : RecyclerView.Adapter<IngredientShopAdapter.MyViewHolder>() {

    private var selectOption: Boolean = true
    private var valueSize:Double = 0.0

    private var onItemClickListener: OnItemClickListener? = null

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
        viewHolder.binding.lblName.text = ingredientList.get(position).name
        viewHolder.binding.lblUnitValue.text = String.format("%.2f", ingredientList.get(position).unitValue)
        viewHolder.binding.item.setOnClickListener{
            var select = viewHolder.binding.lblSelect.text
            if(select == "0"){
                viewHolder.binding.btnRdItem.visibility = View.VISIBLE
                viewHolder.binding.btnRdItemBN.visibility = View.GONE
                viewHolder.binding.lblSelect.text = "1"
                val color = ContextCompat.getColor(context, R.color.color_select)
                viewHolder.binding.item.setBackgroundColor(color)
                valueSize += ingredientList.get(position).unitValue
                onItemClickListener?.onItemClick(valueSize)
            }
            else{
                viewHolder.binding.btnRdItem.visibility= View.GONE
                viewHolder.binding.btnRdItemBN.visibility = View.VISIBLE
                viewHolder.binding.lblSelect.text = "0"
                val color = ContextCompat.getColor(context, R.color.white)
                viewHolder.binding.item.setBackgroundColor(color)
                valueSize -= ingredientList.get(position).unitValue
                onItemClickListener?.onItemClick(valueSize)

            }
        }
    }

    fun getItem(i: Int): Ingredient {
        return ingredientList[i]
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    interface OnItemClickListener {
        fun onItemClick(valueSize: Double)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class MyViewHolder(val binding: ItemIngredientAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

}