package crawler.builer;

import crawler.models.ElementFile;
import crawler.models.Perform;
import crawler.models.PublicService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import support.StringStandardized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuilderServices {
    private Document document;
    private PublicService publicService;
    private String link;

    // connect document to link and parse
    public void connect(String link){
        this.link = link;
        this.document = null;
        try {
            document = Jsoup
                    .connect(link)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void build(){
        Elements h2Elemnts = document.getElementsByClass("main-title-sub");

        // Declare 4 element of public services
        String title; // 0
        String orderOfExecution; // 1
        List<Perform> howToPerform; // 2
        List<ElementFile> profileComposition; //3
        String place; // 4

        // get title
        title = document.getElementsByClass("main-title -none").text();

        // get content of order of execution
        // belong to tag class content
        orderOfExecution = h2Elemnts.get(0)
                .nextElementSibling()
                .getElementsByClass("content")
                .html();

        orderOfExecution = StringStandardized.standardized(orderOfExecution);

        // get content of perform
        howToPerform = new ArrayList<Perform>();
        Element tableElement = h2Elemnts.get(1)
                .nextElementSibling();

        //Skip <br> tag
        tableElement = skipBrTag(tableElement);

        Elements trTableElement = tableElement.getElementsByTag("tbody")
                .get(0) // tag <tbody> only one
                .getElementsByTag("tr");

        for(Element element: trTableElement){
            String tmpString1 = null;
            String tmpString2 = null;
            String tmpString3 = null;
            String tmpString4 = null;

            Elements tdElement = element.getElementsByTag("td");
            tmpString1 = tdElement.get(0).text();
            tmpString2 = tdElement.get(1).text();
            tmpString3 = tdElement.get(2).text();
            tmpString4 = tdElement.get(3).text();

            Perform perform = new Perform(tmpString1, tmpString2, tmpString3, tmpString4);
            howToPerform.add(perform);
        }

        // get content of profile composition
        profileComposition = new ArrayList<ElementFile>();
        tableElement = h2Elemnts.get(2)
                .nextElementSibling();

        //Skip <br> tag
        tableElement = skipBrTag(tableElement);

        trTableElement = tableElement.getElementsByTag("tbody")
                .get(0) // tag <tbody> only one
                .getElementsByTag("tr");

        for(Element element: trTableElement){
            String tmpString1 = null;
            String tmpString2 = null;
            String tmpString3 = null;

            Elements tdElement = element.getElementsByTag("td");
            tmpString1 = tdElement.get(0).text();
            tmpString2 = tdElement.get(1).getElementsByTag("a").get(0).attr("href");
            tmpString3 = tdElement.get(2).text();

            ElementFile elementFile = new ElementFile(tmpString1, tmpString2, tmpString3);
            profileComposition.add(elementFile);
        }

        // get content of place
        place = h2Elemnts.get(3).nextElementSibling().text();

        PublicService publicService = new PublicService(title, orderOfExecution, howToPerform, profileComposition, place);
        this.publicService = publicService;
    }

    // skip all <br> tag
    private Element skipBrTag(Element element){
        while(element.tagName().equals("br")){
            element = element.nextElementSibling();
        }

        return element;
    }

    public PublicService getPublicService() {
        return publicService;
    }
}
