package pt.isec.amov.utils.firebase

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreObserver {

    private val db = FirebaseFirestore.getInstance()
    fun observeCategories(onCategoriesChanged: () -> Unit) {
        db.collection("category")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                onCategoriesChanged()
            }
    }

    fun observeLocations(onLocationsChanged: () -> Unit) {
        db.collection("locations")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                onLocationsChanged()
            }
    }

    fun observePointsOfInterest(onPointsOfInterestChanged: () -> Unit) {
        db.collectionGroup("pointsOfInterest")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                onPointsOfInterestChanged()
            }
    }
}
