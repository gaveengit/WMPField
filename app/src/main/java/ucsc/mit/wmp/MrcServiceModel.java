package ucsc.mit.wmp;

public class MrcServiceModel {
    public String service_id;
    public String service_date;
    public String service_time;
    public String service_status;
    public String mrc_run_id;
    public String trap_id;
    public String coordinates;
    public String add_line1;
    public String add_line2;
    public String location_description;
    public String full_name;
    public String contact_number;

    public MrcServiceModel(){

    }

    public MrcServiceModel(String mrc_run_id,String trap_id,String coordinates, String add_line1, String add_line2, String location_description,
                          String full_name, String contact_number) {
        this.mrc_run_id = mrc_run_id;
        this.trap_id = trap_id;
        this.coordinates = coordinates;
        this.add_line1 = add_line1;
        this.add_line2 = add_line2;
        this.location_description = location_description;
        this.full_name = full_name;
        this.contact_number = contact_number;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setDate(String service_date) {
        this.service_date = service_date;
    }

    public void setTime(String service_time) {
        this.service_time = service_time;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }

    public void setMrc_trap_id(String mrc_trap_id) {
        this.trap_id = mrc_trap_id;
    }

    public void setMrc_run_id(String mrc_run_id) {
        this.mrc_run_id = mrc_run_id;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setAdd_line1(String add_line1) {
        this.add_line1 = add_line1;
    }

    public void setAdd_line2(String add_line2) {
        this.add_line2 = add_line2;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }


    public String getService_id() {
        return service_id;
    }

    public String getDate() {
        return service_date;
    }

    public String getTime() {
        return service_time;
    }

    public String getService_status() {
        return service_status;
    }

    public String getMrc_run_id() {
        return mrc_run_id;
    }

    public String getMrc_trap_id() {
        return trap_id;
    }

    public String get_coordinates() {
        return coordinates;
    }

    public String get_addline1() {
        return add_line1;
    }

    public String get_addline2() {
        return add_line2;
    }

    public String getLocation_description() {
        return location_description;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getContact_number() {
        return contact_number;
    }

}


