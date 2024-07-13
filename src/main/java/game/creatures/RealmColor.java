package game.creatures;


public enum RealmColor {
    RED("\u001B[31m","Dragons"),GREEN("\u001B[32m","Gaias"),BLUE("\u001B[34m","Hydras"),MAGENTA("\u001B[35m","Phoenixes"),
    YELLOW("\u001B[33m","Lions"),WHITE("\u001B[39m","");
    private String TextColor;
    private String name;
    
    RealmColor (String TextColor,String name){
        this.TextColor=TextColor;
        this.name = name;
    }
    
    public String getTextColor() {
        return TextColor;
    }
    public String getName() {
        return name;
    }
}
