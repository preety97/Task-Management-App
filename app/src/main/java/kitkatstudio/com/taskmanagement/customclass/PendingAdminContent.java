package kitkatstudio.com.taskmanagement.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PendingAdminContent {

    public static final List<PendingAdminContentItem> ITEMS = new ArrayList<>();

    public static class PendingAdminContentItem {
        public final String id;
        public final String user_name;
        public final String task;
        public final String description;
        public final String start_date;
        public final String end_date;
        public final String percentage;
        public final String assigned_by;

        public PendingAdminContentItem(String id, String user_name, String task, String description, String start_date, String end_date, String percentage, String assigned_by) {
            this.id = id;
            this.user_name = user_name;
            this.task = task;
            this.description = description;
            this.start_date = start_date;
            this.end_date = end_date;
            this.percentage = percentage;
            this.assigned_by = assigned_by;
        }

        @Override
        public String toString() {
            return percentage;
        }
    }
}
