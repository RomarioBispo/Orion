package subscription;


/**
 * This class is used to help to represent a object java as JSON. In this case, used to represent a subscription
 * on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Notification {
    private Http http;

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
