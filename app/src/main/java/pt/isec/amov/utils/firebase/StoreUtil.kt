package pt.isec.amov.utils.firebase

import android.content.res.AssetManager
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import pt.isec.amov.models.Category
import pt.isec.amov.models.Location
import pt.isec.amov.models.PointOfInterest
import java.io.IOException
import java.io.InputStream
import java.util.Locale

@Suppress("CAST_NEVER_SUCCEEDS")
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
                "category" to location.category
            )

            db.collection("locations").document(location.id)
                .set(locationData)
        }

        private val observers = mutableMapOf<String, FirestoreObserver>()
        fun observeCollectionsForChanges(collections: List<String>, onDataChanged: () -> Unit) {
            collections.forEach { collectionName ->
                val observer = observers[collectionName]

                if (observer == null) {
                    val firestoreObserver = FirestoreObserver()
                    observers[collectionName] = firestoreObserver

                    when (collectionName) {
                        "category" -> firestoreObserver.observeCategories(onDataChanged)
                        "locations" -> firestoreObserver.observeLocations(onDataChanged)
                        "pointsOfInterest" -> firestoreObserver.observePointsOfInterest(onDataChanged)
                    }
                }
            }
        }

        fun addPointOfInterestToLocation(
            locationName: String?,
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
                "category" to poi.category
            )

            db.collection("locations").document(locationName!!)
                .collection("pointsOfInterest")
                .document(newPointOfInterest["name"] as String)
                .set(newPointOfInterest)
        }

        fun deletePointOfInterestFromLocation(poiName: String) {
            val db = FirebaseFirestore.getInstance()
            val normalizedPoiName = poiName.trim().lowercase(Locale.getDefault()).replace("\\s+".toRegex(), "")

            Log.i("NAME_POI_TO_DELETE", normalizedPoiName)

            db.collectionGroup("pointsOfInterest")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val documentName = document.getString("name")?.trim()?.lowercase(Locale.getDefault())
                        ?.replace("\\s+".toRegex(), "")
                        if (documentName == normalizedPoiName) {
                            val storage = Firebase.storage
                            if (document.getString("photoUrl")!!.isNotBlank()) {
                                val storageRef = storage.reference.child(document.getString("photoUrl")!!)
                                storageRef.delete()
                            } else {
                                Log.i("DELETE_PHOTO", "URL da foto em branco, nenhum arquivo para deletar")
                            }
                            document.reference.delete()
                                .addOnSuccessListener {
                                    Log.i("DELETE_POI", "POI($poiName) Eliminado com sucesso")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("DELETE_POI", "Erro ao excluir POI($poiName): $e")
                                }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("DELETE_POI", "Erro ao buscar POI($poiName): $e")
                }
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

        fun loadCategoriesFromFireStone(onCategoriesLoaded: (MutableList<Category>) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val categoriesCollection = db.collection("category")
            val response = mutableListOf<Category>()

            categoriesCollection.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val id = document.getString("id") ?: ""
                        val name = document.getString("name") ?: ""
                        val iconUrl = document.getString("iconUrl")
                        val description = document.getString("description") ?: ""

                        val category = Category(id, name, iconUrl, description)
                        response.add(category)
                    }
                    onCategoriesLoaded(response)
                }
        }

        fun readLocationsFromFirebase(onLocationLoaded: (MutableList<Location>) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val locationCollection = db.collection("locations")
            val response = mutableListOf<Location>()

            locationCollection.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val location = Location(
                            document.getString("id") ?: "",
                            document.getString("name") ?: "",
                            document.getDouble("latitude") ?: 0.0,
                            document.getDouble("longitude") ?: 0.0,
                            document.getString("description") ?: "",
                            document.getString("photoUrl") ?: "",
                            document.getString("createdBy") ?: "",
                            document.getLong("votes")?.toInt() ?: 0,
                            document.getLong("grade")?.toInt() ?: 1,
                            document.getString("category") ?: "",
                        )
                        response.add(location)
                    }

                    onLocationLoaded(response)
                }
        }

        fun readPOIFromFirebase(onPOILoaded: (MutableList<PointOfInterest>) -> Unit) {
            val db = FirebaseFirestore.getInstance()

            val response = mutableListOf<PointOfInterest>()

            db.collectionGroup("pointsOfInterest")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val poi = PointOfInterest(
                            document.getString("id") ?: "",
                            document.getString("name") ?: "",
                            document.getString("locationId") ?: "",
                            document.getString("description") ?: "",
                            document.getString("photoUrl") ?: "",
                            document.getDouble("latitude") ?: 0.0,
                            document.getDouble("longitude") ?: 0.0,
                            document.getLong("votes")?.toInt() ?: 0,
                            document.getString("createdBy") ?: "",
                            document.getString("category") ?: ""
                        )
                        response.add(poi)
                    }
                    onPOILoaded(response)
                }
                .addOnFailureListener { exception ->
                }
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
                    Log.d("DOWNLOAD_URI", downloadUri.toString())
                } else {
                    // Handle failures
                    // ...
                }
            }
        }
    }

}