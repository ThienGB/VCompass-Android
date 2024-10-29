
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileForm()
        }
    }
}

@Composable
fun Profile_Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary))
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(

            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified,
                contentDescription = "Setting"
            )
        }
        Text(
            text = "Profile",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.height(20.dp),
            textAlign = TextAlign.Center

        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_settings),
                contentDescription = "Setting",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, placeHolder: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {

                onValueChange(it)
            },
            label = { Text(text = label) },
            placeholder = {
                Text(
                    text = placeHolder,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )
    }
}

@Composable
fun Profile_Content(modifier: Modifier = Modifier) {
// State for each input field
    var ho by remember { mutableStateOf("") }
    var tenDemTen by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_home_black_24dp),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                        )

                    }
                }
                Row {
                    Text(
                        text = "Ho Khanh Dang",
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        InputField("Số điện thoại", phoneInput, { phoneInput = it }, "Ví dụ: 0987654321")
        InputField("Email", emailInput, { emailInput = it }, "Ví dụ: a@gmail.com")
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Change password")
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log out")
        }
    }
    // Input Fields
}

@Composable
fun ProfileForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Profile_Header()
        Profile_Content()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileFormPreview() {
    ProfileForm()
}
