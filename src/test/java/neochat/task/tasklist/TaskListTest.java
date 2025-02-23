package neochat.task.tasklist;

import neochat.task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TaskListTest {
    private TaskList taskList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testAddTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.toString()).thenReturn("Mock Task");

        String output = taskList.addTask(mockTask);
        assertTrue(output.contains("Got it. I've added this task"));
        assertTrue(output.contains("Mock Task"));
    }

    @Test
    void testDeleteTask_ValidIndex() {
        Task mockTask = mock(Task.class);
        when(mockTask.toString()).thenReturn("Mock Task");

        taskList.addTask(mockTask);

        String output = taskList.delete("1");
        assertTrue(output.contains("Noted. I've removed this task"));
        assertTrue(output.contains("Mock Task"));
    }

    @Test
    void testDeleteTask_invalidIndex() {
        Task mockTask = mock(Task.class);
        when(mockTask.toString()).thenReturn("Mock Task");

        taskList.addTask(mockTask);

        String output = taskList.delete("1000");
        assertTrue(output.contains("Invalid task number."));
    }

    @Test
    void testDeleteTask_invalidInputFormat() {
        Task mockTask = mock(Task.class);
        when(mockTask.toString()).thenReturn("Mock Task");

        taskList.addTask(mockTask);

        String output = taskList.delete("I hate cs2103T");
        assertTrue(output.contains("Invalid input."));
    }
}
