package by.training.constants;

public abstract class ISO8601DateConstants {

    private static final String COLON  = ":";
    private static final String HYPHEN = "-";
    private static final String TIME   = "T";

    public static abstract class Adding {

        public static final String YYYY_MM_DD_HH_MM_SS_MM_ADDING = Default.GMT;
        public static final String YYYY_MM_DD_HH_MM_SS_ADDING    = Default.GMT;
        public static final String YYYY_MM_DD_HH_MM_ADDING       = COLON + Default.SECOND + YYYY_MM_DD_HH_MM_SS_ADDING;
        public static final String YYYY_MM_DD_ADDING             = TIME + Default.HOUR + COLON + Default.MINUTE + YYYY_MM_DD_HH_MM_ADDING;
        public static final String YYYY_MM_ADDING                = HYPHEN + Default.DAY + YYYY_MM_DD_ADDING;
        public static final String YYYY_ADDING                   = HYPHEN + Default.MONTH + YYYY_MM_ADDING;

    }

    public static abstract class Default {

        public static final String DAY    = "01";
        public static final String GMT    = "+00:00";
        public static final String HOUR   = "00";
        public static final String MINUTE = "00";
        public static final String MONTH  = "01";
        public static final String SECOND = "00";

    }

    public static abstract class Pattern {

        public static final java.util.regex.Pattern YYYY_PATTERN                       = java.util.regex.Pattern.compile(Regex.YYYY);
        public static final java.util.regex.Pattern YYYY_MM_PATTERN                    = java.util.regex.Pattern.compile(Regex.YYYY_MM);
        public static final java.util.regex.Pattern YYYY_MM_DD_PATTERN                 = java.util.regex.Pattern.compile(Regex.YYYY_MM_DD);
        public static final java.util.regex.Pattern YYYY_MM_DD_HH_MM_PATTERN           = java.util.regex.Pattern.compile(Regex.YYYY_MM_DD_HH_MM);
        public static final java.util.regex.Pattern YYYY_MM_DD_HH_MM_SS_PATTERN        = java.util.regex.Pattern.compile(Regex.YYYY_MM_DD_HH_MM_SS);
        public static final java.util.regex.Pattern YYYY_MM_DD_HH_MM_SS_MM_PATTERN     = java.util.regex.Pattern.compile(Regex.YYYY_MM_DD_HH_MM_SS_MS);
        public static final java.util.regex.Pattern YYYY_MM_DD_HH_MM_SS_MM_GMT_PATTERN = java.util.regex.Pattern.compile(Regex.YYYY_MM_DD_HH_MM_SS_MS_GMT);

    }
    
    public static abstract class Regex {

        private static final String END                       = "$";
        private static final String START                     = "^";

        private static final String COLON_REGEX               = "[" + COLON + "]";
        private static final String HYPHEN_REGEX              = "[" + HYPHEN + "]";
        private static final String TIME_REGEX                = "[" + TIME + "| ]";

        public static final String YEAR                       = "[0-9]{4}";
        public static final String MONTH                      = "((0[0-9]{1})|(1[0-2]{1}))";
        public static final String DAY                        = "(((0|1|2)[0-9]{1})|(3[0-1]{1}))";
        public static final String HOUR                       = "(((0|1)[0-9]{1})|(2[0-3]{1}))";
        public static final String MINUTE                     = "((0|1|2|3|4|5)[0-9]{1})";
        public static final String SECOND                     = "((0|1|2|3|4|5)[0-9]{1})";
        public static final String MILLISECOND                = "([.][0-9]{6})";
        public static final String GMT                        = "((([+|-]((0[0-9]{1})|(1[0-2]{1})))|([+]1[3-4]{1}))[:]00)|((([+|-]0[3|9]{1})|([+]((0[4-6|8])|(1[0-1]))))[:]30)|([+](05|12)[:]45)";

        public static final String YYYY                       = START + YEAR + END;
        public static final String YYYY_MM                    = START + YEAR + HYPHEN_REGEX + MONTH + END;
        public static final String YYYY_MM_DD                 = START + YEAR + HYPHEN_REGEX + MONTH + HYPHEN_REGEX + DAY + END;
        public static final String YYYY_MM_DD_HH_MM           = START + YEAR + HYPHEN_REGEX + MONTH + HYPHEN_REGEX + DAY + TIME_REGEX + HOUR + COLON_REGEX + MINUTE + END;
        public static final String YYYY_MM_DD_HH_MM_SS        = START + YEAR + HYPHEN_REGEX + MONTH + HYPHEN_REGEX + DAY + TIME_REGEX + HOUR + COLON_REGEX + MINUTE + COLON_REGEX + SECOND + END;
        public static final String YYYY_MM_DD_HH_MM_SS_MS     = START + YEAR + HYPHEN_REGEX + MONTH + HYPHEN_REGEX + DAY + TIME_REGEX + HOUR + COLON_REGEX + MINUTE + COLON_REGEX + SECOND + MILLISECOND + END;
        public static final String YYYY_MM_DD_HH_MM_SS_MS_GMT = START + YEAR + HYPHEN_REGEX + MONTH + HYPHEN_REGEX + DAY + TIME_REGEX + HOUR + COLON_REGEX + MINUTE + COLON_REGEX + SECOND + "(" + MILLISECOND + "|)" + GMT + END;

    }

}
