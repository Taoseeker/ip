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

        taskList.addTask(mockTask);

        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task"));
        assertTrue(output.contains("Mock Task"));
    }

    @Test
    void testDeleteTask_ValidIndex() {
        Task mockTask = mock(Task.class);
        when(mockTask.toString()).thenReturn("Mock Task");

        taskList.addTask(mockTask);
        taskList.delete("1");

        String output = outContent.toString();
        assertTrue(output.contains("Noted. I've removed this task"));
        assertTrue(output.contains("Mock Task"));
    }
}
