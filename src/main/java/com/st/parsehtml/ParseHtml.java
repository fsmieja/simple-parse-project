package com.st.parsehtml;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author frank
 *
 *         Class ParseHtml takes in an HTML file and parses for table columns
 *         using jsoup to extract the DOM. Once it has this it looks for the
 *         first and fifth columns that contain the repo name and time change.
 *         Anything over 4 weeks is deemed to be over 30 days, and we assume
 *         months contain 30 days Output is the list of repos and the time since
 *         last change in days.
 *
 */
public class ParseHtml {

    static final int DAYSINWEEK = 7;
    static final int DAYSINMONTH = 30;

    /*
     * @param args
     */
    @SuppressWarnings("PMD.SystemPrintln")
    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: parsehtml <filename>");
            return;
        }

        final ParseHtml parse = new ParseHtml();
        System.out.println(parse.processRepoFile(args[0]));
    }

    /*
     * process the file
     *
     * @fileName - name of the file
     */
    final public String processRepoFile(final String fileName) {
        final StringBuffer res = new StringBuffer("");
        final File input = new File(fileName);
        final Elements rows = getTableRowsFromFile(input);
        for (final Element row : rows) {
            final Elements cols = row.getElementsByTag("td");
            if (cols.size() == 6) {
                final String repo = cols.get(0).getElementsByTag("a").get(0)
                        .text();
                final String date = cols.get(4).getElementsByTag("span").get(0)
                        .text();
                if (tooLong(date)) {
                    final int days = getNumberOfDays(date);
                    res.append("Repo ");
                    res.append(repo);
                    res.append(": ");
                    res.append(days);
                    res.append(" days since last change\n");
                }
            }
        }
        return res.toString();
    }

    public int performCalc(final int a, final int b) {
        return 0;
    }

    public Elements getTableRowsFromFile(final File input) {
        Document doc;
        try {
            doc = Jsoup.parse(input, "ISO-8859-1", "http://example.com/");
        } catch (final IOException e) {
            System.out
            .println("Error getting DOM from file: " + e.getMessage());
            return null;
        }

        final Elements table = doc.getElementsByTag("table");
        return table.get(0).getElementsByTag("tr");
    }

    public String processHtml(final String html) {

        final Document doc = Jsoup.parse(html);
        final Elements reps = doc.getElementsByClass("list");
        String res = null;
        if (!reps.isEmpty()) {
            final Elements times = doc.getElementsByClass("age4");
            if (!times.isEmpty()) {
                res = reps.get(0) + " : " + times.get(0);
            }
        }
        return res;
    }

    private boolean tooLong(final String dt) {
        final int MINNUMFIELDS = 4;
        if (dt.contains("months") || dt.contains("years")) {
            return true;
        } else {
            final int numFields = Integer.parseInt(dt.split(" ")[0]);
            if (dt.contains("weeks") && numFields > MINNUMFIELDS)
                return true;
        }

        return false;
    }

    private int getNumberOfDays(final String dt) {
        int days = 0;
        final int num = Integer.parseInt(dt.split(" ")[0]);
        if (dt.contains("months")) {
            days = num * DAYSINMONTH;
        } else if (dt.contains("weeks")) {
            days = num * DAYSINWEEK;
        }

        return days;
    }

}
