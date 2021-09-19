package ucsc.mit.wmp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int VERSION = 15;
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

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_CREATE_QUERY_PERSON = "CREATE TABLE " + TABLE_PERSON + " " + "(" + PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PERSON_NAME
                + " TEXT," + PERSON_PHONE +" INTEGER,"+ EXIST_IN_REMOTE_SERVER + " TEXT" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_PERSON);

        String TABLE_CREATE_QUERY_ADDRESS = "CREATE TABLE " + TABLE_ADDRESS + " " + "(" + ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ADDRESS_LINE1
                + " TEXT," + ADDRESS_LINE2 + " TEXT," + LOCATION_DESCRIPTION + " TEXT," + EXIST_IN_REMOTE_SERVER + " TEXT" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_ADDRESS);

        String TABLE_CREATE_QUERY_MRC = "CREATE TABLE " + TABLE_MRC + " " + "(" + IDENTIFIER + " TEXT," + MRC_STATUS
                + " TEXT," + RUN_NAME + " TEXT," + PERSON_ID + " INTEGER," + ADDRESS_ID + " INTEGER," + MRC_COORDINATES + " TEXT," + EXIST_IN_REMOTE_SERVER + " TEXT," + "primary key " + "(" + IDENTIFIER + ")," + "foreign key (person_id) references person(person_id)," + "foreign key (address_id) references address(address_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_MRC);

        String TABLE_CREATE_QUERY_BG_TRAP = "CREATE TABLE " + TABLE_BG_TRAP + " " + "(" + BG_TRAP_ID + " TEXT," + BG_TRAP_STATUS
                + " TEXT," + BG_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_ID + " INTEGER," + ADDRESS_ID + " INTEGER," +  BG_COORDINATES + " TEXT,"+ EXIST_IN_REMOTE_SERVER + " TEXT," + "primary key " + "(" + BG_TRAP_ID + ")," + "foreign key (person_id) references person(person_id)," + "foreign key (address_id) references address(address_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_TRAP);

        String TABLE_CREATE_QUERY_OV_TRAP = "CREATE TABLE " + TABLE_OV_TRAP + " " + "(" + OV_TRAP_ID + " TEXT," + OV_TRAP_STATUS
                + " TEXT," + OV_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_ID + " INTEGER," + ADDRESS_ID + " INTEGER," + OV_COORDINATES + " TEXT,"+EXIST_IN_REMOTE_SERVER + " TEXT," + "primary key " + "(" + OV_TRAP_ID + ")," + "foreign key (person_id) references person(person_id)," + "foreign key (address_id) references address(address_id)" + ");";
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
        String DROP_TABLE_QUERY_TABLE_PERSON = "DROP TABLE IF EXISTS " + TABLE_PERSON;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_PERSON);
        String DROP_TABLE_QUERY_TABLE_ADDRESS = "DROP TABLE IF EXISTS " + TABLE_ADDRESS;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_ADDRESS);
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

    public void insertDataMrc(MrcModel mrcObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDENTIFIER, mrcObj.getIdentifier());
        contentValues.put(MRC_STATUS, mrcObj.getMrc_status());
        contentValues.put(RUN_NAME, mrcObj.getRun_name());
        contentValues.put(PERSON_ID, mrcObj.getPerson_id());
        contentValues.put(ADDRESS_ID, mrcObj.getAddress_id());
        contentValues.put(MRC_COORDINATES, mrcObj.getCoordinates());
        contentValues.put(EXIST_IN_REMOTE_SERVER, mrcObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_MRC, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }


    public void insertDataBgTrap(BgTrapModel bgTrapObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_TRAP_ID, bgTrapObj.getBg_trap_id());
        contentValues.put(BG_TRAP_STATUS, bgTrapObj.getTrap_status());
        contentValues.put(BG_POSITION, bgTrapObj.getPosition());
        contentValues.put(RUN_NAME, bgTrapObj.getRun_name());
        contentValues.put(PERSON_ID, bgTrapObj.getPerson_id());
        contentValues.put(ADDRESS_ID, bgTrapObj.getAddress_id());
        contentValues.put(BG_COORDINATES, bgTrapObj.getCoordinates());
        contentValues.put(EXIST_IN_REMOTE_SERVER, bgTrapObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_BG_TRAP, null, contentValues);
        // close connection
        sqLiteDatabase.close();
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

    public void insertDataOvTrap(OvTrapModel ovTrapObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OV_TRAP_ID, ovTrapObj.getOv_trap_id());
        contentValues.put(OV_TRAP_STATUS, ovTrapObj.getTrap_status());
        contentValues.put(OV_POSITION, ovTrapObj.getPosition());
        contentValues.put(RUN_NAME, ovTrapObj.getRun_name());
        contentValues.put(PERSON_ID, ovTrapObj.getPerson_id());
        contentValues.put(ADDRESS_ID, ovTrapObj.getAddress_id());
        contentValues.put(OV_COORDINATES, ovTrapObj.getCoordinates());
        contentValues.put(EXIST_IN_REMOTE_SERVER, ovTrapObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_OV_TRAP, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }
/*
    public void insertDataOvCollection(OvCollectionModel ovCollectionObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLECTION_ID, ovCollectionObj.getCollection_id());
        contentValues.put(COLLECTION_DATE, ovCollectionObj.getDate());
        contentValues.put(COLLECTION_TIME, ovCollectionObj.getTime());
        contentValues.put(COLLECTION_STATUS, ovCollectionObj.getCollection_status());
        contentValues.put(TRAP_CONDITION, ovCollectionObj.getTrap_condition());
        contentValues.put(OV_TRAP_ID, ovCollectionObj.getOv_trap_id());
        // save to table
        sqLiteDatabase.insert(TABLE_OV_COLLECTION, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }
*/
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
                mrc.setPerson_id(cursor.getInt(3));
                mrc.setAddress_id(cursor.getInt(4));
                mrc.setExist_in_remote_server(cursor.getString(5));
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
                mrc.setPerson_id(cursor.getInt(3));
                mrc.setAddress_id(cursor.getInt(4));
                mrc.setExist_in_remote_server(cursor.getString(5));
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
                mrc.setPerson_id(cursor.getInt(3));
                mrc.setAddress_id(cursor.getInt(4));
                mrc.setCoordinates(cursor.getString(5));
                mrc.setExist_in_remote_server(cursor.getString(6));
                mrcs.add(mrc);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcs;
    }

    public int updateSingleMrc(MrcModel mrcInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_STATUS, mrcInstance.getMrc_status());
        contentValues.put(RUN_NAME, mrcInstance.getRun_name());
        contentValues.put(MRC_COORDINATES, mrcInstance.getCoordinates());
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

    public List<MrcPersonAddressModel> getSingleMrcPersonAddress(String selected_id) {
        List<MrcPersonAddressModel> mrcPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.*,b.*,c.* from " + TABLE_MRC + " a,"+TABLE_PERSON + " b,"+
                TABLE_ADDRESS + " c" +" where a.identifier=" + "\'" + selected_id + "\'" +
                " and a.person_id = b.person_id and a.address_id = c.address_id"+";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcPersonAddressModel mrcPersonAddress = new MrcPersonAddressModel();
                mrcPersonAddress.setIdentifier(cursor.getString(0));
                mrcPersonAddress.setMrc_status(cursor.getString(1));
                mrcPersonAddress.setRun_name(cursor.getString(2));
                mrcPersonAddress.setPerson_id(cursor.getInt(3));
                mrcPersonAddress.setAddress_id(cursor.getInt(4));
                mrcPersonAddress.setCoordinates(cursor.getString(5));
                mrcPersonAddress.setExist_in_remote_server(cursor.getString(6));
                mrcPersonAddress.setPerson_id_dup(cursor.getInt(7));
                mrcPersonAddress.setPerson_name(cursor.getString(8));
                mrcPersonAddress.setPhone(cursor.getInt(9));
                mrcPersonAddress.setExist_in_remote_server_person_dup(cursor.getString(10));
                mrcPersonAddress.setAddress_id_dup(cursor.getInt(11));
                mrcPersonAddress.setAddress_line1(cursor.getString(12));
                mrcPersonAddress.setAddress_line2(cursor.getString(13));
                mrcPersonAddress.setLocation_description(cursor.getString(14));
                mrcPersonAddress.setExist_in_remote_server_address_dup(cursor.getString(15));
                mrcPersonsAddresses.add(mrcPersonAddress);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcPersonsAddresses;
    }

    public int updateSingleMrcRelease(MrcReleaseModel mrcReleaseInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RELEASE_STATUS, mrcReleaseInstance.getRelease_status());
        int status = sqLiteDatabase.update(TABLE_MRC_RELEASE, contentValues,
                "release_id=?", new String[]{String.valueOf(mrcReleaseInstance.getRelease_id())});
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
                bgTrap.setPerson_id(cursor.getInt(4));
                bgTrap.setAddress_id(cursor.getInt(5));
                bgTrap.setExist_in_remote_server(cursor.getString(6));
                bgTraps.add(bgTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgTraps;
    }

    public List<BgPersonAddressModel> getSingleBgTrap(String selected_id) {
        List<BgPersonAddressModel> bgPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.*,b.*,c.* from " + TABLE_BG_TRAP + " a,"+TABLE_PERSON + " b,"+
                TABLE_ADDRESS + " c" +" where a.bg_trap_id=" + "\'" + selected_id + "\'" +
                " and a.person_id = b.person_id and a.address_id = c.address_id"+";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BgPersonAddressModel bgPersonAddress = new BgPersonAddressModel();
                bgPersonAddress.setBg_trap_id(cursor.getString(0));
                bgPersonAddress.setTrap_status(cursor.getString(1));
                bgPersonAddress.setPosition(cursor.getString(2));
                bgPersonAddress.setRun_name(cursor.getString(3));
                bgPersonAddress.setPerson_id(cursor.getInt(4));
                bgPersonAddress.setAddress_id(cursor.getInt(5));
                bgPersonAddress.setCoordinates(cursor.getString(6));
                bgPersonAddress.setExist_in_remote_server(cursor.getString(7));
                bgPersonAddress.setPerson_id_dup(cursor.getInt(8));
                bgPersonAddress.setPerson_name(cursor.getString(9));
                bgPersonAddress.setPhone(cursor.getInt(10));
                bgPersonAddress.setExist_in_remote_server_person_dup(cursor.getString(11));
                bgPersonAddress.setAddress_id_dup(cursor.getInt(12));
                bgPersonAddress.setAddress_line1(cursor.getString(13));
                bgPersonAddress.setAddress_line2(cursor.getString(14));
                bgPersonAddress.setLocation_description(cursor.getString(15));
                bgPersonAddress.setExist_in_remote_server_address_dup(cursor.getString(16));
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
                bgTrap.setPerson_id(cursor.getInt(4));
                bgTrap.setAddress_id(cursor.getInt(5));
                bgTrap.setCoordinates(cursor.getString(6));
                bgTrap.setExist_in_remote_server(cursor.getString(7));
                bgTraps.add(bgTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return bgTraps;
    }
    public int updateSingleBgTrap(BgTrapModel bgTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_TRAP_STATUS, bgTrapInstance.getTrap_status());
        contentValues.put(BG_POSITION, bgTrapInstance.getPosition());
        contentValues.put(BG_COORDINATES, bgTrapInstance.getCoordinates());
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
        contentValues.put(COLLECTION_STATUS, bgCollectionInstance.getCollection_status());
        //contentValues.put(TRAP_CONDITION, bgCollectionInstance.getTrap_condition());
        int status = sqLiteDatabase.update(TABLE_BG_COLLECTION, contentValues,
                "collection_id=?", new String[]{String.valueOf(bgCollectionInstance.getCollection_id())});
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
                ovTrap.setPerson_id(cursor.getInt(4));
                ovTrap.setAddress_id(cursor.getInt(5));
                ovTrap.setExist_in_remote_server(cursor.getString(6));
                ovTraps.add(ovTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return ovTraps;
    }

    public List<OvPersonAddressModel> getSingleOvTrap(String selected_id) {
        List<OvPersonAddressModel> ovPersonsAddresses = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select a.*,b.*,c.* from " + TABLE_OV_TRAP + " a,"+TABLE_PERSON + " b,"+
                TABLE_ADDRESS + " c" +" where a.ov_trap_id=" + "\'" + selected_id + "\'" +
                " and a.person_id = b.person_id and a.address_id = c.address_id"+";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvPersonAddressModel ovPersonAddress = new OvPersonAddressModel();
                ovPersonAddress.setOv_trap_id(cursor.getString(0));
                ovPersonAddress.setTrap_status(cursor.getString(1));
                ovPersonAddress.setPosition(cursor.getString(2));
                ovPersonAddress.setRun_name(cursor.getString(3));
                ovPersonAddress.setPerson_id(cursor.getInt(4));
                ovPersonAddress.setAddress_id(cursor.getInt(5));
                ovPersonAddress.setCoordinates(cursor.getString(6));
                ovPersonAddress.setExist_in_remote_server(cursor.getString(7));
                ovPersonAddress.setPerson_id_dup(cursor.getInt(8));
                ovPersonAddress.setPerson_name(cursor.getString(9));
                ovPersonAddress.setPhone(cursor.getInt(10));
                ovPersonAddress.setExist_in_remote_server_person_dup(cursor.getString(11));
                ovPersonAddress.setAddress_id_dup(cursor.getInt(12));
                ovPersonAddress.setAddress_line1(cursor.getString(13));
                ovPersonAddress.setAddress_line2(cursor.getString(14));
                ovPersonAddress.setLocation_description(cursor.getString(15));
                ovPersonAddress.setExist_in_remote_server_address_dup(cursor.getString(16));
                ovPersonsAddresses.add(ovPersonAddress);
            } while (cursor.moveToNext());
        }
        db.close();
        return ovPersonsAddresses;
    }

    public List<OvTrapModel> getSingleOvTrap(String selected_run,String field_type) {
        List<OvTrapModel> ovTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_TRAP + " where run_name=" + "\'" + selected_run + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                OvTrapModel ovTrap = new OvTrapModel();
                ovTrap.setOv_trap_id(cursor.getString(0));
                ovTrap.setTrap_status(cursor.getString(1));
                ovTrap.setPosition(cursor.getString(2));
                ovTrap.setRun_name(cursor.getString(3));
                ovTrap.setPerson_id(cursor.getInt(4));
                ovTrap.setAddress_id(cursor.getInt(5));
                ovTrap.setCoordinates(cursor.getString(6));
                ovTrap.setExist_in_remote_server(cursor.getString(7));
                ovTraps.add(ovTrap);
            } while (cursor.moveToNext());
        }
        db.close();
        return ovTraps;
    }

    public int updateSingleOvTrap(OvTrapModel ovTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OV_TRAP_STATUS, ovTrapInstance.getTrap_status());
        contentValues.put(OV_POSITION, ovTrapInstance.getPosition());
        contentValues.put(OV_COORDINATES, ovTrapInstance.getCoordinates());
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
        contentValues.put(SERVICE_STATUS,mrcReleaseObj.release_status);
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

