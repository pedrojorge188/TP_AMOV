package pt.isec.amov.models

import com.google.firebase.auth.FirebaseUser

data class User(val name: String, val email:String, val picture: String?)

fun FirebaseUser.toUser() : User {
    val username = this.displayName ?: ""
    val str_email = this.email ?: ""
    val photoUrl = this.photoUrl.toString()
    return User(username,str_email,photoUrl);
}