package by.training.constants;

public abstract class SolrConstants {

    public static abstract class Core {

        private static final String SOLR_URL          = "http://localhost:8983/solr";

        public static final String  CONTENT_CORE_URI  = SOLR_URL + "/booker_content";
        public static final String  METADATA_CORE_URI = SOLR_URL + "/booker_metadata";

    }

    public static abstract class Fields {

        public static final String SCORE = "score";

        public static abstract class ContentFields {

            public static final String CONTENT     = "content";
            public static final String ID          = "id";
            public static final String METADATA_ID = "metadataId";
            public static final String PAGE        = "page";

        }

        public static abstract class MetadataFields {

            public static final String AUTHOR           = "author";
            public static final String CREATION_DATE    = "creationDate";
            public static final String DESCRIPTION      = "description";
            public static final String FILE_NAME        = "fileName";
            public static final String ID               = "id";
            public static final String ISBN             = "isbn";
            public static final String PAGES_COUNT      = "pagesCount";
            public static final String PUBLICATION_DATE = "publicationDate";
            public static final String PUBLISHER        = "publisher";
            public static final String TITLE            = "title";
            public static final String UPLOADER         = "uploader";
            public static final String UPLOAD_DATE      = "uploadDate";

        }

    }

    public static abstract class Key {

        public static final String DOCS_KEY         = "docs";
        public static final String HIGHLIGHTING_KEY = "highlighting";
        public static final String MAX_SCORE        = "maxScore";
        public static final String NUM_FOUND_KEY    = "numFound";
        public static final String RESPONSE_KEY     = "response";
        public static final String SPELLCHECK_KEY   = "spellcheck";
        public static final String START_KEY        = "start";
        public static final String SUGGESTION_KEY   = "suggestion";
        public static final String SUGGESTIONS_KEY  = "suggestions";

    }

    public static abstract class Query {

        public static final String FIELD_LIST         = "fl";
        public static final String FILTER_QUERY       = "fq";
        public static final String HIGHLIGHT          = "hl";
        public static final String HIGHLIGHTED_FIELDS = HIGHLIGHT + ".fl";
        public static final String ROWS               = "rows";
        public static final String SHARDS             = "shards";
        public static final String SORTING            = "sort";
        public static final String START              = "start";
        public static final String QUERY              = "q";
        public static final String WRITER_TYPE        = "wt";

    }

}
