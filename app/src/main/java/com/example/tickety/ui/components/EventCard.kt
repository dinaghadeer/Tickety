package com.example.tickety.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tickety.R
import com.example.tickety.navigation.Screen
import model.Event  //????????????


@Composable
fun EventCard(

    title: String,
    date: String,
    location: String,
    price: Double,
    image: Int, // drawable resource
    event: Event,
    onDetailsClick: () -> Unit
) {
    val navController = rememberNavController()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {

                Text(text = title, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = date, style = MaterialTheme.typography.bodyMedium)

                Text(text = location, style = MaterialTheme.typography.bodyMedium)

                Text(text = price.toString() + " EGP", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onDetailsClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Details")
                }
            }
        }
    }
}
