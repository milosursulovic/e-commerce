package com.ecommerce.shopdaily.presentation.screens.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecommerce.shopdaily.presentation.screens.main.profile.util.ProfileItem
import com.ecommerce.shopdaily.presentation.ui.theme.Gray

@Composable
fun ProfileListItem(item: ProfileItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Text(text = item.subtitle, style = MaterialTheme.typography.h4, color = Gray)
        }
        Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null, tint = Gray)
    }
}