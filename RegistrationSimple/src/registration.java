import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class registration {
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        int menu = 1;
        char g;
        boolean check = false;
        boolean checkRepeat = false;
        int counter = 0;
        String[] array;
        String username = "";
        String password = "";
        String name = "";
        String birthday = "";
        String city = "";
        String number = "";
        String line;
        Charset charset = StandardCharsets.UTF_8;
        String oldPassword;
        Path path = Paths.get("users.txt");

        File file = new File("users.txt");


        while (menu != 0) {

            System.out.println("1 - registration\n" +
                    "2 - authorization\n" +
                    "0 - exit");
            menu = scan.nextInt();

            switch (menu) {
                case 1:
                    System.out.print("create your new login: ");
                    username = scan.next();
                    System.out.print("create your new password (5 non-repeating numbers and +-/*): ");
                    password = scan.next();

                    if (!password.matches("^[0-9+\\/*-]{5}$")) {
                        System.out.println("your password doesn't match the rules! please try again \n");
                        break;
                    }
                    else{
                        checkRepeat = false;
                        for(int i = 0; i < 5; i++) {
                            g = password.charAt(i);
                            counter = 0;
                            checkRepeat = false;
                            for (int j = 0; j < 5; j++) {
                                if(g == password.charAt(j))
                                    counter++;
                            }
                            if (counter > 1)
                                checkRepeat = true;
                        }
                        if(checkRepeat){
                            System.out.println("your password has repeating symbols, please try again");
                            break;
                        }
                    }

                    System.out.print("enter your full name (without spaces 'JohnKennedy'): ");
                    name = scan.next();
                    System.out.print("enter your date of your birthday (DDMMYYYY): ");
                    birthday = scan.next();
                    System.out.print("enter your current city: ");
                    city = scan.next();
                    System.out.print("enter your phone number: ");
                    number = scan.next();

                    try(FileWriter fw = new FileWriter("users.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw))
                    {
                        out.println("\n" + "\n" + username + " " + password + " " + name + " " + birthday + " " + city + " " + number);
                    } catch (IOException e) {
                        //exception handling left as an exercise for the reader
                    }

                    System.out.println("the new user was successfully added!");
                    break;

                case 2:
                    System.out.print("login: ");
                    username = scan.next();
                    System.out.print("password: ");
                    password = scan.next();
                    check = false;

                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    while ((line = br.readLine()) != null) {
                            array = line.split(" ");
                            if ((array[0].equals(username)) && (array[1].equals(password))) {
                                check = true;
                                System.out.println("the authorization is successful!");
                                System.out.println("1 - to change the password\n" +
                                        "2 - the main menu\n" +
                                        "0 - exit");

                                menu = scan.nextInt();
                                if (menu == 1) {
                                    BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                                    oldPassword = password;
                                    System.out.print("enter a new password: ");
                                    password = scan.next();
                                    char k = ' ';
                                    if(password.matches("^[0-9+\\/*-]{5}$")) {
                                        for(int i = 0; i < 5; i++) {
                                            k = password.charAt(i);
                                            counter = 0;
                                            checkRepeat = false;
                                            for (int j = 0; j < 5; j++) {
                                                if(k == password.charAt(j))
                                                    counter++;
                                            }
                                            if (counter > 1)
                                                checkRepeat = true;
                                        }
                                        if(!checkRepeat) {
                                            Files.write(path, new String(Files.readAllBytes(path), charset).replace(oldPassword, password).getBytes(charset));
                                            System.out.println("the password is changed");
                                        }
                                        else
                                            System.out.println("your new password doesn't match the rules of size");
                                    }
                                    else
                                        System.out.println("your new password doesn't match the rules of symbols");
                                }
                            }
                    }
                    if(!check)
                        System.out.println("the authorization is failed, please try again \n");
                    break;

            }
        }
    }
}
