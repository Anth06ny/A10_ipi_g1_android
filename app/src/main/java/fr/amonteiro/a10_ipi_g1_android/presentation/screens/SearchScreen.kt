package fr.amonteiro.a10_ipi_g1_android.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import fr.amonteiro.a10_ipi_g1_android.R
import fr.amonteiro.a10_ipi_g1_android.data.fake.WeatherFakeDataSource
import fr.amonteiro.a10_ipi_g1_android.domain.Weather
import fr.amonteiro.a10_ipi_g1_android.presentation.MainViewModel
import fr.amonteiro.a10_ipi_g1_android.ui.theme.AppTheme

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreProjetTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val list = WeatherFakeDataSource.loadFakeData()

            SearchScreenContent(
                modifier = Modifier.padding(innerPadding),
                list = list,
                runInProgress = true,
                errorMessage = "Une erreur est survenue"
            )
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Composable
fun SearchScreenNoDataPreview() {
    //Il faut remplacer NomVotreProjetTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreenContent(
                modifier = Modifier.padding(innerPadding),
                list = emptyList()
            )
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val list = mainViewModel.dataList.collectAsStateWithLifecycle().value
    val runInProgress = mainViewModel.runInProgress.collectAsStateWithLifecycle().value
    val errorMessage = mainViewModel.errorMessage.collectAsStateWithLifecycle().value

    LaunchedEffect("") {
        mainViewModel.loadWeathers("Nice")
    }

    SearchScreenContent(
        modifier = modifier,
        list = list,
        runInProgress = runInProgress,
        errorMessage = errorMessage,
        onBtLoadClick = { mainViewModel.loadWeathers(it)}
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    list: List<Weather>,
    runInProgress : Boolean = false,
    errorMessage : String = "",
    onBtLoadClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val searchText: MutableState<String> = remember { mutableStateOf("") }
        val filterList = list //.filter { it.name.contains(searchText.value, true) }

        SearchBar(searchText = searchText.value) { searchText.value = it }

        AnimatedVisibility(visible = errorMessage?.isNotBlank() == true) {
            Text(
                text = errorMessage ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.error)
            )
        }

        AnimatedVisibility (runInProgress) {
            CircularProgressIndicator()
        }

        LazyColumn(
            modifier = Modifier.weight(1f),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterList.size) {
                PictureRowItem(data = filterList[it])
            }
        }

        Row {

            Button(
                onClick = { searchText.value = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Cancel")
            }

            Button(
                onClick = { onBtLoadClick(searchText.value) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load")
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onValueChangeListener: (String) -> Unit
) {


    TextField(
        value = searchText, //Valeur affichée
        onValueChange = onValueChangeListener, //Nouveau texte entrée
        leadingIcon = { //Image d'icône
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { //Texte d'aide qui se déplace
            Text("Enter text")
            //Pour aller le chercher dans strings.xml, R de votre package com.nom.projet
            //Text(stringResource(R.string.placeholder_search))
        },
        //placeholder = { //Texte d'aide qui disparait
        //Text("Recherche")
        //},

        //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Définir le bouton "Entrée" comme action de recherche
        //keyboardActions = KeyboardActions(onSearch = {onSearchAction()}), // Déclenche l'action définie
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}


@Composable //Composable affichant 1 élément
fun PictureRowItem(modifier: Modifier = Modifier, data: Weather) {

    var expended by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .fillMaxWidth()
    ) {
        //image
//Permission Internet nécessaire
        AsyncImage(
            model = data.icon,
            //Pour aller le chercher dans strings.xml R de votre package com.nom.projet
            //contentDescription = stringResource(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            contentScale = ContentScale.FillWidth,

            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            error = painterResource(R.drawable.error),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),

            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expended = !expended
                }) {
            Text(text = data.name, fontSize = 20.sp)
            Text(
                modifier = Modifier.animateContentSize(),
                text = if (expended) data.getResume() else data.getResume().take(20) + "...",
                fontSize = 14.sp
            )
        }

    }
}