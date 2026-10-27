package utils.selinoid;

public record BrowserConfig(String name, String version) {

    public String displayName() {
        return version == null ? name : name + " " + version;
    }
}