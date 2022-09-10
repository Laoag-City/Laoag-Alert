package ph.gov.laoagcity.laoagalert

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ph.gov.laoagcity.laoagalert.ui.theme.LaoagAlertTheme

class HotlinesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaoagAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
//                    color = MaterialTheme.colors.background
                ) {
                    Hotlines()
                }
            }
        }
    }
}

@Composable
fun MainContent2() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Laoag City Hotline Numbers") }) },
        content = { Hotlines() }
    )
}

@Composable
fun Hotlines(){
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Hello Geek!", fontSize = 50.sp)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun DefaultPreview2() {
    MainContent2()
}


