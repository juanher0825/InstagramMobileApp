package com.example.retoinsta.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.retoinsta.R
import com.example.retoinsta.RetoInsta
import com.example.retoinsta.UtilDomi
import com.example.retoinsta.databinding.FragmentPostBinding
import com.example.retoinsta.model.Post
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class PostFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private var urlImagen : String? =null
    private var archivo : File? = null
    private lateinit var identificadorUnico : String
    private lateinit var listaCiudades : ArrayAdapter<String>
    private lateinit var ciudadSeleccionada :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentPostBinding.inflate(inflater, container, false)
        val view = binding.root

        listaCiudades= ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_spinner_dropdown_item)

        listaCiudades.addAll(Arrays.asList("En Cali ve!!", "Medellin", "Corinto", "En Lolas"))
        binding.ciudadesDeMiPatriaSpinner.onItemSelectedListener = this
        binding.ciudadesDeMiPatriaSpinner.adapter = listaCiudades


        binding.publicarBtn.setOnClickListener {

            val descripcionFoto = binding.descripcionET.text.toString()
            var usuarioLogueado = RetoInsta.myDataBase.getUserLogin()
            val fechaPublicacion = Calendar.getInstance()

            if(urlImagen!=null){
                var post = Post(usuarioLogueado!!.id,urlImagen!!, descripcionFoto, fechaPublicacion, ciudadSeleccionada)
                Toast.makeText(requireContext(), "La foto fue posteada!!", Toast.LENGTH_SHORT).show()
                //RetoInsta.myDataBase.guardarPublicaciones(post)
            }else{
                Toast.makeText(requireContext(), "Toma una foto!!", Toast.LENGTH_SHORT).show()
            }

        }
        requestPermissions(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE),1)


        val camaraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::resultadoCamara)
        val galeriaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::resultadoGaleria)


        binding.camaraBtn.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            identificadorUnico = UUID.randomUUID().toString()
            archivo = File("${context?.getExternalFilesDir(null)}/$identificadorUnico")
            val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName, archivo!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            camaraLauncher.launch(intent)
        }

        binding.galeriaBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type ="image/*"
            galeriaLauncher.launch(intent)
        }

        return view
    }


    fun resultadoCamara(result: ActivityResult){

        if(result.resultCode == Activity.RESULT_OK){
            urlImagen= archivo?.path
            val bitmap = BitmapFactory.decodeFile(archivo?.path)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)

            binding.image.setImageBitmap(thumbnail)
        }else if(result.resultCode== Activity.RESULT_CANCELED){
            Toast.makeText(requireContext(), "Toma una foto!!", Toast.LENGTH_LONG).show()
        }
    }


    fun resultadoGaleria(result: ActivityResult){


        if(result.resultCode== Activity.RESULT_OK){
            val uriImage = result.data?.data
            val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uriImage)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)
            binding.image.setImageBitmap(thumbnail)
            identificadorUnico = UUID.randomUUID().toString()
            archivo = File("${context?.getExternalFilesDir(null)}/ $identificadorUnico")
            this.urlImagen = archivo!!.path
            val sourcePath= UtilDomi.getPath(requireContext(), uriImage!!)
            copy(File(sourcePath), archivo)
        }
    }
    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }


    interface onNewPostListener{
        fun onNewPost(post: Post)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PostFragment()


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val ciudadDeMiPatriaPosicion = listaCiudades.getItem(p2)
        ciudadSeleccionada = ciudadDeMiPatriaPosicion!!
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}