package fr.amonteiro.a10_ipi_g1_android.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.amonteiro.a10_ipi_g1_android.data.fake.WeatherFakeDataSource
import fr.amonteiro.a10_ipi_g1_android.domain.Weather
import fr.amonteiro.a10_ipi_g1_android.presentation.MainViewModel
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
            val list = WeatherFakeDataSource.loadFakeData()

            SearchScreenContent(
                modifier = Modifier.padding(innerPadding),
                list = list
            )
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = MainViewModel()
) {
    val list = mainViewModel.dataList.collectAsStateWithLifecycle().value

    SearchScreenContent(modifier = modifier, list = list)
}

@Composable
fun SearchScreenContent(modifier: Modifier = Modifier, list: List<Weather>) {
    Column(modifier = modifier) {
        list.forEach {
            PictureRowItem(it.name)
        }

    }
}

@Composable
fun PictureRowItem(text: String) {
    Text(text = text, fontSize = 14.sp)
}