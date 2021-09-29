package ucsc.mit.wmp;

public class MrcModel {
    public String identifier;
    public String mrc_status;
    public String run_name;
    public String person_name;
    public String person_phone;
    public String address_line1;
    public String address_line2;
    public String location_description;
    public String coordinates;
    public String date;
    public String time;

    public MrcModel(){

    }

    public MrcModel(String identifier, String mrc_status, String run_name, String person_name, String person_phone, String address_line1, String address_line2,String location_description, String coordinates) {
        this.identifier = identifier;
        this.mrc_status = mrc_status;
        this.run_name = run_name;
        this.person_name = person_name;
        this.person_phone = person_phone;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.location_description = location_description;
        this.coordinates = coordinates;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
