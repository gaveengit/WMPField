package ucsc.mit.wmp;

public class OvTrapModel {
    public String ov_trap_id;
    public String trap_status;
    public String position;
    public String run_name;
    public String person_name;
    public String person_phone;
    public String address_line1;
    public String address_line2;
    public String location_description;
    public String coordinates;
    public OvTrapModel(){

    }

    public OvTrapModel(String ov_trap_id, String trap_status, String position, String run_name, String person_name, String person_phone, String address_line1, String address_line2,String location_description, String coordinates) {
        this.ov_trap_id = ov_trap_id;
        this.trap_status = trap_status;
        this.position = position;
        this.run_name = run_name;
        this.person_name = person_name;
        this.person_phone = person_phone;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.location_description = location_description;
        this.coordinates = coordinates;
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

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public void setPerson_phone(String person_phone) {
        this.person_phone = person_phone;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }
    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }
    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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

    public String getPerson_name() {
        return person_name;
    }

    public String getPerson_phone() {
        return person_phone;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public String getLocation_description() {
        return location_description;
    }

    public String getCoordinates() {
        return coordinates;
    }
}

