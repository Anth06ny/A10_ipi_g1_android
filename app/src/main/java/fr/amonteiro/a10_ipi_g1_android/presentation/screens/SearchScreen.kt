package fr.amonteiro.a10_ipi_g1_android.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.amonteiro.a10_ipi_g1_android.ui.theme.A10_ipi_g1_androidTheme

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreProjetTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    A10_ipi_g1_androidTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreenContent(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun SearchScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        println("SearchScreenContent()")
        Text(text = "Text1", fontSize = 20.sp)
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2", fontSize = 14.sp)
        PictureRowItem("Coucou")
        PictureRowItem("Hello")
    }
}

@Composable
fun PictureRowItem(text:String){
    Text(text = text, fontSize = 14.sp)
}