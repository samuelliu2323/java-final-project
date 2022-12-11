/**
 * Creates an account menu enum
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public enum AccountMenu {
        MANAGE_EVENTS,
        CHANGE_PASSWORD,
        CHANGE_NAME,
        BACK;
        private int selection;
        private String description;

        AccountMenu(int selection, String description) {
            this.selection = selection;
            this.description = description;
        }
        public static String getMenuOptions() {
            String prompt = "****************\t Evite Menu\t****************";

            for(AccountMenu m : AccountMenu.values()){ //array from the enum
                prompt += "\n" + (m.ordinal()+1)+ ": " + m;
            }
            prompt+="\n**********************************************\n";
            return prompt;
        }
        public static AccountMenu getOption(int num) {
            return AccountMenu.values()[num - 1];
        }
        public static int getNumOptions() {
            return AccountMenu.values().length;
        }
        AccountMenu() {
        }
    }
