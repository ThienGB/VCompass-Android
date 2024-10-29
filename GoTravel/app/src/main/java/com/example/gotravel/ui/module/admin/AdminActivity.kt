
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R
import com.example.gotravel.ui.module.admin.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPartnerScreen(users: List<User>, partners: List<User>) {
    var selectedUser by remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            selectedUser?.let { user ->
                BottomSheetContent(
                    user = user,
                    onBanUser = { /* Handle ban logic */ },
                    onDeactivate = { /* Handle deactivate logic */ }
                )
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // List of Users
            Text("Users", style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(bottom = 8.dp))
            UserList(users = users, onLongPress = { user ->
                selectedUser = user
                scope.launch {
                    scaffoldState.bottomSheetState.expand() // Expand the BottomSheet
                }
            })

            Spacer(modifier = Modifier.height(16.dp))

            // List of Partners
            Text("Partners", style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(bottom = 8.dp))
            UserList(users = partners, onLongPress = { user ->
                selectedUser = user
                scope.launch {
                    scaffoldState.bottomSheetState.expand() // Expand the BottomSheet
                }
            })
        }
    }
}

@Composable
fun UserList(users: List<User>, onLongPress: (User) -> Unit) {
    Column {
        users.forEach { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable(onClick = { onLongPress(user) }),
                horizontalArrangement =  androidx.compose.foundation.layout.Arrangement.Start,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_home_black_24dp),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )

                }

                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(text = user.name, fontSize = 16.sp)
                    Text(text = "ID: ${user.id}", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth() // Set the width for a vertical line
                    .height(1.dp) // Set the desired height
            )
        }
    }
}

@Composable
fun BottomSheetContent(user: User, onBanUser: () -> Unit, onDeactivate: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Actions for ${user.name}", fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))
        Button(onClick = onBanUser, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            Text("Ban User")
        }
        Button(onClick = onDeactivate, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            Text("Deactivate User")
        }
    }
}

@Composable
fun MyApp() {
    val users = listOf(
        User("1", "Alice", R.drawable.ic_launcher_foreground),
        User("2", "Bob", R.drawable.ic_launcher_foreground)
    )
    val partners = listOf(
        User("3", "Charlie", R.drawable.ic_launcher_foreground),
        User("4", "Dave", R.drawable.ic_launcher_foreground)
    )

    UserPartnerScreen(users = users, partners = partners)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

