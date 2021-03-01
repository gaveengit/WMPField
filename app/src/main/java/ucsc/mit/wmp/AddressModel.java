package ucsc.mit.wmp;

public class AddressModel {
    public int address_id;
    public String address_line1;
    public String address_line2;
    public String location_description;
    public String exist_in_remote_server;

    public AddressModel(){

    }

    public AddressModel(int address_id, String address_line1, String address_line2, String location_description, String exist_in_remote_server) {
        this.address_id = address_id;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.location_description = location_description;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public AddressModel(String address_line1, String address_line2, String location_description, String exist_in_remote_server) {
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.location_description = location_description;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
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

    public void setExist_in_remote_server(String exist_in_remote_server) {
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public int getAddress_id() {
        return address_id;
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

    public String getExist_in_remote_server() {
        return exist_in_remote_server;
    }
}

