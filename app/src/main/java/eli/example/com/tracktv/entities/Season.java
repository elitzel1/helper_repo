package eli.example.com.tracktv.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrador on 11/2/15.
 */
public class Season implements Parcelable {

    int number;
    int idTrakt;
    String imgThumb;
    String imgPoster;
    int rating;

    public Season(int number, int idTrakt, String imgThumb,String imgPoster,int rating) {
        this.number = number;
        this.idTrakt = idTrakt;
        this.imgThumb = imgThumb;
        this.imgPoster = imgPoster;
        this.rating = rating;
    }

    public String getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(String imgPoster) {
        this.imgPoster = imgPoster;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdTrakt() {
        return idTrakt;
    }

    public void setIdTrakt(int idTrakt) {
        this.idTrakt = idTrakt;
    }

    public String getImgThumb() {
        return imgThumb;
    }

    public void setImgThumb(String imgThumb) {
        this.imgThumb = imgThumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(number);
        parcel.writeString(imgThumb);
        parcel.writeString(imgPoster);
        parcel.writeInt(rating);
    }

    public static final Parcelable.Creator<Season> CREATOR = new Parcelable.Creator<Season>() {
        public Season createFromParcel(Parcel parcel) {
            return new Season(parcel);
        }

        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    public Season(Parcel parcel){
        number         = parcel.readInt();
        imgThumb        =  parcel.readString();
        imgPoster      = parcel.readString();
        rating =parcel.readInt();
    }
}
