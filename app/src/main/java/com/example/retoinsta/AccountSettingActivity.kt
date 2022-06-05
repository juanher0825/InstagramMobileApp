package com.example.retoinsta

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.retoinsta.RetoInsta.Companion.myDataBase
import com.example.retoinsta.databinding.ActivityAccountSettingBinding
import com.example.retoinsta.fragments.NavigationScreenActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AccountSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingBinding

    private lateinit var cerrarSesion: Button
    private lateinit var cerrarPerfil: ImageView
    var user = myDataBase.getUserLogin()

    private var file : File? = null
    private var imgPath : String? =null
    private var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    var FILE_NAME = "photo_" + timestamp + "_"

    val launcherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResutl)
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cerrarPerfil = findViewById(R.id.closeBtn)
        cerrarSesion = findViewById(R.id.logoutBtn)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        checkDetails()

        cerrarSesion.setOnClickListener {


            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            myDataBase.saveCheckRemember(false)
            myDataBase.logout()
            finish()
        }

        binding.saveBtn.setOnClickListener {
            updateUserInfo()

            Toast.makeText(
                this,
                "Account information has been updated successfully",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.closeBtn.setOnClickListener {
            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.openCameraBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = File("${getExternalFilesDir(null)}/$FILE_NAME")
            val uri = FileProvider.getUriForFile(this, packageName, file!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            launcherCamera.launch(intent)
        }

        binding.openGaleyBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type ="image/*"
            galleryLauncher.launch(intent)
        }
    }



    private fun updateUserInfo() {

        if (!(binding.userET.text.toString() == "" || binding.nombreET.text.toString() == "")) {


            user!!.nombre = binding.userET.text.toString()
            user!!.usuario = binding.nombreET.text.toString()
            user!!.biografia = binding.descriptionET.text.toString()

            if(!imgPath.isNullOrEmpty()&&!imgPath.contentEquals(user?.foto)){
                user!!.foto = imgPath!!
            }


            myDataBase.updateUser(user!!)

        } else {
            Toast.makeText(this, "Username and name are required", Toast.LENGTH_LONG).show()
        }
    }

    fun checkDetails() {
        binding.userET.setText(user?.nombre)
        binding.nombreET.setText(user?.usuario)
        binding.descriptionET.setText(user?.biografia)


    }
    private fun onCameraResutl(activityResult: ActivityResult) {

        //thumbnail
        //val bitmap = activityResult.data?.extras?.get("data") as Bitmap
        //binding.profileImageViewProfilefragment.setImageBitmap(bitmap)

        if(activityResult.resultCode == RESULT_OK){
            imgPath = file?.path!!
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)
            binding.picEdit.setImageBitmap(thumbnail)
        }else if(activityResult.resultCode== RESULT_CANCELED){
            Toast.makeText(this, "No tomó foto", Toast.LENGTH_SHORT).show()
        }
    }

    fun onGalleryResult(result: ActivityResult){


        if(result.resultCode== RESULT_OK){
            val uriImage = result.data?.data
            imgPath = UtilDomi.getPath(this, uriImage!!)
            uriImage?.let{
                binding.picEdit.setImageURI(uriImage)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}