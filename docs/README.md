<!-- ChatTPG User Guide -->
# ChatTPG User Guide

ChatTPG is a lightweight, keyboard‑only task assistant that helps you track three kinds of tasks:

* Todo – simple description, no date
* Deadline – description + one due date/time (/by)
* Event – description + a start (/from) and end (/to)

All data is auto‑saved to `tasks/tasks.txt` whenever you add, delete, mark, or unmark a task. You can safely exit and your tasks will persist.

---
## 1. Quick Start
1. Run the program – you start at the Main Menu.
2. Type `open task organiser` to manage tasks.
3. Add tasks with the formats below.
4. Use `list`, `mark done`, `mark undone`, `find`, or `delete task` as needed.
5. Type `exit task organiser` (or `bye`) to return to Main Menu.
6. Type `bye` again at the Main Menu to fully exit.

Need help inside the Task Organiser? Type `help`.

---
## 2. Main Menu Commands

<br/>

| Command | Action |
|---------|--------|
| `open task organiser` | Enter the task management interface |
| `bye` | Exit the whole application (from main menu) |

<br/>

When inside the organiser, `bye` or `exit task organiser` returns you to the main menu instead of quitting immediately.

---
## 3. Task Organiser Commands

<br/>

| Command Pattern | Description | Example |
|-----------------|-------------|---------|
| `todo <desc>` | Add a todo task | `todo read book` |
| `deadline <desc> /by <when>` | Add a deadline task | `deadline return book /by 2025-10-01` |
| `event <desc> /from <start> /to <end>` | Add an event task | `event project meeting /from 2025-10-03 1400 /to 2025-10-03 1600` |
| `list` | Show all tasks with numbers | `list` |
| `mark done` | Mark a task done (will prompt for number) | `mark done` → enter `2` |
| `mark undone` | Mark a task undone (will prompt for number) | `mark undone` → enter `2` |
| `delete task` | Delete a task (will prompt for number) | `delete task` → enter `3` |
| `find` | Search by a SINGLE keyword (prompts for it) | `find` → enter `book` |
| `help` | Show command summary | `help` |
| `exit task organiser` / `bye` | Return to main menu | `bye` |

<br/>

Notes:
* The `find` feature only accepts one word (no spaces). If you enter multiple words it will warn you.
* Task numbers are 1‑based (as shown in the `list` output).
* Invalid indices or malformed commands show an error without crashing.

---
## 4. Task Display Format
Each listed task looks like:

```
1. [T][ ] read book
2. [D][✓] return book (by: 2025-10-01)
3. [E][ ] project meeting (from: 2025-10-03 1400 to: 2025-10-03 1600)
```

Legend:
* First bracket: Task type (`T`, `D`, `E`).
* Second bracket: Status – `[✓]` done, `[ ]` not done.

---
## 5. Storage Format
Tasks are stored in `tasks/tasks.txt` using a pipe‑delimited line format:

```
T | 0 | read book
D | 1 | return book | 2025-10-01
E | 0 | project meeting | 2025-10-03 1400 | 2025-10-03 1600
```

Meaning:
* First token: `T` (Todo), `D` (Deadline), `E` (Event)
* Second: `1` if done, `0` if not done
* Third: description
* Deadline adds: `| <by>`
* Event adds: `| <from> | <to>`

The file is recreated automatically if missing; no manual setup required.

---
## 6. Examples
Add a todo:
```
todo read book
```
Add a deadline:
```
deadline return book /by 2025-10-01
```
Add an event:
```
event project meeting /from 2025-10-03 1400 /to 2025-10-03 1600
```
Mark task 2 done:
```
mark done
2
```
Find tasks with keyword `book`:
```
find
book
```

---
## 7. Error Handling & Tips

<br/>

| Situation | What Happens |
|-----------|--------------|
| Unknown command | Shows an error + how to view help |
| Invalid task number | Explains valid range |
| Malformed add command | Shows correct format example |
| Multi-word search keyword | Rejected (keyword must be exactly one word) |

<br/>

Tips:
* Keep descriptions concise but searchable.
* Use consistent date formats for easier reading (e.g., `YYYY-MM-DD`).
* You can list tasks anytime to re-orient yourself.

---
## 8. Exiting
* Inside organiser: `exit task organiser` or `bye` → returns to main menu.
* From main menu: `bye` → saves (already auto-saved on every change) and exits.

Your tasks are always saved immediately after each change—no manual save needed.

---
## 9. FAQ
**Q: Do I lose tasks if I close abruptly?**  
Changes are flushed on every modifying command. Only an OS-level forced kill during a disk write is risky.

**Q: Can I edit an existing task’s text?**  
Not yet. Delete and re-add the task for now.

**Q: Can I use spaces in the find keyword?**  
No – `find` accepts exactly one word (no internal spaces). Leading or trailing whitespace is trimmed.

---
Happy tasking with ChatTPG!