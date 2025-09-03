public class Task {

    static int MAX_TASKS = 100;

    String[] taskList = new String[MAX_TASKS];
    int taskCount = 0;

    public void addTask(String description) {
        taskList[taskCount] = "[ ] " + description;
        System.out.println("\tadded: " + taskList[taskCount]);
        taskCount++;
    }

    public void listTask() {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + ". " + taskList[i]);
        }
    }    

    public void markTaskAsDone(int taskNumber) {
        if (taskNumber < 0 || taskNumber >= taskCount) {
            System.out.println("You have gone out of bounds. This task number does not exist.");
            return;
        }
        int taskIndex = taskNumber - 1;
        taskList[taskIndex] = "[X] " + taskList[taskIndex].substring(4);
        System.out.printf("Nice! I've marked task number %d as done. The following is the name of the task: \n", taskNumber);
        System.out.println("  " + taskList[taskIndex]);
    }

    public void markTaskAsUndone(int taskNumber) {
        if (taskNumber < 0 || taskNumber >= taskCount) {
            System.out.println("You have gone out of bounds. This task number does not exist.");
            return;
        }
        int taskIndex = taskNumber - 1;
        taskList[taskIndex] = "[ ] " + taskList[taskIndex].substring(4);
        System.out.printf("Noted. I've marked task number %d as undone. The following is the name of the task: \n", taskNumber);
        System.out.println("  " + taskList[taskIndex]);
    }

    public void deleteTask(int taskNumber) {
        if (taskNumber < 0 || taskNumber >= taskCount) {
            System.out.println("You have gone out of bounds. This task number does not exist.");
            return;
        }
        int taskIndex = taskNumber - 1;
        System.out.printf("Noted. I've removed task number %d. The following is the name of the task: \n", taskNumber);
        System.out.println("  " + taskList[taskIndex]);
        for (int i = taskIndex; i < taskCount - 1; i++) {
            taskList[i] = taskList[i + 1];
        }
        taskList[taskCount - 1] = null; // Clear the last element
        taskCount--;
    }

}
