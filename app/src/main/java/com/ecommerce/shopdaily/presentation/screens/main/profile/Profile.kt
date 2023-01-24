package com.ecommerce.shopdaily.presentation.screens.main.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecommerce.shopdaily.presentation.common.components.appbar.AppBar
import com.ecommerce.shopdaily.presentation.common.components.screen.BottomPaddingColumn
import com.ecommerce.shopdaily.presentation.common.components.screen.ScreenTitle
import com.ecommerce.shopdaily.presentation.screens.main.MainViewModel
import com.ecommerce.shopdaily.presentation.screens.main.profile.components.ProfileListItem
import com.ecommerce.shopdaily.presentation.screens.main.profile.util.profileItems
import com.ecommerce.shopdaily.presentation.ui.theme.Gray

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Profile(mainViewModel: MainViewModel) {
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            AppBar(
                isBackEnabled = false,
                actions = arrayOf(Icons.Outlined.Search)
            )
        },
        content = {
            BottomPaddingColumn {
                ScreenTitle(title = "My Profile")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(40.dp))
                            .width(60.dp)
                            .height(60.dp),
                        model = mainViewModel.loggedUser?.image,
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = "${mainViewModel.loggedUser?.firstName} ${mainViewModel.loggedUser?.lastName}",
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${mainViewModel.loggedUser?.email}",
                            style = MaterialTheme.typography.h3,
                            color = Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(profileItems) { profileListItem ->
                        ProfileListItem(item = profileListItem)
                    }
                }
            }
        })
}