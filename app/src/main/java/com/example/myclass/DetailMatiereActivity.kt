package com.example.myclass

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.DatabaseError
import java.io.ByteArrayOutputStream
import java.util.*

class DetailMatiereActivity : AppCompatActivity() {

    private val storage = FirebaseStorage.getInstance()
    val photoButton = findViewById<Button>(R.id.button_send)
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var vFilename: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_matiere)

        // Récupérez une référence à l'emplacement où vous souhaitez enregistrer la photo :
        val storageRef = storage.reference.child("Photos")

        // Définissez un gestionnaire d'événements pour le bouton de prise de photo :
        /*photoButton.setOnClickListener {
            new takePictureIntent
            // Utilisez l'API de l'application intégrée de l'appareil photo pour prendre une photo :
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }*/
        photoButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                openCamera()
            } else {
                Toast.makeText(applicationContext, "Votre version d'android n'est pas suportée !",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

        // set filename
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        vFilename = "FOTO_" + timeStamp + ".jpg"

        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(applicationContext, "Permission Denied",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            // Récupérez la photo prise :
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Enregistrez la photo dans Firebase Storage
            val photoRef = storage.to(vFilename)
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()

            val uploadTask = photoRef.writeBytes(imageData)
            /*uploadTask.addOnFailureListener {
                // En cas d'échec, affichez un message d'erreur à l'utilisateur
                Toast.makeText(this, "Erreur lors de l'enregistrement de la photo", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

  /*  companion object {
        private const val REQUEST_IMAGE = TODO()

        // Récupérez la référence à l'emplacement où vous avez enregistré la photo :
        val photoRef = storageRef.child("${UUID.randomUUID()}.jpg")

        // Convertissez la photo en un tableau d'octets pour pouvoir l'enregistrer dans Firebase Storage :
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        // Enregistrez la photo dans Firebase Storage en utilisant la référence à l'emplacement :
        val uploadTask = photoRef.putBytes(imageData)
        uploadTask.addOnFailureListener {
            // En cas d'échec, affichez un message d'erreur à l'utilisateur
            Toast.makeText(this, "Erreur lors de l'enregistrement de la photo", Toast.LENGTH_SHORT).show()
        }

    }*/
}

private fun <A, B> Pair<A, B>.writeBytes(imageData: ByteArray) {

}
