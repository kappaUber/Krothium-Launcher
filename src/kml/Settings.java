package kml;

import org.json.JSONObject;

import java.io.File;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
public class Settings {
    private String locale = "en-us";
    private boolean keepLauncherOpen = false;
    private boolean showGameLog = false;
    private boolean enableAdvanced = false;
    private boolean enableHistorical = false;
    private boolean enableSnapshots = false;
    private final Kernel kernel;

    public Settings(Kernel k){
        kernel = k;
    }

    public void loadSettings() {
        kernel.getConsole().printInfo("Loading settings...");
        File launcherProfiles = kernel.getConfigFile();
        if (launcherProfiles.exists()) {
            try {
                JSONObject root = new JSONObject(Utils.readURL(launcherProfiles.toURI().toURL()));
                if (root.has("settings")) {
                    JSONObject settings = root.getJSONObject("settings");
                    if (settings.has("locale")) {
                        setLocale(settings.getString("locale"));
                    } else {
                        setLocale("en-us");
                    }
                    if (settings.has("keepLauncherOpen")) {
                        keepLauncherOpen = settings.getBoolean("keepLauncherOpen");
                    }
                    if (settings.has("showGameLog")) {
                        showGameLog = settings.getBoolean("showGameLog");
                    }
                    if (settings.has("enableAdvanced")) {
                        enableAdvanced = settings.getBoolean("enableAdvanced");
                    }
                    if (settings.has("enableHistorical")) {
                        enableHistorical = settings.getBoolean("enableHistorical");
                    }
                    if (settings.has("enableSnapshots")) {
                        enableSnapshots = settings.getBoolean("enableSnapshots");
                    }
                } else {
                    setLocale("en-us");
                }
            } catch (Exception ex) {
                kernel.getConsole().printError("Failed to load settings data. Using defaults...");
                setLocale("en-us");
                keepLauncherOpen = false;
                showGameLog = false;
                enableAdvanced = false;
                enableHistorical = false;
                enableSnapshots = false;
            }
        } else{
            kernel.getConsole().printError("Launcher profiles file not found. Using defaults.");
            setLocale("en-us");
        }
    }
    public boolean getKeepLauncherOpen(){return this.keepLauncherOpen;}
    public String getLocale(){return this.locale;}
    public boolean getShowGameLog(){return this.showGameLog;}
    public boolean getEnableAdvanced(){return this.enableAdvanced;}
    public boolean getEnableHistorical(){return this.enableHistorical;}
    public boolean getEnableSnapshots(){return this.enableSnapshots;}
    public void setKeepLauncherOpen(boolean b){this.keepLauncherOpen = b;}
    public void setLocale(String s){
        if (s != null){
            if (s.equals("es-es") || s.equals("en-us") || s.equals("pt-pt") || s.equals("val-es")){
                kernel.getConsole().printInfo("Switched language to " + s);
                this.locale = s;
                Language.loadLang(s);
            }
        }
    }
    public void setShowGameLog(boolean b){this.showGameLog = b;}
    public void setEnableAdvanced(boolean b){this.enableAdvanced = b;}
    public void setEnableHistorical(boolean b){this.enableHistorical = b;}
    public void setEnableSnapshots(boolean b){
        this.enableSnapshots = b;
        kernel.getProfiles().updateSessionProfiles();
    }
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("locale", getLocale());
        o.put("keepLauncherOpen", getKeepLauncherOpen());
        o.put("showGameLog", getShowGameLog());
        o.put("enableAdvanced", getEnableAdvanced());
        o.put("enableHistorical", getEnableHistorical());
        o.put("enableSnapshots", getEnableSnapshots());
        return o;
    }
}
