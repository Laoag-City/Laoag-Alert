package ph.gov.laoagcity.laoagalert


import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationRequest
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.google.android.gms.tasks.CancellationTokenSource
import ph.gov.laoagcity.laoagalert.ui.theme.LaoagAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ask for permissions if shouldShowRequestPermissionRationale() is false
        val tag = "PERMISSION"
        val smsSendPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        val locationCoarsePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if ((smsSendPermission != PackageManager.PERMISSION_GRANTED) || (locationCoarsePermission != PackageManager.PERMISSION_GRANTED)) {
            Log.i(tag, "Permission to read location or send SMS denied")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                ) || (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to read current location and send SMS is required for dispatch to contact you.")
                    .setTitle("Permission required")

                builder.setPositiveButton(
                    "OK"
                ) { dialog, id ->
                    Log.i(tag, "Clicked")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.SEND_SMS),
                        101
                    )
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        101
                    )
                }

                val dialog = builder.create()
                dialog.show()
            } else {
                //makeRequest()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 100)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    100
                )
            }
        }
        setContent {
            LaoagAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainAppActivity()
                }
            }
        }
    }
}


/* TODO
* 1. a dialog box to be displayed if outside of Laoag City
* 2. runtime permission or upon install permission for location, send SMS (mostly done)
* 3. disclaimer and data privacy agreement activity
* 4. add photo or video of incident / emergency
* 5. check if SMS is sent within 5 mins., otherwise enable MainActivity() button
*
* 6.instead of #2 an Disclaimer and Privacy Composable that will request needed permissions
* for 1 time only
*
*/

@Composable
fun PrivacyAndDisclaimer() {
    TODO()
}

@Composable
fun MainAppActivity() {
    val mainButtonClick = remember { mutableStateOf(false) }
    val radioOptionsStringIconRes = listOf(
        Pair(R.drawable.ic_baseline_local_police_24, stringResource(id = R.string.police)),
        Pair(R.drawable.ic_emergency_black_24dp, stringResource(id = R.string.medical)),
        Pair(R.drawable.ic_person_pin_circle_black_24dp, stringResource(id = R.string.rescue)),
        Pair(R.drawable.ic_fire_truck_black_24dp, stringResource(id = R.string.fire)),
        Pair(R.drawable.ic_report_black_24dp, stringResource(id = R.string.other))
    )
    val selectedValue = remember { mutableStateOf("") }
    val latitude = 0.0
    val longitude = 0.0
    val senderName = "Anonymous"
    val smsManager: SmsManager = SmsManager.getDefault()
    val mContext = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(
                    id = R.drawable.alert_header,
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(128.dp)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
/*
            Text(
                text = stringResource(id = R.string.app_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
            )
*/

// refactor the code below into a class or fun
//            RadioButtonWithIcon()
            // TODO map alertCode using .Map()
            val alertCode = selectedValue.value.ifEmpty { "NONE" }
            radioOptionsStringIconRes.forEach { item ->
                val selectedColor = if (selectedValue.value == item.second) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.primary
                }
                val selectedBackgroundColor = if (selectedValue.value == item.second) {
                    MaterialTheme.colors.secondary.copy(alpha = .5f)
                } else {
                    Color.LightGray
                }
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = selectedBackgroundColor,
                    border = BorderStroke(
                        width = 2.dp,
                        color = selectedColor
                    ),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .selectable(
                                selected = (selectedValue.value == item.second),
                                onClick = {
                                    selectedValue.value = item.second
                                },
                                role = Role.RadioButton
                            )
                            .padding(8.dp)
                    ) {
                        IconToggleButton(
                            checked = selectedValue.value == item.second,
                            onCheckedChange = { selectedValue.value = item.second },
                            modifier = Modifier.size(56.dp),
                        ) {
                            Icon(
                                painter = painterResource(item.first),
                                contentDescription = null,
                                tint = selectedColor,
                                modifier = Modifier
                                    .width(56.dp)
                                    .height(56.dp)
                                    .clip(MaterialTheme.shapes.medium)

                            )
                        }
                        Text(
                            text = item.second,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 8.dp)
                        )
                    }
                }
            }
/* see TODO # 5
* refactor code below into a class or fun()
* if SEND_SMS and ACCESS_COARSE_LOCATION is not granted show hotlines otherwise send SMS then
* disable send button and show countdown timer
*/
            Button(
                onClick = {
                    mainButtonClick.value = !mainButtonClick.value
                    if (mainButtonClick.value) {
                        if (ContextCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.SEND_SMS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            // get location and store in vals latitude and longitude
//                            val priority = LocationRequest.QUALITY_BALANCED_POWER_ACCURACY
//                            val cancellationTokenSource = CancellationTokenSource()
                            // send SMS code here. Use deprecated method for now
                            val smsMessage = "$alertCode,$senderName,$latitude,$longitude"
                            smsManager.sendTextMessage(
                                "+639065212220",
                                null,
                                smsMessage,
                                null,
                                null
                            )
                            //val fusedLocationProviderClient = FusedLocationProviderClient()
/*
                            fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
                                .addOnSuccessListener { location ->
                                    Log.d("Location", "location is found: $location")
                                }
                                .addOnFailureListener { exception ->
                                    Log.d(
                                        "Location",
                                        "Oops location failed with exception: $exception"
                                    )
                                }
*/
                            // on below line we are displaying a toast message for message send,
                            Toast.makeText(mContext, "Message Sent", Toast.LENGTH_LONG).show()
                        } else // show hotlines if SEND_SMS and ACCESS_COARSE_LOCATIONS is not granted
                            mContext.startActivity(Intent(mContext, HotlinesActivity::class.java))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
            )
            {
                Text(
                    text = "Send ALERT!",
                    style = MaterialTheme.typography.h6
                )
//                Icon(Icons.Filled.List, contentDescription = null)
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun DefaultPreview() {
    LaoagAlertTheme {
        MainAppActivity()
    }
}
