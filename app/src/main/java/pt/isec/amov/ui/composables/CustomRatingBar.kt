package pt.isec.amov.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomRatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    stars: Int = 3,
    starsColor: Color = Color(0xE0FFD700),
    onRatingChange: (Double) -> Unit
) {

    Row {
        for (index in 0 .. stars) {
            Icon(
                modifier = modifier.clickable { onRatingChange(index.toDouble()) },
                contentDescription = null,
                tint = starsColor,
                imageVector = if(index <= rating) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarOutline
                }
            )
        }
    }
}