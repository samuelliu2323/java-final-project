import javax.swing.event.MenuListener;
/**
 * Creates an menu enum
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public enum Menu {
    MANAGE_ACCOUNT,
    HOST,
    RSVP,
    LOGOUT,
    QUIT;
    private int selection;
    private String description;

    Menu(int selection, String description) {
        this.selection = selection;
        this.description = description;
    }
    public static String getMenuOptions() {
        String prompt = "****************\t Evite Menu \t****************";

        for(Menu m : Menu.values()){ //array from the enum
            prompt += "\n" + (m.ordinal()+1)+ ": " + m;
        }
        prompt+="\n**********************************************\n";
        return prompt;
    }
    public static Menu getOption(int num) {
        return Menu.values()[num - 1];
    }
    public static int getNumOptions() {
        return Menu.values().length;
    }
    Menu() {
    }
}
