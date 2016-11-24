package by.training.parser;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.EmbeddedContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.tika.parser.epub.EpubParser;

public class AdvancedEpubParser extends EpubParser {

    private static final long serialVersionUID = -8025822461839132759L;

    @Override
    public void parse(InputStream stream, ContentHandler handler, Metadata metadata,
            ParseContext context) throws IOException, SAXException, TikaException {
        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
        xhtml.startDocument();
        ContentHandler childHandler = new EmbeddedContentHandler(new BodyContentHandler(xhtml));

        ZipInputStream zip = new ZipInputStream(stream);
        ZipEntry entry = zip.getNextEntry();

        while (entry != null) {
            if (entry.getName().equals("mimetype")) {
                String type = IOUtils.toString(zip, UTF_8);
                if (type != null) {
                    type = type.trim();
                }
                metadata.set(HttpHeaders.CONTENT_TYPE, type);
            } else if (entry.getName().equals("metadata.xml")) {
                super.getMetaParser().parse(zip, new DefaultHandler(), metadata, context);
            } else if (entry.getName().endsWith(".opf")) {
                super.getMetaParser().parse(zip, new DefaultHandler(), metadata, context);
            } else if (entry.getName().endsWith(".htm") || entry.getName().endsWith(".html")
                    || entry.getName().endsWith(".xhtml")) {
                super.getContentParser().parse(zip, childHandler, metadata, context);
            }
            entry = zip.getNextEntry();
        }

        xhtml.endDocument();
    }

}
