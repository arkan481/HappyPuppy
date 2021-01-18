package id.wuff.happypuppy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {
    private int id;
    private int petId;
    private String date;

    public Feed() {

    }

    protected Feed(Parcel in) {
        id = in.readInt();
        petId = in.readInt();
        date = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(petId);
        parcel.writeString(date);
    }
}
