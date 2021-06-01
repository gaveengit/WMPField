package ucsc.mit.wmp;

public class MrcModel {
    public String identifier;
    public String mrc_status;
    public String run_name;
    public int person_id;
    public int address_id;
    public String coordinates;
    public String exist_in_remote_server;

    public MrcModel(){

    }

    public MrcModel(String identifier, String mrc_status, String release_type, String run_name, int person_id, int address_id,String coordinates, String exist_in_remote_server) {
        this.identifier = identifier;
        this.mrc_status = mrc_status;
        this.run_name = run_name;
        this.person_id = person_id;
        this.address_id = address_id;
        this.coordinates = coordinates;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setMrc_status(String mrc_status) {
        this.mrc_status = mrc_status;
    }

    public void setRun_name(String run_name) {
        this.run_name = run_name;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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

    public String getRun_name() {
        return run_name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getExist_in_remote_server() {
        return exist_in_remote_server;
    }
}
