package pt.isec.amov.utils.firebase

import android.content.res.AssetManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import java.io.IOException
import java.io.InputStream

class StoreUtil {
    companion object StoreUtil {
        fun addLocationToFirestore(location: Location) {
            val db = FirebaseFirestore.getInstance()
            val locationData = hashMapOf(
                "id" to location.id,
                "name" to location.name,
                "latitude" to location.latitude,
                "longitude" to location.longitude,
                "description" to location.description,
                "photoUrl" to location.photoUrl,
                "createdBy" to location.createdBy,
                "votes" to location.votes,
                "grade" to location.grade,
                "categoryId" to location.category?.id
            )

            db.collection("locations").document(location.name)
                .set(locationData)
        }

        fun addPointOfInterestToLocation(
            poi: PointOfInterest
        ) {
            val db = FirebaseFirestore.getInstance();
            val newPointOfInterest = hashMapOf(
                "id" to poi.id,
                "name" to poi.name,
                "locationId" to poi.locationId,
                "description" to poi.description,
                "photoUrl" to poi.photoUrl,
                "latitude" to poi.latitude,
                "longitude" to poi.longitude,
                "createdBy" to poi.createdBy,
                "categoryId" to poi.category.id
            )

            db.collection("locations").document(poi.name)
                .collection("pointsOfInterest")
                .document(newPointOfInterest["name"] as String)
                .set(newPointOfInterest)
        }


        fun addCategory(
            category: Category
        ) {
            val db = FirebaseFirestore.getInstance();
            val newPointOfInterest = hashMapOf(
                "id" to category.id,
                "name" to category.name,
                "iconUrl" to category.iconUrl,
                "description" to category.description
            )

            db.collection("category").document(category.name)
                .set(newPointOfInterest)
        }


        fun getFileFromAsset(assetManager: AssetManager, strName: String): InputStream? {
            var istr: InputStream? = null
            try {
                istr = assetManager.open(strName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return istr
        }

        fun uploadFile(inputStream: InputStream, imgFile: String) {
            val storage = Firebase.storage
            val ref1 = storage.reference
            val ref2 = ref1.child("images")
            val ref3 = ref2.child(imgFile)

            val uploadTask = ref3.putStream(inputStream)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref3.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    println(downloadUri.toString())
                } else {
                    // Handle failures
                    // ...
                }
            }
        }
    }

}