package kitkatstudio.com.taskmanagement.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PendingContent {

    public static final List<PendingItem> ITEMS = new ArrayList<>();


    public static class PendingItem {
        public final String id;
        public final String task;
        public final String description;
        public final String start_date;
        public final String end_date;
        public final String percentage;
        public final String assigned_by;

        public PendingItem(String id, String task, String description, String start_date, String end_date, String percentage, String assigned_by) {
            this.id = id;
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
