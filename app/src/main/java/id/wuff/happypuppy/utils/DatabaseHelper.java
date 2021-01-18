package id.wuff.happypuppy.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.wuff.happypuppy.model.Feed;
import id.wuff.happypuppy.model.Pet;
import id.wuff.happypuppy.model.Play;

/**
 * Class to operate any SQLite CRUD operation
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "HappyPuppyDB";
    private static int db_Version = 1;

    private static final String COL_PET = "pet";
    private static final String COL_FEED = "feed";
    private static final String COL_PLAY = "play";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, db_Version);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+COL_PET+"" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR," +
                "birthdate VARCHAR," +
                "gender VARCHAR," +
                "photo VARCHAR," +
                "category VARCHAR," +
                "age INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+COL_FEED+"" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "date VARCHAR NOT NULL," +
                "petId INTEGER NOT NULL," +
                "FOREIGN KEY (petId) REFERENCES " + COL_PET + "(id)" +
                "ON DELETE CASCADE" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE "+COL_PLAY+"" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "date VARCHAR NOT NULL," +
                "petId INTEGER NOT NULL," +
                "FOREIGN KEY (petId) REFERENCES " + COL_PET + "(id)" +
                "ON DELETE CASCADE" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+COL_PLAY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+COL_PET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+COL_FEED);
        onCreate(sqLiteDatabase);
    }

    public void insertPet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + COL_PET + " (name, birthdate, gender, photo, category, age) VALUES (?, ?, ? ,? ,? ,?)", new String[] {
                pet.getName(),
                pet.getBirthdate(),
                pet.getGender(),
                pet.getPhoto(),
                pet.getCategory(),
                String.valueOf(pet.getAge())
        });
    }

    public void insertFeed(Feed feed) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + COL_FEED + " (date, petId) VALUES (?, ?)", new String[] {
                feed.getDate(),
                String.valueOf(feed.getPetId())
        });
    }

    public ArrayList<Feed> getFeeds(int petId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COL_FEED + " WHERE petId = (?) ORDER BY id DESC LIMIT 3", new String[]{
                String.valueOf(petId)
        });

        ArrayList<Feed> feeds = new ArrayList<>();

        while (res.moveToNext()) {
            Feed feed = new Feed();
            feed.setId(res.getInt(0));
            feed.setDate(res.getString(1));
            feed.setPetId(res.getInt(2));

            feeds.add(feed);
        }

        return feeds;
    }

    public void insertPlay(Play play) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + COL_PLAY + " (date, petId) VALUES (?, ?)", new String[]{
                play.getDate(),
                String.valueOf(play.getPetId())
        });
    }

    public ArrayList<Play> getPlays(int petId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COL_PLAY + " WHERE petId = (?) ORDER BY id DESC LIMIT 3", new String[]{
                String.valueOf(petId)
        });

        ArrayList<Play> plays = new ArrayList<>();

        while (res.moveToNext()) {
            Play play = new Play();
            play.setId(res.getInt(0));
            play.setDate(res.getString(1));
            play.setPetId(res.getInt(2));

            plays.add(play);
        }

        return plays;
    }

    public void deletePet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + COL_PET + " WHERE id = (?)", new String[] {
                String.valueOf(pet.getId())
        });
    }

    public void updatePet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + COL_PET + " SET name = ?, birthdate = ?, gender = ?, photo = ?, category = ?, age = ? WHERE id = ?", new String[] {
                pet.getName(),
                pet.getBirthdate(),
                pet.getGender(),
                pet.getPhoto(),
                pet.getCategory(),
                String.valueOf(pet.getAge()),
                String.valueOf(pet.getId())
        });
    }

    public ArrayList<Pet> fetchAllPet() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COL_PET, null);
        ArrayList<Pet> pets = new ArrayList<>();
        while (res.moveToNext()) {
            Pet pet = new Pet();
            pet.setId(res.getInt(0));
            pet.setName(res.getString(1));
            pet.setBirthdate(res.getString(2));
            pet.setGender(res.getString(3));
            pet.setPhoto(res.getString(4));
            pet.setCategory(res.getString(5));
            pet.setAge(res.getInt(6));

            pets.add(pet);
        }

        return pets;
    }

    public ArrayList<Pet> fetchCats() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COL_PET + " WHERE category='Cat'", null);
        ArrayList<Pet> pets = new ArrayList<>();
        while (res.moveToNext()) {
            Pet pet = new Pet();
            pet.setId(res.getInt(0));
            pet.setName(res.getString(1));
            pet.setBirthdate(res.getString(2));
            pet.setGender(res.getString(3));
            pet.setPhoto(res.getString(4));
            pet.setCategory(res.getString(5));
            pet.setAge(res.getInt(6));

            pets.add(pet);
        }

        return pets;
    }

    public ArrayList<Pet> fetchDogs() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + COL_PET + " WHERE category='Dog'", null);
        ArrayList<Pet> pets = new ArrayList<>();
        while (res.moveToNext()) {
            Pet pet = new Pet();
            pet.setId(res.getInt(0));
            pet.setName(res.getString(1));
            pet.setBirthdate(res.getString(2));
            pet.setGender(res.getString(3));
            pet.setPhoto(res.getString(4));
            pet.setCategory(res.getString(5));
            pet.setAge(res.getInt(6));

            pets.add(pet);
        }
        return pets;
    }

}
