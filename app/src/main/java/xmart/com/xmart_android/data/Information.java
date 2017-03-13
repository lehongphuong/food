package xmart.com.xmart_android.data;

/**
 * Created by Windows on 22-12-2014.
 */
public class Information {
    public int iconId;
    public String title;

    public Information() {
    }

    public Information(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
