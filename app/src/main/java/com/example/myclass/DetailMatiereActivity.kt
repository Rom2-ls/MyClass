package com.example.myclass

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class DetailMatiereActivity : AppCompatActivity() {

    private val storage = FirebaseStorage.getInstance()
    private lateinit var detailMatiereRecycler: DetailMatiereRecycler
    val storageRef = storage.reference

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var vFilename: String = ""
    var matiere_id: String = ""
    var class_id: String = ""
    var pathImage: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbref: DatabaseReference
    private var imageList = ArrayList<ImageCours>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_matiere)

        detailMatiereRecycler = DetailMatiereRecycler(imageList, applicationContext)
        recyclerView = findViewById(R.id.detailMatiereRecycler)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = detailMatiereRecycler

        imageList = arrayListOf()

        val photoButton = findViewById<Button>(R.id.button_send)
        matiere_id = intent.getStringExtra("matiere_id")!!
        class_id = intent.getStringExtra("class_id")!!

        dbref = FirebaseDatabase.getInstance().getReference("Classes").child(class_id).child("Matiere").child(matiere_id).child("Image")
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(e in snapshot.children){
                        val image = e.getValue(ImageCours::class.java)
                        imageList.add(image!!)
                    }
                    recyclerView.adapter = DetailMatiereRecycler(imageList, this@DetailMatiereActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        // Récupérez une référence à l'emplacement où vous souhaitez enregistrer la photo :

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
        vFilename = "PIC_" + timeStamp + ".jpg"

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
            val photoRef = storageRef.child("Photos").child(class_id).child(matiere_id).child(vFilename)
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()

            val uploadTask = photoRef.putBytes(imageData)
            uploadTask.addOnFailureListener {
                // En cas d'échec, affichez un message d'erreur à l'utilisateur
                Toast.makeText(this, "Erreur lors de l'enregistrement de la photo", Toast.LENGTH_SHORT).show()
            }
            val pathImage = "https://firebasestorage.googleapis.com/v0/b/myclass-11fa5.appspot.com/o/Photos%2F" + class_id + "%2F" + matiere_id + "%2F" + vFilename + "?alt=media"
            val newImage = ImageCours(pathImage)

            dbref.push().setValue(newImage)
        }
    }


}