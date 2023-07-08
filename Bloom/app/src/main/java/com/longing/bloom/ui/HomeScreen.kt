package com.longing.bloom.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longing.bloom.R
import com.longing.bloom.ui.theme.BloomTheme
import com.longing.bloom.ui.theme.caption
import com.longing.bloom.ui.theme.gray
import com.longing.bloom.ui.theme.h1
import com.longing.bloom.ui.theme.h2
import com.longing.bloom.ui.theme.pink100
import com.longing.bloom.ui.theme.white
import com.longing.bloom.ui.theme.white850


val plantThemeList = listOf(
    "Desert chic" to R.drawable.desert_chic,
    "Tiny terrariums" to R.drawable.tiny_terrariums,
    "Jungle Vibes" to R.drawable.jungle_vibes
)

val bloomInfoList = listOf(
    "Monstera" to R.drawable.monstera,
    "Aglaonema" to R.drawable.aglaonema,
    "Peace lily" to R.drawable.peace_lily,
    "Fiddle leaf tree" to R.drawable.fiddle_leaf,
    "Desert chic" to R.drawable.desert_chic,
    "Tiny terrariums" to R.drawable.tiny_terrariums,
    "Junngle Vibes" to R.drawable.jungle_vibes,
)

val navList = listOf(
    "Home" to Icons.Default.Home,
    "Favorites" to Icons.Default.FavoriteBorder,
    "Profile" to Icons.Default.AccountCircle,
    "Cart" to Icons.Default.ShoppingCart
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = { BottomBar() },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                SearchBar()
                BrowseThemesSection()
                BloomInfoSection()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Search") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier.size(18.dp)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PlantThemeCard(name: String, @DrawableRes plantResId: Int) {
    Card(
        modifier = Modifier
            .size(136.dp)
            .clip(RoundedCornerShape(4.dp))

    ) {
        Column {
            Image(
                painterResource(plantResId),
                contentScale = ContentScale.Crop,
                contentDescription = "image", modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(white850)
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    text = name,
                    style = h2,
                    color = gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .paddingFromBaseline(top = 24.dp, bottom = 16.dp)
                )

            }
        }
    }
}

@Composable
private fun BrowseThemesSection() {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Browse themes",
                style = h1,
                color = gray,
                modifier = Modifier.paddingFromBaseline(top = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(modifier = Modifier.height(136.dp)) {
            items(plantThemeList.size) { index ->
                if (index != 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                plantThemeList[index].also {
                    PlantThemeCard(it.first, it.second)
                }
            }
        }
    }
}

@Composable
private fun BloomInfoSection() {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Design your garden",
                style = h1,
                color = gray,
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Default.FilterList, contentDescription = "Filter",
                modifier = Modifier.size(24.dp)
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            items(bloomInfoList.size) {
                if (it != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    BloomInfoListItem(item = bloomInfoList[it])
                }
            }

        }
    }


}

@Composable
private fun BottomBar() {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = pink100
    ) {
        navList.forEach {
            NavigationRailItem(
                selected = false,
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                icon = {
                    Icon(it.second, contentDescription = null, modifier = Modifier.size(24.dp))
                },
                label = { Text(text = it.first, style = caption, color = gray) },
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPrev() {
    BloomTheme {
        HomeScreen()
    }
}