package br.com.ufs.orionframework.httprequests;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * @author Felipe Matheus, Mariana Martins and Romario Bispo.
 * @version %I%, %G%
 * @since 1.0
 *
 * This class have the responsability to run requests to orion.
 * All requests use headers (fiware-service, openiot, fiware-servicepath /)
 * if you need another headers, you must change the source code or extends this class and override this methods.
 */
public class HttpRequests {
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
     * Executes a delete request on a defined url.
     *
     * @param  url  An given string containing a Orion IP address + Port.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void runDeleteRequest(String url) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildDeleteRequest(operationUrl);
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }

    /**
     * Executes a put request on a defined url on orion, given a request body (json format).
     *
     * @param  url  An given string containing IP + Port for the resources.
     * @param requestBody An given string containing the payload (json) for the request
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void runPutRequest(String url, String requestBody) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildPutRequest(operationUrl, ByteArrayContent.fromString("application/json", requestBody));
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }

    /**
     * Executes a patch request on a defined url on orion, given a request body (json format).
     *
     * @param  url  An given string containing IP + Port for the resources.
     * @param requestBody An given string containing the payload (json) for the request
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void runPatchRequest(String url, String requestBody) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildPatchRequest(operationUrl, ByteArrayContent.fromString("application/json", requestBody));
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }



}
