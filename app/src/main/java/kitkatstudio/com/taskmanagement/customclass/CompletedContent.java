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
public class CompletedContent {

    public static final List<CompletedItem> ITEMS = new ArrayList<>();

    public static class CompletedItem {
        public final String task;
        public final String description;
        public final String start_date;
        public final String end_date;
        public final String assigned_by;

        public CompletedItem(String task, String description, String start_date, String end_date, String assigned_by) {
            this.task = task;
            this.description = description;
            this.start_date = start_date;
            this.end_date = end_date;
            this.assigned_by = assigned_by;
        }

        @Override
        public String toString() {
            return end_date;
        }
    }
}
