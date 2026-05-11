package io.github.orizynpx.fivegamescompose.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import io.github.orizynpx.fivegamescompose.R

data class LanguageOption(val name: String, val code: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    val languageOptions = listOf(
        LanguageOption(
            name = stringResource(R.string.language_english),
            code = "en"
        ),
        LanguageOption(
            name = stringResource(R.string.language_indonesian),
            code = "id"
        )
    )

    var showLanguageDialog by remember { mutableStateOf(false) }
    val currentLocale = AppCompatDelegate.getApplicationLocales()
    val currentCode = if (currentLocale.isEmpty) "en" else currentLocale[0]?.language ?: "en"
    var selectedCode by remember { mutableStateOf(currentCode) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.title_settings)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category header
            Text(
                text = stringResource(R.string.pref_category_language_settings),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            // Language preference row
            val currentLangName = languageOptions.find { it.code == selectedCode }?.name
                ?: languageOptions.first().name

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showLanguageDialog = true }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.pref_title_change_language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = currentLangName,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(text = stringResource(R.string.pref_title_change_language)) },
            text = {
                Column {
                    languageOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCode = option.code
                                    showLanguageDialog = false
                                    val appLocale = LocaleListCompat.forLanguageTags(option.code)
                                    AppCompatDelegate.setApplicationLocales(appLocale)
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCode == option.code,
                                onClick = {
                                    selectedCode = option.code
                                    showLanguageDialog = false
                                    val appLocale = LocaleListCompat.forLanguageTags(option.code)
                                    AppCompatDelegate.setApplicationLocales(appLocale)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option.name)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}