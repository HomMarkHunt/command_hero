package types;

public enum TownCommands {
    clear(""),
    ls(""),
    pwd(""),
    help(""),
    ;
 
    private final String command;
 
    private TownCommands(String command) {
        this.command = command;
    }
 
    public String getName() {
        return this.command;
    }
}