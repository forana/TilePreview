package forana.tilepreview;

public class App {
    private static final String HELP_TEXT = "Usage: java -jar TilePreview.jar [-xywhz] image_file\n" +
            "\n" + 
            "Options: (all are integer values)\n" +
            "    -x <x>  - The x-offset inside the source image from which to sample. Default = 0\n" +
            "    -y <y>  - The y-offset inside the image from which to sample. Default = 0\n" +
            "    -w <w>  - The width inside the image to sample. Defaults to the whole thing.\n" +
            "    -h <h>  - The height inside the image to sample. Defaults to the whole thing.\n" + 
            "    -z <z>  - Zoom factor. Defaults to 1. 2 would mean 200% zoom.";
    
    private static final String FLAG_ERROR_TEXT = "Invalid value '%s' for option '%s'.";
    private static final String BAD_FLAG_TEXT = "Invalid option '%s'.";
    private static final String MISSING_VALUE_TEXT = "Missing value for option '%s'.";
    
    private static void printHelp() {
        System.out.println(HELP_TEXT);
    }
    
    private static void printError(String flag, String value) {
        System.out.println(String.format(FLAG_ERROR_TEXT, value, flag));
        printHelp();
        System.exit(-1);
    }
    
    private static void printError(String value) {
        System.out.println(String.format(BAD_FLAG_TEXT, value));
        printHelp();
        System.exit(-1);
    }
    
    private static void printMissingValue(String option) {
        System.out.println(String.format(MISSING_VALUE_TEXT, option));
        printHelp();
        System.exit(-1);
    }
    
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("help")) {
            printHelp();
        } else {
            parseArgs(args);
        }
    }
    
    private static void parseArgs(String[] args) {
        PreviewWindow window = new PreviewWindow(args[args.length - 1]);
        
        for (int i = 0; i < args.length - 1; i += 2) {
            String option = args[i];
            if (i + 1 == args.length - 1) {
                printMissingValue(option);
            }
            int value = 0;
            try {
                value = Integer.parseInt(args[i + 1]);
            } catch (NumberFormatException e) {
                printError(option, args[i + 1]);
            }
            if (value < 0) {
                printError(option, args[i + 1]);
            }
            if (option.equals("-x")) {
                window.setX(value);
            } else if (option.equals("-y")) {
                window.setY(value);
            } else if (option.equals("-w")) {
                window.setWidth(value);
            } else if (option.equals("-h")) {
                window.setHeight(value);
            } else if (option.equals("-z")) {
                window.setZoom(value);
            } else {
                printError(option);
            }
        }
        
        window.show();
    }
}
