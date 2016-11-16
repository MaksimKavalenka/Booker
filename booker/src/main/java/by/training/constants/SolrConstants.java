package by.training.constants;

public abstract class SolrConstants {

    public static abstract class Collections {

        private static final String SOLR_URL            = "http://localhost:8983/solr";

        public static final String  CONTENT_COLLECTION  = SOLR_URL + "/booker.content";
        public static final String  METADATA_COLLECTION = SOLR_URL + "/booker.metadata";

    }

    public static abstract class Fields {

        public static abstract class ContentFields {

            public static final String ID            = "id";
            public static final String METADATA_ID   = "metadata_id";
            public static final String PLAIN_CONTENT = "plain_content";
            public static final String VERSION       = "_version_";

        }

        public static abstract class MetadataFields {

            public static final String ID               = "id";
            public static final String TITLE            = "title";
            public static final String DESCRIPTION      = "description";
            public static final String AUTHOR           = "author";
            public static final String PUBLISHER        = "publisher";
            public static final String CREATION_DATE    = "creation_date";
            public static final String PUBLICATION_DATE = "publication_date";
            public static final String UPLOADING_DATE   = "uploading_date";
            public static final String VERSION          = "_version_";

        }

    }

}
