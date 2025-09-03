// Add the ability to store whatever text entered by the user and display them back to the user when requested.
public class Task {
    

    String[] taskList = new String[100];
    int taskCount = 0;

    public void addTask(String description){
        taskList[taskCount] = description;
        taskCount++;
        System.out.println("\tadded: " + description);
    }

    public void listTask(){
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + ". " + taskList[i]);
        }
    }    
}
