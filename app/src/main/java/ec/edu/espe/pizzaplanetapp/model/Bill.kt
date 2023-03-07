package ec.edu.espe.pizzaplanetapp.model

import android.os.Parcelable
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
class Bill (
    var id: String = "",
    var identification: String = "",
    var name: String = "",
    var total: Double = 0.0,
    var size: Size = Size(),
    var ingredients: MutableList<Ingredient> =mutableListOf()
): Parcelable {
    init {
        this.id = FirebaseHelper.getDataBase().push().key ?: ""
    }
}


