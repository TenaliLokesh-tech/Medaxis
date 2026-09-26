package com.medaxis.app.presentation.emergency

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medaxis.app.R
import com.medaxis.app.ui.theme.FloatingCardElevation

private val EmergencyBackground = Color(0xFFFFF1F0)
private val EmergencyRed = Color(0xFFB3261E)
private val EmergencyCard = Color(0xFFFFFFFF)

@Composable
fun EmergencyScreen(
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val callLabel = stringResource(R.string.emergency_call_button)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = EmergencyBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = EmergencyCard),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = FloatingCardElevation
                )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = stringResource(R.string.emergency_warning_cd),
                        modifier = Modifier.size(88.dp),
                        tint = EmergencyRed
                    )
                    Text(
                        text = stringResource(R.string.emergency_title),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        ),
                        color = EmergencyRed,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.emergency_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    ElevatedButton(
                        onClick = {
                            val dial = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:108")
                            }
                            context.startActivity(dial)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { contentDescription = callLabel },
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = EmergencyRed,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = callLabel,
                            modifier = Modifier.padding(vertical = 10.dp),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    TextButton(onClick = onGoBack) {
                        Text(
                            text = stringResource(R.string.emergency_go_back),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
