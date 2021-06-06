import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        generatePassword();
        askNewPassword();
    }
    public static void toClipboard(String pass) {
        System.out.println("\r\n" + "Пароль скопирован в буфер обмена\n");
        StringSelection stringSelection = new StringSelection(pass);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void generatePassword() {
        String password = getPassword();
        System.out.println(password);
        toClipboard(password);

    }
    public static void askNewPassword() {
        System.out.println("Для генерации нового пароля введите \"n\"\n");
        System.out.println("Чтобы закрыть программу нажмите Enter");
        String answer = inputData();
        if (answer.isEmpty())
            return;
        else if (answer.equalsIgnoreCase("n")) {
            generatePassword();
            askNewPassword();
        }
        else {
            System.out.println("Введите корректное значение");
            askNewPassword();
        }
    }

    public static String getPassword() {
        ArrayList<Character> data = new ArrayList<>(Arrays.asList(
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f',
                'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F',
                'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',
                '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '!', '@', '#', '$', '%', '^', '&', '*'
        ));
        String res = "";
        StringBuilder sb = new StringBuilder();
        int passLength = getPassLength();
        while (true) {
            Collections.shuffle(data);
            data.stream().limit(passLength).forEach(sb::append);
            res = sb.toString();
            Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])");
            Matcher matcher = pattern.matcher(res);
            if (matcher.find()) {
                break;
            }
            sb.delete(0, sb.length());
        }
        return res;
    }

    public static int getPassLength() {
        int length = 8;
        System.out.println("\r\n" + "Введите кол-во символов в пароле" + "\r\n"
                + "Если пропустите этот шаг (Enter), будет установлено значение 6");
        boolean pass = true;
        while (pass) {
            String strLength = inputData();
            if (strLength.equals("")) {
                break;
            }
            if (checkPass(strLength)) {
                length = Integer.parseInt(strLength);
                pass = false;
            }
        }
        return length;
    }
    public static boolean checkPass(String i) {
        if (i.equals("") || i.isEmpty()) {
            return true;
        }
        else {
            try {
                int dex = Integer.parseInt(i);
                if (dex < 3 || dex > 24) {
                    System.out.println("Пароль не может быть меньше 3х или больше 24х символов" + "\r\n"
                            + "Введите новое значение или нажмите (Enter [6 символов])");
                    return false;
                }
                else {
                    return true;
                }
            }catch (NumberFormatException e) {
                System.out.println("Некорректный ввод" + "\r\n"
                        + "Введите новое значение или нажмите (Enter [6 символов])");
                return false;
            }
        }
    }
    public static String inputData() {
        String data = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            data = br.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
