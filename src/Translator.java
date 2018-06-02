import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    public static void main(String[] args) throws Exception {
        Pattern patt = Pattern.compile("\"(.*?)\" = \"(.*?)\";");
        File folder = new File("inputs");
        for (File file : folder.listFiles()) {
            List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
            String fname = file.getName().toLowerCase().replace(".strings", ".xml");
            File o = new File(fname);
            o.createNewFile();
            FileWriter output = new FileWriter(o);
            output.write("<resources>");
            for (String line : lines) {
                Matcher m = patt.matcher(line);
                if (m.matches()) {
                    String key = m.group(1);
                    String value = m.group(2);
                    value = value.replace("'", "\\'");
                    value = value.replace("<", "&lt;");
                    output.write(String.format("\t<string name=\"%s\">%s</string>\n", key, value));
                }
            }
            output.write("</resources>");
            output.close();
        }
    }
}
