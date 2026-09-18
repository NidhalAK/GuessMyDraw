package com.neacoding.guessmydraw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neacoding.designsystem.AppCard
import com.neacoding.designsystem.AppTextField
import com.neacoding.designsystem.GhostToolButton
import com.neacoding.designsystem.GoldBoosterButton
import com.neacoding.designsystem.GuessMyDrawTheme
import com.neacoding.designsystem.MintActionButton
import com.neacoding.designsystem.SecretWordChip
import com.neacoding.designsystem.StatusPill
import com.neacoding.designsystem.TimeBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessMyDrawTheme {
                GuessMyDrawScreen()
            }
        }
    }
}

@Composable
private fun GuessMyDrawScreen() {
    var playerName by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "Guess My Draw",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Crée une room et mets au défi tes amis.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            AppCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        StatusPill(text = "Dessinateur")
                        StatusPill(
                            text = "A trouvé !",
                            containerColor = com.neacoding.designsystem.ArcadeColors.MintAction,
                            contentColor = com.neacoding.designsystem.ArcadeColors.OnMintAction,
                        )
                    }
                    SecretWordChip(revealed = "C", length = 6)
                    TimeBar(progress = 0.35f)
                    AppTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        label = "Ta réponse",
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        MintActionButton(
                            text = "Valider",
                            onClick = { },
                            modifier = Modifier.weight(1f),
                        )
                        GoldBoosterButton(
                            text = "Indice",
                            onClick = { },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        GhostToolButton(
                            icon = Icons.Default.Refresh,
                            contentDescription = "Effacer",
                            onClick = { },
                        )
                        GhostToolButton(
                            icon = Icons.Default.Clear,
                            contentDescription = "Annuler",
                            onClick = { },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GuessMyDrawScreenPreview() {
    GuessMyDrawTheme {
        GuessMyDrawScreen()
    }
}
