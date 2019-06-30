package httpRequests;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
public class HttpRequests {

    /**
     * Executes a post request on a defined url on orion, given a request body (json format).
     *
     * @param  url  An given string containing IP + Port for the resources.
     * @param requestBody An given string containing the payload (json) for the request
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void runPostRequest(String url, String requestBody) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildPostRequest(operationUrl, ByteArrayContent.fromString("application/json", requestBody));
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }


    /**
     * Executes a get request on a defined url on orion.
     *
     * @param  url  An given string containing a Orion IP address + Port.
     * @return a json containing the result of request.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public String runGetRequest(String url) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildGetRequest(operationUrl);
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        return request.execute().parseAsString();
    }

}
