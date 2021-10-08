package ghozkiu.minefectedutil.utilities.paths;

public enum Permissions {
    DISEASE_BYPASS("mfu.bypass"),
    RELOAD("mfu.reload"),
    HAT("mfu.hat"),
    VOLVER("mfu.volver");
    private final String type;

    Permissions(String paramString1) {
        this.type = paramString1;
    }

    public String get() {
        return this.type;
    }
}
