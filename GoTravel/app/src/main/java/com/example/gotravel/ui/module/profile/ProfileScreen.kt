import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.ui.module.booking.BookingListActivity
import com.example.gotravel.ui.module.main.login.MainLoginActivity

@Preview(showSystemUi = true)
@Composable
fun ProfileScreen(
    user: UserAccount = UserAccount(),
    navController: NavController = NavController(LocalContext.current),
    onSaveClick: (UserAccount, Context) -> Unit ={ _, _ -> },
    intentToBooking: () -> Unit = {},
    handleLogout: () -> Unit = {},
    calledBy: String = "user"
){
    val fullName = remember { mutableStateOf(user.fullName) }
    val phone = remember { mutableStateOf(user.phone) }
    val context = LocalContext.current
    val editMode = remember { mutableStateOf(false) }
    Column (modifier = Modifier
        .background(Color.White)
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                painter = painterResource(id = R.drawable.bg_profile),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(100.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_admin),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Row {
                    IconButton(
                        onClick = {navController.popBackStack()},
                        modifier = Modifier
                            .padding(16.dp)
                            .size(30.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.15f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(35.dp),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (!editMode.value) {
                        IconButton(
                            onClick = {editMode.value = true},
                            modifier = Modifier
                                .padding(16.dp)
                                .size(30.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.15f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Back",
                                modifier = Modifier.size(35.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            Text(text = user.fullName, color = Color.White,
                fontWeight = Bold,
                fontSize = 20.sp,  modifier = Modifier
                    .padding(top = 180.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(top = 230.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    HorizontalDivider(thickness = 0.5.dp)
                    InforProfile("Họ tên", fullName.value,Icons.Default.Person, editMode.value)
                    {value -> fullName.value = value}
                    HorizontalDivider(thickness = 0.5.dp)
                    InforProfile("Email", user.email, Icons.Default.Email)
                    HorizontalDivider(thickness = 0.5.dp)
                    InforProfile("Số điện thoại", phone.value,
                        Icons.Default.Call, editMode.value)
                    {value -> phone.value = value}
                    HorizontalDivider(thickness = 0.5.dp)
                    InforProfile("Vai trò", user.role, Icons.Default.Settings)
                    if (!editMode.value) {
                        if (calledBy == "user") {
                            HorizontalDivider(thickness = 0.5.dp)
                            Row(modifier = Modifier.clickable { intentToBooking() }
                                .padding(vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Back",
                                    modifier = Modifier.size(20.dp),
                                    tint = colorResource(id = R.color.primary)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Danh sách đặt phòng",
                                    color = colorResource(id = R.color.primary),
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Back",
                                    modifier = Modifier.size(20.dp),
                                    tint = colorResource(id = R.color.primary)
                                )

                            }
                        }
                        HorizontalDivider(thickness = 0.5.dp)
                        Row(modifier = Modifier.clickable { handleLogout()
                            val intent = Intent(context, MainLoginActivity::class.java)
                            context.startActivity(intent)
                        }
                            .padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Đăng xuất",
                                color = Color.Red,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Red
                            )

                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (editMode.value) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorResource(id = R.color.primary))
                    .clickable {
                        onSaveClick(
                            UserAccount(
                            userId = user.userId,
                            email = user.email,
                            role = user.role,
                            status = user.status,
                            fullName = fullName.value,
                            phone = phone.value
                        ), context)
                        editMode.value = false
                        Toast
                            .makeText(context, "Lưu thành công", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(25.dp),
                    colorFilter = ColorFilter.tint(Color.White))
                Text(text = "Lưu thay đổi", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp, end = 20.dp))
            }
        }
    }
}
@Composable
fun InforProfile(
    field: String,
    value: String,
    icon: ImageVector,
    editMode: Boolean = false,
    onValueChange: (String) -> Unit = {}
) {
    Row(modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "Back",
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = field,
            color = Color.Gray,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.weight(1f))

        if (editMode) {
            BasicTextField(
                value = value,
                onValueChange = { value -> onValueChange(value) },
                textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                modifier = Modifier.border(0.dp, Color.Transparent)
            )
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Back",
                modifier = Modifier.size(20.dp),
            )
        } else {
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}