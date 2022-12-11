/**
 * Creates an Evite System which can be used to make invitations and host events
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;

public class EviteSystem {
    private Map<String, User> userDatabase;
    private Map<String, Party> partyDatabase;
    private User currentUser;
    private Savior bff;

    public EviteSystem() {
        Guest a = new Guest("sam", "sqliu@usc.edu", "password123");
        userDatabase = new HashMap<>();
        partyDatabase = new HashMap<>();
        bff = new Savior();
        currentUser = a;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getUserDatabase() {
        //return all the users in the map, cast to a list instead of a collection
        return new ArrayList<User>(userDatabase.values());
    }

    public List<Party> getPartyDatabase() {
        //return all the users in the map, cast to a list instead of a collection
        return new ArrayList<Party>(partyDatabase.values());
    }

    public Set<String> getAllEmails() {
        //return all the emails in the map, as a Set.
        return userDatabase.keySet(); //get all emails as a set of keys
    }

    public List<Party> getAllParties() {
        return new ArrayList<Party>(partyDatabase.values());
    }

    public List<Party> getAllPartiesByUser(String email) {
        List<Party> userPartyList = new ArrayList<>();
        for (Party p : getAllParties()) {
            if (email.equals(p.getHostEmail())) {
                userPartyList.add(p);
            }
        }
        return userPartyList;
    }

    public List<Party> getInvitesByGuest(String email) {
        List<Party> invitedTo = new ArrayList<>();
        for (Party p : getPartyDatabase()) {
            for (String s : p.getInvited()) {
                if (s.equals(email)) {
                    invitedTo.add(p);
                }
            }
        }
        return invitedTo;
    }


    public List<Party> getRSVPsByGuest(String email){
        List<Party> RSVPedTo = new ArrayList<>();
        for (Party p : getPartyDatabase()) {
            for (String s : p.getInvited()) {
                if (s.equals(email)) {
                    RSVPedTo.add(p);
                }
            }
        }
        return RSVPedTo;
    }



    public User getUserByEmail(String email){
        for(User u: getUserDatabase()){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }
    public User checkPassword(String email, String password) {
        User user = getUserByEmail(email);
        if (getUserByEmail(email) != null) {
            if (!user.checkPassword(password)) {
                user = null;
            }
        }
        return user;
    }
    private void login() { //modified from media example
        System.out.println("Please log in:");
        String email = bff.inputLine("Email: ");
        User u = getUserByEmail(email);
        if (u == null) {
            System.out.println("email not found please create account");
            boolean newAccount = bff.inputYesNo("Would you like to create a new account? (y/n)");
            if(newAccount){
                createAccount();
            }
            else{
                login();
            }
        } else {
            String password = bff.inputLine("Password: ");
            u = checkPassword(email, password);
            if (u != null) {
                System.out.println("password incorrect");
                login();
            } else {
                this.currentUser = u;
            }
        }
    }

    private void createAccount() { //modified from media example
        //make a new user, and log them inp
        String userEmail = bff.inputWord("Please enter your email");
        User u = getUserByEmail(userEmail);
        if (!(u == null)) {
            System.out.println("This email is already associated with an account. Please log in instead.");
            login();
        } else {
            String usersName = bff.inputWord("Please enter your name");
            String userPassword = bff.inputWord("Please enter your password");
            String confirmPassword = bff.inputWord("Confirm your password");
            while (!(userPassword.equals(confirmPassword))) {
                System.out.println("The passwords do not match");
                userPassword = bff.inputWord("Please enter your password");
                confirmPassword = bff.inputWord("Confirm your password");
            }
            //what kind of user account should we make?
            String accountType = bff.inputWord("Choose account type: (guest, host, or premiumUser)",
                    "guest", "host", "premiumUser");
            //todo: consider adding descriptions
            if (accountType.equalsIgnoreCase("guest")) {
                currentUser = new Guest(usersName, userEmail, userPassword);
            } else if (accountType.equalsIgnoreCase("host")) {
                currentUser = new Host(usersName, userEmail, userPassword, new ArrayList<>(), 10, 0);
            } else { // there;s only three choices
                currentUser = new PremiumUser(usersName, userEmail, userPassword, new ArrayList<>(), 50, 0, 20);
            }
            addNewUser(currentUser);
        }
    }
    private void saveUserDatabase() {
        try (FileOutputStream fs = new FileOutputStream("userDatabase.ser")){
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this.userDatabase); //write the object (ANY Java object that is serializable)
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IO exception");
            throw new RuntimeException(e);
        }
    }

    private void savePartyDatabase() {
        try (FileOutputStream fs = new FileOutputStream("PartyDatabase.ser")){
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this.partyDatabase); //write the object (ANY Java object that is serializable)
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IO exception");
            throw new RuntimeException(e);
        }
    }

    private void readUserDatabaseFile(String file) {
        try (FileInputStream fs = new FileInputStream(file);
             ObjectInputStream os = new ObjectInputStream(fs)){
            Map<String, User> userDatabase = (Map<String, User>) os.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println("Error in reading from file" + e);
        }

    }

    private void readPartyDatabaseFile(String file) {
        try (FileInputStream fs = new FileInputStream(file);
             ObjectInputStream os = new ObjectInputStream(fs)){
            Map<String, Party> map = (Map<String, Party>) os.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println("Error in reading from file" + e);
        }

    }
    /*
        public void initializeUsers(String fileName) { //modified from A11
            // read in users from a file
            try(FileInputStream f = new FileInputStream(fileName); Scanner sc = new Scanner(f)){
                while(sc.hasNextLine()){ //read in users line by line separated by commas
                    String line = sc.nextLine();
                    Scanner s = new Scanner(line);
                    s.useDelimiter(",");
                    String email = s.next();
                    String password = s.next();
                    String name = s.next();
                    User u = new Guest(name, email, password); //create new user and add to db if not null
                    if(u != null){
                        userDatabase.put(email, u);
                    }
                }
            } catch (FileNotFoundException e) { //start witih empty map db if file not found or empty
                System.out.println("File not found. Starting with empty map db");
            } catch (IOException e) {
                System.out.println("File might be empty. Starting with empty map db");
            }
        }

        public void initializeParties(String fileName) { //modified from A11
            // read in users from a file
            try(FileInputStream f = new FileInputStream(fileName); Scanner sc = new Scanner(f)){
                while(sc.hasNextLine()) { //read in users line by line separated by commas
                    String line = sc.nextLine();
                    Scanner s = new Scanner(line);
                    s.useDelimiter("/");
                    String type = s.next();
                    String partyName = s.next();
                    String hostEmail = s.next();
                    String[] values = sc.next().split(",");
                    List<String> guests = new ArrayList<>();
                    for (String value : values) {
                        guests.add(value);
                    }
                    String[] inviteArray = sc.next().split(",");
                    List<String> invitedList = new ArrayList<>();
                    for (String i : inviteArray) {
                        invitedList.add(i);
                    }
                    String[] declinedArray = sc.next().split(",");
                    List<String> declinedList = new ArrayList<>();
                    for (String d : declinedArray) {
                        declinedList.add(d);
                    }
                    String dateString = sc.next();
                    LocalDate time = LocalDate.parse(dateString);
                    String location = sc.next();
                    double entryFee = sc.nextInt();
                    if(type.equalsIgnoreCase("giftparty")){

                        Party p = new GiftParty(type, partyName, hostEmail, guests, time, entryFee, location, entryFee, location, invitedList, declinedList, );
                    }
                    if(p != null){
                        userDatabase.put(partyName, p);
                    }
                }
            } catch (FileNotFoundException e) { //start witih empty map db if file not found or empty
                System.out.println("File not found. Starting with empty map db");
            } catch (IOException e) {
                System.out.println("File might be empty. Starting with empty map db");
            }
        }
        public void saveUserTxt(String fileName){ //writes all users to file
            File f = new File(fileName);
            try(PrintWriter out = new PrintWriter(f)){
                for(User u: getUserDatabase()){ //loop through users and write to file
                    out.println(u.toFileString());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void savePartyTxt(String fileName){ //writes all parties to file as txt
            File f = new File(fileName);
            try(PrintWriter out = new PrintWriter(f)){
                for(Party p: getPartyDatabase()){ //loop through parties and write to file
                    out.println(p.toFileString());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    */
    public void addNewUser(User u){
        userDatabase.put(u.getEmail(), u); //adds user to map
    }

    public void changePassword(){ //method to change current user password with error checking
        String attempt = bff.inputLine("type in current password:");
        if(currentUser.checkPassword(attempt)){
            String userPassword = bff.inputWord("Please enter your new password\n");
            String confirmPassword = bff.inputWord("Confirm your new password\n");
            while (!(userPassword.equals(confirmPassword))) {
                System.out.println("The passwords do not match");
                userPassword = bff.inputWord("Please enter your password\n");
                confirmPassword = bff.inputWord("Confirm your password\n");
            }
            currentUser.setPassword(userPassword);
        }
        else{
            System.out.println("password incorrect, please try again");
            changePassword();
        }
    }
    public void showRSVPs(){
        List<Party> guestTo = getInvitesByGuest(currentUser.getEmail());
        if((guestTo.isEmpty())) {
            System.out.println("no events to show at this time");
        }
        else{
            int i = 0;
            while (i < guestTo.size()) {
                System.out.println(i + 1 + " : " + guestTo.get(i));
                i++;
            }
        }
    }
    public void manageAccount(){ //prints account menu and lets user select option
        boolean quit = false;
        while(!quit) {
            // loop until quit
            int choice = bff.inputInt(AccountMenu.getMenuOptions() + ">", 1, AccountMenu.getNumOptions());
            System.out.println("You picked " + choice + " which is: " + AccountMenu.getOption(choice));
            AccountMenu option = AccountMenu.getOption(choice);
            //do choice
            switch(option) {
                case MANAGE_EVENTS: showRSVPs(); break;
                case CHANGE_PASSWORD: changePassword(); break;
                case CHANGE_NAME: currentUser.setName(bff.inputWord("Please enter your new name")); break; //TODO: Implement
                case BACK: quit = true; break;
            }
    }
}
    public void checkHost(){
        if(!(currentUser instanceof Host)){
            boolean newHost = bff.inputYesNo("Current account is not a host account, would you like to set one up? (Y/N)");
            if(newHost){
                if(bff.inputYesNo("Would you like a premium account (host up to 50 events instead of 10)")){
                    PremiumUser p = new PremiumUser(currentUser.getName(), currentUser.getEmail(), currentUser.getPassword(), new ArrayList<>(), 50, 0, 20);
                    userDatabase.remove(currentUser.getEmail());
                    currentUser = p;
                    userDatabase.put(p.getEmail(), p);
                }
                else{
                    Host h = new Host(currentUser.getName(), currentUser.getEmail(), currentUser.getPassword(), new ArrayList<>(), 10, 0);
                    userDatabase.remove(currentUser.getEmail());
                    currentUser = h;
                    userDatabase.put(h.getEmail(), h);
                }
            }
        }
        else{
            hostMenu();
        }
    }

    public void makeEvent() { //gets input from host to make a new event
        String type = bff.inputWord("What type of event are you hosting (PotluckParty, Rager, GiftParty)", "PotluckParty", "Rager", "GiftParty");
        String partyName = bff.inputLine("What is the name of your event?");
        //String hostEmail = bff.inputLine("What is your email?");
        boolean inviting = bff.inputYesNo("Would you like to invite more guests? (Y/N)");
        List<String> invitedList = new ArrayList<>();
        while (inviting) {
            String guestEmail = bff.inputLine("What is the guest's email? (you will receive confirmation of invitation, otherwise, the guest was not found)");
            for (User u : getUserDatabase()) { //loops through to find guest in user database, if found add
                if (u.getEmail().equals(guestEmail)) {
                    invitedList.add(guestEmail);
                    System.out.println("guest successfully invited");
                }
            }
            inviting = bff.inputYesNo("Would you like to invite more guests? (Y/N)");
        }
        int year = bff.inputInt("When will this event be happening?\nEnter the year of the event:", 2022, 2500);
        int month = bff.inputInt("Enter the month of the event (1-12)", 1, 12);
        int day = bff.inputInt("Enter the day of the event (1-31)", 1, 31);
        LocalDate date = LocalDate.of(year, month, day);
        String location = bff.inputLine("What is the address of the event?");
        double entryFee = bff.inputDouble("What is the entry fee of the event?");
        HashMap<String, String> giftMap = new HashMap<String, String>();
        if(type.equalsIgnoreCase("Rager")) {
            String club = bff.inputLine("What club is hosting this event?");
            String theme = bff.inputLine("What will be the theme of the party?");
            int ageLimit = bff.inputInt("What is the minimum age to get in?");
            Rager r = new Rager(type, partyName, currentUser.getEmail(), new ArrayList<String>(), date, entryFee, location, invitedList, new ArrayList<String>(), giftMap, club, theme, ageLimit);
            partyDatabase.put(partyName, r);
        }
        else if(type.equalsIgnoreCase("GiftParty")) {
            String guestOfHonor = bff.inputLine("Who is the guest of honor?");
            String partyFavor = bff.inputLine("What will the party favor be?");
            GiftParty gp = new GiftParty(type, partyName, currentUser.getEmail(), new ArrayList<String>(), date, entryFee, location, invitedList, new ArrayList<String>(), giftMap, guestOfHonor, partyFavor);
            partyDatabase.put(partyName, gp);
        } else if(type.equalsIgnoreCase("PotluckParty")){
            boolean dishing = bff.inputYesNo("Would you like to add dishes to the dish list? (Y/N)");
            List<String> dishList = new ArrayList<>();
            while (dishing) {
                String dish = bff.inputLine("What dish would you like to add?");
                dishList.add(dish);
                dishing = bff.inputYesNo("Would you like to add more dishes? (Y/N)");
            }
            HashMap<String, String> signup = new HashMap<String, String>();
            PotluckParty pp = new PotluckParty(type, partyName, currentUser.getEmail(), new ArrayList<String>(), date, entryFee, location, invitedList, new ArrayList<String>(), giftMap, dishList, signup);
            partyDatabase.put(partyName, pp);
        }
    }

    public void addGuest(Party toEdit){
        List<String> editedList = toEdit.getInvited();
        String email = bff.inputLine("What is the email of the guest you'd like to invite?");
        for(User u: getUserDatabase()){ //loops through to find guest in user database, if found add
            if(u.getEmail().equals(email)){
                editedList.add(email);
                toEdit.setInvited(editedList);
                System.out.println("guest successfully invited");
            }
        }
    }
    public void removeGuest(Party toEdit){
        List<String> editedList = toEdit.getInvited();
        List<String> editedGuest = toEdit.getGuestList();
        changePassword();String email = bff.inputLine("What is the email of the guest you'd like to remove");
        for(User u: getUserDatabase()){ //loops through to find guest in user database, if found add
            if(u.getEmail().equals(email)){
                for(String s: editedList){
                    if(s.equals(email)){
                        editedList.remove(email);
                        toEdit.setInvited(editedList);
                        System.out.println("invite successfully removed");
                    }
                }
            }
            if(u.getEmail().equals(email)){ //remove guest if found on guest list
                for(String s: editedGuest){
                    if(s.equals(email)){
                        editedGuest.remove(email);
                        toEdit.setGuestList(editedGuest);
                        System.out.println("guest successfully removed");
                    }
                }
            }
        }
    }
    public void editGuestMenu(Party toEdit){
        boolean quit = false;
        while (!quit){
            int choice = bff.inputInt("1) invite guest\n2) remove guest\n3) back to main menu", 1,3);
            if(choice == 1){
                addGuest(toEdit);
            }
            if(choice == 2){ //if email found in users and invite list remove
                removeGuest(toEdit);
            }
            else{
                quit = true;
            }
        }
    }
    public void hostMenu(){ //menu for hosting options
        boolean quit = false;
        while (!quit){
            int limit = 10; //calculate limit for free/premium users
            if(currentUser instanceof PremiumUser){
                limit = 50;
            }
            List<Party> partyList = getAllPartiesByUser(currentUser.getEmail());
            if(partyList.size()>=limit){
                System.out.println("You are over your hosting limit");
            }
            System.out.println("Welcome "+currentUser.getName()+"! you are hosting "+partyList.size()+" out of "+limit); printStars(75);
            int choice = bff.inputInt("1) make event\n2) see events hosting\n3) back to main menu", 1,3);
            if(choice == 1){
                makeEvent();
            }
            if(choice == 2){
                if(!(partyList.isEmpty())){
                    int i=0;
                    while(i<partyList.size()) {
                        System.out.println(i + 1 + " : " + partyList.get(i));
                        i++;
                    }
                    int selection = bff.inputInt(" select an event by entering the corresponding index \n",1, partyList.size()) - 1; printStars(75);
                    System.out.println("you are hosting "+partyList.get(selection).getPartyName()+" on "+partyList.get(selection).getDate() + " at "+partyList.get(selection).getLocation()); System.out.println("Invited:");
                    for(String invite: partyList.get(selection).getInvited()){
                        System.out.println(invite);
                    } printStars(75);
                    System.out.println("RSVP'ed:");
                    for(String guest: partyList.get(selection).getGuestList()){
                        System.out.println(guest);
                    } printStars(75);
                    System.out.println("Declined:");
                    for(String decline: partyList.get(selection).getDeclined()){
                        System.out.println(decline);
                    } printStars(75);
                    System.out.println("Gifts:");
                    Set<Map.Entry<String, String>> entries = partyList.get(selection).getGiftList().entrySet(); //get set of key value pairs and print them out
                    for (Map.Entry<String, String> entry : entries) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    } printStars(75);
                    if(bff.inputYesNo("Would you like to remake this event? (WARNING THIS WILL DELETE THE OLD EVENT)")){
                        makeEvent();
                        partyDatabase.remove(partyList.get(selection).getPartyName());
                    }
                    if(bff.inputYesNo("Would you like to make edits to the guest list?")){
                        editGuestMenu(partyList.get(selection));
                    }
                }
                else{
                    printStars(50);
                    System.out.println("There are currently no events to display");
                    printStars(50);
                }
            }
            else{
                quit = true;
            }
        }
    }

    public void showInvited(){
        List<Party> invitedTo = getInvitesByGuest(currentUser.getEmail());
        if(!(invitedTo.isEmpty())) {
            int i = 0;
            while (i < invitedTo.size()) {
                System.out.println(i + 1 + " : " + invitedTo.get(i));
                i++;
            }
            int selection = bff.inputInt(" select an event by entering the corresponding index \n", 1, invitedTo.size()) - 1;
            printStars(75);
            System.out.println("you are hosting " + invitedTo.get(selection).getPartyName() + " on " + invitedTo.get(selection).getDate() + " hosted by" + invitedTo.get(selection).getHostEmail() + " at " + invitedTo.get(selection).getLocation() + " the entry fee is " + invitedTo.get(selection).getEntryFee());
            if (bff.inputYesNo("Would you like to RSVP for this event (Y/N)")) {
                if (invitedTo.get(selection) instanceof Rager) {
                    System.out.println("Welcome to " + invitedTo.get(selection).getPartyName() + " hosted by " + ((Rager) invitedTo.get(selection)).getClub() + " the theme for " + invitedTo.get(selection).getDate() + " is " + ((Rager) invitedTo.get(selection)).getTheme());
                    if (bff.inputYesNo("Are you above the age of " + ((Rager) invitedTo.get(selection)).getAgeLimit() + "?")) {
                        rsvp(invitedTo.get(selection));
                    }
                }
                else if (invitedTo.get(selection) instanceof GiftParty) {
                    if (bff.inputYesNo("Welcome to " + invitedTo.get(selection) + "'s celebration!\nWould you like to give a gift (Y/N)")) {
                        String gift = bff.inputLine("What gift will you be bringing?");
                        invitedTo.get(selection).addGift(currentUser.getEmail(), gift);
                    }
                    rsvp(invitedTo.get(selection));
                }
                else if (invitedTo.get(selection) instanceof PotluckParty) {
                    int c = 0;
                    while (c < ((PotluckParty) invitedTo.get(selection)).getDishList().size()) {
                        System.out.println(i + 1 + " : " + ((PotluckParty) invitedTo.get(selection)).getDishList().get(selection));
                        i++;
                    }
                    int choice = bff.inputInt(" select an event by entering the corresponding index \n", 1, invitedTo.size());
                    String dish = ((PotluckParty) invitedTo.get(selection)).getDishList().get(selection);
                    ((PotluckParty) invitedTo.get(selection)).addSignup(getCurrentUser().getEmail(), dish); //add signup
                    ((PotluckParty) invitedTo.get(selection)).removeDish(dish); //remove dish from dish list
                }

            } else {
                decline(invitedTo.get(selection));
            }
        }
        else{
            System.out.println("no events to show at this time");
        }
    }

    public void rsvp(Party p){
        List<String> newInvited = p.getInvited();
        String takeout = "";
        for(String s: p.getInvited()){
            if(s.equals(currentUser.getEmail())){
                takeout = s;
            }
        }
        newInvited.remove(takeout);
        p.setInvited(newInvited);

        List<String> newRSVPed = p.getGuestList();
        for(String z: p.getGuestList()){
            if(z.equals(currentUser.getEmail())){
                newRSVPed.add(z);
                p.setGuestList(newRSVPed);
            }
        }
        System.out.println("You have successfully RSVP'ed!");
    }
    public void decline(Party p){ //remove user from invited list and add user to declined list
        List<String> newInvited = p.getInvited();
        for(String s: p.getInvited()){
            if(s.equals(currentUser.getEmail())){
                newInvited.remove(s);
                p.setInvited(newInvited);
            }
        }
        List<String> newDeclined = p.getDeclined();
        for(String z: p.getDeclined()){
            if(z.equals(currentUser.getEmail())){
                newDeclined.add(z);
                p.setDeclined(newDeclined);
            }
        }
    }
    public void save(){
        savePartyDatabase();
        saveUserDatabase();
    }

    public void printStars(int num){ //helper method to print out num stars adapted from A5
        for(int i=0; i<num; i++){
            System.out.print("*");
        }
        System.out.println("\n");
    }
    public void run() { //modified from media example
        System.out.print("****************************** \n Welcome to Evite!");
        int selection = bff.inputInt("Please select an option: \n1. Login\n2. Create New Account",1,2);
        if(selection == 1){
            login();
        }
        else{
            createAccount();
        }
        boolean quit = false;
        while(!quit) {
            // loop until quit
            int choice = bff.inputInt(Menu.getMenuOptions() + ">", 1, Menu.getNumOptions());
            System.out.println("You picked " + choice + " which is: " + Menu.getOption(choice));

            Menu option = Menu.getOption(choice);
            //do choice
            switch(option) {
                case MANAGE_ACCOUNT: manageAccount(); break;
                case HOST: checkHost(); break;
                case RSVP: showInvited(); break;
                case LOGOUT: currentUser=null; login(); break;
                case QUIT: save(); quit = true; break;
            }
            if(!quit){ //pause before printing menu again.
                bff.inputLine("Hit return to continue.");
            }
        }
        System.out.println("Goodbye");
    }


    public static void main(String[] args) {
        EviteSystem E = new EviteSystem();
        E.run();
    }
}
