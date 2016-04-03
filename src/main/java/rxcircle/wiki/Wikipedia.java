package rxcircle.wiki;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by Madeleine on 2016-03-26.
 */
public class Wikipedia {

    private static final String SEARCH_URL = "https://sv.wikipedia.org/w/api.php?action=query&list=search&srsearch={query}&format=json&utf8=";
    private static final String PAGEID_URL = "https://sv.wikipedia.org/w/api.php?action=query&titles={query}&prop=revisions&rvprop=content&format=json&utf8=";

    public static List<String> searchTitle(String query) {
        try {
            String result = search(query, SEARCH_URL);
            List<String> resultList = new ArrayList<>();
            JSONObject root = new JSONObject(result);
            JSONObject resultQuery = root.getJSONObject("query");
            JSONArray reultSearch = resultQuery.getJSONArray("search");
            for (int i = 0; i < reultSearch.length(); i++) {
                JSONObject searchJson = reultSearch.getJSONObject(i);
                String title = searchJson.getString("title");
                System.out.println(title);
                resultList.add(title);
            }
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static String searchArticle(String query) throws Exception {
        String result = search(query, PAGEID_URL);
        List<String> resultList = new ArrayList<>();
        JSONObject root = new JSONObject(result);
        JSONObject resultQuery = root.getJSONObject("query");
        JSONObject pages = resultQuery.getJSONObject("pages");
        Iterator keys = pages.keys();
        String pageId = keys.next().toString();
        return "http://sv.wikipedia.org/?curid=" + pageId;
    }


    private static String search(String query, String urlString) throws IOException {
        URL url = new URL(urlString.replace("{query}", query.replace(" ", "%20")));

        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.close();

        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        String l = null;
        String returnString = "";
        while ((l = br.readLine()) != null) {
            System.out.println(l);
            returnString += l;
        }
        br.close();
        return returnString;
    }

    public static void main(String[] args) throws Exception {
        Wikipedia.searchTitle("Simon");
        String komet = Wikipedia.searchArticle("Komet");
        System.out.println(komet);
    }
}
