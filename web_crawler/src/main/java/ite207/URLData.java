package ite207;
import java.util.List;
class URLData
{
    private String url;
    private List<String> links;

    public URLData(String url, List<String> links)
    {
        this.url = url;
        this.links = links;
    }

    public String getURL()
    {
        return url;
    }

    public List<String> getLinks()
    {
        return links;
    }
}
