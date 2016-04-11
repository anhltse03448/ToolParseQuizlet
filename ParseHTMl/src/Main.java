
import java.io.*;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Le Tuan Anh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //String url = args[0];
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect("https://quizlet.com/92342618/dekiru-nihongo-chapter-1-flash-cards/").get();

            // get page title
            String title = doc.title();

            Element cc = doc.select("article[class]").get(1);
            //  System.out.println(cc.toString());
            Elements links = cc.select("div[data-id]");
            BufferedWriter writer = null;
            //String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File("json.txt");

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

           // writer = new BufferedWriter(new FileWriter(logFile));
            String res = ("{ 'res' : [\n");
            

            for (int i = 0; i < links.size()-1; i++) {
                String word = (links.get(i).select("h3[class]").get(0).text());
                String meaning = links.get(i).select("p[class]").get(0).text();
                res = res + "{'n' : '" + word + "' , 'm': '" + meaning + "'},\n";               
            }
            String word = (links.get(links.size()-1).select("h3[class]").get(0).text());
            String meaning = links.get(links.size()-1).select("p[class]").get(0).text();
            res = res + "{'n' : '" + word + "' , 'm': '" + meaning + "'}\n";               
            res = res + "] }";
            res.replace("'", "");
            writeUtf8ToFile(logFile, false, res);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeUtf8ToFile(File file, boolean append, String data)
            throws IOException {
        boolean skipBOM = append && file.isFile() && (file.length() > 0);
        Closer res = new Closer();
        try {
            OutputStream out = res.using(new FileOutputStream(file, append));
            Writer writer = res.using(new OutputStreamWriter(out, Charset.forName("UTF-8")));
            if (!skipBOM) {
                writer.write('\uFEFF');
            }
            writer.write(data);
        } finally {
            res.close();
        }
    }
}
//
//        print("\nMedia: (%d)", media.size());
//        for (Element src : media) {
//            if (src.tagName().equals("img")) {
//                print(" * %s: <%s> %sx%s (%s)",
//                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
//                        trim(src.attr("alt"), 20));
//            } else {
//                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
//            }
//        }
//
//        print("\nImports: (%d)", imports.size());
//        for (Element link : imports) {
//            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
//        }
//
//        print("\nLinks: (%d)", links.size());
//        for (Element link : links) {
//            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
//        }
// TODO code application logic here

