import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gotravel.R
import retrofit2.http.Header

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

@Composable
fun Header(){
    Row (
        modifier = Modifier.fillMaxWidth().background(colorResource(id = R.color.primary)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,

    ){
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
        ) {
            Text(text = "<")
        }
        Text(
            text = "Thông tin khách",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),)
        {
            Text(text = ":")
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
        TextField(
            placeholder = ({ Text(text = placeHolder) }),
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.White, RoundedCornerShape(8.dp))  // Border with rounded corners
                .background(Color.White, RoundedCornerShape(8.dp))  // Background with rounded corners
                .padding(horizontal = 3.dp, vertical = 3.dp),
        )
    }
}

@Composable
fun ProfileForm(modifier: Modifier = Modifier) {
    // State for each input field
    var ho by remember { mutableStateOf("") }
    var tenDemTen by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()

            .background(Color.White)
    ) {
        Header()
        // Title
        Text(
            text = "Thông tin khách",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Fields
        InputField("Họ", ho, { ho = it }, "Ví dụ: Nguyễn")
        InputField("Tên đệm và tên", tenDemTen, { tenDemTen = it }, "Ví dụ: Tuấn Anh")
        InputField("Số điện thoại", phoneInput, { phoneInput = it }, "Ví dụ: 0987654321")
        InputField("Email", emailInput, { emailInput = it }, "Ví dụ: a@gmail.com")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileFormPreview() {
    ProfileForm()
}
