package ec.edu.espe.pizzaplanetapp.ui.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ec.edu.espe.pizzaplanetapp.R
import ec.edu.espe.pizzaplanetapp.databinding.ItemIngredientAdapterBinding
import ec.edu.espe.pizzaplanetapp.databinding.ItemSizeAdapterBinding
import ec.edu.espe.pizzaplanetapp.model.Size

class SizeShopAdapter (
    private val context: Context,
    private val sizeList: List<Size>,
) : RecyclerView.Adapter<SizeShopAdapter.MyViewHolder>() {

    private var selectOption: Boolean = true
    private var valueSize:Double = 0.0

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSizeAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.binding.lblId.text = sizeList.get(position).id
        //viewHolder.binding.iconImageView.setImageResource() //imagen
        viewHolder.binding.lblName.text = sizeList.get(position).name
        viewHolder.binding.lblUnitValue.text = String.format("%.2f", sizeList.get(position).unitValue)
        viewHolder.binding.item.setOnClickListener{
            var select = viewHolder.binding.lblSelect.text
            if(select == "0" && selectOption){
                viewHolder.binding.btnRdItem.visibility = View.VISIBLE
                viewHolder.binding.btnRdItemBN.visibility = View.GONE
                viewHolder.binding.lblSelect.text = "1"
                val color = ContextCompat.getColor(context, R.color.color_select)
                viewHolder.binding.item.setBackgroundColor(color)
                valueSize = sizeList.get(position).unitValue
                onItemClickListener?.onItemClick(valueSize)
                selectOption = false
            }
            if(select == "1" && !selectOption){
                viewHolder.binding.btnRdItem.visibility= View.GONE
                viewHolder.binding.btnRdItemBN.visibility = View.VISIBLE
                viewHolder.binding.lblSelect.text = "0"
                val color = ContextCompat.getColor(context, R.color.white)
                viewHolder.binding.item.setBackgroundColor(color)
                valueSize = 0.00
                onItemClickListener?.onItemClick(valueSize)
                selectOption = true
            }
        }
    }

    fun getItem(i: Int): Size {
        return sizeList[i]
    }

    override fun getItemCount(): Int {
        return sizeList.size
    }



    interface OnItemClickListener {
        fun onItemClick(valueSize: Double)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class MyViewHolder(val binding: ItemSizeAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

}