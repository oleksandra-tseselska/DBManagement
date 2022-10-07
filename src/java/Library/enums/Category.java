package Library.enums;

public enum Category {
    ADVENTURE_STORIES("adventure"),
    CLASSICS("classics"),
    FAIRY_TALES("tales"),
    FANTASY("fantasy"),
    HISTORICAL_FICTION("history"),
    OTHER("other");

    private final String readableName;

    Category(final String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName(){
        return readableName;
    }
}
