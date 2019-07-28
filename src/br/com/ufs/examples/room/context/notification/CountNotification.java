package br.com.ufs.examples.room.context.notification;

import java.util.ArrayList;
import java.util.List;

public class CountNotification {
    private String subscriptionId;
    private List<Data> data;

    public CountNotification() {
        this.data = new ArrayList<>();
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String id;
        private Count count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Count getCount() {
            return count;
        }

        public void setCount(Count count) {
            this.count = count;
        }
    }

    public static class Count {
        private String type;
        private String value;
        private Metadata matadata;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValue() {
            if(this.value == null || this.value.equals("") || this.value.equals(" ")){
                return 0;
            }
            return Integer.valueOf(this.value);
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Metadata getMatadata() {
            return matadata;
        }

        public void setMatadata(Metadata matadata) {
            this.matadata = matadata;
        }
    }

    public static class Metadata {

    }
}
