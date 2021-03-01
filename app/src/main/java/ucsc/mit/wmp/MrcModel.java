package ucsc.mit.wmp;

public class MrcModel {
    public String identifier;
    public String mrc_status;
    public String release_type;
    public String run_name;
    public String person_id;
    public String address_id;
    public String exist_in_remote_server;

    public MrcModel(){

    }

    public MrcModel(String identifier, String mrc_status, String release_type, String run_name, String person_id, String address_id, String exist_in_remote_server) {
        this.identifier = identifier;
        this.mrc_status = mrc_status;
        this.release_type = release_type;
        this.run_name = run_name;
        this.person_id = person_id;
        this.address_id = address_id;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setMrc_status(String mrc_status) {
        this.mrc_status = mrc_status;
    }

    public void setRelease_type(String release_type) {
        this.release_type = release_type;
    }

    public void setRun_name(String run_name) {
        this.run_name = run_name;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public void setExist_in_remote_server(String exist_in_remote_server) {
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getMrc_status() {
        return mrc_status;
    }

    public String getRelease_type() {
        return release_type;
    }

    public String getRun_name() {
        return run_name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getExist_in_remote_server() {
        return exist_in_remote_server;
    }
}
