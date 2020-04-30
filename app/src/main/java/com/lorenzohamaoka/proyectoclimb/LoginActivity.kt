package com.lorenzohamaoka.proyectoclimb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lorenzohamaoka.proyectoclimb.models.Vias
import com.lorenzohamaoka.proyectoclimb.models.Sectores
import dam.lorenzohamaoka.climbingapp.models.ZonasEscalada
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        var zonasArray: MutableList<ZonasEscalada> = arrayListOf()
    }

    private lateinit var auth: FirebaseAuth
    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializamos Firebase Auth
        auth = FirebaseAuth.getInstance()

        //Listener para enviar el mail de verificación al pulsar el botón
        createAccount.setOnClickListener {
            val myIntent = Intent(this, RegistrationActivity::class.java)
            // Lanzamos la activity
            startActivity(myIntent)
        }

        //Listener para enviar el mail de verificación al pulsar el botón
        logCardView.setOnClickListener {
            signIn(loginMail.text.toString(), loginPass.text.toString())
        }
    }

    fun openMainActivity(){
        // Creamos un objeto de tipo Intent
        val myIntent = Intent(this, MainActivity::class.java)
        // Lanzamos la activity
        startActivity(myIntent)
        finish()
    }

    /**
     * Se ejecuta tras onCreate().
     */
    override fun onStart() {
        super.onStart()
        // Si el usuario existe NO será nulo.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
/*
    /**
     * Método para comprobar el estado del usuario actual,
     * Si está registrado su valor no será nulo.
     */
    private fun updateUI(user: FirebaseUser?) {
        // Usuario registrado.
        if (user != null) {

            // Abrimos la activity principal
            val myIntent = Intent(this, MainActivity::class.java)
            // Lanzamos la activity
            startActivity(myIntent)
            finish()
            // Mostramos la información de la cuenta.
            status.text = getString(
                R.string.emailpassword_status_fmt,
                user.email,
                user.isEmailVerified
            )
            detail.text = getString(R.string.firebase_status_fmt, user.uid)
            // Ocultamos los botones de registro.
            emailPasswordButtons.visibility = View.GONE
            emailPasswordFields.visibility = View.GONE
            // Mostramos los botones de logout y verificación.
            signedInButtons.visibility = View.VISIBLE
            verifyEmailButton.isEnabled = !user.isEmailVerified
        } else { // Usuario NO registrado.
            // Mostramos la información inicial.
            status.setText(R.string.signed_out)
            detail.text = null
            // Mostramos los botones de registro.
            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            // Ocultamos los botones de logout y verificación.
            signedInButtons.visibility = View.GONE
        }
    }

*/
    /**
     * Método para validar el contenido de los campos
     * del formulario para el inicio de sesión y
     * creación de una cuenta.
     */
    private fun validateForm(): Boolean {
        var valid = true
        val email = loginMail.text.toString()
        if (TextUtils.isEmpty(email)) {
            // Esta opción, si tiene valor muestra una exclamación de aviso.
            loginMail.error = "Requerido"
            valid = false
        } else {
            loginMail.error = null
        }
        val password = loginPass.text.toString()
        if (TextUtils.isEmpty(password)) {
            loginPass.error = "Requerido"
            valid = false
        } else {
            loginPass.error = null
        }
        return valid
    }

    /**
     * Método para iniciar sesión con una cuenta
     * ya existente.
     */
    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login, actualizamos UI con la información del usuario.
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    getZonas()
                } else {
                    // Login fallido, mostramos un mensaje de error.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        applicationContext,
                        "Authentication failed ${task.exception!!.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
                // Fallo en la autenticación.
                if (!task.isSuccessful) {
//                    getZonas()
                }
            }
    }

    private fun getZonas(){

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val zonasCollection: CollectionReference = db.collection("zonas")
        // Obtener todos los documentos de una colección (sin escucha).
        zonasCollection
            .orderBy("nombre")
            .get().apply {
            addOnSuccessListener {
                for (document in it) {
                    zonasArray.add(
                        ZonasEscalada(
                            document["referenciaPortada"].toString(),
                            document.id,
                            document["nombre"].toString(),
                            document["latitud"].toString().toDouble(),
                            document["longitud"].toString().toDouble(),
                            document["localidad"].toString(),
                            document["imagen"].toString(),
                            document["orientacion"].toString(),
                            document["restricciones"].toString(),
                            document["tipoRoca"].toString(),
                            document["tipoEscalada"].toString(),
                            getSectores(document.id)
                        )
                    )
                }
                openMainActivity()
            }

            addOnFailureListener { exception ->
                Log.d(
                    "DOC",
                    "Error durante la recogida de documentos: ",
                    exception
                )
            }
        }
    }

    private fun getSectores(referenciaZona : String): MutableList<Sectores>{

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val sectoresCollection: CollectionReference = db.collection(
            "zonas")
            .document(referenciaZona)
            .collection("sectores")

        val sectores: MutableList<Sectores> = arrayListOf()

        // Obtener todos los documentos de una colección (con escucha).
        sectoresCollection
            .orderBy("nombre")
            .addSnapshotListener { querySnapshot,
                                                 firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.w(
                    "addSnapshotListener",
                    "Escucha fallida!.",
                    firebaseFirestoreException
                )
                return@addSnapshotListener
            }
            for (document in querySnapshot!!) {
                Log.d("DOC", "${document.id} => ${document.data}")
                sectores.add(
                    Sectores(
                        document.id,
                        document["nombre"].toString(),
                        document["portadaReferencia"].toString(),
                        getVias(referenciaZona,document.id)
                    )
                )

            }
        }
        return sectores
    }

    private fun getVias(referenciaZona : String, referenciaSector : String): MutableList<Vias>{

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val viasCollection: CollectionReference = db.collection(
            "zonas")
            .document(referenciaZona)
            .collection("sectores")
            .document(referenciaSector)
            .collection("vías")

        val vias: MutableList<Vias> = arrayListOf()

        // Obtener todos los documentos de una colección (con escucha).
        viasCollection
            .orderBy("numero")
            .addSnapshotListener { querySnapshot,
                                                 firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.w(
                    "addSnapshotListener",
                    "Escucha fallida!.",
                    firebaseFirestoreException
                )
                return@addSnapshotListener
            }
            for (document in querySnapshot!!) {
                Log.d("DOC", "${document.id} => ${document.data}")
                vias.add(
                    Vias(
                        document.id,
                        document["nombre"].toString(),
                        document["grado"].toString(),
                        null,
                        document["tipo"].toString(),
                        document["numero"].toString().toInt()
                    )
                )
            }
        }
        return vias
    }
}
