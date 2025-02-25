# NeoChat

NeoChat is a task management chatbot that allows users to manage their tasks using simple text commands. It supports different types of tasks such as **Todo, Deadline, and Event**, and provides features like task tracking, archiving, and searching.

## Features
- 📋 **Manage Tasks**: Add, delete, and list tasks efficiently.
- ⏳ **Track Deadlines**: Set deadlines for tasks with time and date.
- 📅 **Event Scheduling**: Schedule events with start and end times.
- ✅ **Mark Tasks**: Mark tasks as done or not done.
- 🔍 **Search for Tasks**: Find tasks using keywords.
- 📂 **Archive Old Tasks**: Move completed tasks to an archive.
- 💾 **Persistent Storage**: Tasks are saved in `/src/data/`, ensuring they persist across sessions.

## Installation
### Prerequisites
- Ensure you have **Java 11 or later** installed.

### Steps
1. **Download the latest release** from the [GitHub Releases](https://github.com/Taoseeker/ip/releases).
2. **Extract the downloaded file** to a suitable location.
3. **Run the application** using:
   ```sh
   java -jar NeoChat.jar
   ```

## Usage
### List all tasks
```
list
```

### Add a Todo task
```
todo <description>
```
Example:
```
todo Buy groceries
```

### Add a Deadline task
```
deadline <description> /by <yyyy-MM-dd HH:mm>
```
Example:
```
deadline Submit assignment /by 2025-02-28 23:59
```

### Add an Event task
```
event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>
```
Example:
```
event Project meeting /from 2025-02-25 14:00 /to 2025-02-25 16:00
```

### Mark a task as done
```
mark <task number>
```
Example:
```
mark 1
```

### Unmark a task
```
unmark <task number>
```
Example:
```
unmark 1
```

### Delete a task
```
delete <task number>
```
Example:
```
delete 2
```

### Find a task
```
find <keyword>
```
Example:
```
find project
```

### Archive a task
```
archive <task number>
```
Example:
```
archive 3
```

### Load archived tasks
```
loadarchive
```

### Exit the application
```
bye
```

## Future Development Plans
- ✏️ **Edit Feature**: Allow modification of existing tasks.
- 📁 **Custom Save Path**: Let users define where tasks are stored.

## Support
For issues and feature requests, please open an issue in the [GitHub repository](https://github.com/Taoseeker/ip/issues).


