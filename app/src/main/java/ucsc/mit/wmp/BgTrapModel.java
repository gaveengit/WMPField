package ucsc.mit.wmp;

public class BgTrapModel {
    public String bg_trap_id;
    public String trap_status;
    public String position;
    public String run_name;
    public int person_id;
    public int address_id;
    public String coordinates;
    public String exist_in_remote_server;

    public BgTrapModel(){

    }

    public BgTrapModel(String bg_trap_id, String trap_status, String position, String run_name, int person_id, int address_id, String coordinates, String exist_in_remote_server) {
        this.bg_trap_id = bg_trap_id;
        this.trap_status = trap_status;
        this.position = position;
        this.run_name = run_name;
        this.person_id = person_id;
        this.address_id = address_id;
        this.coordinates = coordinates;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setBg_trap_id(String bg_trap_id) {
        this.bg_trap_id = bg_trap_id;
    }

    public void setTrap_status(String trap_status) {
        this.trap_status = trap_status;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getBg_trap_id() {
        return bg_trap_id;
    }

    public String getTrap_status() {
        return trap_status;
    }

    public String getPosition() {
        return position;
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

