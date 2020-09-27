# User Guide

## Features 

### Track tasks 
This program helps you to keep track of upcoming events, deadlines or any tasks to complete in general.
At anytime, you can mark a task as done, or delete it from the list. If you have a long list of tasks,
you can search for a task with a particular keyword too.

### Persistent storage
The program stores the list of tasks locally on your computer using a readable format,
and it could be transferred to another computer if needed.

## Usage

### `todo` - Adds a general task to the list

Creates a task with the given description and stores it in the list of existing tasks.
`todo <description>`

Example of usage: 

```
> todo borrow book
```

Expected outcome:

```
  Added: [T][✘] borrow book
  You now have 6 tasks in your list.
```

### `event` - Adds an upcoming event to the list
Creates an event with the given description and date, then stores it in the list of existing tasks.
`event [description] /at [when (format: dd/mm/yy hhmm)]`

Example of usage:

```
> event presentation /at 27/09/20 1000
```

Expected outcome:

```
  Added: [E][✘] presentation (at: Sun Sep 27 10:00:00 SGT 2020)
  You now have 7 tasks in your list.
```

### `deadline` - Adds a task with a deadline to the list
Creates a task with the given description and deadline, then stores it in the list of existing tasks.
`deadline [description] /by [when (format: dd/mm/yy hhmm)]`

Example of usage:

```
> deadline tutorial /by 27/09/20 1000
```

Expected outcome:

```
  Added: [D][✘] tutorial (at: Sun Sep 27 10:00:00 SGT 2020)
  You now have 7 tasks in your list.
```

### `done` - Marks a task as done

Marks a task in the list as done based on the given index.
`done <index>`

Example of usage: 

```
> done 1
```

Expected outcome:

```
  Ok! I've marked this task as done:
    [E][✓] CS2113 lecture (at: Fri Sep 18 16:00:00 SGT 2020)
```

### `delete` - Deletes a task

Deletes a task from the list based on the given index.
`done <index>`

Example of usage: 

```
> delete 1
```

Expected outcome:

```
  Ok! I've deleted this task:
    [E][✓] CS2113 lecture (at: Fri Sep 18 16:00:00 SGT 2020)
```

### `find` - Searches for tasks

Searches for tasks that contains the given text.
`find <filter>`

Example of usage: 

```
> find lecture
```

Expected outcome:

```
  Here are the tasks that contains the text "lecture"
  1. [T][✘] cs1231 lecture
  2. [T][✘] no lecture
  3. [T][✘] skip lecture aaa
```