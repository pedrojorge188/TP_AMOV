package pt.isec.amov.ui.composables

import pt.isec.amov.R

public fun getResourceIdForImage(imageName: String): Int? {
    // Mapeia o nome da imagem para o ID do recurso
    return when (imageName) {
        "cidade" -> R.drawable.img_cidade
        "desporto" -> R.drawable.img_desporto
        "ginasio" -> R.drawable.img_ginasio
        "montanhas" -> R.drawable.img_montanhas
        "restaurante" -> R.drawable.img_restaurante
        "museu" -> R.drawable.img_museu
        "natureza" -> R.drawable.img_natureza
        "praia" -> R.drawable.img_praia
        else -> null
    }
}