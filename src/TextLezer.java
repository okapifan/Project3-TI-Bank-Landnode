import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TextLezer {
    public static void main(String[] args) throws FileNotFoundException {

        //https://github.com/okapifan/Project3-TI-Bank-Landnode/blob/master/ip%20en%20landcodes.txt

        File data = new File("C:\\Users\\marvi\\stack\\Github\\Project3-TI-Bank-Landnode\\DataBanken.txt");
        Scanner scan = new Scanner(data);

        String dataString = "";
        dataString = dataString + scan.nextLine();
        List<String> dataArray = Arrays.asList(dataString.split("\\s*,\\s*"));

        Scanner myInput = new Scanner(System.in);
        System.out.println("Voer de bank code in");
        String bankCode = myInput.nextLine();

        int index = -1;
        for (int i=0;i<dataArray.size();i++) {
            if (dataArray.get(i).equals(bankCode)) {
                index = i;
                break;
            }
        }
        System.out.println(dataArray.get(index - 1));
    }
}
