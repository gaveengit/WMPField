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
    private static final int VERSION = 2;
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

    private static final String TABLE_OV_COLLECTION = "ov_collection";


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

        String TABLE_CREATE_QUERY_MRC_RELEASE = "CREATE TABLE " + TABLE_MRC_RELEASE + " " + "(" + RELEASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RELEASE_DATE
                + " TEXT," + RELEASE_TIME + " TEXT," + RELEASE_STATUS + " TEXT," + IDENTIFIER + " TEXT," + "foreign key (identifier) references mrc(identifier)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_MRC_RELEASE);

        String TABLE_CREATE_QUERY_BG_TRAP = "CREATE TABLE " + TABLE_BG_TRAP + " " + "(" + BG_TRAP_ID + " TEXT," + BG_TRAP_STATUS
                + " TEXT," + BG_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_ID + " INTEGER," + ADDRESS_ID + " INTEGER," +  BG_COORDINATES + " TEXT,"+ EXIST_IN_REMOTE_SERVER + " TEXT," + "primary key " + "(" + BG_TRAP_ID + ")," + "foreign key (person_id) references person(person_id)," + "foreign key (address_id) references address(address_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_TRAP);

        String TABLE_CREATE_QUERY_BG_COLLECTION = "CREATE TABLE " + TABLE_BG_COLLECTION + " " + "(" + COLLECTION_ID + " TEXT," + COLLECTION_DATE
                + " TEXT," + COLLECTION_TIME + " TEXT," + COLLECTION_STATUS + " TEXT," + TRAP_CONDITION + " TEXT," + BG_TRAP_ID + " TEXT," + "primary key " + "(" + COLLECTION_ID + ")," + "foreign key (bg_trap_id) references bg_trap(bg_trap_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_BG_COLLECTION);

        String TABLE_CREATE_QUERY_OV_TRAP = "CREATE TABLE " + TABLE_OV_TRAP + " " + "(" + OV_TRAP_ID + " TEXT," + OV_TRAP_STATUS
                + " TEXT," + OV_POSITION + " TEXT," + RUN_NAME + " TEXT," + PERSON_ID + " INTEGER," + ADDRESS_ID + " INTEGER," + OV_COORDINATES + " TEXT,"+EXIST_IN_REMOTE_SERVER + " TEXT," + "primary key " + "(" + OV_TRAP_ID + ")," + "foreign key (person_id) references person(person_id)," + "foreign key (address_id) references address(address_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_OV_TRAP);

        String TABLE_CREATE_QUERY_OV_COLLECTION = "CREATE TABLE " + TABLE_OV_COLLECTION + " " + "(" + COLLECTION_ID + " TEXT," + COLLECTION_DATE
                + " TEXT," + COLLECTION_TIME + " TEXT," + COLLECTION_STATUS + " TEXT," + TRAP_CONDITION + " TEXT," + OV_TRAP_ID + " TEXT," + "primary key " + "(" + COLLECTION_ID + ")," + "foreign key (ov_trap_id) references ov_trap(ov_trap_id)" + ");";
        sqLiteDatabase.execSQL(TABLE_CREATE_QUERY_OV_COLLECTION);
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
        String DROP_TABLE_QUERY_TABLE_OV_COLLECTION = "DROP TABLE IF EXISTS " + TABLE_OV_COLLECTION;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_TABLE_OV_COLLECTION);
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

    public void insertDataMrc_Release(MrcReleaseModel mrcReleaseObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RELEASE_ID, mrcReleaseObj.getRelease_id());
        contentValues.put(RELEASE_DATE, mrcReleaseObj.getDate());
        contentValues.put(RELEASE_TIME, mrcReleaseObj.getTime());
        contentValues.put(RELEASE_STATUS, mrcReleaseObj.getRelease_status());
        contentValues.put(IDENTIFIER, mrcReleaseObj.getMrc_id());
        // save to table
        sqLiteDatabase.insert(TABLE_MRC_RELEASE, null, contentValues);
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
        contentValues.put(BG_COORDINATES, bgTrapObj.getAddress_id());
        contentValues.put(EXIST_IN_REMOTE_SERVER, bgTrapObj.getExist_in_remote_server());
        // save to table
        sqLiteDatabase.insert(TABLE_BG_TRAP, null, contentValues);
        // close connection
        sqLiteDatabase.close();
    }

    public void insertDataBgCollection(BgCollectionModel bgCollectionObj) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLECTION_ID, bgCollectionObj.getCollection_id());
        contentValues.put(COLLECTION_DATE, bgCollectionObj.getDate());
        contentValues.put(COLLECTION_TIME, bgCollectionObj.getTime());
        contentValues.put(COLLECTION_STATUS, bgCollectionObj.getCollection_status());
        contentValues.put(TRAP_CONDITION, bgCollectionObj.getTrap_condition());
        contentValues.put(BG_TRAP_ID, bgCollectionObj.getBg_trap_id());
        // save to table
        sqLiteDatabase.insert(TABLE_BG_COLLECTION, null, contentValues);
        // close connection
        sqLiteDatabase.close();
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

    public int updateSingleMrc(MrcModel mrcInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MRC_STATUS, mrcInstance.getMrc_status());
        contentValues.put(RUN_NAME, mrcInstance.getRun_name());
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
                mrcRelease.setRelease_id(cursor.getInt(0));
                mrcRelease.setDate(cursor.getString(1));
                mrcRelease.setTime(cursor.getString(2));
                mrcRelease.setRelease_status(cursor.getString(3));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
    }

    public List<MrcReleaseModel> getSingleMrcRelease(Integer selected_id) {
        List<MrcReleaseModel> mrcReleases = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_MRC_RELEASE + " where release_id=" + "\'" + selected_id + "\'" + ";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MrcReleaseModel mrcRelease = new MrcReleaseModel();
                mrcRelease.setRelease_id(cursor.getInt(0));
                mrcRelease.setDate(cursor.getString(1));
                mrcRelease.setTime(cursor.getString(2));
                mrcRelease.setRelease_status(cursor.getString(3));
                mrcReleases.add(mrcRelease);
            } while (cursor.moveToNext());
        }
        db.close();
        return mrcReleases;
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

    public List<BgTrapModel> getSingleBgTrap(String selected_id) {
        List<BgTrapModel> bgTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_BG_TRAP + " where bg_trap_id=" + "\'" + selected_id + "\'" + ";";
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

    public int updateSingleBgTrap(BgTrapModel bgTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BG_TRAP_STATUS, bgTrapInstance.getTrap_status());
        contentValues.put(BG_POSITION, bgTrapInstance.getPosition());
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
                bgCollection.setCollection_id(cursor.getString(0));
                bgCollection.setDate(cursor.getString(1));
                bgCollection.setTime(cursor.getString(2));
                bgCollection.setCollection_status(cursor.getString(3));
                bgCollection.setTrap_condition(cursor.getString(4));
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
                bgCollection.setCollection_id(cursor.getString(0));
                bgCollection.setDate(cursor.getString(1));
                bgCollection.setTime(cursor.getString(2));
                bgCollection.setCollection_status(cursor.getString(3));
                bgCollection.setTrap_condition(cursor.getString(4));
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
        contentValues.put(TRAP_CONDITION, bgCollectionInstance.getTrap_condition());
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

    public List<OvTrapModel> getSingleOvTrap(String selected_id) {
        List<OvTrapModel> ovTraps = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_OV_TRAP + " where ov_trap_id=" + "\'" + selected_id + "\'" + ";";
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

    public int updateSingleOvTrap(OvTrapModel ovTrapInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OV_TRAP_STATUS, ovTrapInstance.getTrap_status());
        contentValues.put(OV_POSITION, ovTrapInstance.getPosition());
        int status = sqLiteDatabase.update(TABLE_OV_TRAP, contentValues,
                "ov_trap_id=?", new String[]{String.valueOf(ovTrapInstance.getOv_trap_id())});
        return status;
    }

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
    public int updateSingleOvCollection(OvCollectionModel ovCollectionInstance) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLECTION_STATUS, ovCollectionInstance.getCollection_status());
        contentValues.put(TRAP_CONDITION, ovCollectionInstance.getTrap_condition());
        int status = sqLiteDatabase.update(TABLE_OV_COLLECTION, contentValues,
                "collection_id=?", new String[]{String.valueOf(ovCollectionInstance.getCollection_id())});
        return status;
    }
}

