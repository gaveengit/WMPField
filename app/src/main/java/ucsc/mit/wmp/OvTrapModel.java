package ucsc.mit.wmp;

public class OvTrapModel {
    public String ov_trap_id;
    public String trap_status;
    public String position;
    public String run_name;
    public int person_id;
    public int address_id;
    public String exist_in_remote_server;

    public OvTrapModel(){

    }

    public OvTrapModel(String ov_trap_id, String trap_status, String position, String run_name, int person_id, int address_id, String exist_in_remote_server) {
        this.ov_trap_id = ov_trap_id;
        this.trap_status = trap_status;
        this.position = position;
        this.run_name = run_name;
        this.person_id = person_id;
        this.address_id = address_id;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setOv_trap_id(String ov_trap_id) {
        this.ov_trap_id = ov_trap_id;
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

    public void setExist_in_remote_server(String exist_in_remote_server) {
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public String getOv_trap_id() {
        return ov_trap_id;
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

    public String getExist_in_remote_server() {
        return exist_in_remote_server;
    }
}

