package ni.edu.uca.sistematicopersistencia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Entity
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ni.edu.uca.sistematicopersistencia.data.database.BaseDatos
import ni.edu.uca.sistematicopersistencia.data.database.entities.EntityProducto
import ni.edu.uca.sistematicopersistencia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var db: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            BaseDatos::class.java, "basedatos"
        ).build()

        binding.btnAgregar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val precio = binding.etPrecio.text.toString().toDoubleOrNull()
            val existencia = binding.etExistencia.text.toString().toIntOrNull()

            val producto = EntityProducto(null, nombre, precio, existencia)

            GlobalScope.launch {
                db.productoDao().insertarReg(producto)
            }

            // Limpia los campos de texto después de insertar el producto
           binding.etNombre.text.clear()
            binding.etPrecio.text.clear()
            binding.etExistencia.text.clear()

            Toast.makeText(this, "Se agregó correctamente el registro", Toast.LENGTH_SHORT).show()
        }
    }


}



