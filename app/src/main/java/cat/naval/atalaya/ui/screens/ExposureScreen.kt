package cat.naval.atalaya.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.naval.atalaya.CellDataRepository

@Composable
fun ExposureScreen() {

    val cells by CellDataRepository.cellDataFlow.collectAsState()



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 48.dp, 0.dp, 0.dp)
                    .height(300.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Your carrier",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = NetMonsterHelper.decodeTechnology(cells.first()),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = cells.first().band?.name ?: "N/A",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        // MEASURE CI
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }



                    }

                    Spacer(modifier = Modifier.width(64.dp))
                    Column {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Column {
                                Text(
                                    text = "CI",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                                Text(
                                    text = "123",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Entities",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Add your EntityCard composable here for each entity
    }



}