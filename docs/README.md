# Wiz User Guide

Wiz is an intelligent desktop assistant and task manager tailored for tracking tasks, events, deadlines, and client contacts.

---

## Features

### Task Management

#### Adding a To-Do: `todo`
Adds a new to-do task to your list.

Format: `todo <description>`

Example: `todo read book`

Expected Output:
```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
```

#### Adding a Deadline: `deadline`
Adds a task that needs to be done before a specific date/time (`yyyy-MM-dd HHmm`).

Format: `deadline <description> /by <yyyy-MM-dd HHmm>`

Example: `deadline return book /by 2026-10-15 1800`

Expected Output:
```
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2026, 6:00PM)
Now you have 2 tasks in the list.
```

#### Adding an Event: `event`
Adds a task that occurs over a specific time range (`yyyy-MM-dd HHmm`).

Format: `event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`

Example: `event project meeting /from 2026-10-15 1400 /to 2026-10-15 1600`

Expected Output:
```
Got it. I've added this task:
  [E][ ] project meeting (from: Oct 15 2026, 2:00PM to: Oct 15 2026, 4:00PM)
Now you have 3 tasks in the list.
```

#### Listing Tasks: `list`
Displays all tasks currently stored in your list.

Format: `list`

Expected Output:
```
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Oct 15 2026, 6:00PM)
3.[E][ ] project meeting (from: Oct 15 2026, 2:00PM to: Oct 15 2026, 4:00PM)
```

#### Marking a Task: `mark`
Marks a task as completed.

Format: `mark <index>`

Example: `mark 1`

Expected Output:
```
Nice! I've marked this task as done:
  [T][X] read book
```

#### Unmarking a Task: `unmark`
Marks a completed task as not done yet.

Format: `unmark <index>`

Example: `unmark 1`

Expected Output:
```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

#### Deleting a Task: `delete`
Removes a task from the list at the given index.

Format: `delete <index>`

Example: `delete 1`

Expected Output:
```
Noted. I've removed this task:
  [T][X] read book
Now you have 2 tasks in the list.
```

#### Finding Tasks: `find`
Finds tasks that contain the specified keyword.

Format: `find <keyword>`

Example: `find book`

Expected Output:
```
Here are the matching tasks in your list:
1.[D][ ] return book (by: Oct 15 2026, 6:00PM)
```

---

### Client Management (Extension: D-Clients)

#### Adding a Client: `client`
Adds a new client contact with phone number, email address, and optional notes (e.g. policy details).

Format: `client <name> /phone <phone> /email <email> [/note <note>]`

Examples:
- `client Alice Tan /phone 91234567 /email alice@example.com`
- `client Bob Lee /phone 98765432 /email bob@example.com /note Life insurance policy #1234`

Expected Output:
```
Got it. I've added this client:
  [C] Bob Lee (Phone: 98765432, Email: bob@example.com, Note: Life insurance policy #1234)
Now you have 1 clients in the list.
```

#### Listing Clients: `clients`
Displays all registered clients.

Format: `clients`

Expected Output:
```
Here are the clients in your list:
1.[C] Alice Tan (Phone: 91234567, Email: alice@example.com)
2.[C] Bob Lee (Phone: 98765432, Email: bob@example.com, Note: Life insurance policy #1234)
```

#### Finding Clients: `findclient`
Finds clients whose name, phone number, email, or notes match the keyword.

Format: `findclient <keyword>`

Example: `findclient insurance`

Expected Output:
```
Here are the matching clients in your list:
1.[C] Bob Lee (Phone: 98765432, Email: bob@example.com, Note: Life insurance policy #1234)
```

#### Deleting a Client: `deleteclient`
Removes a client from your client list.

Format: `deleteclient <index>`

Example: `deleteclient 1`

Expected Output:
```
Noted. I've removed this client:
  [C] Alice Tan (Phone: 91234567, Email: alice@example.com)
Now you have 1 clients in the list.
```

---

### General Commands

#### Exiting the Application: `bye`
Exits the application.

Format: `bye`

Expected Output:
```
Bye. Hope to see you again soon!
```
