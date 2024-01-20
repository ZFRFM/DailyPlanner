package com.example.dailyplanner.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dailyplanner.DailyPlannerTopAppBar
import com.example.dailyplanner.R
import com.example.dailyplanner.data.Task
import com.example.dailyplanner.ui.AppViewModelProvider
import com.example.dailyplanner.ui.home.Hours.hours
import com.example.dailyplanner.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import  java.util.Calendar
import java.util.Date
import java.util.Locale

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToTaskEntry: () -> Unit,
    navigateToTaskUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val datePickerState: DatePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Calendar.getInstance().timeInMillis
    )

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DailyPlannerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.task_entry_title)
                )
            }
        }
    ) {
        innerPadding ->
        HomeBody(
            taskList = homeUiState.taskList,
            onTaskClick = navigateToTaskUpdate,
            datePickerState = datePickerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeBody(
    taskList: List<Task>,
    onTaskClick: (Int) -> Unit,
    datePickerState: DatePickerState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val day = datePickerState.selectedDateMillis!!
        Calendar(
            datePickerState = datePickerState,
            modifier = modifier
        )
        TaskList(
            taskList = taskList.filter {
                it.dateStart in day..day + 86_399_999
            },
            onTaskClick = { onTaskClick(it.id) },
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    datePickerState: DatePickerState,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        DatePicker(state = datePickerState, modifier = Modifier)
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
        Text(
            text = "Задачи на ${formatter.format(Date(datePickerState.selectedDateMillis!!))}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskList(
    taskList: List<Task>,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        hours.forEach { hour ->
            stickyHeader {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                    text = hour.first
                )
            }
            items(items = taskList.filter { it.dateStart!! % 86_400_000L in hour.second }) { task ->
                TaskItem(
                    task = task,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onTaskClick(task) }
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    /**
    * Я пытался в timeText получить formatter для времени с самарским часовым поясом, не вышло.
    * Поэтому использовал такое решение. На TaskDetailsScreen такое же решение.
    */
    val minuteValue = ((task.dateStart!! % 86_400_000L) % 3_600_000) / 60_000
    val timeText = "${(task.dateStart % 86_400_000L)/3_600_000}:$minuteValue" +
            if (minuteValue == 0L) "0" else ""

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_extra_small))
        ) {
            Text(
                text = task.name,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small))
            )
            Text(
                text = timeText,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small))
            )
        }
    }
}