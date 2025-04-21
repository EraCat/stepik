import java.util.Scanner;

class PhoneBook {

    String[] names = new String[10_000_000];

    public void add(int phone, String name) {
        names[phone] = name;
    }

    public void delete(int phone) {
        names[phone] = null;
    }

    public String find(int phone) {
        return names[phone];
    }
}


public class Main {

    //https://stepik.org/lesson/41562/step/1?unit=20016
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());

        PhoneBook phoneBook = new PhoneBook();

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] items = line.split(" ");
            if (line.startsWith("a")) {
                phoneBook.add(Integer.parseInt(items[1]), items[2]);
            } else if (line.startsWith("d")) {
                phoneBook.delete(Integer.parseInt(items[1]));
            } else if (line.startsWith("f")) {
                String result = phoneBook.find(Integer.parseInt(items[1]));
                if (result != null) {
                    System.out.println(result);
                } else {
                    System.out.println("not found");
                }
            }
        }

    }

}
