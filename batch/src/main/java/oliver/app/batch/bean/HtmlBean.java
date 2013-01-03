package oliver.app.batch.bean;

import org.jsoup.nodes.Document;

/**
 * Bean for hold HTML
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 4:37 PM
 */
public class HtmlBean {

    public HtmlBean(String url, Document document) {
        this.url = url;
        this.document = document;
    }

    /**
     * url
     */
    private String url;
    /**
     * html content
     */
    private Document document;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
