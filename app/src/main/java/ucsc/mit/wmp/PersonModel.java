package ucsc.mit.wmp;

public class PersonModel {
    public int person_id;
    public String person_name;
    public int phone;
    public String exist_in_remote_server;

    public PersonModel() {

    }

    public PersonModel(String person_name, int phone, String exist_in_remote_server) {
        this.person_name = person_name;
        this.phone = phone;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public PersonModel(int person_id, String person_name, int phone, String exist_in_remote_server) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.phone = phone;
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setExist_in_remote_server(String exist_in_remote_server) {
        this.exist_in_remote_server = exist_in_remote_server;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public int getPhone() {
        return phone;
    }

    public String getExist_in_remote_server() {
        return exist_in_remote_server;
    }
}

