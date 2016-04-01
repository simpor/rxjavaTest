package rxcircle.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Madeleine on 2016-03-26.
 */
public class Wikipedia {

    private static final String SEARCH_URL = "https://sv.wikipedia.org/w/api.php?action=query&list=search&srsearch={query}&format=json&utf8=";
    private static final String ARTICLE_URL = "https://sv.wikipedia.org/w/api.php?action=query&titles={title}&prop=revisions&rvprop=content&format=json&utf8=";

    public static List<String> search(String query) throws IOException {
        URL url = new URL(SEARCH_URL.replace("{query}", query));
    //    String query = INSERT_HERE_YOUR_URL_PARAMETERS;

        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
      //  ps.print(query);
        ps.close();

        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
                .getInputStream()));
        String l = null;
        while ((l=br.readLine())!=null) {
            System.out.println(l);
        }
        br.close();
        return Collections.emptyList();
    }

    public static void main(String[] args) throws IOException {
        Wikipedia.search("Simon");
    }
}
