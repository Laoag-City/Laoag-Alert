package ph.gov.laoagcity.laoagalert

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ph.gov.laoagcity.laoagalert.ui.theme.LaoagAlertTheme

class HotlinesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaoagAlertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HotlinesContent()
                }
            }
        }
    }
}

@Composable
fun HotlinesContent() {
    LaoagAlertTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Laoag City Hotline Numbers") }) },
            content = { Hotlines() }
        )
    }
}

@Composable
fun Hotlines() {
    // display the City's hotline numbers so that when clicked it will dial that number
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        ) {
        Row(
            modifier = Modifier.fillMaxWidth()
    //        horizontalArrangement = Arrangement.Center
        ) {
            Text("Hello Geek!", fontSize = 50.sp)
            Text("Required permissions text here")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun HotlinesPreview() {
    HotlinesContent()
}


