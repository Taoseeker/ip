# NeoChat User Guide

## Table of Contents
- [Introduction](#introduction)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Features](#features)
    - [List all tasks](#list-all-tasks)
    - [Add a Todo task](#add-a-todo-task)
    - [Add a Deadline task](#add-a-deadline-task)
    - [Add an Event task](#add-an-event-task)
    - [Mark a task as done](#mark-a-task-as-done)
    - [Unmark a task](#unmark-a-task)
    - [Delete a task](#delete-a-task)
    - [Find a task](#find-a-task)
    - [Archive a task](#archive-a-task)
    - [Load archived tasks](#load-archived-tasks)
    - [Exit the application](#exit-the-application)
- [Customization](#customization)
- [FAQ](#faq)
- [Support](#support)

## Introduction
NeoChat is a task management chatbot designed to help you organize your tasks efficiently through a chat interface.

## Installation
1. Ensure you have Java 17 or later installed.
2. Download the latest release from the [GitHub Releases](https://github.com/Taoseeker/ip/releases).
3. Extract the downloaded file to a suitable location.
4. Open a terminal and navigate to the project directory.

## Running the Application
Run the application using the following command:
```sh
java -jar neochat.jar
```

## Features

### List all tasks
Displays all current tasks.
```
list
```

### Add a Todo task
Adds a task without a date/time.
```
todo <description>
```
Example:
```
todo Buy groceries
```

### Add a Deadline task
Adds a task with a specific deadline.
```
deadline <description> /by <yyyy-MM-dd HH:mm>
```
Example:
```
deadline Submit assignment /by 2025-02-28 23:59
```

### Add an Event task
Adds a task with a start and end time.
```
event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>
```
Example:
```
event Project meeting /from 2025-02-25 14:00 /to 2025-02-25 16:00
```

### Mark a task as done
Marks a specified task as completed.
```
mark <task number>
```
Example:
```
mark 1
```

### Unmark a task
Marks a specified task as not completed.
```
unmark <task number>
```
Example:
```
unmark 1
```

### Delete a task
Removes a task from the list.
```
delete <task number>
```
Example:
```
delete 2
```

### Find a task
Searches for tasks containing a specific keyword.
```
find <keyword>
```
Example:
```
find project
```

### Archive a task
Moves a task to the archive.
```
archive <task number>
```
Example:
```
archive 3
```

### Load archived tasks
Loads archived tasks back into the active list.
```
loadarchive
```

### Exit the application
Saves the current task list and exits.
```
bye
```

## Customization
You can modify the chat UI appearance by changing the `style.css` file located in the `/styles/` directory.

## FAQ
**Q: What happens if I close the application?**  
A: Your tasks are automatically saved and will be available when you restart NeoChat.

**Q: Can I edit a task after adding it?**  
A: Currently, tasks cannot be edited once added. You can delete and re-add a task with the updated description.

## Support
For any issues or feature requests, please create an issue in the [GitHub repository](https://github.com/Taoseeker/ip/issues).
