package ucsc.mit.wmp;

public class OvCollectionModel {
    public String collection_id;
    public String date;
    public String time;
    public String collection_status;
    public String trap_condition;
    public String ov_trap_id;

    public OvCollectionModel(){

    }

    public OvCollectionModel(String collection_id, String date, String time, String collection_status, String trap_condition, String ov_trap_id) {
        this.collection_id = collection_id;
        this.date = date;
        this.time = time;
        this.collection_status = collection_status;
        this.trap_condition = trap_condition;
        this.ov_trap_id = ov_trap_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCollection_status(String collection_status) {
        this.collection_status = collection_status;
    }

    public void setTrap_condition(String trap_condition) {
        this.trap_condition = trap_condition;
    }

    public void setOv_trap_id(String ov_trap_id) {
        this.ov_trap_id = ov_trap_id;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCollection_status() {
        return collection_status;
    }

    public String getTrap_condition() {
        return trap_condition;
    }

    public String getOv_trap_id() {
        return ov_trap_id;
    }
}

