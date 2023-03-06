package ec.edu.espe.pizzaplanetapp.model

import android.os.Parcelable
import ec.edu.espe.pizzaplanetapp.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient (
    var id: String = "",
    var name: String = "",
    var state: String = "",
    var description: String = "",
    var unitValue: Double = 0.0

) : Parcelable {
    init {
        this.id = FirebaseHelper.getDataBase().push().key ?: ""
    }
}