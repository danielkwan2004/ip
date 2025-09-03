public class Task {

    private Boolean isDone;
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
        if (taskNumber < 0 || taskNumber >= 100) {
            System.out.println("You have gone out of bounds. This task number does not exist.");
            return;
        }
        int taskIndex = taskNumber - 1;
        taskList[taskCount]
        System.out.println("Nice! I've marked task number %d as done. The following is the name of the task: ", taskNumber);
        System.out.println("  " + )



    }

}
