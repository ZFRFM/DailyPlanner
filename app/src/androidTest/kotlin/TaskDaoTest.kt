import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyplanner.data.DailyPlannerDatabase
import com.example.dailyplanner.data.Task
import com.example.dailyplanner.data.TaskDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var taskDao: TaskDao
    private lateinit var dailyPlannerDatabase: DailyPlannerDatabase

    private var task1 = Task(1, 1705500000000, 1705501000000, "Task 1", "Desc for test task 1")
    private var task2 = Task(2, 1705800000000, 1705810000000, "Task 2", "Desc for test task 2")

    private suspend fun addOneTaskToDb() {
        taskDao.insertTask(task1)
    }
    private suspend fun addTwoTasksToDb() {
        taskDao.insertTask(task1)
        taskDao.insertTask(task2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsTaskIntoDB() = runBlocking {
        addOneTaskToDb()
        val allItems = taskDao.getAllTasks().first()
        assertEquals(allItems[0], task1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoTasksToDb()
        val allTasks = taskDao.getAllTasks().first()
        assertEquals(allTasks[0], task1)
        assertEquals(allTasks[1], task2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateTasks_updatesTasksInDB() = runBlocking {
        addTwoTasksToDb()
        taskDao.updateTask(Task(1, 1705600000000, 1705601000000, "Updated Task 1", "Desc for UT1"))
        taskDao.updateTask(Task(2, 1705900000000, 1705910000000, "Updated Task 2", "Desc for UT2"))
        val allTasks = taskDao.getAllTasks().first()
        assertEquals(allTasks[0], Task(1, 1705600000000, 1705601000000, "Updated Task 1", "Desc for UT1"))
        assertEquals(allTasks[1], Task(2, 1705900000000, 1705910000000, "Updated Task 2", "Desc for UT2"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteTasks_deletesAllTasksFromDB() = runBlocking {
        addTwoTasksToDb()
        taskDao.deleteTask(task1)
        taskDao.deleteTask(task2)
        val allTasks = taskDao.getAllTasks().first()
        assertTrue(allTasks.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetTask_returnsTaskFromDB() = runBlocking {
        addOneTaskToDb()
        val task = taskDao.getTask(1)
        assertEquals(task.first(), task1)
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        dailyPlannerDatabase = Room.inMemoryDatabaseBuilder(context, DailyPlannerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = dailyPlannerDatabase.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        dailyPlannerDatabase.close()
    }
}