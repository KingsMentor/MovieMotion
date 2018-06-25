package xyz.belvi.motion.data.realmObject

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.belvi.motion.constants.IMG_PATH
import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * Movie is a @RealmObject that saves movies retrieved from api.
 * it's identified by a unique.
 * This class also implements @Parcelable so it can be passed as an extra via Intent or a bundle.
 *
 */
open class Movie(
        @PrimaryKey
        var id: Int = 0,
        var voteCount: Int = 0,
        var video: Boolean = false,
        var vote_average: Float = 0f,
        var title: String = "",
        var popularity: Float = 0f,
        var poster_path: String = "",
        var original_language: String = "",
        var original_title: String = "",
        var backdrop_path: String? = "",
        var adult: Boolean = false,
        var overview: String = "",
        var release_date: String = ""

) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor() : this(id = 0)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(voteCount)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeFloat(vote_average)
        parcel.writeString(title)
        parcel.writeFloat(popularity)
        parcel.writeString(poster_path)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(backdrop_path)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }


}
