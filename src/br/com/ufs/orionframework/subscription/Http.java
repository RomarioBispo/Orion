package br.com.ufs.orionframework.subscription;

/**
 * This class is used to help to represent a object java as JSON. In this case, used to represent a br.com.ufs.orionframework.subscription
 * on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Http {
    private String url;

    public Http(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
