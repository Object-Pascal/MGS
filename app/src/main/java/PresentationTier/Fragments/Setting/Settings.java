package PresentationTier.Fragments.Setting;

public class Settings {

    private String language;
    private boolean colorBlindmode;

    public Settings(String language, boolean colorBlindmode) {
        this.language = language;
        this.colorBlindmode = colorBlindmode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isColorBlindmode() {
        return colorBlindmode;
    }

    public void setColorBlindmode(boolean colorBlindmode) {
        this.colorBlindmode = colorBlindmode;
    }
}
