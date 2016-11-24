package by.training.constants;

public abstract class SolrConstants {

    public static abstract class Core {

        private static final String SOLR_URL          = "http://localhost:8983/solr";

        public static final String  CONTENT_CORE_URI  = SOLR_URL + "/booker_content";
        public static final String  METADATA_CORE_URI = SOLR_URL + "/booker_metadata";

    }

    public static abstract class Fields {

        public static abstract class ContentFields {

            public static final String CONTENT     = "content";
            public static final String ID          = "id";
            public static final String METADATA_ID = "metadata_id";
            public static final String PAGE        = "page";

        }

        public static abstract class MetadataFields {

            public static final String AUTHOR           = "author";
            public static final String CREATION_DATE    = "creation_date";
            public static final String DESCRIPTION      = "description";
            public static final String FILE_NAME        = "file_name";
            public static final String ID               = "id";
            public static final String ISBN             = "isbn";
            public static final String PUBLICATION_DATE = "publication_date";
            public static final String PUBLISHER        = "publisher";
            public static final String TITLE            = "title";
            public static final String UPLOADER         = "uploader";
            public static final String UPLOAD_DATE      = "upload_date";

        }

    }

    public static abstract class Key {

        public static final String DOCS_KEY        = "docs";
        public static final String NUM_FOUND_KEY   = "numFound";
        public static final String PAGES_COUNT_KEY = "pagesCount";
        public static final String RESPONSE_KEY    = "response";
        public static final String START_KEY       = "start";

    }

    public static abstract class Query {

        public static final String FIELD_LIST   = "fl";
        public static final String FILTER_QUERY = "fq";
        public static final String INDENT       = "indent";
        public static final String ROWS         = "rows";
        public static final String SORTING      = "sort";
        public static final String START        = "start";
        public static final String QUERY        = "q";
        public static final String WRITER_TYPE  = "wt";

    }

}
