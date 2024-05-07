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
public class ShowMessageContent {

    public static final List<ShowMessageItem> ITEMS = new ArrayList<>();

    public static class ShowMessageItem {
        public final String task;
        public final String description;
        public final String message;

        public ShowMessageItem(String task, String description, String message) {
            this.task = task;
            this.description = description;
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
