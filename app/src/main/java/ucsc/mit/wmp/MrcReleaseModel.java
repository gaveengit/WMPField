package ucsc.mit.wmp;

public class MrcReleaseModel {
    public int release_id;
    public String date;
    public String time;
    public String release_status;
    public String mrc_id;

    public MrcReleaseModel(){

    }

    public MrcReleaseModel(int release_id, String date, String time, String release_status, String mrc_id) {
        this.release_id = release_id;
        this.date = date;
        this.time = time;
        this.release_status = release_status;
        this.mrc_id = mrc_id;
    }

    public MrcReleaseModel(String date, String time, String release_status) {
        this.date = date;
        this.time = time;
        this.release_status = release_status;
    }

    public void setRelease_id(int release_id) {
        this.release_id = release_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRelease_status(String release_status) {
        this.release_status = release_status;
    }

    public void setMrc_id(String mrc_id) {
        this.mrc_id = mrc_id;
    }

    public int getRelease_id() {
        return release_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRelease_status() {
        return release_status;
    }

    public String getMrc_id() {
        return mrc_id;
    }
}
