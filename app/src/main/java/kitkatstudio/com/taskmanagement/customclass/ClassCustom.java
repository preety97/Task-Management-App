package kitkatstudio.com.taskmanagement.customclass;

import java.util.ArrayList;

public class ClassCustom {

    public static ArrayList<ClassData> CLASSDATA = new ArrayList();

    public static class ClassData{
        public final String user_name;
//        public final String block;
//        public final String class_id;
//        public final String subject_id;

        public ClassData(String user_name) {
            this.user_name= user_name;
//            this.block = block;
//            this.class_id = class_id;
//            this.subject_id = subject_id;
        }

        @Override
        public String toString() {
            return user_name;
        }
    }
}

