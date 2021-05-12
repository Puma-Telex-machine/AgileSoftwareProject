package model.boxes;

/**
 * An enum representing the different visibilities of java objects/methods/variables.
 * Originally created by Filip Hanberg.
 */
public enum Visibility {
    PRIVATE,
    PACKAGE_PRIVATE,
    PROTECTED,
    PUBLIC;

    /**
     * Returns the right sign for the visibility
     *
     * @param visibility
     * @return
     */
    public static String getString(Visibility visibility) {
        String ret = "";
        switch (visibility) {
            case PUBLIC:
                ret = "+";
                break;
            case PRIVATE:
                ret = "-";
                break;
            case PROTECTED:
                ret = "#";
                break;
            case PACKAGE_PRIVATE:
                ret = "~";
                break;
        }

        return ret;
    }
}
