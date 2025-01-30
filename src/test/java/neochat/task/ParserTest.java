package neochat.task;

import neochat.task.tasklist.TaskList;
import neochat.task.taskexception.EmptyTaskDescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    private TaskList mockTaskList;
    private Parser parser;

    @BeforeEach
    void setUp() {
        mockTaskList = Mockito.mock(TaskList.class);
        parser = new Parser(mockTaskList);
    }

    @Test
    void parseCommand_ByeCommand_SetsIsExitToTrue() {
        parser.parseCommand("bye");
        assertTrue(parser.isExit());
    }

    @Test
    void parseDateTime_InvalidFormat_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parseDateTime("2023/10/10 12:00");
        });
    }
}
