package dam.lorenzohamaoka.proyectofinalandroid.zonasescalada

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lorenzohamaoka.proyectoclimb.LoginActivity
import com.lorenzohamaoka.proyectoclimb.MainActivity
import com.lorenzohamaoka.proyectoclimb.R
import com.lorenzohamaoka.proyectoclimb.ui.map.MapFragment
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.synthetic.main.activity_zonas_escalada.*

class ZonasEscaladaActivity : AppCompatActivity() {
    private val myAdapter : ZonasRecyclerAdapter = ZonasRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas_escalada)
        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        // Esta opción a true significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación
        rv_zonas_escalada.setHasFixedSize(true)
        // Indicamos el contexto para RecyclerView en forma de lista
        rv_zonas_escalada.layoutManager = LinearLayoutManager(this)
        // Generamos el adapter
        myAdapter.RecyclerAdapter(LoginActivity.zonasArray, this)
        // Asignamos el adapter al RV
        rv_zonas_escalada.adapter = myAdapter
    }

    class ZonasRecyclerAdapter : RecyclerView.Adapter<ZonasRecyclerAdapter.ViewHolder>() {
        // Variables internas de la clase
        var zonasEscalada: MutableList<ZonasEscalada> = ArrayList()
        lateinit var context: Context
        // Constructor de la clase
        fun RecyclerAdapter(zonasEscalada: MutableList<ZonasEscalada>, context: Context) {
            this.zonasEscalada = zonasEscalada
            this.context = context
        }

        // Este método se encarga de pasar los objetos al ViewHolder
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = zonasEscalada.get(position)
            holder.bind(item, context)
        }

        // Es el encargado de devolver el ViewHolder ya configurado
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(
                layoutInflater.inflate(
                    R.layout.item_zonas_escalada,
                    parent,
                    false
                )
            )
        }

        // Devuelve el tamaño del array
        override fun getItemCount(): Int {
            return zonasEscalada.size
        }

        // Esta clase se encarga de rellenar cada una de las vistas que
        // se inflarán en el RecyclerView
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // Aquí es necesario utilizar findViewById para localizar el elemento
            // de la vista que se pasa como parámetro
            private val nombreZona = view.findViewById(
                R.id.tv_nombreZona
            ) as TextView
            private val animalImage = view.findViewById(
                R.id.iv_animalImage
            ) as ImageView

            fun bind(zonaEscalada: ZonasEscalada, context: Context) {
                MapFragment.referenciaZona = zonaEscalada.referencia
                nombreZona.text = zonaEscalada.nombreZona
                // Reference to an image file in Cloud Storage
                val storageReference = zonaEscalada.referenciaPortada
                // Download directly from StorageReference using Glide
                // (See MyAppGlideModule for Loader registration)
                Glide.with(context /* context */)
                    .load(storageReference)
                    .into(animalImage)
                // [END storage_load_with_glide]
                itemView.setOnClickListener {
//                val myIntent = Intent(context, SectoresActivity::class.java).apply{
//                    putExtra(NOMBRE_ZONA, zonaEscalada.nombreZona)

                }
                MapFragment.referenciaZona = zonaEscalada.referencia
                // Lanzamos la activity
//                context.startActivity(myIntent)
                Toast.makeText(
                    context,
                    zonaEscalada.nombreZona,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

