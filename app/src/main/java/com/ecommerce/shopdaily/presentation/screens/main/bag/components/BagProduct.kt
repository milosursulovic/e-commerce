package com.ecommerce.shopdaily.presentation.screens.main.bag.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecommerce.shopdaily.domain.model.product.Product
import com.ecommerce.shopdaily.presentation.common.components.product.CardImage
import com.ecommerce.shopdaily.presentation.common.components.product.ColorAndSize
import com.ecommerce.shopdaily.presentation.common.components.product.ProductAmount
import com.ecommerce.shopdaily.presentation.common.components.product.ProductPrice
import com.ecommerce.shopdaily.presentation.common.components.product.ProductTitle
import com.ecommerce.shopdaily.presentation.ui.theme.Gray

@Composable
fun BagProduct(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.background(color = MaterialTheme.colors.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(), product.image
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProductTitle(product = product)
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                            tint = Gray
                        )
                    }
                }
                ColorAndSize()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProductAmount()
                    ProductPrice(product = product, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}