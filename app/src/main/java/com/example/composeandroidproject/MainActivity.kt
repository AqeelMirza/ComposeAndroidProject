package com.example.composeandroidproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeandroidproject.ui.theme.ComposeAndroidProjectTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            ComposeAndroidProjectTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            elevation = 0.dp,
                            title = { Text(text = getString(R.string.transaction_history)) },
                            navigationIcon =
                            {
                                IconButton(onClick = { finish() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            actions = {
                                // RowScope here, so these icons will be placed horizontally
                                IconButton(onClick = {
                                    showToast(context = context, "Search button pressed")
                                }
                                ) {
                                    Icon(
                                        Icons.Filled.Search,
                                        contentDescription = "Search description"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Box {
                        Column {
                            AmountHeader(amount = 10000)
                            ChipsLayout(viewModel)
                            DisplayListOfMessage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AmountHeader(amount: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF6200EE))
            .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = "Bank Account balance", color = Color.White, fontSize = 16.sp)
        Text(
            text = "$$amount",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ChipsLayout(
    viewModel: MainViewModelInterface = MainViewModel.composeViewModel
) {
    val context = LocalContext.current
    ChipGroupSingleSelection(
        chips = listOf(*ChipCategories.values()),
        selectedCategory = viewModel.selectedCar.value,
        onSelectedChanged = { changedSelection ->
            viewModel.selectedCar.value = ChipCategories.valueOf(changedSelection)
            showToast(context = context, changedSelection)
        }
    )
}

@Composable
fun ChipGroupSingleSelection(
    modifier: Modifier = Modifier,
    chips: List<ChipCategories> = listOf(*ChipCategories.values()),
    selectedCategory: ChipCategories? = ChipCategories.All,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background((colorResource(id = R.color.purple_200)))
            .padding(16.dp)
    ) {
        LazyRow {
            items(chips) { item ->
                Chip(
                    name = item.value,
                    isSelected = selectedCategory == item,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                ) {
                    Text(
                        text = item.name,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun enterText(): String {
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by remember { mutableStateOf("") }
    ProvideTextStyle(TextStyle(color = Color.White)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            maxLines = 1,
            label = {
                Text(
                    text = "Enter Name",
                    color = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
    }
    return text
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayListOfMessage() {
    val messages1: List<Message> = listOf(
        Message("Message 1", "Title 1", 14),
        Message("Message 2", "Title 2"),
        Message("Message 3", "Title 3"),
        Message("Message 4", "Title 4", 8),
        Message("Message 5", "Title 5"),
        Message("Message 1", "Title 1"),
        Message("Message 2", "Title 2", 40),
        Message("Message 3", "Title 3"),
        Message("Message 4", "Title 4", 100),
        Message("Message 5", "Title 5"),
        Message("Message 1", "Title 1"),
        Message("Message 2", "Title 2", 5),
        Message("Message 3", "Title 3", 1),
        Message("Message 4", "Title 4", 5),
        Message("Message 5", "Title 5", 6)
    )
    val messages2: List<Message> = listOf(
        Message("Message 6", "Title 6"),
        Message("Message 7", "Title 7", 1),
        Message("Message 8", "Title 8"),
        Message("Message 9", "Title 9"),
        Message("Message 10", "Title 10", 5),
        Message("Message 6", "Title 6"),
        Message("Message 7", "Title 7", 5),
        Message("Message 8", "Title 8"),
        Message("Message 9", "Title 9"),
        Message("Message 10", "Title 10"),
        Message("Message 6", "Title 6"),
        Message("Message 7", "Title 7", 54),
        Message("Message 8", "Title 8"),
        Message("Message 9", "Title 9"),
        Message("Message 10", "Title 10", 12)
    )
    val messages3: List<Message> = listOf(
        Message("Message 11", "Title 11"),
        Message("Message 12", "Title 12"),
        Message("Message 13", "Title 13", 4),
        Message("Message 14", "Title 14", 45),
        Message("Message 15", "Title 15", 45),
        Message("Message 11", "Title 11"),
        Message("Message 12", "Title 12", 12),
        Message("Message 13", "Title 13"),
        Message("Message 14", "Title 14", 900),
        Message("Message 15", "Title 15"),
        Message("Message 13", "Title 13"),
        Message("Message 14", "Title 14", 6),
        Message("Message 15", "Title 15"),
        Message("Message 13", "Title 13"),
        Message("Message 14", "Title 14"),
        Message("Message 15", "Title 15")

    )
    val messagesWithHeader = mapOf(
        "Head 1" to (messages1),
        "Head 2" to (messages2),
        "Head 3" to (messages3)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        messagesWithHeader.forEach { (head, mess) ->
            stickyHeader {
                Text(
                    text = head,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp)

                )
            }
            items(mess) { message ->
                //MessageRow(message)
                ListItemView(message = message)
            }
        }
    }
}

@Composable
private fun CustomImageChip(
    title: String,
    imageId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelectionChanged: (String) -> Unit = {}
) {
    // define properties to the chip
    // such as color, shape, width
    Surface(
        color = when {
            selected -> MaterialTheme.colors.primary
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onPrimary
            else -> Color.LightGray
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colors.primary
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        // Inside a Row pack the Image and text together to
        // show inside the chip
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .toggleable(
                    value = selected,
                    onValueChange = {
                        onSelectionChanged(title)
                    })
        ) {
            Image(
                painter = painterResource(imageId),
                colorFilter = ColorFilter.tint(
                    Color.White
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            Text(
                text = title,
                style = typography.body2,
                modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun ListItemView(message: Message) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Text(
                text = message.title,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            when {
                message.amount > 10 -> {
                    Text(
                        text = "+$${message.amount}",
                        color = Color.Green
                    )
                }
                message.amount < 10 -> {
                    Text(
                        text = "-$${message.amount}",
                        color = Color.Red
                    )
                }
                message.amount == 10 -> {
                    Text(
                        text = "$${message.amount}",
                        color = Color.Black
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            if (message.amount < 10) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_time_black_16dp),
                        contentDescription = "content description",
                        colorFilter = ColorFilter.tint(
                            Color.Gray
                        ),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = message.msg, color = Color.Gray)
                }
            } else {
                Text(text = message.msg, color = Color.Gray)
            }


            Text(
                text = message.time,
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

data class Message(
    val msg: String,
    val title: String,
    val amount: Int = 10,
    val time: String = "10:30 pm"
)
