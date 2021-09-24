package ucsc.mit.wmp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DbHandler extends SQLiteOpenHelper {
    private static final int VERSION = 31;
    private static final String DB_NAME = "FieldDatabase";
    // table and column names for person table
    private static final String TABLE_PERSON = "person";
    private static final String PERSON_ID = "person_id";
    private static final String PERSON_NAME = "person_name";
    private static final String PERSON_PHONE = "person_phone";
    private static final String EXIST_IN_REMOTE_SERVER = "exist_in_remote_server";

    // table and column names for address table
    private static final String TABLE_ADDRESS = "address";
    private static final String ADDRESS_ID = "address_id";
    private static final String ADDRESS_LINE1 = "address_line1";
    private static final String ADDRESS_LINE2 = "address_line2";
    private static final String LOCATION_DESCRIPTION = "location_description";

    // table and column names for Mrc table
    private static final String TABLE_MRC = "mrc";
    private static final String IDENTIFIER = "identifier";
    private static final String MRC_STATUS = "mrc_status";
    private static final String RUN_NAME = "run_name";
    private static final String MRC_COORDINATES = "mrc_coordinates";

    // table and column names for Mrc_Release table
    private static final String TABLE_MRC_RELEASE = "mrc_release";
    private static final String RELEASE_ID = "release_id";
    private static final String RELEASE_DATE = "release_date";
    private static final String RELEASE_TIME = "release_time";
    private static final String RELEASE_STATUS = "release_status";

    // table and column names for Bg_Trap table
    private static final String TABLE_BG_TRAP = "bg_trap";
    private static final String BG_TRAP_ID = "bg_trap_id";
    private static final String BG_TRAP_STATUS = "bg_trap_status";
    private static final String BG_POSITION = "bg_position";
    private static final String BG_COORDINATES = "bg_coordinates";

    // table and column names for Bg_Collection table
    private static final String TABLE_BG_COLLECTION = "bg_collection";
    private static final String COLLECTION_ID = "collection_id";
    private static final String COLLECTION_DATE = "collection_date";
    private static final String COLLECTION_TIME = "collection_time";
    private static final String COLLECTION_STATUS = "collection_status";
    private static final String TRAP_CONDITION = "trap_condition";

    // table and column names for Ov_Trap table
    private static final String TABLE_OV_TRAP = "ov_trap";
    private static final String OV_TRAP_ID = "ov_trap_id";
    private static final String OV_TRAP_STATUS = "ov_trap_status";
    private static final String OV_POSITION = "ov_position";
    private static final String OV_COORDINATES = "ov_coordinates";

    private static final String TABLE_OVI_COLLECTION = "ovi_collection";

    private static final String TABLE_OVI_SERVICE = "ovi_service";
    private static final String TABLE_BG_SERVICE = "bg_service";
    private static final String TABLE_MRC_SERVICE = "mrc_service";

    private static final String OVI_RUN_ID = "ovi_run_id";
    private static final String BG_RUN_ID = "bg_run_id";
    private static final String MRC_RUN_ID = "mrc_run_id";

    private static final String TRAP_ID = "trap_id";
    private static final String TRAP_POSITION = "trap_position";
    private static final String COORDINATES = "coordinates";
    private static final String ADD_LINE1 = "add_line1";
    private static final String ADD_LINE2 = "add_line2";
    private static final String FULL_NAME = "full_name";
    private static final String CONTACT_NUMBER = "contact_number";
    private static final String SERVICE_ID = "service_id";
    private static final String SERVICE_DATE = "service_date";
    private static final String SERVICE_TIME = "service_time";
    private static final String SERVICE_STATUS = "service_status";

    private static final String DATE = "date";
    private static final String TIME = "time";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String TABLE_CREATE_QUERY_MRC = "CREATE TABLE " + TABLE_MRC + " " + "(" + IDENTIFIER + " TEXT," + MRC_STATUS
                + " TEXT," + RUN_NAME + " TEXT," + PERSON_NAME + " TEXT," + PERSON_PHONE +" INTEGER," +  ADDRESS_LINE1 + " TEXT," + ADDRESS_LINE2 + " TEXT," + LOCATION_DESCRIPTION + " TEXT," + MRC_COORDINATES + " TEXT," + DATE +" TEXT,"+ TIME +" TEXT," + "primary key " + "(" + IDENTIFIER + ")"+");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_MRC);

        String TABLE_CREATE_QUERY_BG_TRAP = "CREATE TABLE " + TABLE_BG_TRAP + " " + "(" + BG_TRAP_ID + " TEXT," + BG_TRAP_STATUS
                + " TEXT," + BG_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_NAME + " TEXT," + PERSON_PHONE +" INTEGER," +  ADDRESS_LINE1 + " TEXT," + ADDRESS_LINE2 + " TEXT," + LOCATION_DESCRIPTION + " TEXT," +  BG_COORDINATES + " TEXT,"+ DATE +" TEXT,"+ TIME +" TEXT," + "primary key " + "(" + BG_TRAP_ID + ")"+");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_TRAP);

        String TABLE_CREATE_QUERY_OV_TRAP = "CREATE TABLE " + TABLE_OV_TRAP + " " + "(" + OV_TRAP_ID + " TEXT," + OV_TRAP_STATUS
                + " TEXT," + OV_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_NAME + " TEXT," + PERSON_PHONE +" INTEGER," +  ADDRESS_LINE1 + " TEXT," + ADDRESS_LINE2 + " TEXT," + LOCATION_DESCRIPTION + " TEXT," + OV_COORDINATES + " TEXT," + DATE +" TEXT,"+ TIME +" TEXT," + "primary key " + "(" + OV_TRAP_ID + ")"+ ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_OV_TRAP);

        String TABLE_CREATE_QUERY_OVI_SERVICE = "CREATE TABLE " + TABLE_OVI_SERVICE + " " + "(" + OVI_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + TRAP_POSITION + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + SERVICE_ID + " TEXT," + SERVICE_DATE +" TEXT,"+ SERVICE_TIME +" TEXT,"+ SERVICE_STATUS+ " TEXT," +"primary key " + "(" + OVI_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_OVI_SERVICE);

        String TABLE_CREATE_QUERY_BG_SERVICE = "CREATE TABLE " + TABLE_BG_SERVICE + " " + "(" + BG_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + TRAP_POSITION + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + SERVICE_ID + " TEXT," + SERVICE_DATE +" TEXT,"+ SERVICE_TIME +" TEXT,"+ SERVICE_STATUS+ " TEXT," +"primary key " + "(" + BG_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_SERVICE);

        String TABLE_CREATE_QUERY_MRC_SERVICE = "CREATE TABLE " + TABLE_MRC_SERVICE + " " + "(" + MRC_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + SERVICE_ID + " TEXT," + SERVICE_DATE +" TEXT,"+ SERVICE_TIME +" TEXT,"+ SERVICE_STATUS+ " TEXT," +"primary key " + "(" + MRC_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_MRC_SERVICE);

        String TABLE_CREATE_QUERY_BG_COLLECTION = "CREATE TABLE " + TABLE_BG_COLLECTION + " " + "(" + BG_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + TRAP_POSITION + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + COLLECTION_ID + " TEXT," + COLLECTION_DATE +" TEXT,"+ COLLECTION_TIME +" TEXT,"+ COLLECTION_STATUS+ " TEXT," +"primary key " + "(" + BG_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_COLLECTION);

        String TABLE_CREATE_QUERY_OVI_COLLECTION = "CREATE TABLE " + TABLE_OVI_COLLECTION + " " + "(" + OVI_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + TRAP_POSITION + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + COLLECTION_ID + " TEXT," + COLLECTION_DATE +" TEXT,"+ COLLECTION_TIME +" TEXT,"+ COLLECTION_STATUS+ " TEXT," +"primary key " + "(" + OVI_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_OVI_COLLECTION);

        String TABLE_CREATE_QUERY_MRC_RELEASE = "CREATE TABLE " + TABLE_MRC_RELEASE + " " + "(" + MRC_RUN_ID + " TEXT," + TRAP_ID
                + " TEXT," + COORDINATES + " TEXT," + ADD_LINE1 + " TEXT," + ADD_LINE2 + " TEXT," + LOCATION_DESCRIPTION+" TEXT,"+ FULL_NAME + " TEXT," + CONTACT_NUMBER + " TEXT," + RELEASE_ID + " TEXT," + RELEASE_DATE +" TEXT,"+ RELEASE_TIME +" TEXT,"+ RELEASE_STATUS+ " TEXT," +"primary key " + "(" + MRC_RUN_ID +","+ TRAP_ID + ")" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_MRC_RELEASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_QUERY_TABLE_MRC = "DROP TABLE IF EXISTS " + TABLE_MRC;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_MRC);
        String DROP_TABLE_QUERY_TABLE_MRC_RELEASE = "DROP TABLE IF EXISTS " + TABLE_MRC_RELEASE;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_MRC_RELEASE);
        String DROP_TABLE_QUERY_TABLE_BG_TRAP = "DROP TABLE IF EXISTS " + TABLE_BG_TRAP;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_BG_TRAP);
        String DROP_TABLE_QUERY_TABLE_BG_COLLECTION = "DROP TABLE IF EXISTS " + TABLE_BG_COLLECTION;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_BG_COLLECTION);
        String DROP_TABLE_QUERY_TABLE_OV_TRAP = "DROP TABLE IF EXISTS " + TABLE_OV_TRAP;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_OV_TRAP);
        String DROP_TABLE_QUERY_TABLE_OV_COLLECTION = "DROP TABLE IF EXISTS " + TABLE_OVI_COLLECTION;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_OV_COLLECTION);
        String DROP_TABLE_QUERY_TABLE_OVI_SERVICE = "DROP TABLE IF EXISTS " + TABLE_OVI_SERVICE;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_OVI_SERVICE);
        String DROP_TABLE_QUERY_TABLE_BG_SERVICE = "DROP TABLE IF EXISTS " + TABLE_BG_SERVICE;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_BG_SERVICE);
        String DROP_TABLE_QUERY_TABLE_MRC_SERVICE = "DROP TABLE IF EXISTS " + TABLE_MRC_SERVICE;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_MRC_SERVICE);

        onCreate(sqLiteDatabase);
    }

    public void insertDataPerson(PersonModel personObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME, personObj.getPerson_name());
        contentValues.put(PERSON_PHONE, personObj.getPhone());
        contentValues.put(EXIST_IN_REMOTE_SERVER, personObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_PERSON, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }

    public void insertDataAddress(AddressModel addressObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_LINE1, addressObj.getAddress_line1());
        contentValues.put(ADDRESS_LINE2, addressObj.getAddress_line2());
        contentValues.put(LOCATION_DESCRIPTION, addressObj.getLocation_description());
        contentValues.put(EXIST_IN_REMOTE_SERVER, addressObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_ADDRESS, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }

    public long insertDataMrc(MrcModel mrcObj) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar currentTime = Calendar.getInstance();
        String hour = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currentTime.get(Calendar.MINUTE));
        String current_time = hour + ":" + minute;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDENTIFIER, mrcObj.getIdentifier());
        contentValues.put(MRC_STATUS, mrcObj.getMrc_status());
        contentValues.put(RUN_NAME, mrcObj.getRun_name());
        contentValues.put(PERSON_NAME, mrcObj.getPerson_name());
        contentValues.put(PERSON_PHONE, mrcObj.getPerson_phone());
        contentValues.put(ADDRESS_LINE1, mrcObj.getAddress_line1());
        contentValues.put(ADDRESS_LINE2 ,mrcObj.getAddress_line2());
        contentValues.put(MRC_COORDINATES, mrcObj.getCoordinates());
        contentValues.put(LOCATION_DESCRIPTION, mrcObj.getLocation_description());
        contentValues.put(DATE, currentDate);
        contentValues.put(TIME, current_time);

        // save to table
        long flag = sqLiteDatabase.insert(TABLE_MRC, null, contentValues);
        // close connection
        sqLiteDatabase.close();

        return flag;
    }


    public long insertDataBgTrap(BgTrapModel bgTrapObj) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar currentTime = Calendar.getInstance();
        String hour = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currentTime.get(Calendar.MINUTE));
        String current_time = hour + ":" + minute;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_TRAP_ID, bgTrapObj.getBg_trap_id());
        contentValues.put(BG_TRAP_STATUS, bgTrapObj.getTrap_status());
        contentValues.put(BG_POSITION, bgTrapObj.getPosition());
        contentValues.put(RUN_NAME, bgTrapObj.getRun_name());
        contentValues.put(PERSON_NAME, bgTrapObj.getPerson_name());
        contentValues.put(PERSON_PHONE, bgTrapObj.getPerson_phone());
        contentValues.put(ADDRESS_LINE1, bgTrapObj.getAddress_line1());
        contentValues.put(ADDRESS_LINE2 ,bgTrapObj.getAddress_line2());
        contentValues.put(BG_COORDINATES, bgTrapObj.getCoordinates());
        contentValues.put(LOCATION_DESCRIPTION, bgTrapObj.getLocation_description());
        contentValues.put(DATE, currentDate);
        contentValues.put(TIME, current_time);

        // save to table
        long flag = sqLiteDatabase.insert(TABLE_BG_TRAP, null, contentValues);
        // close connection
        sqLiteDatabase.close();

        return flag;
    }

    public long insertDataBgCollection(BgCollectionModel bgCollectionObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_RUN_ID,bgCollectionObj.bg_run_id);
        contentValues.put(TRAP_ID,bgCollectionObj.trap_id);
        contentValues.put(TRAP_POSITION,bgCollectionObj.trap_position);
        contentValues.put(COORDINATES,bgCollectionObj.coordinates);
        contentValues.put(ADD_LINE1,bgCollectionObj.add_line1);
        contentValues.put(ADD_LINE2,bgCollectionObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,bgCollectionObj.location_description);
        contentValues.put(FULL_NAME,bgCollectionObj.full_name);
        contentValues.put(CONTACT_NUMBER,bgCollectionObj.contact_number);
        contentValues.put(COLLECTION_STATUS,bgCollectionObj.collection_status);
        // save to table
        long flag = sqLiteDatabase.insert(TABLE_BG_COLLECTION, null, contentValues);
        // close connection
        sqLiteDatabase.close();

        return flag;
    }

    public long insertDataOviCollection(OviCollectionModel oviCollectionObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVI_RUN_ID,oviCollectionObj.ovi_run_id);
        contentValues.put(TRAP_ID,oviCollectionObj.trap_id);
        contentValues.put(TRAP_POSITION,oviCollectionObj.trap_position);
        contentValues.put(COORDINATES,oviCollectionObj.coordinates);
        contentValues.put(ADD_LINE1,oviCollectionObj.add_line1);
        contentValues.put(ADD_LINE2,oviCollectionObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,oviCollectionObj.location_description);
        contentValues.put(FULL_NAME,oviCollectionObj.full_name);
        contentValues.put(CONTACT_NUMBER,oviCollectionObj.contact_number);
        contentValues.put(COLLECTION_STATUS,oviCollectionObj.collection_status);
        // save to table
        long flag = sqLiteDatabase.insert(TABLE_OVI_COLLECTION, null, contentValues);
        // close connection
        sqLiteDatabase.close();

        return flag;
    }

    public long insertDataOvTrap(OvTrapModel ovTrapObj) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar currentTime = Calendar.getInstance();
        String hour = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currentTime.get(Calendar.MINUTE));
        String current_time = hour + ":" + minute;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OV_TRAP_ID, ovTrapObj.getOv_trap_id());
        contentValues.put(OV_TRAP_STATUS, ovTrapObj.getTrap_status());
        contentValues.put(OV_POSITION, ovTrapObj.getPosition());
        contentValues.put(RUN_NAME, ovTrapObj.getRun_name());
        contentValues.put(PERSON_NAME, ovTrapObj.getPerson_name());
        contentValues.put(PERSON_PHONE, ovTrapObj.getPerson_phone());
        contentValues.put(ADDRESS_LINE1, ovTrapObj.getAddress_line1());
        contentValues.put(ADDRESS_LINE2 ,ovTrapObj.getAddress_line2());
        contentValues.put(OV_COORDINATES, ovTrapObj.getCoordinates());
        contentValues.put(LOCATION_DESCRIPTION, ovTrapObj.getLocation_description());
        contentValues.put(DATE, currentDate);
        contentValues.put(TIME, current_time);
        // save to table
        long flag = sqLiteDatabase.insert(TABLE_OV_TRAP, null, contentValues);
        // close connection
        sqLiteDatabase.close();
        return flag;
    }
public List<String> getAllOviRuns() {
    List<String> runs = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    String query = "select distinct run_name from ov_trap union all select distinct ovi_run_id from ovi_service union all select distinct ovi_run_id from ovi_collection";
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
        do {
            runs.add(cursor.getString(0));
        } while (cursor.moveToNext());
    }
    db.close();
    return runs;
}
    public List<String> getAllBgRuns() {
        List<String> runs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select distinct run_name from bg_trap union all select distinct bg_run_id from bg_service union all select distinct bg_run_id from bg_collection";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                runs.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        return runs;
    }
    public List<String> getAllMrcRuns() {
        List<String> runs = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select distinct run_name from mrc union all select distinct mrc_run_id from mrc_service union all select distinct mrc_run_id from mrc_release";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                runs.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        return runs;
    }
    public List<PersonModel> getAllPersons() {
        List<PersonModel> persons = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_PERSON + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PersonModel person = new PersonModel();
                person.setPerson_id(cursor.getInt(0));
                person.setPerson_name(cursor.getString(1));
                person.setExist_in_remote_server(cursor.getString(2));

                persons.add(person);
            } while (cursor.moveToNext());
        }
        db.close();
        return persons;
    }

    public List<PersonModel> getLastPerson() {
        List<PersonModel> persons = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select max(person_id) as max_person_id from " + TABLE_PERSON + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PersonModel person = new PersonModel();
                person.setPerson_id(cursor.getInt(0));
                persons.add(person);
            } while (cursor.moveToNext());
        }
        db.close();
        return persons;
    }


    public List<PersonModel> getSinglePersons(Integer selected_id) {
        List<PersonModel> persons = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_PERSON + " where person_id=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PersonModel publisher = new PersonModel();
                publisher.setPerson_id(cursor.getInt(0));
                publisher.setPerson_name(cursor.getString(1));
                publisher.setExist_in_remote_server(cursor.getString(2));

                persons.add(publisher);
            } while (cursor.moveToNext());
        }
        db.close();
        return persons;
    }

    public int updateSinglePerson(PersonModel personInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_NAME, personInstance.getPerson_name());
        contentValues.put(PERSON_PHONE, personInstance.getPhone());
        int status = sqLiteDatabase.update(TABLE_PERSON, contentValues, "person_id=?", new String[]{String.valueOf(personInstance.getPerson_id())});
        return status;
    }

    public List<AddressModel> getAllAddresses() {
        List<AddressModel> addresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_ADDRESS + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                AddressModel address = new AddressModel();
                address.setAddress_id(cursor.getInt(0));
                address.setAddress_line1(cursor.getString(1));
                address.setAddress_line2(cursor.getString(2));
                address.setLocation_description(cursor.getString(3));
                address.setExist_in_remote_server(cursor.getString(4));
                addresses.add(address);
            } while (cursor.moveToNext());
        }
        db.close();
        return addresses;
    }

    public List<AddressModel> getLastAddresses() {
        List<AddressModel> addresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select max(address_id) from " + TABLE_ADDRESS + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                AddressModel address = new AddressModel();
                address.setAddress_id(cursor.getInt(0));
                addresses.add(address);
            } while (cursor.moveToNext());
        }
        db.close();
        return addresses;
    }


    public List<AddressModel> getSingleAddresses(Integer selected_id) {
        List<AddressModel> addresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_ADDRESS + " where address_id=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                AddressModel address = new AddressModel();
                address.setAddress_id(cursor.getInt(0));
                address.setAddress_line1(cursor.getString(1));
                address.setAddress_line2(cursor.getString(2));
                address.setLocation_description(cursor.getString(3));
                address.setExist_in_remote_server(cursor.getString(4));
                addresses.add(address);
            } while (cursor.moveToNext());
        }
        db.close();
        return addresses;
    }

    public int updateSingleAddress(AddressModel addressInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_LINE1, addressInstance.getAddress_line1());
        contentValues.put(ADDRESS_LINE2, addressInstance.getAddress_line2());
        contentValues.put(LOCATION_DESCRIPTION, addressInstance.getLocation_description());
        int status = sqLiteDatabase.update(TABLE_ADDRESS, contentValues, "address_id=?",
                new String[]{String.valueOf(addressInstance.getAddress_id())});
        return status;
    }

    public List<MrcModel> getAllMrc() {
        List<MrcModel> mrcs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcModel mrc = new MrcModel();
                mrc.setIdentifier(cursor.getString(0));
                mrc.setMrc_status(cursor.getString(1));
                mrc.setRun_name(cursor.getString(2));
                //mrc.setPerson_id(cursor.getInt(3));
                //mrc.setAddress_id(cursor.getInt(4));
                //mrc.setExist_in_remote_server(cursor.getString(5));
                mrcs.add(mrc);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcs;
    }

    public List<MrcModel> getSingleMrc(String selected_id) {
        List<MrcModel> mrcs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC + " where identifier=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcModel mrc = new MrcModel();
                mrc.setIdentifier(cursor.getString(0));
                mrc.setMrc_status(cursor.getString(1));
                mrc.setRun_name(cursor.getString(2));
                mrc.setCoordinates(cursor.getString(8));
                mrc.setPerson_name(cursor.getString(3));
                mrc.setPerson_phone(cursor.getString(4));
                mrc.setAddress_line1(cursor.getString(5));
                mrc.setAddress_line2(cursor.getString(6));
                mrc.setLocation_description(cursor.getString(7));
                mrcs.add(mrc);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcs;
    }
    public List<MrcModel> getSingleMrc(String selected_run, String field_type) {
        List<MrcModel> mrcs = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC + " where run_name=" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcModel mrc = new MrcModel();
                mrc.setIdentifier(cursor.getString(0));
                mrc.setMrc_status(cursor.getString(1));
                mrc.setRun_name(cursor.getString(2));
                //mrc.setPerson_id(cursor.getInt(3));
                //mrc.setAddress_id(cursor.getInt(4));
                mrc.setCoordinates(cursor.getString(5));
                //mrc.setExist_in_remote_server(cursor.getString(6));
                mrcs.add(mrc);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcs;
    }

    public List<MrcServiceModel> getSingleMrcService(String selected_run,String field_type) {
        List<MrcServiceModel> mrcServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_SERVICE + " where mrc_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcServiceModel mrcService = new MrcServiceModel();
                mrcService.setMrc_run_id(cursor.getString(0));
                mrcService.setMrc_trap_id(cursor.getString(1));
                mrcService.setCoordinates(cursor.getString(2));
                mrcService.setAdd_line1(cursor.getString(3));
                mrcService.setAdd_line2(cursor.getString(4));
                mrcService.setLocation_description(cursor.getString(5));
                mrcService.setFull_name(cursor.getString(6));
                mrcService.setContact_number(cursor.getString(7));
                mrcService.setService_id(cursor.getString(8));
                mrcService.setDate(cursor.getString(9));
                mrcService.setTime(cursor.getString(10));
                mrcService.setService_status(cursor.getString(11));
                mrcServices.add(mrcService);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcServices;
    }

    public List<MrcServiceModel> getSingleMrcServiceTrap(String selected_run,String trap_id) {
        List<MrcServiceModel> mrcServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_SERVICE + " where mrc_run_id =" + "\'" + selected_run + "\'" +" and trap_id="+"\'" + trap_id + "\'" +";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcServiceModel mrcService = new MrcServiceModel();
                mrcService.setMrc_run_id(cursor.getString(0));
                mrcService.setMrc_trap_id(cursor.getString(1));
                mrcService.setCoordinates(cursor.getString(2));
                mrcService.setAdd_line1(cursor.getString(3));
                mrcService.setAdd_line2(cursor.getString(4));
                mrcService.setLocation_description(cursor.getString(5));
                mrcService.setFull_name(cursor.getString(6));
                mrcService.setContact_number(cursor.getString(7));
                mrcService.setService_id(cursor.getString(8));
                mrcService.setDate(cursor.getString(9));
                mrcService.setTime(cursor.getString(10));
                mrcService.setService_status(cursor.getString(11));

                mrcServices.add(mrcService);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcServices;
    }

    public List<MrcServiceModel> getSingleMrcServiceTrapById(String service_id) {
        List<MrcServiceModel> mrcServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_SERVICE + " where service_id =" + "\'" + service_id + "\'" +";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcServiceModel mrcService = new MrcServiceModel();
                mrcService.setMrc_run_id(cursor.getString(0));
                mrcService.setMrc_trap_id(cursor.getString(1));
                mrcService.setCoordinates(cursor.getString(2));
                mrcService.setAdd_line1(cursor.getString(3));
                mrcService.setAdd_line2(cursor.getString(4));
                mrcService.setLocation_description(cursor.getString(5));
                mrcService.setFull_name(cursor.getString(6));
                mrcService.setContact_number(cursor.getString(7));
                mrcService.setService_id(cursor.getString(8));
                mrcService.setDate(cursor.getString(9));
                mrcService.setTime(cursor.getString(10));
                mrcService.setService_status(cursor.getString(11));

                mrcServices.add(mrcService);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcServices;
    }

    public List<MrcReleaseModel> getSingleMrcRelease(String selected_run,String field_type) {
        List<MrcReleaseModel> mrcReleases = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_RELEASE + " where mrc_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcReleaseModel mrcRelease = new MrcReleaseModel();
                mrcRelease.setMrc_run_id(cursor.getString(0));
                mrcRelease.setMrc_trap_id(cursor.getString(1));
                mrcRelease.setCoordinates(cursor.getString(2));
                mrcRelease.setAdd_line1(cursor.getString(3));
                mrcRelease.setAdd_line2(cursor.getString(4));
                mrcRelease.setLocation_description(cursor.getString(5));
                mrcRelease.setFull_name(cursor.getString(6));
                mrcRelease.setContact_number(cursor.getString(7));
                mrcRelease.setRelease_id(cursor.getString(8));
                mrcRelease.setDate(cursor.getString(9));
                mrcRelease.setTime(cursor.getString(10));
                mrcRelease.setRelease_status(cursor.getString(11));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
    }

    public List<MrcReleaseModel> getSingleMrcReleaseTrap(String selected_run,String trap_id) {
        List<MrcReleaseModel> mrcReleases = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_RELEASE + " where mrc_run_id =" + "\'" + selected_run + "\'" +" and trap_id="+"\'" + trap_id + "\'" +";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcReleaseModel mrcRelease = new MrcReleaseModel();
                mrcRelease.setMrc_run_id(cursor.getString(0));
                mrcRelease.setMrc_trap_id(cursor.getString(1));
                mrcRelease.setCoordinates(cursor.getString(2));
                mrcRelease.setAdd_line1(cursor.getString(3));
                mrcRelease.setAdd_line2(cursor.getString(4));
                mrcRelease.setLocation_description(cursor.getString(5));
                mrcRelease.setFull_name(cursor.getString(6));
                mrcRelease.setContact_number(cursor.getString(7));
                mrcRelease.setRelease_id(cursor.getString(8));
                mrcRelease.setDate(cursor.getString(9));
                mrcRelease.setTime(cursor.getString(10));
                mrcRelease.setRelease_status(cursor.getString(11));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
    }

    public List<MrcReleaseModel> getSingleMrcReleaseTrapById(String release_id) {
        List<MrcReleaseModel> mrcReleases = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_RELEASE + " where release_id =" + "\'" + release_id + "\'" +";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcReleaseModel mrcRelease = new MrcReleaseModel();
                mrcRelease.setMrc_run_id(cursor.getString(0));
                mrcRelease.setMrc_trap_id(cursor.getString(1));
                mrcRelease.setCoordinates(cursor.getString(2));
                mrcRelease.setAdd_line1(cursor.getString(3));
                mrcRelease.setAdd_line2(cursor.getString(4));
                mrcRelease.setLocation_description(cursor.getString(5));
                mrcRelease.setFull_name(cursor.getString(6));
                mrcRelease.setContact_number(cursor.getString(7));
                mrcRelease.setRelease_id(cursor.getString(8));
                mrcRelease.setDate(cursor.getString(9));
                mrcRelease.setTime(cursor.getString(10));
                mrcRelease.setRelease_status(cursor.getString(11));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
    }

    public int updateSingleMrc(MrcModel mrcInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_STATUS, mrcInstance.getMrc_status());
        contentValues.put(MRC_COORDINATES, mrcInstance.getCoordinates());
        contentValues.put(IDENTIFIER, mrcInstance.getIdentifier());
        contentValues.put(PERSON_NAME, mrcInstance.person_name);
        contentValues.put(PERSON_PHONE, mrcInstance.person_phone);
        contentValues.put(ADDRESS_LINE1, mrcInstance.address_line1);
        contentValues.put(ADDRESS_LINE2, mrcInstance.address_line2);
        contentValues.put(LOCATION_DESCRIPTION, mrcInstance.location_description);
        int status = sqLiteDatabase.update(TABLE_MRC, contentValues, "identifier=?",
                new String[]{String.valueOf(mrcInstance.getIdentifier())});
        return status;
    }

    public List<MrcReleaseModel> getAllMrcRelease() {
        List<MrcReleaseModel> mrcReleases = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_RELEASE + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcReleaseModel mrcRelease = new MrcReleaseModel();
               // mrcRelease.setRelease_id(cursor.getInt(0));
                mrcRelease.setDate(cursor.getString(1));
                mrcRelease.setTime(cursor.getString(2));
                mrcRelease.setRelease_status(cursor.getString(3));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
    }

    public List<MrcModel> getSingleMrcPersonAddress(String selected_id) {
        List<MrcModel> mrcPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.* from " + TABLE_MRC + " a"+" where a.identifier=" + "\'" + selected_id + "\'" +
               ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcModel mrcPersonAddress = new MrcModel();
                mrcPersonAddress.setIdentifier(cursor.getString(0));
                mrcPersonAddress.setMrc_status(cursor.getString(1));
                mrcPersonAddress.setRun_name(cursor.getString(2));
                mrcPersonAddress.setCoordinates(cursor.getString(8));
                mrcPersonAddress.setPerson_name(cursor.getString(3));
                mrcPersonAddress.setPerson_phone(cursor.getString(4));
                mrcPersonAddress.setAddress_line1(cursor.getString(5));
                mrcPersonAddress.setAddress_line2(cursor.getString(6));
                mrcPersonAddress.setLocation_description(cursor.getString(7));
                mrcPersonsAddresses.add(mrcPersonAddress);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcPersonsAddresses;
    }
    public List<MrcModel> getSingleMrcPersonAddress(String run_id, String field_type) {
        List<MrcModel> mrcPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.* from " + TABLE_MRC + " a"+" where a.run_name=" + "\'" + run_id + "\'" +
                ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcModel mrcPersonAddress = new MrcModel();
                mrcPersonAddress.setIdentifier(cursor.getString(0));
                mrcPersonAddress.setMrc_status(cursor.getString(1));
                mrcPersonAddress.setRun_name(cursor.getString(2));
                mrcPersonAddress.setCoordinates(cursor.getString(8));
                mrcPersonAddress.setPerson_name(cursor.getString(3));
                mrcPersonAddress.setPerson_phone(cursor.getString(4));
                mrcPersonAddress.setAddress_line1(cursor.getString(5));
                mrcPersonAddress.setAddress_line2(cursor.getString(6));
                mrcPersonAddress.setLocation_description(cursor.getString(7));
                mrcPersonsAddresses.add(mrcPersonAddress);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcPersonsAddresses;
    }

    public int updateSingleMrcRelease(MrcReleaseModel mrcReleaseInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_RUN_ID, mrcReleaseInstance.getMrc_run_id());
        contentValues.put(TRAP_ID, mrcReleaseInstance.getMrc_trap_id());
        contentValues.put(RELEASE_ID, mrcReleaseInstance.getRelease_id());
        contentValues.put(RELEASE_DATE, mrcReleaseInstance.getDate());
        contentValues.put(RELEASE_TIME, mrcReleaseInstance.getTime());
        contentValues.put(RELEASE_STATUS, mrcReleaseInstance.getRelease_status());
        int status = sqLiteDatabase.update(TABLE_MRC_RELEASE, contentValues,
                "trap_id=? AND mrc_run_id=?", new String[]{String.valueOf(mrcReleaseInstance.getMrc_trap_id()),String.valueOf(mrcReleaseInstance.getMrc_run_id())});
        return status;
    }

    public List<BgTrapModel> getAllBgTraps() {
        List<BgTrapModel> bgTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_TRAP + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgTrapModel bgTrap = new BgTrapModel();
                bgTrap.setBg_trap_id(cursor.getString(0));
                bgTrap.setTrap_status(cursor.getString(1));
                bgTrap.setPosition(cursor.getString(2));
                bgTrap.setRun_name(cursor.getString(3));
                //bgTrap.setPerson_id(cursor.getInt(4));
                //bgTrap.setAddress_id(cursor.getInt(5));
                //bgTrap.setExist_in_remote_server(cursor.getString(6));
                bgTraps.add(bgTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgTraps;
    }

    public List<BgTrapModel> getSingleBgTrap(String selected_id) {
        List<BgTrapModel> bgPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.* from " + TABLE_BG_TRAP + " a"+" where a.bg_trap_id=" + "\'" + selected_id + "\'" +
                ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgTrapModel bgPersonAddress = new BgTrapModel();
                bgPersonAddress.setBg_trap_id(cursor.getString(0));
                bgPersonAddress.setTrap_status(cursor.getString(1));
                bgPersonAddress.setPosition(cursor.getString(2));
                bgPersonAddress.setRun_name(cursor.getString(3));
                bgPersonAddress.setCoordinates(cursor.getString(9));
                bgPersonAddress.setPerson_name(cursor.getString(4));
                bgPersonAddress.setPerson_phone(cursor.getString(5));
                bgPersonAddress.setAddress_line1(cursor.getString(6));
                bgPersonAddress.setAddress_line2(cursor.getString(7));
                bgPersonAddress.setLocation_description(cursor.getString(8));
                bgPersonsAddresses.add(bgPersonAddress);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgPersonsAddresses;
    }
    public List<BgTrapModel> getSingleBgTrap(String selected_run, String field_type) {
        List<BgTrapModel> bgTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_TRAP + " where run_name=" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgTrapModel bgTrap = new BgTrapModel();
                bgTrap.setBg_trap_id(cursor.getString(0));
                bgTrap.setTrap_status(cursor.getString(1));
                bgTrap.setPosition(cursor.getString(2));
                bgTrap.setRun_name(cursor.getString(3));
                bgTrap.setCoordinates(cursor.getString(9));
                bgTrap.setPerson_name(cursor.getString(4));
                bgTrap.setPerson_phone(cursor.getString(5));
                bgTrap.setAddress_line1(cursor.getString(6));
                bgTrap.setAddress_line2(cursor.getString(7));
                bgTrap.setLocation_description(cursor.getString(8));
                bgTraps.add(bgTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgTraps;
    }
    public List<BgServiceModel> getSingleBgService(String selected_run,String field_type) {
        List<BgServiceModel> bgServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_SERVICE + " where bg_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgServiceModel bgService = new BgServiceModel();
                bgService.setBg_run_id(cursor.getString(0));
                bgService.setBg_trap_id(cursor.getString(1));
                bgService.setBg_trap_position(cursor.getString(2));
                bgService.setCoordinates(cursor.getString(3));
                bgService.setAdd_line1(cursor.getString(4));
                bgService.setAdd_line2(cursor.getString(5));
                bgService.setLocation_description(cursor.getString(6));
                bgService.setFull_name(cursor.getString(7));
                bgService.setContact_number(cursor.getString(8));
                bgService.setService_id(cursor.getString(9));
                bgService.setDate(cursor.getString(10));
                bgService.setTime(cursor.getString(11));
                bgService.setService_status(cursor.getString(12));
                bgServices.add(bgService);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgServices;
    }
    public List<BgServiceModel> getSingleBgServiceTrap(String selected_run,String trap_id) {
        List<BgServiceModel> bgServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_SERVICE + " where bg_run_id =" + "\'" + selected_run + "\'"+" and trap_id=" +"\'" + trap_id + "\'"+ ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgServiceModel bgService = new BgServiceModel();
                bgService.setBg_run_id(cursor.getString(0));
                bgService.setBg_trap_id(cursor.getString(1));
                bgService.setBg_trap_position(cursor.getString(2));
                bgService.setCoordinates(cursor.getString(3));
                bgService.setAdd_line1(cursor.getString(4));
                bgService.setAdd_line2(cursor.getString(5));
                bgService.setLocation_description(cursor.getString(6));
                bgService.setFull_name(cursor.getString(7));
                bgService.setContact_number(cursor.getString(8));
                bgService.setService_id(cursor.getString(9));
                bgService.setDate(cursor.getString(10));
                bgService.setTime(cursor.getString(11));
                bgService.setService_status(cursor.getString(12));

                bgServices.add(bgService);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgServices;
    }

    public List<BgServiceModel> getSingleBgServiceTrapById(String service_id) {
        List<BgServiceModel> bgServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_SERVICE + " where service_id =" + "\'" + service_id + "\'"+";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgServiceModel bgService = new BgServiceModel();
                bgService.setBg_run_id(cursor.getString(0));
                bgService.setBg_trap_id(cursor.getString(1));
                bgService.setBg_trap_position(cursor.getString(2));
                bgService.setCoordinates(cursor.getString(3));
                bgService.setAdd_line1(cursor.getString(4));
                bgService.setAdd_line2(cursor.getString(5));
                bgService.setLocation_description(cursor.getString(6));
                bgService.setFull_name(cursor.getString(7));
                bgService.setContact_number(cursor.getString(8));
                bgService.setService_id(cursor.getString(9));
                bgService.setDate(cursor.getString(10));
                bgService.setTime(cursor.getString(11));
                bgService.setService_status(cursor.getString(12));

                bgServices.add(bgService);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgServices;
    }

    public List<BgCollectionModel> getSingleBgCollection(String selected_run,String field_type) {
        List<BgCollectionModel> bgCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_COLLECTION + " where bg_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgCollectionModel bgCollection = new BgCollectionModel();
                bgCollection.setBg_run_id(cursor.getString(0));
                bgCollection.setBg_trap_id(cursor.getString(1));
                bgCollection.setBg_trap_position(cursor.getString(2));
                bgCollection.setCoordinates(cursor.getString(3));
                bgCollection.setAdd_line1(cursor.getString(4));
                bgCollection.setAdd_line2(cursor.getString(5));
                bgCollection.setLocation_description(cursor.getString(6));
                bgCollection.setFull_name(cursor.getString(7));
                bgCollection.setContact_number(cursor.getString(8));
                bgCollection.setCollection_id(cursor.getString(9));
                bgCollection.setDate(cursor.getString(10));
                bgCollection.setTime(cursor.getString(11));
                bgCollection.setCollection_status(cursor.getString(12));
                bgCollections.add(bgCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgCollections;
    }
    public List<BgCollectionModel> getSingleBgCollectionTrap(String selected_run,String trap_id) {
        List<BgCollectionModel> bgCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_COLLECTION + " where bg_run_id =" + "\'" + selected_run + "\'" +" and trap_id="+ "\'" + trap_id + "\'"+ ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgCollectionModel bgCollection = new BgCollectionModel();
                bgCollection.setBg_run_id(cursor.getString(0));
                bgCollection.setBg_trap_id(cursor.getString(1));
                bgCollection.setBg_trap_position(cursor.getString(2));
                bgCollection.setCoordinates(cursor.getString(3));
                bgCollection.setAdd_line1(cursor.getString(4));
                bgCollection.setAdd_line2(cursor.getString(5));
                bgCollection.setLocation_description(cursor.getString(6));
                bgCollection.setFull_name(cursor.getString(7));
                bgCollection.setContact_number(cursor.getString(8));
                bgCollection.setCollection_id(cursor.getString(9));
                bgCollection.setDate(cursor.getString(10));
                bgCollection.setTime(cursor.getString(11));
                bgCollection.setCollection_status(cursor.getString(12));
                bgCollections.add(bgCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgCollections;
    }
    public List<BgCollectionModel> getSingleBgCollectionTrapById(String collection_id) {
        List<BgCollectionModel> bgCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_COLLECTION + " where collection_id =" + "\'" + collection_id + "\'"+ ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgCollectionModel bgCollection = new BgCollectionModel();
                bgCollection.setBg_run_id(cursor.getString(0));
                bgCollection.setBg_trap_id(cursor.getString(1));
                bgCollection.setBg_trap_position(cursor.getString(2));
                bgCollection.setCoordinates(cursor.getString(3));
                bgCollection.setAdd_line1(cursor.getString(4));
                bgCollection.setAdd_line2(cursor.getString(5));
                bgCollection.setLocation_description(cursor.getString(6));
                bgCollection.setFull_name(cursor.getString(7));
                bgCollection.setContact_number(cursor.getString(8));
                bgCollection.setCollection_id(cursor.getString(9));
                bgCollection.setDate(cursor.getString(10));
                bgCollection.setTime(cursor.getString(11));
                bgCollection.setCollection_status(cursor.getString(12));
                bgCollections.add(bgCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgCollections;
    }
    public int updateSingleBgTrap(BgTrapModel bgTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_TRAP_STATUS, bgTrapInstance.getTrap_status());
        contentValues.put(BG_POSITION, bgTrapInstance.getPosition());
        contentValues.put(BG_COORDINATES, bgTrapInstance.getCoordinates());
        contentValues.put(BG_TRAP_ID, bgTrapInstance.getBg_trap_id());
        contentValues.put(PERSON_NAME, bgTrapInstance.person_name);
        contentValues.put(PERSON_PHONE, bgTrapInstance.person_phone);
        contentValues.put(ADDRESS_LINE1, bgTrapInstance.address_line1);
        contentValues.put(ADDRESS_LINE2, bgTrapInstance.address_line2);
        contentValues.put(LOCATION_DESCRIPTION, bgTrapInstance.location_description);
        int status = sqLiteDatabase.update(TABLE_BG_TRAP, contentValues, "bg_trap_id=?",
                new String[]{String.valueOf(bgTrapInstance.getBg_trap_id())});
        return status;
    }

    public List<BgCollectionModel> getAllBgCollections() {
        List<BgCollectionModel> bgCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_COLLECTION + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgCollectionModel bgCollection = new BgCollectionModel();
               // bgCollection.setCollection_id(cursor.getString(0));
                bgCollection.setDate(cursor.getString(1));
                bgCollection.setTime(cursor.getString(2));
                bgCollection.setCollection_status(cursor.getString(3));
                //bgCollection.setTrap_condition(cursor.getString(4));
                bgCollections.add(bgCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgCollections;
    }

    public List<BgCollectionModel> getSingleBgCollection(String selected_id) {
        List<BgCollectionModel> bgCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_COLLECTION + " where collection_id=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgCollectionModel bgCollection = new BgCollectionModel();
                //bgCollection.setCollection_id(cursor.getString(0));
                bgCollection.setDate(cursor.getString(1));
                bgCollection.setTime(cursor.getString(2));
                bgCollection.setCollection_status(cursor.getString(3));
                //bgCollection.setTrap_condition(cursor.getString(4));
                bgCollections.add(bgCollection);

            } while (cursor.moveToNext());
        }
        db.close();
        return bgCollections;
    }

    public int updateSingleBgCollection(BgCollectionModel bgCollectionInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_RUN_ID, bgCollectionInstance.getBg_run_id());
        contentValues.put(TRAP_ID, bgCollectionInstance.getBg_trap_id());
        contentValues.put(COLLECTION_ID, bgCollectionInstance.getCollection_id());
        contentValues.put(COLLECTION_DATE, bgCollectionInstance.getDate());
        contentValues.put(COLLECTION_TIME, bgCollectionInstance.getTime());
        contentValues.put(COLLECTION_STATUS, bgCollectionInstance.getCollection_status());
        int status = sqLiteDatabase.update(TABLE_BG_COLLECTION, contentValues,
                "trap_id=? AND bg_run_id=?", new String[]{String.valueOf(bgCollectionInstance.getBg_trap_id()),String.valueOf(bgCollectionInstance.getBg_run_id())});
        return status;
    }
    public int updateSingleOviCollection(OviCollectionModel oviCollectionInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVI_RUN_ID, oviCollectionInstance.getOvi_run_id());
        contentValues.put(TRAP_ID, oviCollectionInstance.getOvi_trap_id());
        contentValues.put(COLLECTION_ID, oviCollectionInstance.getCollection_id());
        contentValues.put(COLLECTION_DATE, oviCollectionInstance.getDate());
        contentValues.put(COLLECTION_TIME, oviCollectionInstance.getTime());
        contentValues.put(COLLECTION_STATUS, oviCollectionInstance.getCollection_status());
        int status = sqLiteDatabase.update(TABLE_OVI_COLLECTION, contentValues,
                "trap_id=? AND ovi_run_id=?", new String[]{String.valueOf(oviCollectionInstance.getOvi_trap_id()),String.valueOf(oviCollectionInstance.getOvi_run_id())});
        return status;
    }
    public int updateSingleBgService(BgServiceModel bgServiceInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_RUN_ID, bgServiceInstance.getBg_run_id());
        contentValues.put(TRAP_ID, bgServiceInstance.getBg_trap_id());
        contentValues.put(SERVICE_ID, bgServiceInstance.getService_id());
        contentValues.put(SERVICE_DATE, bgServiceInstance.getDate());
        contentValues.put(SERVICE_TIME, bgServiceInstance.getTime());
        contentValues.put(SERVICE_STATUS, bgServiceInstance.getService_status());
        int status = sqLiteDatabase.update(TABLE_BG_SERVICE, contentValues,
                "trap_id=? AND bg_run_id=?", new String[]{String.valueOf(bgServiceInstance.getBg_trap_id()),String.valueOf(bgServiceInstance.getBg_run_id())});
        return status;
    }
    public int updateSingleMrcService(MrcServiceModel mrcServiceInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_RUN_ID, mrcServiceInstance.getMrc_run_id());
        contentValues.put(TRAP_ID, mrcServiceInstance.getMrc_trap_id());
        contentValues.put(SERVICE_ID, mrcServiceInstance.getService_id());
        contentValues.put(SERVICE_DATE, mrcServiceInstance.getDate());
        contentValues.put(SERVICE_TIME, mrcServiceInstance.getTime());
        contentValues.put(SERVICE_STATUS, mrcServiceInstance.getService_status());
        Log.d("service_status",mrcServiceInstance.getService_status());
        int status = sqLiteDatabase.update(TABLE_MRC_SERVICE, contentValues,
                "trap_id=? AND mrc_run_id=?", new String[]{String.valueOf(mrcServiceInstance.getMrc_trap_id()),String.valueOf(mrcServiceInstance.getMrc_run_id())});
        return status;
    }

    public int updateSingleOviService(OviServiceModel oviServiceInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVI_RUN_ID, oviServiceInstance.getOvi_run_id());
        contentValues.put(TRAP_ID, oviServiceInstance.getOvi_trap_id());
        contentValues.put(SERVICE_ID, oviServiceInstance.getService_id());
        contentValues.put(SERVICE_DATE, oviServiceInstance.getDate());
        contentValues.put(SERVICE_TIME, oviServiceInstance.getTime());
        contentValues.put(SERVICE_STATUS, oviServiceInstance.getService_status());
        int status = sqLiteDatabase.update(TABLE_OVI_SERVICE, contentValues,
                "trap_id=? AND ovi_run_id=?", new String[]{String.valueOf(oviServiceInstance.getOvi_trap_id()),String.valueOf(oviServiceInstance.getOvi_run_id())});
        return status;
    }

    public List<OvTrapModel> getAllOvTraps() {
        List<OvTrapModel> ovTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_TRAP + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvTrapModel ovTrap = new OvTrapModel();
                ovTrap.setOv_trap_id(cursor.getString(0));
                ovTrap.setTrap_status(cursor.getString(1));
                ovTrap.setPosition(cursor.getString(2));
                ovTrap.setRun_name(cursor.getString(3));
                //ovTrap.setPerson_id(cursor.getInt(4));
                //ovTrap.setAddress_id(cursor.getInt(5));
                //ovTrap.setExist_in_remote_server(cursor.getString(6));
                ovTraps.add(ovTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return ovTraps;
    }

    public List<OvTrapModel> getSingleOvTrap(String selected_id) {
        List<OvTrapModel> oviTrap = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.* from " + TABLE_OV_TRAP + " a"+
              " where a.ov_trap_id=" + "\'" + selected_id + "\'" +
               ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvTrapModel oviTraps = new OvTrapModel();
                oviTraps.setOv_trap_id(cursor.getString(0));
                oviTraps.setTrap_status(cursor.getString(1));
                oviTraps.setPosition(cursor.getString(2));
                oviTraps.setRun_name(cursor.getString(3));
                oviTraps.setCoordinates(cursor.getString(9));
                oviTraps.setPerson_name(cursor.getString(4));
                oviTraps.setPerson_phone(cursor.getString(5));
                oviTraps.setAddress_line1(cursor.getString(6));
                oviTraps.setAddress_line2(cursor.getString(7));
                oviTraps.setLocation_description(cursor.getString(8));
                oviTrap.add(oviTraps);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviTrap;
    }

    public List<OvTrapModel> getSingleOvTrap(String selected_run,String field_type) {
        List<OvTrapModel> oviTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_TRAP + " where run_name=" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvTrapModel oviTrap = new OvTrapModel();
                oviTrap.setOv_trap_id(cursor.getString(0));
                oviTrap.setTrap_status(cursor.getString(1));
                oviTrap.setPosition(cursor.getString(2));
                oviTrap.setRun_name(cursor.getString(3));
                oviTrap.setCoordinates(cursor.getString(9));
                oviTrap.setPerson_name(cursor.getString(4));
                oviTrap.setPerson_phone(cursor.getString(5));
                oviTrap.setAddress_line1(cursor.getString(6));
                oviTrap.setAddress_line2(cursor.getString(7));
                oviTrap.setLocation_description(cursor.getString(8));
                oviTraps.add(oviTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviTraps;
    }

    public List<OviServiceModel> getSingleOviService(String selected_run,String field_type) {
        List<OviServiceModel> oviServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_SERVICE + " where ovi_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviServiceModel oviService = new OviServiceModel();
                oviService.setOvi_run_id(cursor.getString(0));
                oviService.setOvi_trap_id(cursor.getString(1));
                oviService.setOvi_trap_position(cursor.getString(2));
                oviService.setCoordinates(cursor.getString(3));
                oviService.setAdd_line1(cursor.getString(4));
                oviService.setAdd_line2(cursor.getString(5));
                oviService.setLocation_description(cursor.getString(6));
                oviService.setFull_name(cursor.getString(7));
                oviService.setContact_number(cursor.getString(8));
                oviService.setService_id(cursor.getString(9));
                oviService.setDate(cursor.getString(10));
                oviService.setTime(cursor.getString(11));
                oviService.setService_status(cursor.getString(12));

                oviServices.add(oviService);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviServices;
    }
    public List<OviServiceModel> getSingleOviServiceTrap(String selected_run,String trap_id) {
        List<OviServiceModel> oviServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_SERVICE + " where ovi_run_id =" + "\'" + selected_run + "\'"+" and trap_id =" +"\'" + trap_id + "\'"+ ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviServiceModel oviService = new OviServiceModel();
                oviService.setOvi_run_id(cursor.getString(0));
                oviService.setOvi_trap_id(cursor.getString(1));
                oviService.setOvi_trap_position(cursor.getString(2));
                oviService.setCoordinates(cursor.getString(3));
                oviService.setAdd_line1(cursor.getString(4));
                oviService.setAdd_line2(cursor.getString(5));
                oviService.setLocation_description(cursor.getString(6));
                oviService.setFull_name(cursor.getString(7));
                oviService.setContact_number(cursor.getString(8));
                oviService.setService_id(cursor.getString(9));
                oviService.setDate(cursor.getString(10));
                oviService.setTime(cursor.getString(11));
                oviService.setService_status(cursor.getString(12));

                oviServices.add(oviService);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviServices;
    }
    public List<OviServiceModel> getSingleOviServiceTrapById(String service_id) {
        List<OviServiceModel> oviServices = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_SERVICE + " where service_id =" + "\'" + service_id + "\'"+";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviServiceModel oviService = new OviServiceModel();
                oviService.setOvi_run_id(cursor.getString(0));
                oviService.setOvi_trap_id(cursor.getString(1));
                oviService.setOvi_trap_position(cursor.getString(2));
                oviService.setCoordinates(cursor.getString(3));
                oviService.setAdd_line1(cursor.getString(4));
                oviService.setAdd_line2(cursor.getString(5));
                oviService.setLocation_description(cursor.getString(6));
                oviService.setFull_name(cursor.getString(7));
                oviService.setContact_number(cursor.getString(8));
                oviService.setService_id(cursor.getString(9));
                oviService.setDate(cursor.getString(10));
                oviService.setTime(cursor.getString(11));
                oviService.setService_status(cursor.getString(12));

                oviServices.add(oviService);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviServices;
    }

    public List<OviCollectionModel> getSingleOviCollection(String selected_run,String field_type) {
        List<OviCollectionModel> oviCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_COLLECTION + " where ovi_run_id =" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviCollectionModel oviCollection = new OviCollectionModel();
                oviCollection.setOvi_run_id(cursor.getString(0));
                oviCollection.setOvi_trap_id(cursor.getString(1));
                oviCollection.setOvi_trap_position(cursor.getString(2));
                oviCollection.setCoordinates(cursor.getString(3));
                oviCollection.setAdd_line1(cursor.getString(4));
                oviCollection.setAdd_line2(cursor.getString(5));
                oviCollection.setLocation_description(cursor.getString(6));
                oviCollection.setFull_name(cursor.getString(7));
                oviCollection.setContact_number(cursor.getString(8));
                oviCollection.setCollection_id(cursor.getString(9));
                oviCollection.setDate(cursor.getString(10));
                oviCollection.setTime(cursor.getString(11));
                oviCollection.setCollection_status(cursor.getString(12));

                oviCollections.add(oviCollection);

            } while (cursor.moveToNext());
        }
        db.close();
        return oviCollections;
    }

    public List<OviCollectionModel> getSingleOviCollectionTrap(String selected_run,String trap_id) {
        List<OviCollectionModel> oviCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_COLLECTION + " where ovi_run_id =" + "\'" + selected_run + "\'" + " and trap_id=" +"\'" + trap_id + "\'"+ ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviCollectionModel oviCollection = new OviCollectionModel();
                oviCollection.setOvi_run_id(cursor.getString(0));
                oviCollection.setOvi_trap_id(cursor.getString(1));
                oviCollection.setOvi_trap_position(cursor.getString(2));
                oviCollection.setCoordinates(cursor.getString(3));
                oviCollection.setAdd_line1(cursor.getString(4));
                oviCollection.setAdd_line2(cursor.getString(5));
                oviCollection.setLocation_description(cursor.getString(6));
                oviCollection.setFull_name(cursor.getString(7));
                oviCollection.setContact_number(cursor.getString(8));
                oviCollection.setCollection_id(cursor.getString(9));
                oviCollection.setDate(cursor.getString(10));
                oviCollection.setTime(cursor.getString(11));
                oviCollection.setCollection_status(cursor.getString(12));

                oviCollections.add(oviCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviCollections;
    }

    public List<OviCollectionModel> getSingleOviCollectionTrapById(String collection_id) {
        List<OviCollectionModel> oviCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OVI_COLLECTION + " where collection_id =" + "\'" + collection_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OviCollectionModel oviCollection = new OviCollectionModel();
                oviCollection.setOvi_run_id(cursor.getString(0));
                oviCollection.setOvi_trap_id(cursor.getString(1));
                oviCollection.setOvi_trap_position(cursor.getString(2));
                oviCollection.setCoordinates(cursor.getString(3));
                oviCollection.setAdd_line1(cursor.getString(4));
                oviCollection.setAdd_line2(cursor.getString(5));
                oviCollection.setLocation_description(cursor.getString(6));
                oviCollection.setFull_name(cursor.getString(7));
                oviCollection.setContact_number(cursor.getString(8));
                oviCollection.setCollection_id(cursor.getString(9));
                oviCollection.setDate(cursor.getString(10));
                oviCollection.setTime(cursor.getString(11));
                oviCollection.setCollection_status(cursor.getString(12));

                oviCollections.add(oviCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return oviCollections;
    }

    public int updateSingleOvTrap(OvTrapModel ovTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OV_TRAP_STATUS, ovTrapInstance.getTrap_status());
        contentValues.put(OV_POSITION, ovTrapInstance.getPosition());
        contentValues.put(OV_COORDINATES, ovTrapInstance.getCoordinates());
        contentValues.put(OV_TRAP_ID, ovTrapInstance.getOv_trap_id());
        contentValues.put(PERSON_NAME, ovTrapInstance.person_name);
        contentValues.put(PERSON_PHONE, ovTrapInstance.person_phone);
        contentValues.put(ADDRESS_LINE1, ovTrapInstance.address_line1);
        contentValues.put(ADDRESS_LINE2, ovTrapInstance.address_line2);
        contentValues.put(LOCATION_DESCRIPTION, ovTrapInstance.location_description);

        int status = sqLiteDatabase.update(TABLE_OV_TRAP, contentValues,
                "ov_trap_id=?", new String[]{String.valueOf(ovTrapInstance.getOv_trap_id())});
        return status;
    }

    public long insertDataOviService(OviServiceModel oviServiceObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OVI_RUN_ID,oviServiceObj.ovi_run_id);
        contentValues.put(TRAP_ID,oviServiceObj.trap_id);
        contentValues.put(TRAP_POSITION,oviServiceObj.trap_position);
        contentValues.put(COORDINATES,oviServiceObj.coordinates);
        contentValues.put(ADD_LINE1,oviServiceObj.add_line1);
        contentValues.put(ADD_LINE2,oviServiceObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,oviServiceObj.location_description);
        contentValues.put(FULL_NAME,oviServiceObj.full_name);
        contentValues.put(CONTACT_NUMBER,oviServiceObj.contact_number);
        contentValues.put(SERVICE_STATUS,oviServiceObj.service_status);
        // save to table
        long status = sqLiteDatabase.insert(TABLE_OVI_SERVICE, null, contentValues);
        // close connection
        sqLiteDatabase.close();
        return status;
    }

    public long insertDataBgService(BgServiceModel bgServiceObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_RUN_ID,bgServiceObj.bg_run_id);
        contentValues.put(TRAP_ID,bgServiceObj.trap_id);
        contentValues.put(TRAP_POSITION,bgServiceObj.trap_position);
        contentValues.put(COORDINATES,bgServiceObj.coordinates);
        contentValues.put(ADD_LINE1,bgServiceObj.add_line1);
        contentValues.put(ADD_LINE2,bgServiceObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,bgServiceObj.location_description);
        contentValues.put(FULL_NAME,bgServiceObj.full_name);
        contentValues.put(CONTACT_NUMBER,bgServiceObj.contact_number);
        contentValues.put(SERVICE_STATUS,bgServiceObj.service_status);
        // save to table
        long status = sqLiteDatabase.insert(TABLE_BG_SERVICE, null, contentValues);
        // close connection
        sqLiteDatabase.close();
        return status;
    }
    public long insertDataMrcService(MrcServiceModel mrcServiceObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_RUN_ID,mrcServiceObj.mrc_run_id);
        contentValues.put(TRAP_ID,mrcServiceObj.trap_id);
        contentValues.put(COORDINATES,mrcServiceObj.coordinates);
        contentValues.put(ADD_LINE1,mrcServiceObj.add_line1);
        contentValues.put(ADD_LINE2,mrcServiceObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,mrcServiceObj.location_description);
        contentValues.put(FULL_NAME,mrcServiceObj.full_name);
        contentValues.put(CONTACT_NUMBER,mrcServiceObj.contact_number);
        contentValues.put(SERVICE_STATUS,mrcServiceObj.service_status);
        // save to table
        long status = sqLiteDatabase.insert(TABLE_MRC_SERVICE, null, contentValues);
        // close connection
        sqLiteDatabase.close();
        return status;
    }

    public long insertDataMrcRelease(MrcReleaseModel mrcReleaseObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_RUN_ID,mrcReleaseObj.mrc_run_id);
        contentValues.put(TRAP_ID,mrcReleaseObj.trap_id);
        contentValues.put(COORDINATES,mrcReleaseObj.coordinates);
        contentValues.put(ADD_LINE1,mrcReleaseObj.add_line1);
        contentValues.put(ADD_LINE2,mrcReleaseObj.add_line2);
        contentValues.put(LOCATION_DESCRIPTION,mrcReleaseObj.location_description);
        contentValues.put(FULL_NAME,mrcReleaseObj.full_name);
        contentValues.put(CONTACT_NUMBER,mrcReleaseObj.contact_number);
        contentValues.put(RELEASE_ID,mrcReleaseObj.release_id);
        contentValues.put(RELEASE_STATUS,mrcReleaseObj.release_status);
        // save to table
        long status = sqLiteDatabase.insert(TABLE_MRC_RELEASE, null, contentValues);
        // close connection
        sqLiteDatabase.close();
        return status;
    }

/*
    public List<OvCollectionModel> getAllOvCollections() {
        List<OvCollectionModel> ovCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_COLLECTION + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvCollectionModel ovCollection = new OvCollectionModel();
                ovCollection.setCollection_id(cursor.getString(0));
                ovCollection.setDate(cursor.getString(1));
                ovCollection.setTime(cursor.getString(2));
                ovCollection.setCollection_status(cursor.getString(3));
                ovCollection.setTrap_condition(cursor.getString(4));
                ovCollections.add(ovCollection);
            } while (cursor.moveToNext());
        }
        db.close();
        return ovCollections;
    }
*/
    /*
    public List<OvCollectionModel> getSingleOvCollection(String selected_id) {
        List<OvCollectionModel> ovCollections = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_COLLECTION + " where collection_id=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvCollectionModel ovCollection = new OvCollectionModel();
                ovCollection.setCollection_id(cursor.getString(0));
                ovCollection.setDate(cursor.getString(1));
                ovCollection.setTime(cursor.getString(2));
                ovCollection.setCollection_status(cursor.getString(3));
                ovCollection.setTrap_condition(cursor.getString(4));
                ovCollections.add(ovCollection);

            } while (cursor.moveToNext());
        }
        db.close();
        return ovCollections;
    }

     */
    /*
    public int updateSingleOvCollection(OvCollectionModel ovCollectionInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLECTION_STATUS, ovCollectionInstance.getCollection_status());
        contentValues.put(TRAP_CONDITION, ovCollectionInstance.getTrap_condition());
        int status = sqLiteDatabase.update(TABLE_OV_COLLECTION, contentValues,
                "collection_id=?", new String[]{String.valueOf(ovCollectionInstance.getCollection_id())});
        return status;
    }

     */
}

