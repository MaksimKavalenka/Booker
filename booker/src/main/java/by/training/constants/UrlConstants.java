package by.training.constants;

public abstract class URLConstants {

    public static final String ANY = "/**";

    public abstract class Key {

        public static final String ID_KEY   = "/{id}";
        public static final String PAGE_KEY = "/{page:[0-9]{1,}}";

    }

    public abstract class Page {

        private static final String UPLOAD_OPERATION = "/upload";

        public static final String  LOGIN_URL        = "/login";
        public static final String  REGISTER_URL     = "/register";

        public static final String  BOOKS_URL        = "/books";
        public static final String  BOOK_URL         = "/book" + Key.ID_KEY;

        public static final String  UPLOAD_BOOKS_URL = BOOKS_URL + UPLOAD_OPERATION;

    }

    public abstract class Rest {

        private static final String SERVICE    = "/service";

        public static final String  JSON_EXT   = ".json";

        public static final String  BOOKS_URL  = SERVICE + "/books";
        public static final String  UPLOAD_URL = SERVICE + "/upload";
        public static final String  USERS_URL  = SERVICE + "/users";

    }

}
