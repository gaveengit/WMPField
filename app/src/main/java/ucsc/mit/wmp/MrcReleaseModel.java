package ucsc.mit.wmp;

import android.util.Log;

public class MrcReleaseModel {
    public String mrc_run_id;
    public String trap_id;
    public String coordinates;
    public String add_line1;
    public String add_line2;
    public String location_description;
    public String full_name;
    public String contact_number;
    public String release_id;
    public String release_date;
    public String release_time;
    public String release_status;

    public MrcReleaseModel(){

    }

    public MrcReleaseModel(String mrc_run_id,String trap_id,String coordinates, String add_line1, String add_line2, String location_description,
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
    public MrcReleaseModel(String mrc_run_id,String trap_id, String release_id,String release_date, String release_time, String release_status) {
        this.mrc_run_id = mrc_run_id;
        this.trap_id = trap_id;
        this.release_id = release_id;
        this.release_date = release_date;
        this.release_time = release_time;
        this.release_status = release_status;
    }

    public void setRelease_id(String release_id) {
        this.release_id = release_id;
    }

    public void setDate(String release_date) {
        this.release_date = release_date;
    }

    public void setTime(String release_time) {
        this.release_time = release_time;
    }

    public void setRelease_status(String release_status) {
        this.release_status = release_status;
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


    public String getRelease_id() {
        return release_id;
    }

    public String getDate() {
        return release_date;
    }

    public String getTime() {
        return release_time;
    }

    public String getRelease_status() {
        return release_status;
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


